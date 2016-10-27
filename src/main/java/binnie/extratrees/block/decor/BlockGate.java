package binnie.extratrees.block.decor;

import binnie.Binnie;
import binnie.core.block.IBlockMetadata;
import binnie.core.block.TileEntityMetadata;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.block.IPlankType;
import binnie.extratrees.block.WoodManager;
import forestry.api.core.Tabs;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.SoundType;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

public class BlockGate extends BlockFenceGate implements IBlockMetadata {
	public BlockGate() {
		super(BlockPlanks.EnumType.OAK);
		this.setCreativeTab(Tabs.tabArboriculture);
		this.setResistance(5.0f);
		this.setHardness(2.0f);
		this.setSoundType(SoundType.WOOD);
		setRegistryName("blockGate");
	}

	@Override
	public void getSubBlocks(final Item itemIn, final CreativeTabs par2CreativeTabs, final List<ItemStack> itemList) {
		for (final IPlankType type : WoodManager.getAllPlankTypes()) {
			final ItemStack gate = WoodManager.getGate(type);
			if (gate != null) {
				itemList.add(gate);
			}
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
//
//	@Override
//	public IIcon getIcon(final int side, final int meta) {
//		return WoodManager.getPlankType(meta).getIcon();
//	}

	@Override
	public void dropAsStack(final World world, final BlockPos pos, final ItemStack drop) {
		//this.dropBlockAsItem(world, x, y, z, drop);
	}

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
		return Binnie.Language.localise(ExtraTrees.instance, "block.woodgate.name", WoodManager.getPlankType(meta).getName());
	}

	@Override
	public void getBlockTooltip(final ItemStack par1ItemStack, final List par3List) {
	}

//	@Override
//	public void breakBlock(final World par1World, final int par2, final int par3, final int par4, final Block par5, final int par6) {
//		super.breakBlock(par1World, par2, par3, par4, par5, par6);
//		par1World.removeTileEntity(par2, par3, par4);
//	}

	@Override
	public boolean isWood(IBlockAccess world, BlockPos pos) {
		return true;
	}

	@Override
	public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face) {
		return 20;
	}

	@Override
	public boolean isFlammable(IBlockAccess world, BlockPos pos, EnumFacing face) {
		return true;
	}

	@Override
	public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face) {
		return 5;
	}

//	@Override
//	public ItemStack getPickBlock(final MovingObjectPosition target, final World world, final int x, final int y, final int z) {
//		return BlockMetadata.getPickBlock(world, x, y, z);
//	}

//	@Override
//	public boolean getBlocksMovement(final IBlockAccess p_149655_1_, final int p_149655_2_, final int p_149655_3_, final int p_149655_4_) {
//		return false;
//	}
}
