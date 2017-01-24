package binnie.extratrees.block.decor;

import binnie.Binnie;
import binnie.core.block.BlockMetadata;
import binnie.core.block.IBlockMetadata;
import binnie.core.block.TileEntityMetadata;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.block.IFenceProvider;
import binnie.extratrees.block.PlankType;
import binnie.extratrees.block.WoodManager;
import forestry.api.core.Tabs;
import forestry.arboriculture.PluginArboriculture;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
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
	public boolean eventReceived(IBlockState state, World world, BlockPos pos, int id, int param) {
		TileEntity tileentity = world.getTileEntity(pos);
		return tileentity != null && tileentity.receiveClientEvent(id, param);
	}
	
	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		return BlockMetadata.getBlockDroppedAsList(this, world, pos);
	}
	
	@Override
	public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
		return BlockMetadata.breakBlock(this, player, world, pos);
	}

	@Override
	public TileEntity createNewTileEntity(final World var1, final int i) {
		return new TileEntityMetadata();
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

	@Override
	public int getPlacedMeta(final ItemStack stack, final World world, final BlockPos pos, final EnumFacing clickedBlock) {
		return TileEntityMetadata.getItemDamage(stack);
	}

	@Override
	public int getDroppedMeta(IBlockState state, final int tileMeta) {
		return tileMeta;
	}

	@Override
	public String getDisplayName(final ItemStack par1ItemStack) {
		final int meta = TileEntityMetadata.getItemDamage(par1ItemStack);
		return Binnie.LANGUAGE.localise(ExtraTrees.instance, "block.woodfence.name", this.getDescription(meta).getPlankType().getName());
	}

	@Override
	public boolean canPlaceTorchOnTop(IBlockState state, IBlockAccess world, BlockPos pos) {
		return true;
	}
	
	@Override
	public boolean canConnectTo(IBlockAccess worldIn, BlockPos pos) {
		IBlockState state = worldIn.getBlockState(pos);
		Block block = state.getBlock();
		if (PluginArboriculture.validFences.contains(block)) {
			return true;
		}

		if (block != Blocks.BARRIER) {
			Material blockMaterial = block.getMaterial(state);
			if (block instanceof BlockFence || block instanceof BlockFenceGate || block instanceof IBlockFence) {
				return blockMaterial == this.blockMaterial;
			}
			if (blockMaterial.isOpaque() && state.isFullCube() && blockMaterial != Material.GOURD) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		super.breakBlock(world, pos, state);
		world.removeTileEntity(pos);
	}
	
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
	
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		return BlockMetadata.getPickBlock(world, pos);
	}

}
