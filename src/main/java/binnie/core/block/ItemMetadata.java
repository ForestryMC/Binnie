package binnie.core.block;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class ItemMetadata extends ItemBlock {
    public ItemMetadata(final Block block) {
        super(block);
    }

    @Override
    public int getMetadata(final int par1) {
        return 0;
    }

//	@Override
//	public boolean placeBlockAt(final ItemStack stack, final EntityPlayer player, final World world, final int x, final int y, final int z, final int side, final float hitX, final float hitY, final float hitZ, final int metadata) {
//		final Block block = this.field_150939_a;
//		if (!(block instanceof IBlockMetadata)) {
//			return false;
//		}
//		final int placedMeta = ((IBlockMetadata) block).getPlacedMeta(stack, world, x, y, z, ForgeDirection.values()[side]);
//		if (placedMeta < 0) {
//			return false;
//		}
//		if (!world.setBlock(x, y, z, block, metadata, 3)) {
//			return false;
//		}
//		if (world.getBlock(x, y, z) == block) {
//			final TileEntityMetadata tile = TileEntityMetadata.getTile(world, x, y, z);
//			if (tile != null) {
//				tile.setTileMetadata(placedMeta, false);
//			}
//			block.onBlockPlacedBy(world, x, y, z, player, stack);
//			block.onPostBlockPlaced(world, x, y, z, metadata);
//		}
//		return true;
//	}
//
//	@Override
//	@SideOnly(Side.CLIENT)
//	public String getItemStackDisplayName(final ItemStack par1ItemStack) {
//		return ((IBlockMetadata) this.field_150939_a).getBlockName(par1ItemStack);
//	}
//
//	@Override
//	@SideOnly(Side.CLIENT)
//	public void addInformation(final ItemStack par1ItemStack, final EntityPlayer par2EntityPlayer, final List par3List, final boolean par4) {
//		((IBlockMetadata) this.field_150939_a).getBlockTooltip(par1ItemStack, par3List);
//	}
//
//	@Override
//	public IIcon getIconFromDamage(final int par1) {
//		return this.field_150939_a.getIcon(1, par1);
//	}
}
