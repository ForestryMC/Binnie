package binnie.botany.farm;

import binnie.Binnie;
import binnie.botany.Botany;
import binnie.botany.api.EnumAcidity;
import binnie.botany.api.EnumMoisture;
import binnie.botany.api.EnumSoilType;
import binnie.botany.api.gardening.IBlockSoil;
import binnie.botany.flower.TileEntityFlower;
import binnie.botany.gardening.Gardening;
import binnie.core.Mods;
import com.mojang.authlib.GameProfile;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.farming.FarmDirection;
import forestry.api.farming.ICrop;
import forestry.api.farming.IFarmHousing;
import forestry.api.farming.IFarmable;
import forestry.core.access.IOwnable;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GardenLogic extends FarmLogic {
	protected EnumMoisture moisture;
	protected EnumAcidity acidity;
	protected boolean fertilised;
	protected String name;
	protected ArrayList<ItemStack> produce;
	protected List<IFarmable> farmables;

	private ItemStack icon;

	public GardenLogic(IFarmHousing housing) {
		super(housing);
		produce = new ArrayList<>();
		(farmables = new ArrayList<>()).add(new FarmableFlower());
		farmables.add(new FarmableVanillaFlower());
	}

	@Override
	public int getFertilizerConsumption() {
		return fertilised ? 8 : 2;
	}

	@Override
	public int getWaterConsumption(float hydrationModifier) {
		return (int) (moisture.ordinal() * 40 * hydrationModifier);
	}

	@Override
	public boolean isAcceptedResource(ItemStack stack) {
		Item item = stack.getItem();
		return Gardening.isSoil(item)
			|| item == Item.getItemFromBlock(Blocks.sand)
			|| item == Item.getItemFromBlock(Blocks.dirt)
			|| Gardening.isAcidFertiliser(stack)
			|| Gardening.isAlkalineFertiliser(stack);
	}

	@Override
	public Collection<ItemStack> collect() {
		Collection<ItemStack> products = produce;
		produce = new ArrayList<>();
		return products;
	}

	@Override
	public boolean cultivate(int x, int y, int z, FarmDirection direction, int extent) {
		world = housing.getWorld();
		return maintainSoil(x, y, z, direction, extent)
			|| (!isManual && maintainWater(x, y, z, direction, extent))
			|| maintainCrops(x, y + 1, z, direction, extent);
	}

	private boolean isWaste(ItemStack stack) {
		return stack.getItem() == Item.getItemFromBlock(Blocks.dirt);
	}

	private boolean maintainSoil(int x2, int y2, int z2, FarmDirection direction, int extent) {
		for (int i = 0; i < extent; ++i) {
			Vect position = translateWithOffset(x2, y2, z2, direction, i);
			if (fertilised && Gardening.isSoil(world.getBlock(position.x, position.y, position.z))) {
				IBlockSoil soil = (IBlockSoil) world.getBlock(position.x, position.y, position.z);
				if (soil.fertilise(world, position.x, position.y, position.z, EnumSoilType.FLOWERBED)) {
					continue;
				}
			}

			if (world.getBlock(position.x, position.y + 1, position.z) == Botany.plant) {
				world.setBlockToAir(position.x, position.y + 1, position.z);
			} else {
				if (acidity != null && Gardening.isSoil(world.getBlock(position.x, position.y, position.z))) {
					IBlockSoil soil = (IBlockSoil) world.getBlock(position.x, position.y, position.z);
					EnumAcidity pH = soil.getPH(world, position.x, position.y, position.z);
					if (pH.ordinal() < acidity.ordinal()) {
						ItemStack stack = getAvailableAlkaline();
						if (stack != null && soil.setPH(world, position.x, position.y, position.z, EnumAcidity.values()[pH.ordinal() + 1])) {
							housing.getFarmInventory().removeResources(new ItemStack[]{stack});
							continue;
						}
					}

					if (pH.ordinal() > acidity.ordinal()) {
						ItemStack stack = getAvailableAcid();
						if (stack != null && soil.setPH(world, position.x, position.y, position.z, EnumAcidity.values()[pH.ordinal() - 1])) {
							housing.getFarmInventory().removeResources(new ItemStack[]{stack});
							continue;
						}
					}
				}

				if (!isAirBlock(position) && !world.getBlock(position.x, position.y, position.z).isReplaceable(world, position.x, position.y, position.z)) {
					ItemStack block = getAsItemStack(position);
					ItemStack loam = getAvailableLoam();
					if (isWaste(block) && loam != null) {
						produce.addAll(Blocks.dirt.getDrops(world, position.x, position.y, position.z, block.getItemDamage(), 0));
						setBlock(position, Blocks.air, 0);
						return trySetSoil(position, loam);
					}
				} else if (!isManual) {
					if (!isWaterBlock(position)) {
						if (i % 2 == 0) {
							return trySetSoil(position);
						}
						FarmDirection cclock = FarmDirection.EAST;
						if (direction == FarmDirection.EAST) {
							cclock = FarmDirection.SOUTH;
						} else if (direction == FarmDirection.SOUTH) {
							cclock = FarmDirection.EAST;
						} else if (direction == FarmDirection.WEST) {
							cclock = FarmDirection.SOUTH;
						}
						Vect previous = translateWithOffset(position.x, position.y, position.z, cclock, 1);
						ItemStack soil2 = getAsItemStack(previous);
						if (!Gardening.isSoil(soil2.getItem())) {
							trySetSoil(position);
						}
					}
				}
			}
		}
		return false;
	}

	private boolean maintainWater(int x, int y, int z, FarmDirection direction, int extent) {
		for (int i = 0; i < extent; ++i) {
			Vect position = translateWithOffset(x, y, z, direction, i);
			if (isAirBlock(position) || world.getBlock(x, y, z).isReplaceable(world, position.x, position.y, position.z)) {
				if (isWaterBlock(position)) {
					continue;
				}
	
				boolean isEnclosed = true;
				if (world.isAirBlock(position.x + 1, position.y, position.z)) {
					isEnclosed = false;
				} else if (world.isAirBlock(position.x - 1, position.y, position.z)) {
					isEnclosed = false;
				} else if (world.isAirBlock(position.x, position.y, position.z + 1)) {
					isEnclosed = false;
				} else if (world.isAirBlock(position.x, position.y, position.z - 1)) {
					isEnclosed = false;
				}

				isEnclosed = (isEnclosed || moisture != EnumMoisture.Damp);
				if (isEnclosed) {
					return trySetWater(position);
				}
			}
		}
		return false;
	}

	private ItemStack getAvailableLoam() {
		EnumMoisture[] moistures;
		if (moisture == EnumMoisture.Damp) {
			moistures = new EnumMoisture[]{EnumMoisture.Damp, EnumMoisture.Normal, EnumMoisture.Dry};
		} else if (moisture == EnumMoisture.Dry) {
			moistures = new EnumMoisture[]{EnumMoisture.Dry, EnumMoisture.Damp, EnumMoisture.Dry};
		} else {
			moistures = new EnumMoisture[]{EnumMoisture.Dry, EnumMoisture.Normal, EnumMoisture.Damp};
		}

		EnumAcidity[] acidities = {EnumAcidity.Neutral, EnumAcidity.Acid, EnumAcidity.Alkaline};
		for (EnumMoisture moist : moistures) {
			for (EnumAcidity acid : acidities) {
				for (Block type : new Block[]{Botany.flowerbed, Botany.loam, Botany.soil}) {
					int meta = acid.ordinal() * 3 + moist.ordinal();
					ItemStack[] resource = {new ItemStack(type, 1, meta)};
					if (housing.getFarmInventory().hasResources(resource)) {
						return resource[0];
					}
				}
			}
		}
		if (housing.getFarmInventory().hasResources(new ItemStack[]{new ItemStack(Blocks.dirt)})) {
			return new ItemStack(Blocks.dirt);
		}
		return null;
	}

	private boolean trySetSoil(Vect position) {
		return trySetSoil(position, getAvailableLoam());
	}

	private boolean trySetSoil(Vect position, ItemStack loam) {
		ItemStack copy = loam;
		if (loam != null) {
			if (loam.getItem() == Item.getItemFromBlock(Blocks.dirt)) {
				loam = new ItemStack(Botany.soil, 0, 4);
			}
			setBlock(position, ((ItemBlock) loam.getItem()).field_150939_a, loam.getItemDamage());
			housing.getFarmInventory().removeResources(new ItemStack[]{copy});
			return true;
		}
		return false;
	}

	private boolean trySetWater(Vect position) {
		FluidStack water = Binnie.Liquid.getLiquidStack("water", 1000);
		if (moisture == EnumMoisture.Damp) {
			if (!housing.hasLiquid(water)) {
				return false;
			}

			setBlock(position, Blocks.water, 0);
			housing.removeLiquid(water);
			return true;
		}

		if (moisture != EnumMoisture.Dry) {
			return trySetSoil(position);
		}

		ItemStack[] sand = {new ItemStack(Blocks.sand, 1)};
		if (!housing.getFarmInventory().hasResources(sand)) {
			return false;
		}

		setBlock(position, Blocks.sand, 0);
		housing.getFarmInventory().removeResources(sand);
		return true;
	}

	@Override
	public boolean isAcceptedGermling(ItemStack itemstack) {
		for (IFarmable farmable : farmables) {
			if (farmable.isGermling(itemstack)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Collection<ICrop> harvest(int x, int y, int z, FarmDirection direction, int extent) {
		return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon() {
		return icon.getIconIndex();
	}

	public void setIcon(ItemStack icon) {
		this.icon = icon;
	}

	@Override
	public ResourceLocation getSpriteSheet() {
		return TextureMap.locationBlocksTexture;
	}

	@Override
	public String getName() {
		return name;
	}

	protected boolean maintainCrops(int x, int y, int z, FarmDirection direction, int extent) {
		for (int i = 0; i < extent; ++i) {
			Vect position = translateWithOffset(x, y, z, direction, i);
			if (isAirBlock(position) || world.getBlock(x, y, z).isReplaceable(world, position.x, position.y, position.z)) {
				ItemStack below = getAsItemStack(position.add(new Vect(0, -1, 0)));
				if (Gardening.isSoil(below.getItem())) {
					return trySetCrop(position);
				}
			}
		}
		return false;
	}

	private boolean trySetCrop(Vect position) {
		for (IFarmable farmable : farmables) {
			boolean success = housing.plantGermling(farmable, world, position.x, position.y, position.z);
			if (!success || !(housing instanceof IOwnable)) {
				continue;
			}

			TileEntity tile = housing.getWorld().getTileEntity(position.x, position.y, position.z);
			if (!(tile instanceof TileEntityFlower)) {
				return true;
			}

			try {
				IOwnable housing2 = (IOwnable) housing;
				GameProfile prof;
				if (Mods.Forestry.dev()) {
					prof = (GameProfile) IOwnable.class.getMethod("getOwner", new Class[0]).invoke(housing2);
				} else {
					prof = (GameProfile) IOwnable.class.getMethod("getOwnerProfile", new Class[0]).invoke(housing2);
				}
				((TileEntityFlower) tile).setOwner(prof);
			} catch (Exception ex) {
				// ignored
			}
			return true;
		}
		return false;
	}

	public ItemStack getAvailableAcid() {
		for (ItemStack stack : Gardening.getAcidFertilisers()) {
			if (stack != null && stack.getItem() != null && housing.getFarmInventory().hasResources(new ItemStack[]{stack})) {
				return stack;
			}
		}
		return null;
	}

	public ItemStack getAvailableAlkaline() {
		for (ItemStack stack : Gardening.getAlkalineFertilisers()) {
			if (stack != null && stack.getItem() != null && housing.getFarmInventory().hasResources(new ItemStack[]{stack})) {
				return stack;
			}
		}
		return null;
	}

	public void setData(EnumMoisture moisture2, EnumAcidity acidity2, boolean isManual, boolean isFertilised, ItemStack icon2, String name2) {
		moisture = moisture2;
		acidity = acidity2;
		this.isManual = isManual;
		fertilised = isFertilised;
		icon = icon2;
		name = name2;
	}
}
