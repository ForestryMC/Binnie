package binnie.botany.gardening;

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

import forestry.api.core.IItemModelRegister;
import forestry.api.core.IModelManager;

import binnie.botany.Botany;
import binnie.botany.CreativeTabBotany;
import binnie.botany.api.EnumAcidity;
import binnie.botany.api.EnumMoisture;
import binnie.botany.api.EnumSoilType;
import binnie.botany.api.IBlockSoil;
import binnie.botany.core.BotanyCore;
import binnie.core.util.I18N;

public class BlockSoil extends Block implements IBlockSoil, IItemModelRegister {
	public static final PropertyEnum<EnumMoisture> MOISTURE = PropertyEnum.create("moisture", EnumMoisture.class);
	public static final PropertyEnum<EnumAcidity> ACIDITY = PropertyEnum.create("acidity", EnumAcidity.class);
	public static final AxisAlignedBB SOIL_BLOCK_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.9375D, 1.0D);
	private final boolean weedKilled;
	private final EnumSoilType type;

	public BlockSoil(EnumSoilType type, String blockName, boolean weedKilled) {
		super(Material.GROUND);
		this.weedKilled = weedKilled;
		setUnlocalizedName("botany.soil." + type.getName());
		this.setCreativeTab(CreativeTabBotany.instance);
		this.setRegistryName(blockName);
		this.setTickRandomly(true);
		this.setLightOpacity(255);
		this.setHardness(0.5f);
		this.setSoundType(SoundType.GROUND);
		this.type = type;
	}

	public static int getMeta(final EnumAcidity acid, final EnumMoisture moisture) {
		return acid.ordinal() * 3 + moisture.ordinal();
	}

	public static String getPH(ItemStack stack, boolean withColor, boolean byNeutralNone) {
		EnumAcidity ph = EnumAcidity.values()[stack.getItemDamage() / 3];
		if (byNeutralNone) {
			if (ph == EnumAcidity.NEUTRAL) {
				return "";
			}
		}
		return TextFormatting.GRAY + I18N.localise("botany.ph") + ": " + ph.getLocalisedName(withColor);
	}

	public static String getMoisture(ItemStack stack, boolean withColor, boolean byNormalNone) {
		EnumMoisture moisure = EnumMoisture.values()[stack.getItemDamage() % 3];
		if (byNormalNone) {
			if (moisure == EnumMoisture.NORMAL) {
				return "";
			}
		}
		return TextFormatting.GRAY + I18N.localise("botany.moisture") + ": " + moisure.getLocalisedName(withColor);
	}

	@Override
	@SideOnly(Side.CLIENT)
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
			default:
				return super.shouldSideBeRendered(blockState, blockAccess, pos, side);
		}
	}

	/**
	 * Used to determine ambient occlusion and culling when rebuilding chunks for render
	 */
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public String getUnlocalizedName() {
		return super.getUnlocalizedName().replaceFirst("tile.", "");
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return SOIL_BLOCK_AABB;
	}

	@Nullable
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		return SOIL_BLOCK_AABB;
	}

	@Override
	public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World worldIn, BlockPos pos) {
		return SOIL_BLOCK_AABB;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(final Item itemIn, final CreativeTabs tab, final NonNullList<ItemStack> list) {
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
				final String identifier;
				if (weedKilled) {
					identifier = type.getName() + "_no_weed/" + modelName;
				} else {
					identifier = type.getName() + "/" + modelName;
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
	public IBlockState getStateFromMeta(int meta) {
		final EnumMoisture moisture = EnumMoisture.values()[meta % 3];
		final EnumAcidity acidity = EnumAcidity.values()[meta / 3];
		return getDefaultState().withProperty(MOISTURE, moisture).withProperty(ACIDITY, acidity);
	}

	public EnumSoilType getType() {
		return this.type;
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
		}
		if (desiredMoisture.ordinal() < moisture.ordinal()) {
			moisture = ((moisture == EnumMoisture.DAMP) ? EnumMoisture.NORMAL : EnumMoisture.DRY);
		}
		IBlockState blockState = state.withProperty(MOISTURE, moisture);
		if (state != blockState) {
			world.setBlockState(pos, blockState, 2);
		}
		if(!weedKilled){
			if(rand.nextInt(5 - getType(world, pos).ordinal()) != 0){
				return;
			}
			pos = pos.up();
			if(!world.isAirBlock(pos)){
				return;
			}
			world.setBlockState(pos, Botany.plant.getStateFromMeta(BlockPlant.Type.Weeds.ordinal()), 2);
		}
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		super.neighborChanged(state, worldIn, pos, blockIn, fromPos);

		if (worldIn.getBlockState(pos.up()).getMaterial().isSolid()) {
			worldIn.setBlockState(pos, Blocks.DIRT.getDefaultState());
		}
	}

	@Override
	public EnumAcidity getPH(final World world, final BlockPos pos) {
		return world.getBlockState(pos).getValue(ACIDITY);
	}

	@Override
	public EnumMoisture getMoisture(final World world, final BlockPos pos) {
		return world.getBlockState(pos).getValue(MOISTURE);
	}

	@Override
	public EnumSoilType getType(final World world, final BlockPos pos) {
		return this.type;
	}

	@Override
	public boolean fertilise(final World world, final BlockPos pos, final EnumSoilType maxLevel) {
		final EnumSoilType type = this.getType(world, pos);
		if (type.ordinal() >= maxLevel.ordinal()) {
			return false;
		}
		final IBlockState old = world.getBlockState(pos);
		return world.setBlockState(pos, BotanyCore.getGardening().getSoilBlock(maxLevel, this.weedKilled).getDefaultState().withProperty(ACIDITY, old.getValue(ACIDITY)).withProperty(MOISTURE, old.getValue(MOISTURE)), 2);
	}

	@Override
	public boolean degrade(final World world, final BlockPos pos, final EnumSoilType minLevel) {
		final EnumSoilType type = this.getType(world, pos);
		if (type.ordinal() <= minLevel.ordinal()) {
			return false;
		}
		final IBlockState old = world.getBlockState(pos);
		return world.setBlockState(pos, BotanyCore.getGardening().getSoilBlock(minLevel, this.weedKilled).getDefaultState().withProperty(ACIDITY, old.getValue(ACIDITY)).withProperty(MOISTURE, old.getValue(MOISTURE)), 2);
	}

	@Override
	public boolean setPH(final World world, final BlockPos pos, final EnumAcidity pH) {
		IBlockState blockState = world.getBlockState(pos);
		return world.setBlockState(pos, blockState.withProperty(ACIDITY, pH));
	}

	@Override
	public boolean setMoisture(final World world, final BlockPos pos, final EnumMoisture moisture) {
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
		if (plant.getBlock() == Botany.flower) {
			return true;
		}
		if (plant.getBlock() == Botany.plant) {
			return !this.weedKilled || !BlockPlant.isWeed(world, pos);
		}
		return world instanceof World && Blocks.DIRT.canSustainPlant(state, world, pos, direction, plantable);
	}

	@Override
	public boolean resistsWeeds(final World world, final BlockPos pos) {
		return this.weedKilled;
	}

	public boolean isWeedKilled() {
		return weedKilled;
	}
}
