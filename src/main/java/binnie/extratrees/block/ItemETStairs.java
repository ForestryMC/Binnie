package binnie.extratrees.block;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;

public class ItemETStairs extends ItemBlock {
//	@Override
//	@SideOnly(Side.CLIENT)
//	public IIcon getIconFromDamage(final int par1) {
//		return PlankType.ExtraTreePlanks.values()[par1].getIcon();
//	}

	public ItemETStairs(final Block block) {
		super(block);
		this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
		this.setUnlocalizedName("stairs");
	}

//	@Override
//	public boolean placeBlockAt(final ItemStack stack, final EntityPlayer player, final World world, final int x, final int y, final int z, final int side, final float hitX, final float hitY, final float hitZ, final int metadata) {
//		final boolean done = super.placeBlockAt(stack, player, world, x, y, z, side, hitX, hitY, hitZ, metadata);
//		final TileEntityMetadata tile = (TileEntityMetadata) world.getTileEntity(x, y, z);
//		if (tile != null) {
//			tile.setTileMetadata(stack.getItemDamage(), false);
//		}
//		return done;
//	}

//	@Override
//	@SideOnly(Side.CLIENT)
//	public String getItemStackDisplayName(final ItemStack par1ItemStack) {
//		return ((IBlockMetadata) this.field_150939_a).getBlockName(par1ItemStack);
//	}
}
