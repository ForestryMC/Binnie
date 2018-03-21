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
	ECTOPLASM{
		@Override
		protected IEffectData doEffect(IBeeGenome genome, IEffectData storedData, IBeeHousing housing, World world, BlockPos position) {
			if (world.rand.nextInt(100) < 4) {
				if (world.isAirBlock(position) && (world.isBlockNormalCube(position.down(), false) || world.getBlockState(position.down()).getBlock() == ExtraBees.ectoplasm)) {
					world.setBlockState(position, ExtraBees.ectoplasm.getDefaultState());
				}
				return storedData;
			}
			return storedData;
		}
	},
	ACID{
		@Override
		protected IEffectData doEffect(IBeeGenome genome, IEffectData storedData, IBeeHousing housing, World world, BlockPos position) {
			if (world.rand.nextInt(100) < 6) {
				doAcid(world, position);
			}
			return storedData;
		}
	},
	SPAWN_ZOMBIE{
		@Override
		protected IEffectData doEffect(IBeeGenome genome, IEffectData storedData, IBeeHousing housing, World world, BlockPos position) {
			if (world.rand.nextInt(200) < 2) {
				this.spawnMob(world, position, new ResourceLocation("zombie"));
			}
			return storedData;
		}
	},
	SPAWN_SKELETON{
		@Override
		protected IEffectData doEffect(IBeeGenome genome, IEffectData storedData, IBeeHousing housing, World world, BlockPos position) {
			if (world.rand.nextInt(200) < 2) {
				this.spawnMob(world, position, new ResourceLocation("skeleton"));
			}
			return storedData;
		}
	},
	SPAWN_CREEPER{
		@Override
		protected IEffectData doEffect(IBeeGenome genome, IEffectData storedData, IBeeHousing housing, World world, BlockPos position) {
			if (world.rand.nextInt(200) < 2) {
				this.spawnMob(world, position, new ResourceLocation("creeper"));
			}
			return storedData;
		}
	},
	LIGHTNING{
		@Override
		protected IEffectData doEffect(IBeeGenome genome, IEffectData storedData, IBeeHousing housing, World world, BlockPos position) {
			if (world.rand.nextInt(100) < 1 && world.canBlockSeeSky(position) && world instanceof WorldServer) {
				world.addWeatherEffect(new EntityBeeLightning(world, position.getX(), position.getY(), position.getZ()));
			}
			return storedData;
		}
	},
	RADIOACTIVE{
		@Override
		protected IEffectData doEffect(IBeeGenome genome, IEffectData storedData, IBeeHousing housing, World world, BlockPos position) {
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
			return storedData;
		}
	},
	METEOR{
		@Override
		protected IEffectData doEffect(IBeeGenome genome, IEffectData storedData, IBeeHousing housing, World world, BlockPos position) {
			if (world.rand.nextInt(100) < 1 && world.canBlockSeeSky(position)) {
				world.spawnEntity(new EntitySmallFireball(world, position.getX(), position.getY() + 64, position.getZ(), 0.0, -0.6, 0.0));
			}
			return storedData;
		}
	},
	HUNGER{
		@Override
		protected IEffectData doEffect(IBeeGenome genome, IEffectData storedData, IBeeHousing housing, World world, BlockPos position) {
			for (EntityPlayer player : this.getEntities(EntityPlayer.class, genome, housing)) {
				if (world.rand.nextInt(4) < wearsItems(player)) {
					continue;
				}
				player.getFoodStats().addExhaustion(4.0f);
				player.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 100));
			}
			return storedData;
		}
	},
	FOOD{
		@Override
		protected IEffectData doEffect(IBeeGenome genome, IEffectData storedData, IBeeHousing housing, World world, BlockPos position) {
			for (EntityPlayer player : this.getEntities(EntityPlayer.class, genome, housing)) {
				player.getFoodStats().addStats(2, 0.2f);
			}
			return storedData;
		}
	},
	BLINDNESS {
		@Override
		protected IEffectData doEffect(IBeeGenome genome, IEffectData storedData, IBeeHousing housing, World world, BlockPos position) {
			for (EntityPlayer player : this.getEntities(EntityPlayer.class, genome, housing)) {
				if (world.rand.nextInt(4) < wearsItems(player)) {
					continue;
				}
				player.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 200));
			}
			return storedData;
		}
	},
	CONFUSION{
		@Override
		protected IEffectData doEffect(IBeeGenome genome, IEffectData storedData, IBeeHousing housing, World world, BlockPos position) {
			for (EntityPlayer player : this.getEntities(EntityPlayer.class, genome, housing)) {
				if (world.rand.nextInt(4) < wearsItems(player)) {
					continue;
				}
				player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 200));
			}
			return storedData;
		}
	},
	FIREWORKS{
		@Override
		protected IEffectData doEffect(IBeeGenome genome, IEffectData storedData, IBeeHousing housing, World world, BlockPos position) {
			if (world.rand.nextInt( 8) < 1) {
				final FireworkCreator.Firework firework = new FireworkCreator.Firework();
				firework.setShape(FireworkCreator.Shape.Ball);
				firework.addColor(genome.getPrimary().getSpriteColour(0));
				firework.addColor(genome.getPrimary().getSpriteColour(0));
				firework.addColor(genome.getPrimary().getSpriteColour(1));
				firework.addColor(genome.getSecondary().getSpriteColour(0));
				firework.addColor(genome.getSecondary().getSpriteColour(0));
				firework.addColor(genome.getPrimary().getSpriteColour(1));
						firework.setTrail();
				final EntityFireworkRocket var11 = new EntityFireworkRocket(world, position.getX(), position.getY(), position.getZ(), firework.getFirework());
				if (world.canBlockSeeSky(position)) {
					world.spawnEntity(var11);
				}
			}
			return storedData;
		}
	},
	FESTIVAL{
		@Override
		protected IEffectData doEffect(IBeeGenome genome, IEffectData storedData, IBeeHousing housing, World world, BlockPos position) {
			if (world.rand.nextInt((this == ExtraBeesEffect.FIREWORKS) ? 8 : 12) < 1) {
				final FireworkCreator.Firework firework = new FireworkCreator.Firework();
				final EntityFireworkRocket var11 = new EntityFireworkRocket(world, position.getX(), position.getY(), position.getZ(), firework.getFirework());
				if (world.canBlockSeeSky(position)) {
					world.spawnEntity(var11);
				}
			}
			return storedData;
		}
	},
	BIRTHDAY{
		@Override
		protected IEffectData doEffect(IBeeGenome genome, IEffectData storedData, IBeeHousing housing, World world, BlockPos position) {
			if (world.rand.nextInt((this == ExtraBeesEffect.FIREWORKS) ? 8 : 12) < 1) {
				FireworkCreator.Firework firework = new FireworkCreator.Firework();
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
				final EntityFireworkRocket var11 = new EntityFireworkRocket(world, position.getX(), position.getY(), position.getZ(), firework.getFirework());
				if (world.canBlockSeeSky(position)) {
					world.spawnEntity(var11);
				}
			}
			return storedData;
		}
	},
	TELEPORT{
		@Override
		protected IEffectData doEffect(IBeeGenome genome, IEffectData storedData, IBeeHousing housing, World world, BlockPos position) {
			if (world.rand.nextInt(80) > 1) {
				return storedData;
			}
			final List<Entity> entities = this.getEntities(Entity.class, genome, housing);
			if (entities.size() == 0) {
				return storedData;
			}
			Entity entity = entities.get(world.rand.nextInt(entities.size()));
			if (!(entity instanceof EntityLiving)) {
				return storedData;
			}
			int y = position.getY();
			if (y < 4) {
				y = 4;
			}
			if (!world.isAirBlock(position) || !world.isAirBlock(position.up())) {
				return storedData;
			}
			entity.setPositionAndUpdate(position.getX(), y, position.getZ());
			((EntityLiving) entity).addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 160, 10));
			return storedData;
		}
	},
	GRAVITY{
		@Override
		protected IEffectData doEffect(IBeeGenome genome, IEffectData storedData, IBeeHousing housing, World world, BlockPos position) {
			final List<Entity> entities = this.getEntities(Entity.class, genome, housing);
			for (final Entity entity : entities) {
				float entityStrength = 1.0f;
				if (entity instanceof EntityPlayer) {
					entityStrength *= 100.0f;
				}
				double posX = position.getX() - entity.posX;
				double posY = position.getY() - entity.posY;
				double posZ = position.getZ() - entity.posZ;
				if (posX * posX + posY * posY + posZ * posZ < 2.0) {
					return storedData;
				}
				final double strength = 0.5 / (posX * posX + posY * posY + posZ * posZ) * entityStrength;
				entity.addVelocity(posX * strength, posY * strength, posZ * strength);
			}
			return storedData;
		}
	},
	THIEF{
		@Override
		protected IEffectData doEffect(IBeeGenome genome, IEffectData storedData, IBeeHousing housing, World world, BlockPos position) {
			final List<EntityPlayer> players = this.getEntities(EntityPlayer.class, genome, housing);
			for (EntityPlayer player : players) {
				final double posX = position.getX() - player.posX;
				final double posY = position.getY() - player.posY;
				final double posZ = position.getZ() - player.posZ;
				if (posX * posX + posY * posY + posZ * posZ < 2.0) {
					return storedData;
				}
				final double strength = 0.5 / (posX * posX + posY * posY + posZ * posZ);
				player.addVelocity(-posX * strength, -posY * strength, -posZ * strength);
			}
			return storedData;
		}
	},
	WITHER{
		@Override
		protected IEffectData doEffect(IBeeGenome genome, IEffectData storedData, IBeeHousing housing, World world, BlockPos position) {
			//TODO: add function ?
			return storedData;
		}
	},
	WATER{
		@Override
		protected IEffectData doEffect(IBeeGenome genome, IEffectData storedData, IBeeHousing housing, World world, BlockPos position) {
			if (world.rand.nextInt(120) > 1) {
				return storedData;
			}
			IFluidHandler fluidHandler = Utils.getCapability(world, position, CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, EnumFacing.UP);
			if (fluidHandler != null) {
				fluidHandler.fill(new FluidStack(FluidRegistry.WATER, 100), true);
				return storedData;
			}
			return storedData;
		}
	},
	SLOW{
		@Override
		protected IEffectData doEffect(IBeeGenome genome, IEffectData storedData, IBeeHousing housing, World world, BlockPos position) {
			for (EntityPlayer player : this.getEntities(EntityPlayer.class, genome, housing)) {
				if (world.rand.nextInt(4) < wearsItems(player)) {
					continue;
				}
				player.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 200));
			}
			return storedData;
		}
	},
	BONEMEAL_SAPLING{
		@Override
		protected IEffectData doEffect(IBeeGenome genome, IEffectData storedData, IBeeHousing housing, World world, BlockPos position) {
			if (world.rand.nextInt(20) > 1) {
				return storedData;
			}
			if (ExtraBeesFlowers.SAPLING.isAcceptedFlower(world, position)) {
				ItemDye.applyBonemeal(new ItemStack(Blocks.DIRT, 1), world, position);
				return storedData;
			}
			return storedData;
		}
	},
	BONEMEAL_FRUIT{
		@Override
		protected IEffectData doEffect(IBeeGenome genome, IEffectData storedData, IBeeHousing housing, World world, BlockPos position) {
			if (world.rand.nextInt(20) > 1) {
				return storedData;
			}
			if (ExtraBeesFlowers.FRUIT.isAcceptedFlower(world, position)) {
				ItemDye.applyBonemeal(new ItemStack(Blocks.DIRT, 1), world, position);
				return storedData;
			}
			return storedData;
		}
	},
	BONEMEAL_MUSHROOM{
		@Override
		protected IEffectData doEffect(IBeeGenome genome, IEffectData storedData, IBeeHousing housing, World world, BlockPos position) {
			if (world.rand.nextInt(20) > 1) {
				return storedData;
			}
			IBlockState blockState = world.getBlockState(position);
			if (blockState.getBlock() == Blocks.BROWN_MUSHROOM || blockState.getBlock() == Blocks.RED_MUSHROOM) {
				ItemDye.applyBonemeal(new ItemStack(Blocks.DIRT, 1), world, position);
				return storedData;
			}
			return storedData;
		}
	},
	POWER{
		@Override
		protected IEffectData doEffect(IBeeGenome genome, IEffectData storedData, IBeeHousing housing, World world, BlockPos position) {
			final TileEntity tile = world.getTileEntity(position);
			if (tile != null) {
				IEnergyStorage storage = tile.getCapability(CapabilityEnergy.ENERGY, EnumFacing.UP);
				if (storage != null) {
					storage.receiveEnergy(5, false);
				}
			}
			return storedData;
		}
	};

	private static final List<Birthday> birthdays = new ArrayList<>();

	static {
		birthdays.add(new Birthday(3, 10, "Binnie"));
	}

	private String fx;
	private final boolean combinable;
	private final boolean dominant;
	private final String uid;

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
		return ExtraBees.proxy.localise("effect." + this.name().toLowerCase() + ".name");
	}

	@Override
	public boolean isDominant() {
		return this.dominant;
	}

	public void spawnMob(World world, BlockPos pos, ResourceLocation name) {
		if (this.anyPlayerInRange(world, pos, 16)) {
			double particleX = pos.getX() + world.rand.nextFloat();
			double particleY = pos.getY() + world.rand.nextFloat();
			double particleZ = pos.getZ() + world.rand.nextFloat();
			world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, particleX, particleY, particleZ, 0.0, 0.0, 0.0);
			world.spawnParticle(EnumParticleTypes.FLAME, particleX, particleY, particleZ, 0.0, 0.0, 0.0);
			EntityLiving entity = (EntityLiving) EntityList.createEntityByIDFromName(name, world);
			if (entity != null) {
				int nearbyEntityCount = world.getEntitiesWithinAABB(entity.getClass(), new AxisAlignedBB(pos, pos.add(1, 1, 1)).expand(8.0, 4.0, 8.0)).size();
				if (nearbyEntityCount < 6) {
					double posX = pos.getX() + (world.rand.nextDouble() - world.rand.nextDouble()) * 4.0;
					double posY = pos.getY() + world.rand.nextInt(3) - 1;
					double posZ = pos.getZ() + (world.rand.nextDouble() - world.rand.nextDouble()) * 4.0;
					entity.setLocationAndAngles(posX, posY, posZ, world.rand.nextFloat() * 360.0f, 0.0f);
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

	protected abstract IEffectData doEffect(IBeeGenome genome, IEffectData storedData, IBeeHousing housing, World world, BlockPos position);

	@Override
	public IEffectData doEffect(IBeeGenome genome, IEffectData storedData, IBeeHousing housing) {
		final World world = housing.getWorldObj();
		BlockPos coordinates = housing.getCoordinates();
		int xHouse = coordinates.getX();
		int yHouse = coordinates.getY();
		int zHouse = coordinates.getZ();
		Vec3i area = this.getModifiedArea(genome, housing);
		int offsetX = 1 + area.getX() / 2;
		int offsetY = 1 + area.getY() / 2;
		int offsetZ = 1 + area.getZ() / 2;
		int xPos = xHouse - offsetX + world.rand.nextInt(2 * offsetX + 1);
		int yPos = yHouse - offsetY + world.rand.nextInt(2 * offsetY + 1);
		int zPos = zHouse - offsetZ + world.rand.nextInt(2 * offsetZ + 1);
		BlockPos position = new BlockPos(xPos, yPos, zPos);
		if (world.isBlockLoaded(position)) {
			doEffect(genome, storedData, housing, world, position);
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

	private void setFX(String string) {
		this.fx = "particles/" + string;
	}

	public <T extends Entity> List<T> getEntities(Class<T> eClass, IBeeGenome genome, IBeeHousing housing) {
		final Vec3i area = genome.getTerritory();
		BlockPos coordinates = housing.getCoordinates();
		int[] offset = {-Math.round(area.getX() / 2), -Math.round(area.getY() / 2), -Math.round(area.getZ() / 2)};
		int[] min = {coordinates.getX() + offset[0], coordinates.getY() + offset[1], coordinates.getZ() + offset[2]};
		int[] max = {coordinates.getX() + offset[0] + area.getX(), coordinates.getY() + offset[1] + area.getY(), coordinates.getZ() + offset[2] + area.getZ()};
		AxisAlignedBB box = new AxisAlignedBB(min[0], min[1], min[2], max[0], max[1], max[2]);
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
