package binnie.botany.farm;

import binnie.Binnie;
import binnie.botany.Botany;
import binnie.botany.api.EnumAcidity;
import binnie.botany.api.EnumMoisture;
import binnie.botany.api.EnumSoilType;
import binnie.botany.api.IBlockSoil;
import binnie.botany.flower.TileEntityFlower;
import binnie.botany.gardening.Gardening;
import forestry.api.farming.FarmDirection;
import forestry.api.farming.ICrop;
import forestry.api.farming.IFarmHousing;
import forestry.api.farming.IFarmable;
import forestry.core.owner.IOwnedTile;
import forestry.core.utils.BlockUtil;
import net.minecraft.block.Block;
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

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class GardenLogic extends FarmLogic {
	List<IFarmable> farmables;
	private EnumMoisture moisture;
	@Nullable
	private EnumAcidity acidity;
	private boolean fertilised;
	private String name;
	private NonNullList<ItemStack> produce;
	private ItemStack icon;

	public GardenLogic(EnumMoisture moisture2, @Nullable EnumAcidity acidity2, boolean isManual, boolean isFertilised, ItemStack icon2, String name2) {
		this.produce = NonNullList.create();
		(this.farmables = new ArrayList<>()).add(new FarmableFlower());
		this.farmables.add(new FarmableVanillaFlower());
		this.moisture = moisture2;
		this.acidity = acidity2;
		this.isManual = isManual;
		this.fertilised = isFertilised;
		this.icon = icon2;
		this.name = name2;
	}

	@Override
	public int getFertilizerConsumption() {
		return this.fertilised ? 8 : 2;
	}

	@Override
	public int getWaterConsumption(final float hydrationModifier) {
		return (int) (this.moisture.ordinal() * 40 * hydrationModifier);
	}

	@Override
	public boolean isAcceptedResource(final ItemStack itemstack) {
		return Gardening.isSoil(itemstack.getItem()) ||
			itemstack.getItem() == Item.getItemFromBlock(Blocks.SAND) ||
			itemstack.getItem() == Item.getItemFromBlock(Blocks.DIRT) ||
			Gardening.isAcidFertiliser(itemstack) ||
			Gardening.isAlkalineFertiliser(itemstack);
	}

	@Override
	public NonNullList<ItemStack> collect(World world, IFarmHousing farmHousing) {
		final NonNullList<ItemStack> products = this.produce;
		this.produce = NonNullList.create();
		return products;
	}

	@Override
	public boolean cultivate(World world, IFarmHousing farmHousing, BlockPos pos, FarmDirection direction, int extent) {
		return this.maintainSoil(world, pos, direction, extent, farmHousing) ||
			(!this.isManual && this.maintainWater(world, pos, direction, extent, farmHousing)) ||
			this.maintainCrops(world, pos.up(), direction, extent, farmHousing);
	}

	private boolean isWaste(final ItemStack stack) {
		return stack.getItem() == Item.getItemFromBlock(Blocks.DIRT);
	}

	private boolean maintainSoil(World world, BlockPos pos, FarmDirection direction, int extent, IFarmHousing housing) {
		for (int i = 0; i < extent; ++i) {
			BlockPos position = pos.offset(direction.getFacing(), i);
			if (this.fertilised && Gardening.isSoil(getBlock(world, position))) {
				IBlockSoil soil = (IBlockSoil) getBlock(world, position);
				if (soil.fertilise(world, position, EnumSoilType.FLOWERBED)) {
					continue;
				}
			}
			if (getBlock(world, position.up()) == Botany.plant) {
				world.setBlockToAir(position.up());
			} else {
				if (this.acidity != null && Gardening.isSoil(getBlock(world, position))) {
					IBlockSoil soil = (IBlockSoil) getBlock(world, position);
					EnumAcidity pH = soil.getPH(world, position);
					if (pH.ordinal() < this.acidity.ordinal()) {
						ItemStack stack = this.getAvailableAlkaline(housing);
						if (!stack.isEmpty() && soil.setPH(world, position, EnumAcidity.values()[pH.ordinal() + 1])) {
							NonNullList<ItemStack> resources = NonNullList.create();
							resources.add(stack);
							housing.getFarmInventory().removeResources(resources);
							continue;
						}
					}
					if (pH.ordinal() > this.acidity.ordinal()) {
						final ItemStack stack = this.getAvailableAcid(housing);
						if (!stack.isEmpty() && soil.setPH(world, position, EnumAcidity.values()[pH.ordinal() - 1])) {
							NonNullList<ItemStack> resources = NonNullList.create();
							resources.add(stack);
							housing.getFarmInventory().removeResources(resources);
							continue;
						}
					}
				}
				if (!this.isAirBlock(world, position) && !BlockUtil.isReplaceableBlock(getBlockState(world, position), world, position)) {
					ItemStack block = this.getAsItemStack(world, position);
					ItemStack loam = this.getAvailableLoam(housing);
					if (this.isWaste(block) && !loam.isEmpty()) {
						this.produce.addAll(Blocks.DIRT.getDrops(world, position, Block.getBlockFromItem(block.getItem()).getStateFromMeta(block.getItemDamage()), 0));
						this.setBlock(world, position, Blocks.AIR, 0);
						return trySetSoil(world, position, loam, housing);
					}
				} else if (!this.isManual) {
					if (!this.isWaterBlock(world, position)) {
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
						final ItemStack soil2 = this.getAsItemStack(world, previous);
						if (!Gardening.isSoil(soil2.getItem())) {
							this.trySetSoil(world, position, housing);
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
			if (this.isAirBlock(world, position) || BlockUtil.isReplaceableBlock(getBlockState(world, position), world, position)) {
				if (!this.isWaterBlock(world, position)) {
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
					isEnclosed = (isEnclosed || this.moisture != EnumMoisture.DAMP);
					if (isEnclosed) {
						return this.trySetWater(world, position, housing);
					}
				}
			}
		}
		return false;
	}

	private ItemStack getAvailableLoam(IFarmHousing housing) {
		EnumMoisture[] moistures;
		if (this.moisture == EnumMoisture.DAMP) {
			moistures = new EnumMoisture[]{EnumMoisture.DAMP, EnumMoisture.NORMAL, EnumMoisture.DRY};
		} else if (this.moisture == EnumMoisture.DRY) {
			moistures = new EnumMoisture[]{EnumMoisture.DRY, EnumMoisture.DAMP, EnumMoisture.DRY};
		} else {
			moistures = new EnumMoisture[]{EnumMoisture.DRY, EnumMoisture.NORMAL, EnumMoisture.DAMP};
		}
		final EnumAcidity[] acidities = {EnumAcidity.NEUTRAL, EnumAcidity.ACID, EnumAcidity.ALKALINE};
		for (final EnumMoisture moist : moistures) {
			for (final EnumAcidity acid : acidities) {
				for (final Block type : new Block[]{Botany.flowerbed, Botany.loam, Botany.soil}) {
					final int meta = acid.ordinal() * 3 + moist.ordinal();
					final NonNullList<ItemStack> resources = NonNullList.create();
					ItemStack resourceStack = new ItemStack(type, 1, meta);
					resources.add(resourceStack);
					if (housing.getFarmInventory().hasResources(resources)) {
						return resourceStack;
					}
				}
			}
		}
		final NonNullList<ItemStack> resources = NonNullList.create();
		ItemStack resourceStack = new ItemStack(Blocks.DIRT);
		resources.add(resourceStack);
		if (housing.getFarmInventory().hasResources(resources)) {
			return new ItemStack(Blocks.DIRT);
		}
		return ItemStack.EMPTY;
	}

	private boolean trySetSoil(World world, BlockPos position, IFarmHousing housing) {
		return this.trySetSoil(world, position, this.getAvailableLoam(housing), housing);
	}

	private boolean trySetSoil(World world, BlockPos position, ItemStack loam, IFarmHousing housing) {
		ItemStack copy = loam;
		if (!loam.isEmpty()) {
			if (loam.getItem() == Item.getItemFromBlock(Blocks.DIRT)) {
				loam = new ItemStack(Botany.soil, 0, 4);
			}
			setBlock(world, position, ((ItemBlock) loam.getItem()).getBlock(), loam.getItemDamage());
			final NonNullList<ItemStack> resources = NonNullList.create();
			resources.add(copy);
			housing.getFarmInventory().removeResources(resources);
			return true;
		}
		return false;
	}

	private boolean trySetWater(World world, BlockPos position, IFarmHousing housing) {
		final FluidStack water = Binnie.LIQUID.getFluidStack("water", 1000);
		if (this.moisture == EnumMoisture.DAMP) {
			if (water == null || !housing.hasLiquid(water)) {
				return false;
			}
			this.setBlock(world, position, Blocks.WATER, 0);
			housing.removeLiquid(water);
			return true;
		} else {
			if (this.moisture != EnumMoisture.DRY) {
				return trySetSoil(world, position, housing);
			}
			final ItemStack sand = new ItemStack(Blocks.SAND, 1);
			final NonNullList<ItemStack> resources = NonNullList.create();
			resources.add(sand);
			if (!housing.getFarmInventory().hasResources(resources)) {
				return false;
			}
			this.setBlock(world, position, Blocks.SAND, 0);
			housing.getFarmInventory().removeResources(resources);
			return true;
		}
	}

	public void setIcon(final ItemStack icon) {
		this.icon = icon;
	}

	@Override
	public boolean isAcceptedGermling(final ItemStack itemstack) {
		for (final IFarmable farmable : this.farmables) {
			if (farmable.isGermling(itemstack)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Collection<ICrop> harvest(World world, BlockPos pos, FarmDirection direction, int extent) {
		return Collections.emptyList();
	}

	@Override
	public ResourceLocation getTextureMap() {
		return TextureMap.LOCATION_BLOCKS_TEXTURE;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public ItemStack getIconItemStack() {
		return icon;
	}

	protected boolean maintainCrops(World world, BlockPos pos, FarmDirection direction, int extent, IFarmHousing housing) {
		for (int i = 0; i < extent; ++i) {
			BlockPos position = pos.offset(direction.getFacing(), i);
			if (this.isAirBlock(world, position) || BlockUtil.isReplaceableBlock(getBlockState(world, position), world, position)) {
				ItemStack below = this.getAsItemStack(world, position.down());
				if (Gardening.isSoil(below.getItem())) {
					return trySetCrop(world, position, housing);
				}
			}
		}
		return false;
	}

	private boolean trySetCrop(World world, BlockPos position, IFarmHousing housing) {
		for (IFarmable farmable : this.farmables) {
			if (housing.plantGermling(farmable, world, position)) {
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
		}
		return false;
	}

	public ItemStack getAvailableAcid(IFarmHousing housing) {
		for (final ItemStack stack : Gardening.getAcidFertilisers()) {
			if (!stack.isEmpty()) {
				final NonNullList<ItemStack> resources = NonNullList.create();
				resources.add(stack);
				if (housing.getFarmInventory().hasResources(resources)) {
					return stack;
				}
			}
		}
		return ItemStack.EMPTY;
	}

	public ItemStack getAvailableAlkaline(IFarmHousing housing) {
		for (final ItemStack stack : Gardening.getAlkalineFertilisers()) {
			if (!stack.isEmpty()) {
				final NonNullList<ItemStack> resources = NonNullList.create();
				resources.add(stack);
				if (housing.getFarmInventory().hasResources(resources)) {
					return stack;
				}
			}
		}
		return ItemStack.EMPTY;
	}
}
