// 
// Decompiled by Procyon v0.5.30
// 

package binnie.botany.ceramic;

import net.minecraft.util.MovingObjectPosition;
import binnie.botany.Botany;
import net.minecraft.client.renderer.texture.IIconRegister;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
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
import binnie.botany.CreativeTabBotany;
import net.minecraft.block.material.Material;
import binnie.core.block.IBlockMetadata;
import net.minecraft.block.Block;

public class BlockCeramic extends Block implements IBlockMetadata
{
	public BlockCeramic() {
		super(Material.rock);
		this.setHardness(1.0f);
		this.setResistance(5.0f);
		this.setBlockName("ceramic");
		this.setCreativeTab(CreativeTabBotany.instance);
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
		return EnumFlowerColor.get(meta).getName() + " Ceramic Block";
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
		this.blockIcon = Botany.proxy.getIcon(register, "ceramic");
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
	public ItemStack getPickBlock(final MovingObjectPosition target, final World world, final int x, final int y, final int z) {
		return BlockMetadata.getPickBlock(world, x, y, z);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderColor(final int meta) {
		return EnumFlowerColor.get(meta).getColor(false);
	}
}
