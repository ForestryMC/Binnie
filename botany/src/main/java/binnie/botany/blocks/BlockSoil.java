package binnie.botany.blocks;

import javax.annotation.Nullable;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import net.minecraftforge.common.IPlantable;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.core.ForestryAPI;
import forestry.api.core.IItemModelRegister;
import forestry.api.core.IModelManager;

import binnie.botany.CreativeTabBotany;
import binnie.botany.EnumHelper;
import binnie.botany.api.gardening.EnumAcidity;
import binnie.botany.api.gardening.EnumMoisture;
import binnie.botany.api.gardening.EnumSoilType;
import binnie.botany.api.gardening.IBlockSoil;
import binnie.botany.core.BotanyCore;
import binnie.botany.modules.ModuleFlowers;
import binnie.botany.modules.ModuleGardening;
import binnie.core.Constants;
import binnie.core.modules.BotanyModuleUIDs;
import binnie.core.util.I18N;

public class BlockSoil extends Block implements IBlockSoil, IItemModelRegister {
	public static final PropertyEnum<EnumMoisture> MOISTURE = PropertyEnum.create("moisture", EnumMoisture.class);
	public static final PropertyEnum<EnumAcidity> ACIDITY = PropertyEnum.create("acidity", EnumAcidity.class);
	public static final AxisAlignedBB SOIL_BLOCK_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.9375D, 1.0D);
	private final boolean weedKilled;
	private final EnumSoilType type;

	public BlockSoil(EnumSoilType type, String blockName, boolean weedKilled) {
		super(Material.GROUND);
		this.type = type;
		this.weedKilled = weedKilled;
		setUnlocalizedName("botany.soil." + type.getName());
		setCreativeTab(CreativeTabBotany.INSTANCE);
		setRegistryName(blockName);
		setTickRandomly(true);
		setLightOpacity(255);
		setHardness(0.5f);
		setSoundType(SoundType.GROUND);
	}

	public static int getMeta(EnumAcidity acid, EnumMoisture moisture) {
		return acid.ordinal() * 3 + moisture.ordinal();
	}

	public static String getPH(ItemStack stack, boolean withColor, boolean byNeutralNone) {
		int index = stack.getItemDamage() / 3;
		index = (index < EnumAcidity.values().length) ? index : EnumAcidity.values().length - 1;
		EnumAcidity ph = EnumAcidity.values()[index];
		if (byNeutralNone) {
			if (ph == EnumAcidity.NEUTRAL) {
				return "";
			}
		}
		return TextFormatting.GRAY + I18N.localise("botany.ph") + ": " + EnumHelper.getLocalisedName(ph, withColor);
	}

	public static String getMoisture(ItemStack stack, boolean withColor, boolean byNormalNone) {
		EnumMoisture moisure = EnumMoisture.values()[stack.getItemDamage() % 3];
		if (byNormalNone) {
			if (moisure == EnumMoisture.NORMAL) {
				return "";
			}
		}
		return TextFormatting.GRAY + I18N.localise("botany.moisture") + ": " + EnumHelper.getLocalisedName(moisure, withColor);
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings("deprecation")
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		switch (side) {
			case UP:
				return true;

			case NORTH:
			case SOUTH:
			case WEST:
			case EAST:
				IBlockState iblockstate = blockAccess.getBlockState(pos.offset(side));
				Block block = iblockstate.getBlock();
				return !iblockstate.isOpaqueCube() && block != Blocks.FARMLAND && block != Blocks.GRASS_PATH && block != this;
		}
		return super.shouldSideBeRendered(blockState, blockAccess, pos, side);
	}

	/**
	 * Used to determine ambient occlusion and culling when rebuilding chunks for render
	 */
	@Override
	@SuppressWarnings("deprecation")
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	@SuppressWarnings("deprecation")
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public String getUnlocalizedName() {
		return super.getUnlocalizedName().replaceFirst("tile.", "");
	}

	@Override
	@SuppressWarnings("deprecation")
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return SOIL_BLOCK_AABB;
	}

	@Nullable
	@Override
	@SuppressWarnings("deprecation")
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		return SOIL_BLOCK_AABB;
	}

	@Override
	@SuppressWarnings("deprecation")
	public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World worldIn, BlockPos pos) {
		return SOIL_BLOCK_AABB;
	}

	@Override
	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list) {
		for (int i = 0; i < 9; ++i) {
			list.add(new ItemStack(this, 1, i));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerModel(Item item, IModelManager manager) {
		for (EnumAcidity acidity : EnumAcidity.values()) {
			for (EnumMoisture moisture : EnumMoisture.values()) {
				String modelName = "";
				if (acidity != EnumAcidity.NEUTRAL) {
					modelName += acidity.getName();
				}

				if (moisture != EnumMoisture.NORMAL) {
					if (!modelName.isEmpty()) {
						modelName += "_";
					}
					modelName += moisture.getName();
				}

				if (modelName.isEmpty()) {
					modelName = "normal";
				}

				String identifier;
				if (weedKilled) {
					identifier = type.getName() + "_no_weed/" + modelName;
				} else {
					identifier = type.getName() + '/' + modelName;
				}
				manager.registerItemModel(item, moisture.ordinal() + acidity.ordinal() * 3, identifier);
			}
		}
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, MOISTURE, ACIDITY);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return getMeta(state.getValue(ACIDITY), state.getValue(MOISTURE));
	}

	@Override
	@SuppressWarnings("deprecation")
	public IBlockState getStateFromMeta(int meta) {
		EnumMoisture moisture = EnumMoisture.values()[meta % 3];
		EnumAcidity acidity = EnumAcidity.values()[meta / 3];
		return getDefaultState().withProperty(MOISTURE, moisture).withProperty(ACIDITY, acidity);
	}

	public EnumSoilType getType() {
		return type;
	}

	@Override
	public int damageDropped(IBlockState state) {
		return getMetaFromState(state);
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		EnumMoisture moisture = state.getValue(MOISTURE);
		EnumMoisture desiredMoisture = BotanyCore.getGardening().getNaturalMoisture(world, pos);
		if (desiredMoisture.ordinal() > moisture.ordinal()) {
			moisture = ((moisture == EnumMoisture.DRY) ? EnumMoisture.NORMAL : EnumMoisture.DAMP);
		} else if (desiredMoisture.ordinal() < moisture.ordinal()) {
			moisture = ((moisture == EnumMoisture.DAMP) ? EnumMoisture.NORMAL : EnumMoisture.DRY);
		}

		IBlockState blockState = state.withProperty(MOISTURE, moisture);
		if (state != blockState) {
			world.setBlockState(pos, blockState, 2);
		}

		if (!weedKilled) {
			if (rand.nextInt(5 - getType(world, pos).ordinal()) != 0) {
				return;
			}
			pos = pos.up();
			if (!world.isAirBlock(pos)) {
				return;
			}
			world.setBlockState(pos, ModuleGardening.plant.getStateFromMeta(PlantType.WEEDS.ordinal()), 2);
		}
	}

	@Override
	@SuppressWarnings("deprecation")
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		super.neighborChanged(state, worldIn, pos, blockIn, fromPos);

		if (worldIn.getBlockState(pos.up()).getMaterial().isSolid()) {
			worldIn.setBlockState(pos, Blocks.DIRT.getDefaultState());
		}
	}

	@Override
	public EnumAcidity getPH(World world, BlockPos pos) {
		return world.getBlockState(pos).getValue(ACIDITY);
	}

	@Override
	public EnumMoisture getMoisture(World world, BlockPos pos) {
		return world.getBlockState(pos).getValue(MOISTURE);
	}

	@Override
	public EnumSoilType getType(World world, BlockPos pos) {
		return type;
	}

	@Override
	public boolean fertilise(World world, BlockPos pos, EnumSoilType maxLevel) {
		EnumSoilType type = getType(world, pos);
		if (type.ordinal() >= maxLevel.ordinal()) {
			return false;
		}
		IBlockState old = world.getBlockState(pos);
		IBlockState newState = BotanyCore.getGardening().getSoil(maxLevel, weedKilled, old.getValue(MOISTURE), old.getValue(ACIDITY));
		return world.setBlockState(pos, newState, 2);
	}

	@Override
	public boolean degrade(World world, BlockPos pos, EnumSoilType minLevel) {
		EnumSoilType type = getType(world, pos);
		if (type.ordinal() <= minLevel.ordinal()) {
			return false;
		}
		IBlockState old = world.getBlockState(pos);
		IBlockState newState = BotanyCore.getGardening().getSoil(minLevel, weedKilled, old.getValue(MOISTURE), old.getValue(ACIDITY));
		return world.setBlockState(pos, newState, 2);
	}

	@Override
	public boolean setPH(World world, BlockPos pos, EnumAcidity pH) {
		IBlockState blockState = world.getBlockState(pos);
		return world.setBlockState(pos, blockState.withProperty(ACIDITY, pH));
	}

	@Override
	public boolean setMoisture(World world, BlockPos pos, EnumMoisture moisture) {
		IBlockState blockState = world.getBlockState(pos);
		return world.setBlockState(pos, blockState.withProperty(MOISTURE, moisture));
	}

	@Override
	public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor) {
		super.onNeighborChange(world, pos, neighbor);
		if (world.isSideSolid(pos.up(), EnumFacing.DOWN, false)) {
			((World) world).setBlockState(pos, Blocks.DIRT.getDefaultState(), 2);
		}
	}

	@Override
	public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, IPlantable plantable) {
		IBlockState plant = plantable.getPlant(world, pos.up());
		if (ForestryAPI.moduleManager.isModuleEnabled(Constants.BOTANY_MOD_ID, BotanyModuleUIDs.FLOWERS)) {
			if (plant.getBlock() == ModuleFlowers.flower) {
				return true;
			}
		}

		if (plant.getBlock() == ModuleGardening.plant) {
			return !weedKilled || !BlockPlant.isWeed(world, pos);
		}
		return world instanceof World && Blocks.DIRT.canSustainPlant(state, world, pos, direction, plantable);
	}

	@Override
	public boolean resistsWeeds(World world, BlockPos pos) {
		return weedKilled;
	}

	public boolean isWeedKilled() {
		return weedKilled;
	}
}
