package binnie.extratrees.block;

import binnie.Constants;
import binnie.extratrees.block.property.PropertyETType;
import binnie.extratrees.genetics.ETTreeDefinition;
import forestry.api.arboriculture.IFruitProvider;
import forestry.api.arboriculture.ITreeGenome;
import forestry.api.core.IItemModelRegister;
import forestry.api.core.IModelManager;
import forestry.api.core.Tabs;
import forestry.core.blocks.IColoredBlock;
import forestry.core.proxy.Proxies;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class BlockETDecorativeLeaves extends Block implements IItemModelRegister, IColoredBlock, IShearable {
	private static final int VARIANTS_PER_BLOCK = 16;
	private final int blockNumber;

	public BlockETDecorativeLeaves(int blockNumber) {
		super(Material.LEAVES);
		this.blockNumber = blockNumber;
		this.setCreativeTab(Tabs.tabArboriculture);
		this.setHardness(0.2F);
		this.setLightOpacity(1);
		this.setSoundType(SoundType.PLANT);
		String name = "leaves.decorative." + blockNumber;
		setUnlocalizedName(name);
		setRegistryName(new ResourceLocation(Constants.EXTRA_TREES_MOD_ID, name));
	}

	public static List<BlockETDecorativeLeaves> create() {
		List<BlockETDecorativeLeaves> blocks = new ArrayList<>();
		final int blockCount = PropertyETType.getBlockCount(VARIANTS_PER_BLOCK);
		for (int blockNumber = 0; blockNumber < blockCount; blockNumber++) {
			final PropertyETType variant = PropertyETType.create("variant", blockNumber, VARIANTS_PER_BLOCK);
			BlockETDecorativeLeaves block = new BlockETDecorativeLeaves(blockNumber) {
				@Override
				public PropertyETType getVariant() {
					return variant;
				}
			};
			blocks.add(block);
		}
		return blocks;
	}

	public int getBlockNumber() {
		return blockNumber;
	}

	public abstract PropertyETType getVariant();

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, getVariant());
	}

	/**
	 * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
	 */
	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, NonNullList<ItemStack> list) {
		for (IBlockState state : getBlockState().getValidStates()) {
			int meta = getMetaFromState(state);
			ItemStack itemStack = new ItemStack(item, 1, meta);
			list.add(itemStack);
		}
	}

	/**
	 * Called When an Entity Collided with the Block
	 */
	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
		entityIn.motionX *= 0.4D;
		entityIn.motionZ *= 0.4D;
	}

	/**
	 * Used to determine ambient occlusion and culling when rebuilding chunks for render
	 */
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return !Proxies.render.fancyGraphicsEnabled();
	}

	@Override
	public boolean causesSuffocation(IBlockState state) {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		return (Proxies.render.fancyGraphicsEnabled() || blockAccess.getBlockState(pos.offset(side)).getBlock() != this) && super.shouldSideBeRendered(blockState, blockAccess, pos, side);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT_MIPPED; // fruit overlays require CUTOUT_MIPPED, even in Fast graphics
	}

	/* MODELS */
	@Override
	@SideOnly(Side.CLIENT)
	public void registerModel(Item item, IModelManager manager) {
		for (IBlockState state : blockState.getValidStates()) {
			int meta = getMetaFromState(state);
			ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(getRegistryName(), "inventory"));
		}
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	@Override
	public IBlockState getStateFromMeta(int meta) {
		ETTreeDefinition type = getTreeType(meta);
		return getDefaultState().withProperty(getVariant(), type);
	}

	public ETTreeDefinition getTreeType(int meta) {
		int variantCount = getVariant().getAllowedValues().size();
		int variantMeta = (meta % variantCount) + blockNumber * VARIANTS_PER_BLOCK;
		return ETTreeDefinition.byMetadata(variantMeta);
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(IBlockState state) {
	    /*
		  Gets the metadata of the item this Block can drop. This method is called when the block gets destroyed. It returns
		  the metadata of the dropped item based on the old metadata of the block.
		 */
		return damageDropped(state);
	}

	/**
	 * Gets the metadata of the item this Block can drop. This method is called when the block gets destroyed. It
	 * returns the metadata of the dropped item based on the old metadata of the block.
	 */
	@Override
	public int damageDropped(IBlockState state) {
		return state.getValue(getVariant()).getMetadata() - blockNumber * VARIANTS_PER_BLOCK;
	}

	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		return Collections.emptyList();
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		ETTreeDefinition type = getTreeType(meta);
		return getDefaultState().withProperty(getVariant(), type);
	}

	/* PROPERTIES */
	@Override
	public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face) {
		return 60;
	}

	@Override
	public boolean isFlammable(IBlockAccess world, BlockPos pos, EnumFacing face) {
		return true;
	}

	@Override
	public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face) {
		if (face == EnumFacing.DOWN) {
			return 20;
		} else if (face != EnumFacing.UP) {
			return 10;
		} else {
			return 5;
		}
	}

	@Override
	public boolean isShearable(ItemStack item, IBlockAccess world, BlockPos pos) {
		return true;
	}

	@Override
	public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
		IBlockState state = world.getBlockState(pos);
		return Collections.singletonList(new ItemStack(this, 1, damageDropped(state)));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex) {
		ETTreeDefinition treeDefinition = state.getValue(getVariant());

		ITreeGenome genome = treeDefinition.getGenome();

		if (tintIndex == 0) {
			return genome.getPrimary().getLeafSpriteProvider().getColor(false);
		} else {
			IFruitProvider fruitProvider = genome.getFruitProvider();
			return fruitProvider.getDecorativeColor();
		}
	}
}