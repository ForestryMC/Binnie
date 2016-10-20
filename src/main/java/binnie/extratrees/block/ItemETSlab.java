// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extratrees.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.world.IBlockAccess;
import binnie.core.block.TileEntityMetadata;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import binnie.extratrees.ExtraTrees;
import net.minecraft.block.Block;
import binnie.core.block.ItemMetadata;

public class ItemETSlab extends ItemMetadata
{
	private final boolean isFullBlock;
	private final BlockETSlab theHalfSlab;
	private final BlockETSlab doubleSlab;

	public ItemETSlab(final Block block) {
		super(block);
		this.theHalfSlab = (BlockETSlab) ExtraTrees.blockSlab;
		this.doubleSlab = (BlockETSlab) ExtraTrees.blockDoubleSlab;
		this.isFullBlock = (block != this.theHalfSlab);
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean onItemUse(final ItemStack par1ItemStack, final EntityPlayer par2EntityPlayer, final World par3World, final int par4, final int par5, final int par6, final int par7, final float par8, final float par9, final float par10) {
		if (this.isFullBlock) {
			return super.onItemUse(par1ItemStack, par2EntityPlayer, par3World, par4, par5, par6, par7, par8, par9, par10);
		}
		if (par1ItemStack.stackSize == 0) {
			return false;
		}
		if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack)) {
			return false;
		}
		final Block i1 = par3World.getBlock(par4, par5, par6);
		final int j1 = par3World.getBlockMetadata(par4, par5, par6);
		final int tileMeta = TileEntityMetadata.getTileMetadata(par3World, par4, par5, par6);
		final int k1 = j1 & 0x7;
		final boolean flag = (j1 & 0x8) != 0x0;
		if (((par7 == 1 && !flag) || (par7 == 0 && flag)) && i1 == this.theHalfSlab && tileMeta == par1ItemStack.getItemDamage()) {
			if (par3World.checkNoEntityCollision(this.doubleSlab.getCollisionBoundingBoxFromPool(par3World, par4, par5, par6)) && par3World.setBlock(par4, par5, par6, this.doubleSlab, k1, 3)) {
				final TileEntityMetadata tile = TileEntityMetadata.getTile(par3World, par4, par5, par6);
				if (tile != null) {
					tile.setTileMetadata(tileMeta, true);
				}
				--par1ItemStack.stackSize;
			}
			return true;
		}
		return this.func_77888_a(par1ItemStack, par2EntityPlayer, par3World, par4, par5, par6, par7) || super.onItemUse(par1ItemStack, par2EntityPlayer, par3World, par4, par5, par6, par7, par8, par9, par10);
	}

	private boolean func_77888_a(final ItemStack par1ItemStack, final EntityPlayer par2EntityPlayer, final World par3World, int par4, int par5, int par6, final int par7) {
		if (par7 == 0) {
			--par5;
		}
		if (par7 == 1) {
			++par5;
		}
		if (par7 == 2) {
			--par6;
		}
		if (par7 == 3) {
			++par6;
		}
		if (par7 == 4) {
			--par4;
		}
		if (par7 == 5) {
			++par4;
		}
		final Block i1 = par3World.getBlock(par4, par5, par6);
		final int j1 = par3World.getBlockMetadata(par4, par5, par6);
		final int k1 = j1 & 0x7;
		final int tileMeta = TileEntityMetadata.getTileMetadata(par3World, par4, par5, par6);
		if (i1 == this.theHalfSlab && tileMeta == par1ItemStack.getItemDamage()) {
			if (par3World.checkNoEntityCollision(this.doubleSlab.getCollisionBoundingBoxFromPool(par3World, par4, par5, par6)) && par3World.setBlock(par4, par5, par6, this.doubleSlab, k1, 3)) {
				final TileEntityMetadata tile = TileEntityMetadata.getTile(par3World, par4, par5, par6);
				if (tile != null) {
					tile.setTileMetadata(tileMeta, false);
					par3World.markBlockForUpdate(par4, par5, par6);
				}
				--par1ItemStack.stackSize;
			}
			return true;
		}
		return false;
	}
}
