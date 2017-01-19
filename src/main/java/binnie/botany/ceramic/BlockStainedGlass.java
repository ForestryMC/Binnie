package binnie.botany.ceramic;

import binnie.botany.CreativeTabBotany;
import binnie.botany.genetics.EnumFlowerColor;
import binnie.core.BinnieCore;
import binnie.core.block.BlockMetadata;
import binnie.core.block.IBlockMetadata;
import binnie.core.block.TileEntityMetadata;
import binnie.core.util.TileUtil;
import forestry.core.blocks.IColoredBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class BlockStainedGlass extends Block implements IBlockMetadata, IColoredBlock {
	public BlockStainedGlass() {
		super(Material.GLASS);
		this.setCreativeTab(CreativeTabBotany.instance);
		this.setRegistryName("stained");
	}

	@Override
	public int quantityDropped(final Random p_149745_1_) {
		return 0;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT_MIPPED;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		Block block2 = blockAccess.getBlockState(pos.offset(side)).getBlock();
		Block block3 = blockAccess.getBlockState(pos).getBlock();
		return true/*block3 != this && block3 != ExtraTrees.blockStained && super.shouldSideBeRendered(blockState, blockAccess, pos, side)*/;
	}

	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		List<ItemStack> list = new ArrayList<>();
		TileEntityMetadata tile = TileEntityMetadata.getTile(world, pos);
		if (tile != null && !tile.hasDroppedBlock()) {
			int meta = getDroppedMeta(getMetaFromState(state), tile.getTileMetadata());
			list.add(TileEntityMetadata.getItemStack(this, meta));
		}
		return list;
	}

	@Override
	public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
		List<ItemStack> drops = new ArrayList<>();
		TileCeramic ceramic = TileUtil.getTile(world, pos, TileCeramic.class);
		if (ceramic != null) {
			drops = Collections.singletonList(ceramic.getStack());
		}
		boolean hasBeenBroken = world.setBlockToAir(pos);
		if (hasBeenBroken && BinnieCore.proxy.isSimulating(world) && drops.size() > 0 && (player == null || !player.capabilities.isCreativeMode)) {
			for (ItemStack drop : drops) {
				spawnAsEntity(world, pos, drop);
			}
		}
		return hasBeenBroken;
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
	public boolean eventReceived(IBlockState state, World worldIn, BlockPos pos, int id, int param) {
		super.eventReceived(state, worldIn, pos, id, param);
		TileEntity tileentity = worldIn.getTileEntity(pos);
		return tileentity == null ? false : tileentity.receiveClientEvent(id, param);
	}

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
		return EnumFlowerColor.get(meta).getColourName() + " Pigmented Glass";
	}

	@Override
	public void getBlockTooltip(final ItemStack par1ItemStack, final List par3List) {
	}

	@Override
	public void dropAsStack(World world, BlockPos pos, ItemStack itemStack) {
		spawnAsEntity(world, pos, itemStack);

	}

	@Override
	public void getSubBlocks(final Item itemIn, final CreativeTabs par2CreativeTabs, final List<ItemStack> itemList) {
		for (final EnumFlowerColor c : EnumFlowerColor.values()) {
			itemList.add(TileEntityMetadata.getItemStack(this, c.ordinal()));
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

//	@Override
//	public IIcon getIcon(final int side, final int meta) {
//		return this.blockIcon;
//	}
//
//	@Override
//	@SideOnly(Side.CLIENT)
//	public void registerBlockIcons(final IIconRegister register) {
//		this.blockIcon = Botany.proxy.getIcon(register, "stained");
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

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		return BlockMetadata.getPickBlock(world, pos);
	}

	@Override
	public int colorMultiplier(IBlockState state, IBlockAccess worldIn, BlockPos pos, int tintIndex) {
		return EnumFlowerColor.get(getMetaFromState(state)).getColor(false);
	}
}
