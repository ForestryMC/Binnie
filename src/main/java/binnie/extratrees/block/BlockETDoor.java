// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extratrees.block;

import net.minecraft.util.MovingObjectPosition;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import java.util.List;
import binnie.Binnie;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraft.tileentity.TileEntity;
import binnie.core.block.BlockMetadata;
import net.minecraft.item.ItemStack;
import java.util.ArrayList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.client.renderer.IconFlipped;
import binnie.extratrees.ExtraTrees;
import net.minecraft.client.renderer.texture.IIconRegister;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import binnie.core.block.TileEntityMetadata;
import net.minecraft.world.IBlockAccess;
import forestry.api.core.Tabs;
import net.minecraft.block.material.Material;
import net.minecraft.util.IIcon;
import binnie.core.block.IBlockMetadata;
import net.minecraft.block.BlockDoor;

public class BlockETDoor extends BlockDoor implements IBlockMetadata
{
	private IIcon getFlippedIcon(final boolean upper, final boolean flip, final int tileMeta) {
		final DoorType type = getDoorType(tileMeta);
		return upper ? (flip ? type.iconDoorUpperFlip : type.iconDoorUpper) : (flip ? type.iconDoorLowerFlip : type.iconDoorLower);
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
		this.setHardness(3.0f).setStepSound(Block.soundTypeWood);
		this.setCreativeTab(Tabs.tabArboriculture);
		this.setBlockName("door");
	}

	@Override
	public IIcon getIcon(final int side, final int meta) {
		return DoorType.Standard.iconDoorLower;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4, final int par5) {
		if (par5 != 1 && par5 != 0) {
			final int i1 = this.getFullMetadata(par1IBlockAccess, par2, par3, par4);
			final int j1 = i1 & 0x3;
			final boolean flag = (i1 & 0x4) != 0x0;
			boolean flag2 = false;
			final boolean flag3 = (i1 & 0x8) != 0x0;
			if (flag) {
				if (j1 == 0 && par5 == 2) {
					flag2 = !flag2;
				}
				else if (j1 == 1 && par5 == 5) {
					flag2 = !flag2;
				}
				else if (j1 == 2 && par5 == 3) {
					flag2 = !flag2;
				}
				else if (j1 == 3 && par5 == 4) {
					flag2 = !flag2;
				}
			}
			else {
				if (j1 == 0 && par5 == 5) {
					flag2 = !flag2;
				}
				else if (j1 == 1 && par5 == 3) {
					flag2 = !flag2;
				}
				else if (j1 == 2 && par5 == 4) {
					flag2 = !flag2;
				}
				else if (j1 == 3 && par5 == 2) {
					flag2 = !flag2;
				}
				if ((i1 & 0x10) != 0x0) {
					flag2 = !flag2;
				}
			}
			int tileMeta = 0;
			if (flag3) {
				tileMeta = TileEntityMetadata.getTileMetadata(par1IBlockAccess, par2, par3 - 1, par4);
			}
			else {
				tileMeta = TileEntityMetadata.getTileMetadata(par1IBlockAccess, par2, par3, par4);
			}
			return this.getFlippedIcon(flag3, flag2, tileMeta);
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
	public int colorMultiplier(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4) {
		final int par5 = 2;
		final int i1 = this.getFullMetadata(par1IBlockAccess, par2, par3, par4);
		final int j1 = i1 & 0x3;
		final boolean flag = (i1 & 0x4) != 0x0;
		boolean flag2 = false;
		final boolean flag3 = (i1 & 0x8) != 0x0;
		if (flag) {
			if (j1 == 0 && par5 == 2) {
				flag2 = !flag2;
			}
			else if (j1 == 1 && par5 == 5) {
				flag2 = !flag2;
			}
			else if (j1 == 2 && par5 == 3) {
				flag2 = !flag2;
			}
			else if (j1 == 3 && par5 == 4) {
				flag2 = !flag2;
			}
		}
		else {
			if (j1 == 0 && par5 == 5) {
				flag2 = !flag2;
			}
			else if (j1 == 1 && par5 == 3) {
				flag2 = !flag2;
			}
			else if (j1 == 2 && par5 == 4) {
				flag2 = !flag2;
			}
			else if (j1 == 3 && par5 == 2) {
				flag2 = !flag2;
			}
			if ((i1 & 0x10) != 0x0) {
				flag2 = !flag2;
			}
		}
		if (flag3) {
			final int meta = TileEntityMetadata.getTileMetadata(par1IBlockAccess, par2, par3 - 1, par4);
			return WoodManager.getPlankType(meta & 0xFF).getColour();
		}
		final int meta = TileEntityMetadata.getTileMetadata(par1IBlockAccess, par2, par3, par4);
		return WoodManager.getPlankType(meta & 0xFF).getColour();
	}

	public int getFullMetadata(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4) {
		final int l = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
		final boolean flag = (l & 0x8) != 0x0;
		int i1;
		int j1;
		if (flag) {
			i1 = par1IBlockAccess.getBlockMetadata(par2, par3 - 1, par4);
			j1 = l;
		}
		else {
			i1 = l;
			j1 = par1IBlockAccess.getBlockMetadata(par2, par3 + 1, par4);
		}
		final boolean flag2 = (j1 & 0x1) != 0x0;
		return (i1 & 0x7) | (flag ? 8 : 0) | (flag2 ? 16 : 0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onBlockHarvested(final World par1World, final int par2, final int par3, final int par4, final int par5, final EntityPlayer par6EntityPlayer) {
		if (par6EntityPlayer.capabilities.isCreativeMode && (par5 & 0x8) != 0x0 && par1World.getBlock(par2, par3 - 1, par4) == this) {
			par1World.setBlockToAir(par2, par3 - 1, par4);
		}
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
	public TileEntity createNewTileEntity(final World var1, final int k) {
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
		final String typeName = getDoorType(meta).getName();
		final String woodName = WoodManager.getPlankType(meta & 0xFF).getName();
		if (typeName.equals("")) {
			return Binnie.Language.localise(ExtraTrees.instance, "block.door.name", woodName);
		}
		return Binnie.Language.localise(ExtraTrees.instance, "block.door.name.adv", woodName, typeName);
	}

	@Override
	public void getBlockTooltip(final ItemStack par1ItemStack, final List par3List) {
	}

	@Override
	public void dropAsStack(final World world, final int x, final int y, final int z, final ItemStack drop) {
		this.dropBlockAsItem(world, x, y, z, drop);
	}

	@Override
	public void getSubBlocks(final Item par1, final CreativeTabs par2CreativeTabs, final List itemList) {
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
	public void breakBlock(final World par1World, final int par2, final int par3, final int par4, final Block par5, final int par6) {
		super.breakBlock(par1World, par2, par3, par4, par5, par6);
		par1World.removeTileEntity(par2, par3, par4);
	}

	@Override
	public ItemStack getPickBlock(final MovingObjectPosition target, final World world, final int x, final int y, final int z) {
		return BlockMetadata.getPickBlock(world, x, y, z);
	}
}
