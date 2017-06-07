package binnie.extratrees.block.decor;

import binnie.core.Mods;
import binnie.core.block.BlockMetadata;
import binnie.core.block.IBlockMetadata;
import binnie.core.block.TileEntityMetadata;
import binnie.core.util.I18N;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.block.IFenceProvider;
import binnie.extratrees.block.PlankType;
import binnie.extratrees.block.WoodManager;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.core.Tabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.List;

public class BlockFence extends net.minecraft.block.BlockFence implements IBlockMetadata, IBlockFence {
	public BlockFence() {
		super("", Material.wood);
		setCreativeTab(Tabs.tabArboriculture);
		setBlockName("fence");
		setResistance(5.0f);
		setHardness(2.0f);
		setStepSound(Block.soundTypeWood);
	}

	public static boolean canConnect(Block block) {
		return block == ((ItemBlock) Mods.forestry.item("fences")).field_150939_a || block == Blocks.fence || block == Blocks.fence_gate || block == Blocks.nether_brick_fence || block instanceof IBlockFence || block == ExtraTrees.blockGate;
	}

	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for (IFenceProvider type : PlankType.ExtraTreePlanks.values()) {
			list.add(type.getFence());
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
		TileEntityMetadata tile = TileEntityMetadata.getTile(world, x, y, z);
		if (tile != null) {
			return getIcon(side, tile.getTileMetadata());
		}
		return super.getIcon(world, x, y, z, side);
	}

	public FenceDescription getDescription(int meta) {
		return WoodManager.getFenceDescription(meta);
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		return getDescription(meta).getPlankType().getIcon();
	}

	@Override
	public int getRenderType() {
		return ExtraTrees.fenceID;
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
	public int getPlacedMeta(ItemStack itemStack, World world, int x, int y, int z, ForgeDirection direction) {
		return TileEntityMetadata.getItemDamage(itemStack);
	}

	@Override
	public int getDroppedMeta(int blockMeta, int tileMeta) {
		return tileMeta;
	}

	@Override
	public String getBlockName(ItemStack itemStack) {
		int meta = TileEntityMetadata.getItemDamage(itemStack);
		return I18N.localise("extratrees.block.woodfence.name", getDescription(meta).getPlankType().getName());
	}

	@Override
	public void addBlockTooltip(ItemStack itemStack, List tooltip) {
	}

	@Override
	public boolean canPlaceTorchOnTop(World world, int x, int y, int z) {
		return true;
	}

	@Override
	public boolean canConnectFenceTo(IBlockAccess world, int x, int y, int z) {
		if (!isFence(world, x, y, z)) {
			Block block = world.getBlock(x, y, z);
			return block != null && block.getMaterial().isOpaque() && block.renderAsNormalBlock();
		}
		return true;
	}

	public boolean isFence(IBlockAccess world, int x, int y, int z) {
		Block block = world.getBlock(x, y, z);
		return canConnect(block);
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int side) {
		super.breakBlock(world, x, y, z, block, side);
		world.removeTileEntity(x, y, z);
	}

	@Override
	public boolean isWood(IBlockAccess world, int x, int y, int z) {
		return true;
	}

	@Override
	public int getFlammability(IBlockAccess world, int x, int y, int z, ForgeDirection face) {
		return 20;
	}

	@Override
	public boolean isFlammable(IBlockAccess world, int x, int y, int z, ForgeDirection face) {
		return true;
	}

	@Override
	public int getFireSpreadSpeed(IBlockAccess world, int x, int y, int z, ForgeDirection face) {
		return 5;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
		return BlockMetadata.getPickBlock(world, x, y, z);
	}
}
