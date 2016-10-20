// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extratrees.block;

import net.minecraft.util.IIcon;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.IBlockAccess;
import binnie.core.block.TileEntityMetadata;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.block.Block;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import binnie.extratrees.ExtraTrees;
import net.minecraft.client.renderer.texture.IIconRegister;
import binnie.core.block.ItemMetadata;

public class ItemETDoor extends ItemMetadata
{
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(final IIconRegister register) {
		for (final DoorType type : DoorType.values()) {
			type.iconItem = ExtraTrees.proxy.getIcon(register, "door." + type.id);
		}
	}

	public ItemETDoor(final Block block) {
		super(block);
		this.maxStackSize = 1;
		this.setCreativeTab(CreativeTabs.tabRedstone);
	}

	@Override
	public boolean onItemUse(final ItemStack par1ItemStack, final EntityPlayer par2EntityPlayer, final World par3World, final int par4, int par5, final int par6, final int par7, final float par8, final float par9, final float par10) {
		if (par7 != 1) {
			return false;
		}
		++par5;
		final Block block = ExtraTrees.blockDoor;
		if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack) || !par2EntityPlayer.canPlayerEdit(par4, par5 + 1, par6, par7, par1ItemStack)) {
			return false;
		}
		if (!block.canPlaceBlockAt(par3World, par4, par5, par6)) {
			return false;
		}
		final int i1 = MathHelper.floor_double((par2EntityPlayer.rotationYaw + 180.0f) * 4.0f / 360.0f - 0.5) & 0x3;
		placeDoorBlock(par3World, par4, par5, par6, i1, block, par1ItemStack, par2EntityPlayer);
		--par1ItemStack.stackSize;
		return true;
	}

	public static void placeDoorBlock(final World par0World, final int par1, final int par2, final int par3, final int par4, final Block par5Block, final ItemStack item, final EntityPlayer player) {
		byte b0 = 0;
		byte b2 = 0;
		if (par4 == 0) {
			b2 = 1;
		}
		if (par4 == 1) {
			b0 = -1;
		}
		if (par4 == 2) {
			b2 = -1;
		}
		if (par4 == 3) {
			b0 = 1;
		}

		final int i1 = (par0World.isBlockNormalCubeDefault(par1 - b0, par2, par3 - b2, false) ? 1 : 0) + (par0World.isBlockNormalCubeDefault(par1 - b0, par2 + 1, par3 - b2, false) ? 1 : 0);
		final int j1 = (par0World.isBlockNormalCubeDefault(par1 + b0, par2, par3 + b2, false) ? 1 : 0) + (par0World.isBlockNormalCubeDefault(par1 + b0, par2 + 1, par3 + b2, false) ? 1 : 0);
		final boolean flag = par0World.getBlock(par1 - b0, par2, par3 - b2) == par5Block || par0World.getBlock(par1 - b0, par2 + 1, par3 - b2) == par5Block;
		final boolean flag2 = par0World.getBlock(par1 + b0, par2, par3 + b2) == par5Block || par0World.getBlock(par1 + b0, par2 + 1, par3 + b2) == par5Block;
		boolean flag3 = false;
		if (flag && !flag2) {
			flag3 = true;
		}
		else if (j1 > i1) {
			flag3 = true;
		}
		par0World.setBlock(par1, par2, par3, par5Block, par4, 2);
		par0World.setBlock(par1, par2 + 1, par3, par5Block, 0x8 | (flag3 ? 1 : 0), 2);
		if (par0World.getBlock(par1, par2, par3) == par5Block) {
			final TileEntityMetadata tile = TileEntityMetadata.getTile(par0World, par1, par2, par3);
			if (tile != null) {
				tile.setTileMetadata(TileEntityMetadata.getItemDamage(item), false);
			}
			par5Block.onBlockPlacedBy(par0World, par1, par2, par3, player, item);
			par5Block.onPostBlockPlaced(par0World, par1, par2, par3, par4);
		}
		par0World.notifyBlocksOfNeighborChange(par1, par2, par3, par5Block);
		par0World.notifyBlocksOfNeighborChange(par1, par2 + 1, par3, par5Block);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getSpriteNumber() {
		return 1;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIconFromDamage(final int par1) {
		final DoorType type = BlockETDoor.getDoorType(par1);
		return type.iconItem;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(final ItemStack par1ItemStack, final int par2) {
		return WoodManager.getPlankType((par1ItemStack == null) ? 0 : (par1ItemStack.getItemDamage() & 0xFF)).getColour();
	}
}
