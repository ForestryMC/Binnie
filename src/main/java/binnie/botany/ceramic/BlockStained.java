// 
// Decompiled by Procyon v0.5.30
// 

package binnie.botany.ceramic;

import net.minecraft.util.MovingObjectPosition;
import binnie.botany.Botany;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import java.util.List;
import binnie.botany.genetics.EnumFlowerColor;
import net.minecraftforge.common.util.ForgeDirection;
import binnie.core.block.TileEntityMetadata;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.entity.player.EntityPlayer;
import binnie.core.block.BlockMetadata;
import net.minecraft.item.ItemStack;
import java.util.ArrayList;
import net.minecraft.world.World;
import binnie.extratrees.ExtraTrees;
import net.minecraft.util.Facing;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import binnie.botany.CreativeTabBotany;
import net.minecraft.block.material.Material;
import binnie.core.block.IBlockMetadata;
import net.minecraft.block.Block;

public class BlockStained extends Block implements IBlockMetadata
{
	public BlockStained() {
		super(Material.glass);
		this.setCreativeTab(CreativeTabBotany.instance);
		this.setBlockName("stained");
	}

	@Override
	public int quantityDropped(final Random p_149745_1_) {
		return 0;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderBlockPass() {
		return 1;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(final IBlockAccess p_149646_1_, final int p_149646_2_, final int p_149646_3_, final int p_149646_4_, final int p_149646_5_) {
		final Block block2 = p_149646_1_.getBlock(p_149646_2_ - Facing.offsetsXForSide[p_149646_5_], p_149646_3_ - Facing.offsetsYForSide[p_149646_5_], p_149646_4_ - Facing.offsetsZForSide[p_149646_5_]);
		final Block block3 = p_149646_1_.getBlock(p_149646_2_, p_149646_3_, p_149646_4_);
		return block3 != this && block3 != ExtraTrees.blockStained && super.shouldSideBeRendered(p_149646_1_, p_149646_2_, p_149646_3_, p_149646_4_, p_149646_5_);
	}

	@Override
	public ArrayList<ItemStack> getDrops(final World world, final int x, final int y, final int z, final int blockMeta, final int fortune) {
		return BlockMetadata.getBlockDropped(this, world, x, y, z, blockMeta);
	}

	@Override
	public boolean removedByPlayer(final World world, final EntityPlayer player, final int x, final int y, final int z) {
		return BlockMetadata.breakBlock(this, player, world, x, y, z);
	}

	@Override
	public TileEntity createNewTileEntity(final World var1, final int i) {
		return new TileEntityMetadata();
	}

	@Override
	public boolean hasTileEntity(final int meta) {
		return true;
	}

	@Override
	public boolean onBlockEventReceived(final World par1World, final int par2, final int par3, final int par4, final int par5, final int par6) {
		super.onBlockEventReceived(par1World, par2, par3, par4, par5, par6);
		final TileEntity tileentity = par1World.getTileEntity(par2, par3, par4);
		return tileentity != null && tileentity.receiveClientEvent(par5, par6);
	}

	@Override
	public int getPlacedMeta(final ItemStack itemStack, final World world, final int x, final int y, final int z, final ForgeDirection direction) {
		return TileEntityMetadata.getItemDamage(itemStack);
	}

	@Override
	public int getDroppedMeta(final int blockMeta, final int tileMeta) {
		return tileMeta;
	}

	@Override
	public String getBlockName(final ItemStack itemStack) {
		final int meta = TileEntityMetadata.getItemDamage(itemStack);
		return EnumFlowerColor.get(meta).getName() + " Pigmented Glass";
	}

	@Override
	public void getBlockTooltip(final ItemStack itemStack, final List par3List) {
	}

	@Override
	public void dropAsStack(final World world, final int x, final int y, final int z, final ItemStack itemStack) {
		this.dropBlockAsItem(world, x, y, z, itemStack);
	}

	@Override
	public void getSubBlocks(final Item par1, final CreativeTabs par2CreativeTabs, final List itemList) {
		for (final EnumFlowerColor c : EnumFlowerColor.values()) {
			itemList.add(TileEntityMetadata.getItemStack(this, c.ordinal()));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(final IBlockAccess world, final int x, final int y, final int z, final int side) {
		final TileEntityMetadata tile = TileEntityMetadata.getTile(world, x, y, z);
		if (tile != null) {
			return this.getIcon(side, tile.getTileMetadata());
		}
		return super.getIcon(world, x, y, z, side);
	}

	@Override
	public IIcon getIcon(final int side, final int meta) {
		return this.blockIcon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(final IIconRegister register) {
		this.blockIcon = Botany.proxy.getIcon(register, "stained");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(final IBlockAccess world, final int x, final int y, final int z) {
		final TileEntityMetadata tile = TileEntityMetadata.getTile(world, x, y, z);
		if (tile != null) {
			return this.getRenderColor(tile.getTileMetadata());
		}
		return 16777215;
	}

	@Override
	public void breakBlock(final World par1World, final int par2, final int par3, final int par4, final Block par5, final int par6) {
		super.breakBlock(par1World, par2, par3, par4, par5, par6);
		par1World.removeTileEntity(par2, par3, par4);
	}

	@Override
	public boolean isWood(final IBlockAccess world, final int x, final int y, final int z) {
		return true;
	}

	@Override
	public int getFlammability(final IBlockAccess world, final int x, final int y, final int z, final ForgeDirection face) {
		return 20;
	}

	@Override
	public boolean isFlammable(final IBlockAccess world, final int x, final int y, final int z, final ForgeDirection face) {
		return true;
	}

	@Override
	public int getFireSpreadSpeed(final IBlockAccess world, final int x, final int y, final int z, final ForgeDirection face) {
		return 5;
	}

	@Override
	public ItemStack getPickBlock(final MovingObjectPosition target, final World world, final int x, final int y, final int z) {
		return BlockMetadata.getPickBlock(world, x, y, z);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderColor(final int meta) {
		return EnumFlowerColor.get(meta).getColor(false);
	}
}
