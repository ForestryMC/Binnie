package binnie.extratrees.block.decor;

import binnie.Binnie;
import binnie.core.block.IBlockMetadata;
import binnie.core.block.TileEntityMetadata;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.block.IFenceProvider;
import binnie.extratrees.block.PlankType;
import binnie.extratrees.block.WoodManager;
import forestry.api.core.Tabs;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

public class BlockFence extends net.minecraft.block.BlockFence implements IBlockMetadata, IBlockFence {
	public BlockFence(String name) {
		super(Material.WOOD, MapColor.WOOD);
		this.setCreativeTab(Tabs.tabArboriculture);
		this.setRegistryName(name);
		this.setResistance(5.0f);
		this.setHardness(2.0f);
		this.setSoundType(SoundType.WOOD);
	}

	@Override
	public void getSubBlocks(final Item itemIn, final CreativeTabs par2CreativeTabs, final List<ItemStack> itemList) {
		for (final IFenceProvider type : PlankType.ExtraTreePlanks.values()) {
			itemList.add(type.getFence());
		}
	}

//	@Override
//	@SideOnly(Side.CLIENT)
//	public IIcon getIcon(final IBlockAccess world, final int x, final int y, final int z, final int side) {
//		final TileEntityMetadata tile = TileEntityMetadata.getTile(world, x, y, z);
//		if (tile != null) {
//			return this.getIcon(side, tile.getTileMetadata());
//		}
//		return super.getIcon(world, x, y, z, side);
//	}

	public FenceDescription getDescription(final int meta) {
		return WoodManager.getFenceDescription(meta);
	}

//	@Override
//	public IIcon getIcon(final int side, final int meta) {
//		return this.getDescription(meta).getPlankType().getIcon();
//	}
//
//	@Override
//	public int getRenderType() {
//		return ExtraTrees.fenceID;
//	}

	@Override
	public void dropAsStack(final World world, final BlockPos pos, final ItemStack drop) {
		//this.dropBlockAsItem(world, x, y, z, drop);
	}
//
//	@Override
//	public ArrayList<ItemStack> getDrops(final World world, final int x, final int y, final int z, final int blockMeta, final int fortune) {
//		return BlockMetadata.getBlockDropped(this, world, x, y, z, blockMeta);
//	}
//
//	@Override
//	public boolean removedByPlayer(final World world, final EntityPlayer player, final int x, final int y, final int z) {
//		return BlockMetadata.breakBlock(this, player, world, x, y, z);
//	}

	@Override
	public TileEntity createNewTileEntity(final World var1, final int i) {
		return new TileEntityMetadata();
	}

//	@Override
//	public boolean hasTileEntity(final int meta) {
//		return true;
//	}
//
//	@Override
//	public boolean onBlockEventReceived(final World par1World, final int par2, final int par3, final int par4, final int par5, final int par6) {
//		super.onBlockEventReceived(par1World, par2, par3, par4, par5, par6);
//		final TileEntity tileentity = par1World.getTileEntity(par2, par3, par4);
//		return tileentity != null && tileentity.receiveClientEvent(par5, par6);
//	}

	@Override
	public int getPlacedMeta(final ItemStack stack, final World world, final BlockPos pos, final EnumFacing clickedBlock) {
		return TileEntityMetadata.getItemDamage(stack);
	}

	@Override
	public int getDroppedMeta(final int blockMeta, final int tileMeta) {
		return tileMeta;
	}

	@Override
	public String getBlockName(final ItemStack par1ItemStack) {
		final int meta = TileEntityMetadata.getItemDamage(par1ItemStack);
		return Binnie.Language.localise(ExtraTrees.instance, "block.woodfence.name", this.getDescription(meta).getPlankType().getName());
	}

	@Override
	public void getBlockTooltip(final ItemStack par1ItemStack, final List par3List) {
	}


	@Override
	public boolean canPlaceTorchOnTop(IBlockState state, IBlockAccess world, BlockPos pos) {
		return true;
	}

//	@Override
//	public boolean canConnectTo(IBlockAccess worldIn, BlockPos pos) {
//		if (!this.isFence(world, x, y, z)) {
//			final Block block = world.getBlock(x, y, z);
//			return block != null && block.getMaterial().isOpaque() && block.renderAsNormalBlock();
//		}
//		return true;
//	}

//	public boolean isFence(final IBlockAccess world, final int x, final int y, final int z) {
//		final Block block = world.getBlock(x, y, z);
//		return canConnect(block);
//	}

//	public static boolean canConnect(final Block block) {
//		return block == ((ItemBlock) Mods.Forestry.item("fences")).field_150939_a || block == Blocks.fence || block == Blocks.fence_gate || block == Blocks.nether_brick_fence || block instanceof IBlockFence || block == ExtraTrees.blockGate;
//	}
//
//	@Override
//	public void breakBlock(final World par1World, final int par2, final int par3, final int par4, final Block par5, final int par6) {
//		super.breakBlock(par1World, par2, par3, par4, par5, par6);
//		par1World.removeTileEntity(par2, par3, par4);
//	}

	@Override
	public boolean isWood(final IBlockAccess world, final BlockPos pos) {
		return true;
	}

	@Override
	public int getFlammability(final IBlockAccess world, final BlockPos pos, final EnumFacing face) {
		return 20;
	}

	@Override
	public boolean isFlammable(final IBlockAccess world, final BlockPos pos, final EnumFacing face) {
		return true;
	}

	@Override
	public int getFireSpreadSpeed(final IBlockAccess world, final BlockPos pos, final EnumFacing face) {
		return 5;
	}

//	@Override
//	@SideOnly(Side.CLIENT)
//	public void registerBlockIcons(final IIconRegister p_149651_1_) {
//	}

//
//	@Override
//	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
//		return BlockMetadata.getPickBlock(state, target, world, pos, player);
//	}

}
