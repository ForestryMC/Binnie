package binnie.botany.farming;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.minecraftforge.fluids.FluidStack;

import forestry.api.farming.FarmDirection;
import forestry.api.farming.ICrop;
import forestry.api.farming.IFarmHousing;
import forestry.api.farming.IFarmable;
import forestry.core.owner.IOwnedTile;
import forestry.core.utils.BlockUtil;

import binnie.core.Binnie;
import binnie.botany.api.gardening.EnumAcidity;
import binnie.botany.api.gardening.EnumFertiliserType;
import binnie.botany.api.gardening.EnumMoisture;
import binnie.botany.api.gardening.EnumSoilType;
import binnie.botany.api.gardening.IBlockSoil;
import binnie.botany.api.gardening.IGardeningManager;
import binnie.botany.core.BotanyCore;
import binnie.botany.modules.ModuleGardening;
import binnie.botany.tile.TileEntityFlower;
import binnie.core.liquid.ManagerLiquid;

public class GardenLogic extends FarmLogic {
	private final List<IFarmable> farmables;
	private final EnumMoisture moisture;
	@Nullable
	private final EnumAcidity acidity;
	private final boolean fertilised;
	private final String name;
	private NonNullList<ItemStack> produce;
	private ItemStack icon;

	public GardenLogic(EnumMoisture moisture, @Nullable EnumAcidity acidity, boolean isManual, boolean isFertilised, ItemStack icon, String name) {
		this.setManual(isManual);
		this.moisture = moisture;
		this.acidity = acidity;
		this.fertilised = isFertilised;
		this.icon = icon;
		this.name = name;

		produce = NonNullList.create();
		farmables = new ArrayList<>();
		farmables.add(new FarmableFlower());
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
	public boolean isAcceptedResource(ItemStack itemstack) {
		IGardeningManager gardening = BotanyCore.getGardening();
		return gardening.isSoil(itemstack.getItem()) ||
				itemstack.getItem() == Item.getItemFromBlock(Blocks.SAND) ||
				itemstack.getItem() == Item.getItemFromBlock(Blocks.DIRT) ||
				gardening.isFertiliser(EnumFertiliserType.ACID, itemstack) ||
				gardening.isFertiliser(EnumFertiliserType.ALKALINE, itemstack);
	}

	@Override
	public NonNullList<ItemStack> collect(World world, IFarmHousing farmHousing) {
		NonNullList<ItemStack> products = produce;
		produce = NonNullList.create();
		return products;
	}

	@Override
	public boolean cultivate(World world, IFarmHousing farmHousing, BlockPos pos, FarmDirection direction, int extent) {
		return maintainSoil(world, pos, direction, extent, farmHousing) ||
				(!isManual() && maintainWater(world, pos, direction, extent, farmHousing)) ||
				maintainCrops(world, pos.up(), direction, extent, farmHousing);
	}

	private boolean isWaste(ItemStack stack) {
		return stack.getItem() == Item.getItemFromBlock(Blocks.DIRT);
	}

	private boolean maintainSoil(World world, BlockPos pos, FarmDirection direction, int extent, IFarmHousing housing) {
		IGardeningManager gardening = BotanyCore.getGardening();
		for (int i = 0; i < extent; ++i) {
			BlockPos position = pos.offset(direction.getFacing(), i);
			if (fertilised && gardening.isSoil(getBlock(world, position))) {
				IBlockSoil soil = (IBlockSoil) getBlock(world, position);
				if (soil.fertilise(world, position, EnumSoilType.FLOWERBED)) {
					continue;
				}
			}

			if (getBlock(world, position.up()) == ModuleGardening.plant) {
				world.setBlockToAir(position.up());
			} else {
				if (acidity != null && gardening.isSoil(getBlock(world, position))) {
					IBlockSoil soil = (IBlockSoil) getBlock(world, position);
					EnumAcidity pH = soil.getPH(world, position);
					if (pH.ordinal() < acidity.ordinal()) {
						ItemStack stack = getAvailableFertiliser(housing, EnumFertiliserType.ALKALINE);
						if (!stack.isEmpty() && soil.setPH(world, position, EnumAcidity.values()[pH.ordinal() + 1])) {
							NonNullList<ItemStack> resources = NonNullList.create();
							resources.add(stack);
							housing.getFarmInventory().removeResources(resources);
							continue;
						}
					}
					if (pH.ordinal() > acidity.ordinal()) {
						ItemStack stack = getAvailableFertiliser(housing, EnumFertiliserType.ACID);
						if (!stack.isEmpty() && soil.setPH(world, position, EnumAcidity.values()[pH.ordinal() - 1])) {
							NonNullList<ItemStack> resources = NonNullList.create();
							resources.add(stack);
							housing.getFarmInventory().removeResources(resources);
							continue;
						}
					}
				}

				if (!isAirBlock(world, position) && !BlockUtil.isReplaceableBlock(getBlockState(world, position), world, position)) {
					ItemStack block = getAsItemStack(world, position);
					ItemStack loam = getAvailableLoam(housing);
					if (isWaste(block) && !loam.isEmpty()) {
						IBlockState blockState = Block.getBlockFromItem(block.getItem()).getStateFromMeta(block.getItemDamage());
						Blocks.DIRT.getDrops(produce, world, position, blockState, 0);
						setBlock(world, position, Blocks.AIR, 0);
						return trySetSoil(world, position, loam, housing);
					}
				} else if (!isManual()) {
					if (!isWaterBlock(world, position)) {
						if (i % 2 == 0) {
							return trySetSoil(world, position, housing);
						}
						FarmDirection cclock = FarmDirection.EAST;
						if (direction == FarmDirection.EAST) {
							cclock = FarmDirection.SOUTH;
						} else if (direction == FarmDirection.SOUTH) {
							cclock = FarmDirection.EAST;
						} else if (direction == FarmDirection.WEST) {
							cclock = FarmDirection.SOUTH;
						}
						BlockPos previous = position.offset(cclock.getFacing());
						ItemStack soil2 = getAsItemStack(world, previous);
						if (!gardening.isSoil(soil2.getItem())) {
							trySetSoil(world, position, housing);
						}
					}
				}
			}
		}
		return false;
	}

	private boolean maintainWater(World world, BlockPos pos, FarmDirection direction, int extent, IFarmHousing housing) {
		for (int i = 0; i < extent; ++i) {
			BlockPos position = pos.offset(direction.getFacing(), i);
			if (!isAirBlock(world, position) && !BlockUtil.isReplaceableBlock(getBlockState(world, position), world, position)) {
				continue;
			}
			if (isWaterBlock(world, position)) {
				continue;
			}

			boolean isEnclosed = true;
			if (world.isAirBlock(position.east())) {
				isEnclosed = false;
			} else if (world.isAirBlock(position.west())) {
				isEnclosed = false;
			} else if (world.isAirBlock(position.south())) {
				isEnclosed = false;
			} else if (world.isAirBlock(position.north())) {
				isEnclosed = false;
			}

			isEnclosed = (isEnclosed || moisture != EnumMoisture.DAMP);
			if (isEnclosed) {
				return trySetWater(world, position, housing);
			}
		}
		return false;
	}

	private ItemStack getAvailableLoam(IFarmHousing housing) {
		EnumMoisture[] moistures;
		if (moisture == EnumMoisture.DAMP) {
			moistures = new EnumMoisture[]{
					EnumMoisture.DAMP,
					EnumMoisture.NORMAL,
					EnumMoisture.DRY
			};
		} else if (moisture == EnumMoisture.DRY) {
			moistures = new EnumMoisture[]{
					EnumMoisture.DRY,
					EnumMoisture.DAMP,
					EnumMoisture.DRY
			};
		} else {
			moistures = new EnumMoisture[]{
					EnumMoisture.DRY,
					EnumMoisture.NORMAL,
					EnumMoisture.DAMP
			};
		}

		EnumAcidity[] acidities = {
				EnumAcidity.NEUTRAL,
				EnumAcidity.ACID,
				EnumAcidity.ALKALINE
		};

		for (EnumMoisture moist : moistures) {
			for (EnumAcidity acid : acidities) {
				for (Block type : new Block[]{ModuleGardening.flowerbed, ModuleGardening.loam, ModuleGardening.soil}) {
					int meta = acid.ordinal() * 3 + moist.ordinal();
					NonNullList<ItemStack> resources = NonNullList.create();
					ItemStack resourceStack = new ItemStack(type, 1, meta);
					resources.add(resourceStack);
					if (housing.getFarmInventory().hasResources(resources)) {
						return resourceStack;
					}
				}
			}
		}

		NonNullList<ItemStack> resources = NonNullList.create();
		ItemStack resourceStack = new ItemStack(Blocks.DIRT);
		resources.add(resourceStack);
		if (housing.getFarmInventory().hasResources(resources)) {
			return new ItemStack(Blocks.DIRT);
		}
		return ItemStack.EMPTY;
	}

	private boolean trySetSoil(World world, BlockPos position, IFarmHousing housing) {
		return trySetSoil(world, position, getAvailableLoam(housing), housing);
	}

	private boolean trySetSoil(World world, BlockPos position, ItemStack loam, IFarmHousing housing) {
		ItemStack copy = loam;
		if (loam.isEmpty()) {
			return false;
		}

		if (loam.getItem() == Item.getItemFromBlock(Blocks.DIRT)) {
			loam = new ItemStack(ModuleGardening.soil, 1, 4);
		}

		setBlock(world, position, ((ItemBlock) loam.getItem()).getBlock(), loam.getItemDamage());
		NonNullList<ItemStack> resources = NonNullList.create();
		resources.add(copy);
		housing.getFarmInventory().removeResources(resources);
		return true;
	}

	private boolean trySetWater(World world, BlockPos position, IFarmHousing housing) {
		FluidStack water = Binnie.LIQUID.getFluidStack(ManagerLiquid.WATER, 1000);
		if (moisture == EnumMoisture.DAMP) {
			if (water == null || !housing.hasLiquid(water)) {
				return false;
			}
			setBlock(world, position, Blocks.WATER, 0);
			housing.removeLiquid(water);
			return true;
		}

		if (moisture != EnumMoisture.DRY) {
			return trySetSoil(world, position, housing);
		}
		ItemStack sand = new ItemStack(Blocks.SAND, 1);
		NonNullList<ItemStack> resources = NonNullList.create();
		resources.add(sand);
		if (!housing.getFarmInventory().hasResources(resources)) {
			return false;
		}
		setBlock(world, position, Blocks.SAND, 0);
		housing.getFarmInventory().removeResources(resources);
		return true;
	}

	public void setIcon(ItemStack icon) {
		this.icon = icon;
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
	public Collection<ICrop> harvest(World world, BlockPos pos, FarmDirection direction, int extent) {
		if (isManual()) {
			return Collections.emptyList();
		}
		return farmables.stream().map(farmable -> farmable.getCropAt(world, pos.up(), world.getBlockState(pos.up())))
				.filter(Objects::nonNull).collect(Collectors.toList());
	}

	@Override
	public void addSoil(ItemStack resource, IBlockState soilState, boolean hasMetaData) {
		// all {@link IBlockSoil} are soil, and nothing else
	}

	@Override
	public ResourceLocation getTextureMap() {
		return TextureMap.LOCATION_BLOCKS_TEXTURE;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public ItemStack getIconItemStack() {
		return icon;
	}

	protected boolean maintainCrops(World world, BlockPos pos, FarmDirection direction, int extent, IFarmHousing housing) {
		IGardeningManager gardening = BotanyCore.getGardening();
		for (int i = 0; i < extent; ++i) {
			BlockPos position = pos.offset(direction.getFacing(), i);
			if (isAirBlock(world, position) || BlockUtil.isReplaceableBlock(getBlockState(world, position), world, position)) {
				ItemStack below = getAsItemStack(world, position.down());
				if (gardening.isSoil(below.getItem())) {
					return trySetCrop(world, position, housing);
				}
			}
		}
		return false;
	}

	private boolean trySetCrop(World world, BlockPos position, IFarmHousing housing) {
		for (IFarmable farmable : farmables) {
			if (!housing.plantGermling(farmable, world, position)) {
				continue;
			}

			if (housing instanceof IOwnedTile) {
				TileEntity tile = world.getTileEntity(position);
				if (tile instanceof TileEntityFlower) {
					TileEntityFlower flower = (TileEntityFlower) tile;
					IOwnedTile owned = (IOwnedTile) housing;

					flower.setOwner(owned.getOwnerHandler().getOwner());
				}
			}
			return true;
		}
		return false;
	}

	public ItemStack getAvailableFertiliser(IFarmHousing housing, EnumFertiliserType type) {
		for (ItemStack stack : BotanyCore.getGardening().getFertilisers(type)) {
			if (!stack.isEmpty()) {
				NonNullList<ItemStack> resources = NonNullList.create();
				resources.add(stack);
				if (housing.getFarmInventory().hasResources(resources)) {
					return stack;
				}
			}
		}
		return ItemStack.EMPTY;
	}
}
