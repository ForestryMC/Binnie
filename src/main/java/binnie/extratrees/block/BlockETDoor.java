package binnie.extratrees.block;

import binnie.Binnie;
import binnie.core.block.BlockMetadata;
import binnie.core.block.IBlockMetadata;
import binnie.core.block.TileEntityMetadata;
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
	private IIcon getFlippedIcon(final boolean upper, final boolean flip, final int tileMeta) {
		final DoorType type = getDoorType(tileMeta);
		if (upper) {
			return flip ? type.iconDoorUpperFlip : type.iconDoorUpper;
		}
		return flip ? type.iconDoorLowerFlip : type.iconDoorLower;
	}

	public static DoorType getDoorType(final int tileMeta) {
		final int type = (tileMeta & 0xF00) >> 8;
		if (type >= 0 && type < DoorType.values().length) {
			return DoorType.values()[type];
		}
		return DoorType.Standard;
	}

	protected BlockETDoor() {
		super(Material.wood);
		setHardness(3.0f).setStepSound(Block.soundTypeWood);
		setCreativeTab(Tabs.tabArboriculture);
		setBlockName("door");
	}

	@Override
	public IIcon getIcon(final int side, final int meta) {
		return DoorType.Standard.iconDoorLower;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(final IBlockAccess blockAccess, final int x, final int y, final int z, final int par5) {
		if (par5 != 1 && par5 != 0) {
			final int i1 = getFullMetadata(blockAccess, x, y, z);
			final int j1 = i1 & 0x3;
			final boolean flag = (i1 & 0x4) != 0;
			boolean flag2;
			final boolean flag3 = (i1 & 0x8) != 0;
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
		return DoorType.Standard.iconDoorLower;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(final IIconRegister register) {
		for (final DoorType type : DoorType.values()) {
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
	public int colorMultiplier(final IBlockAccess blockAccess, final int x, final int y, final int z) {
		final int i1 = getFullMetadata(blockAccess, x, y, z);
		if ((i1 & 0x8) != 0) {
			final int meta = TileEntityMetadata.getTileMetadata(blockAccess, x, y - 1, z);
			return WoodManager.getPlankType(meta & 0xFF).getColour();
		}
		final int meta = TileEntityMetadata.getTileMetadata(blockAccess, x, y, z);
		return WoodManager.getPlankType(meta & 0xFF).getColour();
	}

	public int getFullMetadata(final IBlockAccess blockAccess, final int x, final int y, final int pzr4) {
		final int l = blockAccess.getBlockMetadata(x, y, pzr4);
		final boolean flag = (l & 0x8) != 0x0;
		int i1;
		int j1;
		if (flag) {
			i1 = blockAccess.getBlockMetadata(x, y - 1, pzr4);
			j1 = l;
		} else {
			i1 = l;
			j1 = blockAccess.getBlockMetadata(x, y + 1, pzr4);
		}
		final boolean flag2 = (j1 & 0x1) != 0x0;
		return (i1 & 0x7) | (flag ? 8 : 0) | (flag2 ? 16 : 0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onBlockHarvested(final World world, final int x, final int y, final int z, final int par5, final EntityPlayer player) {
		if (player.capabilities.isCreativeMode && (par5 & 0x8) != 0x0 && world.getBlock(x, y - 1, z) == this) {
			world.setBlockToAir(x, y - 1, z);
		}
	}

	@Override
	public ArrayList<ItemStack> getDrops(final World world, final int x, final int y, final int z, final int blockMeta, final int fortune) {
		int yCoord = y;
		if ((blockMeta & 0x08) != 0) {
			yCoord -= 1;
		}
		return BlockMetadata.getBlockDropped(this, world, x, yCoord, z, blockMeta);
	}

	@Override
	// TODO fix deprecated code
	public boolean removedByPlayer(final World world, final EntityPlayer player, final int x, final int y, final int z) {
		return BlockMetadata.breakBlock(this, player, world, x, y, z);
	}

	@Override
	public TileEntity createNewTileEntity(final World world, final int k) {
		return new TileEntityMetadata();
	}

	@Override
	public boolean hasTileEntity(final int meta) {
		return true;
	}

	@Override
	public boolean onBlockEventReceived(final World world, final int x, final int y, final int z, final int eventId, final int eventType) {
		super.onBlockEventReceived(world, x, y, z, eventId, eventType);
		final TileEntity tileentity = world.getTileEntity(x, y, z);
		return tileentity != null && tileentity.receiveClientEvent(eventId, eventType);
	}

	@Override
	public int getPlacedMeta(final ItemStack stack, final World world, final int x, final int y, final int z, final ForgeDirection direction) {
		return TileEntityMetadata.getItemDamage(stack);
	}

	@Override
	public int getDroppedMeta(final int blockMeta, final int tileMeta) {
		return tileMeta;
	}

	@Override
	public String getBlockName(final ItemStack itemStack) {
		final int meta = TileEntityMetadata.getItemDamage(itemStack);
		final String typeName = getDoorType(meta).getName();
		final String woodName = WoodManager.getPlankType(meta & 0xFF).getName();
		if (typeName.equals("")) {
			return Binnie.Language.localise(ExtraTrees.instance, "block.door.name", woodName);
		}
		return Binnie.Language.localise(ExtraTrees.instance, "block.door.name.adv", woodName, typeName);
	}

	@Override
	public void getBlockTooltip(final ItemStack itemStack, final List par3List) {
		// ignored
	}

	@Override
	public void dropAsStack(final World world, final int x, final int y, final int z, final ItemStack itemStack) {
		dropBlockAsItem(world, x, y, z, itemStack);
	}

	@Override
	public void getSubBlocks(final Item item, final CreativeTabs tab, final List itemList) {
		for (final IPlankType type : PlankType.ExtraTreePlanks.values()) {
			itemList.add(WoodManager.getDoor(type, DoorType.Standard));
		}
		for (final IPlankType type : PlankType.ForestryPlanks.values()) {
			itemList.add(WoodManager.getDoor(type, DoorType.Standard));
		}
		for (final IPlankType type : PlankType.ExtraBiomesPlank.values()) {
			if (type.getStack() != null) {
				itemList.add(WoodManager.getDoor(type, DoorType.Standard));
			}
		}
		for (final IPlankType type : PlankType.VanillaPlanks.values()) {
			itemList.add(WoodManager.getDoor(type, DoorType.Standard));
		}
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
	public void breakBlock(final World world, final int x, final int y, final int z, final Block block, final int par6) {
		super.breakBlock(world, x, y, z, block, par6);
		world.removeTileEntity(x, y, z);
	}

	@Override
	public ItemStack getPickBlock(final MovingObjectPosition target, final World world, final int x, final int y, final int z) {
		return BlockMetadata.getPickBlock(world, x, y, z);
	}
}
