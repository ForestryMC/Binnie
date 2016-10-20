// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extratrees.block;

import binnie.Binnie;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.entity.player.EntityPlayer;
import binnie.core.block.BlockMetadata;
import java.util.ArrayList;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import binnie.core.block.TileEntityMetadata;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.item.ItemStack;
import java.util.List;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import forestry.api.core.Tabs;
import net.minecraft.block.Block;
import binnie.extratrees.ExtraTrees;
import binnie.core.block.IBlockMetadata;
import net.minecraft.block.BlockStairs;

public class BlockETStairs extends BlockStairs implements IBlockMetadata
{
	@Override
	public int getRenderType() {
		return ExtraTrees.stairsID;
	}

	public BlockETStairs(final Block par2Block) {
		super(par2Block, 0);
		this.setCreativeTab(Tabs.tabArboriculture);
		this.setBlockName("stairs");
		this.setResistance(5.0f);
		this.setHardness(2.0f);
		this.setStepSound(Block.soundTypeWood);
		this.useNeighborBrightness = true;
	}

	@Override
	public void getSubBlocks(final Item par1, final CreativeTabs par2CreativeTabs, final List itemList) {
		for (int i = 0; i < PlankType.ExtraTreePlanks.values().length; ++i) {
			itemList.add(new ItemStack(this, 1, i));
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
		return WoodManager.getPlankType(meta + 32).getIcon();
	}

	@Override
	public void dropAsStack(final World world, final int x, final int y, final int z, final ItemStack drop) {
		this.dropBlockAsItem(world, x, y, z, drop);
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
	public int getPlacedMeta(final ItemStack stack, final World world, final int x, final int y, final int z, final ForgeDirection clickedBlock) {
		return TileEntityMetadata.getItemDamage(stack);
	}

	@Override
	public int getDroppedMeta(final int blockMeta, final int tileMeta) {
		return tileMeta;
	}

	@Override
	public String getBlockName(final ItemStack par1ItemStack) {
		final int meta = TileEntityMetadata.getItemDamage(par1ItemStack);
		return Binnie.Language.localise(ExtraTrees.instance, "block.woodstairs.name", WoodManager.getPlankType(meta + 32).getName());
	}

	@Override
	public void getBlockTooltip(final ItemStack par1ItemStack, final List par3List) {
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
}
