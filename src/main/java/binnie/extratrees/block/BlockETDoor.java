package binnie.extratrees.block;

import binnie.core.block.BlockMetadata;
import binnie.core.block.IBlockMetadata;
import binnie.core.block.TileEntityMetadata;
import binnie.core.util.I18N;
import binnie.extratrees.ExtraTrees;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.core.Tabs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.IconFlipped;
import net.minecraft.client.renderer.texture.IIconRegister;
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

public class BlockETDoor extends BlockDoor implements IBlockMetadata {
	protected BlockETDoor() {
		super(Material.wood);
		setHardness(3.0f).setStepSound(Block.soundTypeWood);
		setCreativeTab(Tabs.tabArboriculture);
		setBlockName("door");
	}

	public static DoorType getDoorType(int tileMeta) {
		int type = (tileMeta & 0xF00) >> 8;
		if (type >= 0 && type < DoorType.values().length) {
			return DoorType.values()[type];
		}
		return DoorType.STANDARD;
	}

	private IIcon getFlippedIcon(boolean upper, boolean flip, int tileMeta) {
		DoorType type = getDoorType(tileMeta);
		if (upper) {
			return flip ? type.iconDoorUpperFlip : type.iconDoorUpper;
		}
		return flip ? type.iconDoorLowerFlip : type.iconDoorLower;
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		return DoorType.STANDARD.iconDoorLower;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess blockAccess, int x, int y, int z, int par5) {
		if (par5 != 1 && par5 != 0) {
			int i1 = getFullMetadata(blockAccess, x, y, z);
			int j1 = i1 & 0x3;
			boolean flag = (i1 & 0x4) != 0;
			boolean flag2;
			boolean flag3 = (i1 & 0x8) != 0;
			if (flag) {
				flag2 = (j1 == 0 && par5 == 2)
					|| (j1 == 1 && par5 == 5)
					|| (j1 == 2 && par5 == 3)
					|| (j1 == 3 && par5 == 4);
			} else {
				flag2 = (j1 == 0 && par5 == 5)
					|| (j1 == 1 && par5 == 3)
					|| (j1 == 2 && par5 == 4)
					|| (j1 == 3 && par5 == 2);

				if ((i1 & 0x10) != 0) {
					flag2 = !flag2;
				}
			}

			int tileMeta;
			if (flag3) {
				tileMeta = TileEntityMetadata.getTileMetadata(blockAccess, x, y - 1, z);
			} else {
				tileMeta = TileEntityMetadata.getTileMetadata(blockAccess, x, y, z);
			}
			return getFlippedIcon(flag3, flag2, tileMeta);
		}
		return DoorType.STANDARD.iconDoorLower;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		for (DoorType type : DoorType.values()) {
			type.iconDoorLower = ExtraTrees.proxy.getIcon(register, "door." + type.id + ".lower");
			type.iconDoorUpper = ExtraTrees.proxy.getIcon(register, "door." + type.id + ".upper");
			type.iconDoorLowerFlip = new IconFlipped(type.iconDoorLower, true, false);
			type.iconDoorUpperFlip = new IconFlipped(type.iconDoorUpper, true, false);
		}
	}

	@Override
	public int getRenderType() {
		return ExtraTrees.doorRenderId;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess blockAccess, int x, int y, int z) {
		int i1 = getFullMetadata(blockAccess, x, y, z);
		if ((i1 & 0x8) != 0) {
			int meta = TileEntityMetadata.getTileMetadata(blockAccess, x, y - 1, z);
			return WoodManager.getPlankType(meta & 0xFF).getColor();
		}

		int meta = TileEntityMetadata.getTileMetadata(blockAccess, x, y, z);
		return WoodManager.getPlankType(meta & 0xFF).getColor();
	}

	public int getFullMetadata(IBlockAccess blockAccess, int x, int y, int pzr4) {
		int l = blockAccess.getBlockMetadata(x, y, pzr4);
		boolean flag = (l & 0x8) != 0x0;
		int i1;
		int j1;
		if (flag) {
			i1 = blockAccess.getBlockMetadata(x, y - 1, pzr4);
			j1 = l;
		} else {
			i1 = l;
			j1 = blockAccess.getBlockMetadata(x, y + 1, pzr4);
		}

		boolean flag2 = (j1 & 0x1) != 0x0;
		return (i1 & 0x7) | (flag ? 8 : 0) | (flag2 ? 16 : 0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onBlockHarvested(World world, int x, int y, int z, int meta, EntityPlayer player) {
		if (player.capabilities.isCreativeMode && (meta & 0x8) != 0x0 && world.getBlock(x, y - 1, z) == this) {
			world.setBlockToAir(x, y - 1, z);
		}
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int meta, int fortune) {
		int yCoord = y;
		if ((meta & 0x08) != 0) {
			yCoord -= 1;
		}
		return BlockMetadata.getBlockDropped(this, world, x, yCoord, z, meta);
	}

	@Override
	public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z, boolean willHarvest) {
		return BlockMetadata.breakBlock(this, player, world, x, y, z);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int k) {
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
	public int getPlacedMeta(ItemStack stack, World world, int x, int y, int z, ForgeDirection direction) {
		return TileEntityMetadata.getItemDamage(stack);
	}

	@Override
	public int getDroppedMeta(int blockMeta, int tileMeta) {
		return tileMeta;
	}

	@Override
	public String getBlockName(ItemStack itemStack) {
		int meta = TileEntityMetadata.getItemDamage(itemStack);
		String typeName = getDoorType(meta).getName();
		String woodName = WoodManager.getPlankType(meta & 0xFF).getName();
		if (typeName.equals("")) {
			return I18N.localise("extratrees.block.door.name", woodName);
		}
		return I18N.localise("extratrees.block.door.name.adv", woodName, typeName);
	}

	@Override
	public void addBlockTooltip(ItemStack itemStack, List tooltip) {
		// ignored
	}

	@Override
	public void dropAsStack(World world, int x, int y, int z, ItemStack itemStack) {
		dropBlockAsItem(world, x, y, z, itemStack);
	}

	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List itemList) {
		for (IPlankType type : PlankType.ExtraTreePlanks.values()) {
			itemList.add(WoodManager.getDoor(type, DoorType.STANDARD));
		}
		for (IPlankType type : PlankType.ForestryPlanks.values()) {
			itemList.add(WoodManager.getDoor(type, DoorType.STANDARD));
		}
		for (IPlankType type : PlankType.ExtraBiomesPlank.values()) {
			if (type.getStack() != null) {
				itemList.add(WoodManager.getDoor(type, DoorType.STANDARD));
			}
		}
		for (IPlankType type : PlankType.VanillaPlanks.values()) {
			itemList.add(WoodManager.getDoor(type, DoorType.STANDARD));
		}
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
	public void breakBlock(World world, int x, int y, int z, Block block, int par6) {
		super.breakBlock(world, x, y, z, block, par6);
		world.removeTileEntity(x, y, z);
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
		return BlockMetadata.getPickBlock(world, x, y, z);
	}
}
