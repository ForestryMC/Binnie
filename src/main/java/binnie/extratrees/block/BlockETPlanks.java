package binnie.extratrees.block;

import forestry.api.core.IItemModelRegister;
import forestry.api.core.IModelManager;
import forestry.api.core.Tabs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static net.minecraft.block.BlockFence.*;

public class BlockETPlanks extends Block implements IItemModelRegister {
	protected static final int VARIANTS_PER_BLOCK = 16;
	protected static final int VARIANTS_META_MASK = VARIANTS_PER_BLOCK - 1;
	private final boolean fireproof;
	public static final int GROUP_COUNT = (int) Math.ceil(EnumExtraTreeLog.values().length / (float) VARIANTS_PER_BLOCK);
	protected int offset = -1;
	protected static PropertyEnum<EnumExtraTreeLog>[] plankTypes;

	static {
		plankTypes = new PropertyEnum[GROUP_COUNT];
		for (int i = 0; i < GROUP_COUNT; i++) {
			int finalI = i;
			List allowed = Arrays.stream(EnumExtraTreeLog.values()).filter(l -> l.getMetadata() >= finalI * VARIANTS_PER_BLOCK && l.getMetadata() < finalI * VARIANTS_PER_BLOCK + VARIANTS_PER_BLOCK).collect(Collectors.toList());
			plankTypes[i] = PropertyEnum.create("wood_type", EnumExtraTreeLog.class, allowed);
		}
	}

	public BlockETPlanks(int offset, boolean fireproof) {
		super(Material.WOOD);
		this.fireproof = fireproof;
		this.offset = offset;
		this.setRegistryName("plank." + (fireproof ? "fireproof." : "") + offset);
		this.setCreativeTab(Tabs.tabArboriculture);
		this.setResistance(5.0f);
		this.setHardness(2.0f);
		this.setHarvestLevel("axe", 0);
		this.setSoundType(SoundType.WOOD);
	}

	public static class PlankItemBlock<V extends BlockETPlanks> extends ItemBlock {
		EnumExtraTreeLog[] values;

		public PlankItemBlock(V block) {
			super(block);
			setHasSubtypes(true);
			setMaxDamage(0);
			setRegistryName(block.getRegistryName());
			values = block.getVariant().getAllowedValues().toArray(new EnumExtraTreeLog[]{});
		}

		@Override
		public int getMetadata(int damage) {
			return damage;
		}

		@Override
		public String getUnlocalizedName(ItemStack stack) {
			return "tile.plank." + values[stack.getMetadata()].getName();
		}
	}


	@Override
	public float getBlockHardness(IBlockState blockState, World worldIn, BlockPos pos) {
		int meta = getMetaFromState(blockState);
		EnumExtraTreeLog woodType = getWoodType(meta);
		return woodType.getHardness();
	}

	@Override
	public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face) {
		if (fireproof) {
			return 0;
		}
		return 20;
	}

	@Override
	public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face) {
		if (fireproof) {
			return 0;
		}
		return 5;
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return damageDropped(state);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		EnumExtraTreeLog woodType = getWoodType(meta);
		return getDefaultState().withProperty(getVariant(), woodType);
	}


	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, getVariant());
	}

	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List<ItemStack> list) {
		list.addAll(getVariant().getAllowedValues().stream().map(woodType -> new ItemStack(this, 1, woodType.getMetadata() % VARIANTS_PER_BLOCK)).collect(Collectors.toList()));
	}


	@Nonnull
	public PropertyEnum<EnumExtraTreeLog> getVariant(){
		return plankTypes[offset];
	}

	@Override
	public final int damageDropped(IBlockState state) {
		return state.getValue(getVariant()).getMetadata() % VARIANTS_PER_BLOCK;
	}

	@Override
	public final IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		EnumExtraTreeLog woodType = getWoodType(meta);
		return getDefaultState().withProperty(getVariant(), woodType);
	}

	public EnumExtraTreeLog getWoodType(int meta) {
		return EnumExtraTreeLog.values()[offset * VARIANTS_PER_BLOCK + (meta & VARIANTS_META_MASK)];
	}

	@Override
	public void registerModel(Item item, IModelManager manager) {

	}

//	public String getBlockName(final ItemStack par1ItemStack) {
//		final int meta = TileEntityMetadata.getItemDamage(par1ItemStack);
//		return Binnie.Language.localise(ExtraTrees.instance, "block.plank.name", PlankType.ExtraTreePlanks.values()[meta].getName());
//	}

//	@Override
//	@SideOnly(Side.CLIENT)
//	public IIcon getIcon(final IBlockAccess world, final int x, final int y, final int z, final int side) {
//		final TileEntityMetadata tile = TileEntityMetadata.getTile(world, x, y, z);
//		if (tile != null) {
//			return this.getIcon(side, tile.getTileMetadata());
//		}
//		return super.getIcon(world, x, y, z, side);
//	}
//
//	@Override
//	public IIcon getIcon(final int side, final int meta) {
//		return PlankType.ExtraTreePlanks.values()[meta].getIcon();
//	}

//	@Override
//	@SideOnly(Side.CLIENT)
//	public void registerBlockIcons(final IIconRegister register) {
//		for (final PlankType.ExtraTreePlanks plank : PlankType.ExtraTreePlanks.values()) {
//			plank.loadIcon(register);
//		}
//	}

}
