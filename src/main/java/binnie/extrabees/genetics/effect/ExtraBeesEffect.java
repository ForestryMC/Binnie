package binnie.extrabees.genetics.effect;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.apiculture.BeeManager;
import forestry.api.apiculture.IAlleleBeeEffect;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.apiculture.IBeekeepingLogic;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IEffectData;
import forestry.core.render.ParticleRender;

import binnie.extrabees.ExtraBees;
import binnie.extrabees.genetics.ExtraBeesFlowers;
import binnie.extrabees.utils.Utils;

public enum ExtraBeesEffect implements IAlleleBeeEffect {
	ECTOPLASM,
	ACID,
	SPAWN_ZOMBIE,
	SPAWN_SKELETON,
	SPAWN_CREEPER,
	LIGHTNING,
	RADIOACTIVE,
	METEOR,
	HUNGER,
	FOOD,
	BLINDNESS,
	CONFUSION,
	FIREWORKS,
	FESTIVAL,
	BIRTHDAY,
	TELEPORT,
	GRAVITY,
	THIEF,
	WITHER,
	WATER,
	SLOW,
	BONEMEAL_SAPLING,
	BONEMEAL_FRUIT,
	BONEMEAL_MUSHROOM,
	POWER;

	private static final List<Birthday> birthdays = new ArrayList<>();

	static {
		birthdays.add(new Birthday(3, 10, "Binnie"));
	}

	private String fx;
	private boolean combinable;
	private boolean dominant;
	private int id;
	private String uid;

	ExtraBeesEffect() {
		this.fx = "";
		this.uid = this.toString().toLowerCase();
		this.combinable = false;
		this.dominant = true;
	}

	public static void doInit() {
		ExtraBeesEffect.BLINDNESS.setFX("blindness");
		ExtraBeesEffect.FOOD.setFX("food");
		ExtraBeesEffect.GRAVITY.setFX("gravity");
		ExtraBeesEffect.THIEF.setFX("gravity");
		ExtraBeesEffect.TELEPORT.setFX("gravity");
		ExtraBeesEffect.LIGHTNING.setFX("lightning");
		ExtraBeesEffect.METEOR.setFX("meteor");
		ExtraBeesEffect.RADIOACTIVE.setFX("radioactive");
		ExtraBeesEffect.WATER.setFX("water");
		ExtraBeesEffect.WITHER.setFX("wither");
		for (final ExtraBeesEffect effect : values()) {
			effect.register();
		}
	}

	public static void doAcid(final World world, final BlockPos pos) {
		final IBlockState blockState = world.getBlockState(pos);
		final Block block = blockState.getBlock();
		if (block == Blocks.COBBLESTONE || block == Blocks.STONE) {
			world.setBlockState(pos, Blocks.GRAVEL.getDefaultState());
		} else if (block == Blocks.DIRT | block == Blocks.GRASS) {
			world.setBlockState(pos, Blocks.SAND.getDefaultState());
		}
	}

	public static int wearsItems(final EntityPlayer player) {
		return BeeManager.armorApiaristHelper.wearsItems(player, "", false);
	}

	public void register() {
		AlleleManager.alleleRegistry.registerAllele(this);
	}

	@Override
	public boolean isCombinable() {
		return this.combinable;
	}

	@Override
	public IEffectData validateStorage(final IEffectData storedData) {
		return storedData;
	}

	@Override
	public String getName() {
		return I18n.translateToLocal("effect." + this.name().toLowerCase() + ".name");
	}

	@Override
	public boolean isDominant() {
		return this.dominant;
	}

	public void spawnMob(final World world, final BlockPos pos, final ResourceLocation name) {
		if (this.anyPlayerInRange(world, pos, 16)) {
			final double var1 = pos.getX() + world.rand.nextFloat();
			final double var2 = pos.getY() + world.rand.nextFloat();
			final double var3 = pos.getZ() + world.rand.nextFloat();
			world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, var1, var2, var3, 0.0, 0.0, 0.0);
			world.spawnParticle(EnumParticleTypes.FLAME, var1, var2, var3, 0.0, 0.0, 0.0);
			final EntityLiving entity = (EntityLiving) EntityList.createEntityByIDFromName(name, world);
			if (entity != null) {
				final int nearbyEntityCount = world.getEntitiesWithinAABB(entity.getClass(), new AxisAlignedBB(pos, pos.add(1, 1, 1)).expand(8.0, 4.0, 8.0)).size();
				if (nearbyEntityCount < 6) {
					final double var6 = pos.getX() + (world.rand.nextDouble() - world.rand.nextDouble()) * 4.0;
					final double var7 = pos.getY() + world.rand.nextInt(3) - 1;
					final double var8 = pos.getZ() + (world.rand.nextDouble() - world.rand.nextDouble()) * 4.0;
					entity.setLocationAndAngles(var6, var7, var8, world.rand.nextFloat() * 360.0f, 0.0f);
					if (entity.getCanSpawnHere()) {
						world.spawnEntity(entity);
						world.playEvent(2004, pos, 0);//playSFX
						entity.spawnExplosionParticle();
					}
				}
			}
		}
	}

	private boolean anyPlayerInRange(final World world, final BlockPos pos, final int distance) {
		return world.getClosestPlayer(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, distance, false) != null;
	}

	@Override
	public String getUID() {
		return "extrabees.effect." + this.uid;
	}

	@Override
	public IEffectData doEffect(final IBeeGenome genome, final IEffectData storedData, final IBeeHousing housing) {
		final World world = housing.getWorldObj();
		final int xHouse = housing.getCoordinates().getX();
		final int yHouse = housing.getCoordinates().getY();
		final int zHouse = housing.getCoordinates().getZ();
		final Vec3i area = this.getModifiedArea(genome, housing);
		final int xd = 1 + area.getX() / 2;
		final int yd = 1 + area.getY() / 2;
		final int zd = 1 + area.getZ() / 2;
		final int x1 = xHouse - xd + world.rand.nextInt(2 * xd + 1);
		int y1 = yHouse - yd + world.rand.nextInt(2 * yd + 1);
		final int z1 = zHouse - zd + world.rand.nextInt(2 * zd + 1);
		final BlockPos pos = new BlockPos(x1, y1, z1);
		switch (this) {
			case ECTOPLASM: {
				if (world.rand.nextInt(100) < 4) {
					if (world.isAirBlock(pos) && (world.isBlockNormalCube(pos.down(), false) || world.getBlockState(pos.down()).getBlock() == ExtraBees.ectoplasm)) {
						world.setBlockState(pos, ExtraBees.ectoplasm.getDefaultState());
					}
					return storedData;
				}
				break;
			}
			case ACID: {
				if (world.rand.nextInt(100) < 6) {
					doAcid(world, pos);
					break;
				}
				break;
			}
			case SPAWN_ZOMBIE: {
				if (world.rand.nextInt(200) < 2) {
					this.spawnMob(world, pos, new ResourceLocation("zombie"));
					break;
				}
				break;
			}
			case SPAWN_SKELETON: {
				if (world.rand.nextInt(200) < 2) {
					this.spawnMob(world, pos, new ResourceLocation("skeleton"));
					break;
				}
				break;
			}
			case SPAWN_CREEPER: {
				if (world.rand.nextInt(200) < 2) {
					this.spawnMob(world, pos, new ResourceLocation("creeper"));
					break;
				}
				break;
			}
			case LIGHTNING: {
				if (world.rand.nextInt(100) < 1 && world.canBlockSeeSky(pos) && world instanceof WorldServer) {
					world.addWeatherEffect(new EntityBeeLightning(world, x1, y1, z1));
					break;
				}
				break;
			}
			case METEOR: {
				if (world.rand.nextInt(100) < 1 && world.canBlockSeeSky(pos)) {
					world.spawnEntity(new EntitySmallFireball(world, x1, y1 + 64, z1, 0.0, -0.6, 0.0));
					break;
				}
				break;
			}
			case RADIOACTIVE: {
				for (final EntityLivingBase entity : this.getEntities(EntityLivingBase.class, genome, housing)) {
					int damage = 4;
					if (entity instanceof EntityPlayer) {
						final int count = wearsItems((EntityPlayer) entity);
						if (count > 3) {
							continue;
						}
						if (count > 2) {
							damage = 1;
						} else if (count > 1) {
							damage = 2;
						} else if (count > 0) {
							damage = 3;
						}
					}
					entity.attackEntityFrom(DamageSource.GENERIC, damage);
				}
				break;
			}
			case FOOD: {
				for (final EntityLivingBase entity : this.getEntities(EntityLivingBase.class, genome, housing)) {
					if (entity instanceof EntityPlayer) {
						final EntityPlayer player = (EntityPlayer) entity;
						player.getFoodStats().addStats(2, 0.2f);
					}
				}
				break;
			}
			case HUNGER: {
				for (final EntityLivingBase entity : this.getEntities(EntityLivingBase.class, genome, housing)) {
					if (entity instanceof EntityPlayer) {
						final EntityPlayer player = (EntityPlayer) entity;
						if (world.rand.nextInt(4) < wearsItems(player)) {
							continue;
						}
						player.getFoodStats().addExhaustion(4.0f);
						player.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 100));
					}
				}
				break;
			}
			case BLINDNESS: {
				for (final EntityLivingBase entity : this.getEntities(EntityLivingBase.class, genome, housing)) {
					if (entity instanceof EntityPlayer) {
						final EntityPlayer player = (EntityPlayer) entity;
						if (world.rand.nextInt(4) < wearsItems(player)) {
							continue;
						}
						player.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 200));
					}
				}
				break;
			}
			case SLOW: {
				for (final EntityLivingBase entity : this.getEntities(EntityLivingBase.class, genome, housing)) {
					if (entity instanceof EntityPlayer) {
						final EntityPlayer player = (EntityPlayer) entity;
						if (world.rand.nextInt(4) < wearsItems(player)) {
							continue;
						}
						player.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 200));
					}
				}
				break;
			}
			case CONFUSION: {
				for (final EntityLivingBase entity : this.getEntities(EntityLivingBase.class, genome, housing)) {
					if (entity instanceof EntityPlayer) {
						final EntityPlayer player = (EntityPlayer) entity;
						if (world.rand.nextInt(4) < wearsItems(player)) {
							continue;
						}
						player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 200));
					}
				}
				break;
			}
			case BIRTHDAY:
			case FESTIVAL:
			case FIREWORKS: {
				if (world.rand.nextInt((this == ExtraBeesEffect.FIREWORKS) ? 8 : 12) < 1) {
					final FireworkCreator.Firework firework = new FireworkCreator.Firework();
					switch (this) {
						case BIRTHDAY: {
							firework.setShape(FireworkCreator.Shape.Star);
							firework.addColor(16768256);
							for (final Birthday birthday : ExtraBeesEffect.birthdays) {
								if (birthday.isToday()) {
									firework.addColor(16711680);
									firework.addColor(65280);
									firework.addColor(255);
									firework.setTrail();
									break;
								}
							}
						}
						case FIREWORKS: {
							firework.setShape(FireworkCreator.Shape.Ball);
							firework.addColor(genome.getPrimary().getSpriteColour(0));
							firework.addColor(genome.getPrimary().getSpriteColour(0));
							firework.addColor(genome.getPrimary().getSpriteColour(1));
							firework.addColor(genome.getSecondary().getSpriteColour(0));
							firework.addColor(genome.getSecondary().getSpriteColour(0));
							firework.addColor(genome.getPrimary().getSpriteColour(1));
							firework.setTrail();
							break;
						}
					}
					final EntityFireworkRocket var11 = new EntityFireworkRocket(world, x1, y1, z1, firework.getFirework());
					if (world.canBlockSeeSky(pos)) {
						world.spawnEntity(var11);
					}
					break;
				}
				break;
			}
			case GRAVITY: {
				final List<Entity> entities2 = this.getEntities(Entity.class, genome, housing);
				for (final Entity entity2 : entities2) {
					float entityStrength = 1.0f;
					if (entity2 instanceof EntityPlayer) {
						entityStrength *= 100.0f;
					}
					final double dx = x1 - entity2.posX;
					final double dy = y1 - entity2.posY;
					final double dz = z1 - entity2.posZ;
					if (dx * dx + dy * dy + dz * dz < 2.0) {
						return storedData;
					}
					final double strength = 0.5 / (dx * dx + dy * dy + dz * dz) * entityStrength;
					entity2.addVelocity(dx * strength, dy * strength, dz * strength);
				}
				break;
			}
			case THIEF: {
				final List<EntityPlayer> entities3 = this.getEntities(EntityPlayer.class, genome, housing);
				for (final EntityPlayer entity3 : entities3) {
					final double dx = x1 - entity3.posX;
					final double dy = y1 - entity3.posY;
					final double dz = z1 - entity3.posZ;
					if (dx * dx + dy * dy + dz * dz < 2.0) {
						return storedData;
					}
					final double strength = 0.5 / (dx * dx + dy * dy + dz * dz);
					entity3.addVelocity(-dx * strength, -dy * strength, -dz * strength);
				}
				break;
			}
			case TELEPORT: {
				if (world.rand.nextInt(80) > 1) {
					return storedData;
				}
				final List<Entity> entities4 = this.getEntities(Entity.class, genome, housing);
				if (entities4.size() == 0) {
					return storedData;
				}
				final Entity entity4 = entities4.get(world.rand.nextInt(entities4.size()));
				if (!(entity4 instanceof EntityLiving)) {
					return storedData;
				}
				final float jumpDist = 5.0f;
				if (y1 < 4) {
					y1 = 4;
				}
				if (!world.isAirBlock(pos) || !world.isAirBlock(pos.up())) {
					return storedData;
				}
				entity4.setPositionAndUpdate(x1, y1, z1);
				((EntityLiving) entity4).addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 160, 10));
				break;
			}
			case WATER: {
				if (world.rand.nextInt(120) > 1) {
					return storedData;
				}
				IFluidHandler fluidHandler = Utils.getCapability(world, pos, CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, EnumFacing.UP);
				if (fluidHandler != null) {
					fluidHandler.fill(new FluidStack(FluidRegistry.WATER, 100), true);
					break;
				}
				break;
			}
			case BONEMEAL_SAPLING: {
				if (world.rand.nextInt(20) > 1) {
					return storedData;
				}
				if (ExtraBeesFlowers.SAPLING.isAcceptedFlower(world, pos)) {
					ItemDye.applyBonemeal(new ItemStack(Blocks.DIRT, 1), world, pos);
					break;
				}
				break;
			}
			case BONEMEAL_FRUIT: {
				if (world.rand.nextInt(20) > 1) {
					return storedData;
				}
				if (ExtraBeesFlowers.FRUIT.isAcceptedFlower(world, pos)) {
					ItemDye.applyBonemeal(new ItemStack(Blocks.DIRT, 1), world, pos);
					break;
				}
				break;
			}
			case BONEMEAL_MUSHROOM: {
				if (world.rand.nextInt(20) > 1) {
					return storedData;
				}
				if (world.getBlockState(pos).getBlock() == Blocks.BROWN_MUSHROOM || world.getBlockState(pos).getBlock() == Blocks.RED_MUSHROOM) {
					ItemDye.applyBonemeal(new ItemStack(Blocks.DIRT, 1), world, pos);
					break;
				}
				break;
			}
			case POWER: {
				final TileEntity tile2 = world.getTileEntity(pos);
				if (tile2.hasCapability(CapabilityEnergy.ENERGY, EnumFacing.UP)) {
					IEnergyStorage storage = tile2.getCapability(CapabilityEnergy.ENERGY, EnumFacing.UP);
					storage.receiveEnergy(5, false);
				}
				break;
			}
		}
		return storedData;
	}

	protected Vec3i getModifiedArea(final IBeeGenome genome, final IBeeHousing housing) {
		Vec3i territory = genome.getTerritory();
		territory = new Vec3i(
			territory.getX() * (int) (BeeManager.beeRoot.createBeeHousingModifier(housing).getTerritoryModifier(genome, 1.0f) * 3.0f),
			territory.getY() * (int) (BeeManager.beeRoot.createBeeHousingModifier(housing).getTerritoryModifier(genome, 1.0f) * 3.0f),
			territory.getZ() * (int) (BeeManager.beeRoot.createBeeHousingModifier(housing).getTerritoryModifier(genome, 1.0f) * 3.0f)
		);
		if (territory.getX() < 1) {
			territory = new Vec3i(1, territory.getY(), territory.getZ());
		}
		if (territory.getY() < 1) {
			territory = new Vec3i(territory.getX(), 1, territory.getZ());
		}
		if (territory.getZ() < 1) {
			territory = new Vec3i(territory.getX(), territory.getY(), 1);
		}
		return territory;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IEffectData doFX(final IBeeGenome genome, final IEffectData storedData, final IBeeHousing housing) {
		IBeekeepingLogic beekeepingLogic = housing.getBeekeepingLogic();
		List<BlockPos> flowerPositions = beekeepingLogic.getFlowerPositions();
		ParticleRender.addBeeHiveFX(housing, genome, flowerPositions);
		return storedData;
	}

	public String getFX() {
		return this.fx;
	}

	private void setFX(final String string) {
		this.fx = "particles/" + string;
	}

	public <T extends Entity> List<T> getEntities(final Class<T> eClass, final IBeeGenome genome, final IBeeHousing housing) {
		final Vec3i area = genome.getTerritory();
		final int[] offset = {-Math.round(area.getX() / 2), -Math.round(area.getY() / 2), -Math.round(area.getZ() / 2)};
		final int[] min = {housing.getCoordinates().getX() + offset[0], housing.getCoordinates().getY() + offset[1], housing.getCoordinates().getZ() + offset[2]};
		final int[] max = {housing.getCoordinates().getX() + offset[0] + area.getX(), housing.getCoordinates().getY() + offset[1] + area.getY(), housing.getCoordinates().getZ() + offset[2] + area.getZ()};
		final AxisAlignedBB box = new AxisAlignedBB(min[0], min[1], min[2], max[0], max[1], max[2]);
		return housing.getWorldObj().getEntitiesWithinAABB(eClass, box);
	}

	@Override
	public String getUnlocalizedName() {
		return this.getUID();
	}

	public static class Birthday {

		private final int month;
		private final int date;
		private final String name;

		private Birthday(final int month, final int date, final String name) {
			this.month = month;
			this.date = date + 1;
			this.name = name;
		}

		public boolean isToday() {
			return Calendar.getInstance().get(Calendar.DATE) == this.date && Calendar.getInstance().get(Calendar.MONTH) == this.month;
		}

		public String getName() {
			return this.name;
		}
	}
}
