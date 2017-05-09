package binnie.extratrees.block;

import binnie.core.block.BlockMetadata;
import binnie.core.block.IBlockMetadata;
import binnie.core.block.TileEntityMetadata;
import binnie.extratrees.ExtraTrees;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.core.Tabs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.List;

// TODO unused class?
public class BlockBranch extends BlockLog implements IBlockMetadata {
	public BlockBranch() {
		setCreativeTab(Tabs.tabArboriculture);
		setBlockName("branch");
		setResistance(5.0f);
		setHardness(2.0f);
		setStepSound(Block.soundTypeWood);
	}

	@Override
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List itemList) {
		for (int i = 0; i < ILogType.ExtraTreeLog.values().length; ++i) {
			itemList.add(TileEntityMetadata.getItemStack(this, i));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
		TileEntityMetadata tile = TileEntityMetadata.getTile(world, x, y, z);
		if (tile != null) {
			return getIcon(side, tile.getTileMetadata(), world.getBlockMetadata(x, y, z));
		}
		return super.getIcon(world, x, y, z, side);
	}

	public IIcon getIcon(int side, int tileMeta, int blockMeta) {
		int oriented = blockMeta & 0xC;
		ILogType.ExtraTreeLog log = ILogType.ExtraTreeLog.values()[tileMeta];
		if (side > 3) {
			return log.getTrunk();
		}
		return log.getBark();
	}

	@Override
	public IIcon getIcon(int side, int tileMeta) {
		return getIcon(side, tileMeta, 0);
	}

	@Override
	public int getRenderType() {
		return ExtraTrees.branchRenderId;
	}

	@Override
	public void dropAsStack(World world, int x, int y, int z, ItemStack itemStack) {
		dropBlockAsItem(world, x, y, z, itemStack);
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int blockMeta, int fortune) {
		return BlockMetadata.getBlockDropped(this, world, x, y, z, blockMeta);
	}

	@Override
	public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z) {
		return BlockMetadata.breakBlock(this, player, world, x, y, z);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityMetadata();
	}

	@Override
	public boolean hasTileEntity(int meta) {
		return true;
	}

	@Override
	public boolean onBlockEventReceived(World world, int x, int y, int z, int eventId, int eventType) {
		super.onBlockEventReceived(world, x, y, z, eventId, eventType);
		TileEntity tileentity = world.getTileEntity(x, y, z);
		return tileentity != null && tileentity.receiveClientEvent(eventId, eventType);
	}

	@Override
	public int getDroppedMeta(int blockMeta, int tileMeta) {
		return tileMeta;
	}

	@Override
	public String getBlockName(ItemStack itemStack) {
		int meta = TileEntityMetadata.getItemDamage(itemStack);
		return ILogType.ExtraTreeLog.values()[meta].getName() + " Branch";
	}

	@Override
	public void addBlockTooltip(ItemStack itemStack, List tooltip) {
		// ignored
	}

	@Override
	public int getPlacedMeta(ItemStack itemStack, World world, int x, int y, int z, ForgeDirection direction) {
		return TileEntityMetadata.getItemDamage(itemStack);
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int side) {
		super.breakBlock(world, x, y, z, block, side);
		world.removeTileEntity(x, y, z);
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
		return BlockMetadata.getPickBlock(world, x, y, z);
	}
}
