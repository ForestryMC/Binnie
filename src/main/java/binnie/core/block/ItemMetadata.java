package binnie.core.block;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemMetadata extends ItemBlock {
	public ItemMetadata(final Block block) {
		super(block);
	}

	@Override
	public int getMetadata(final int par1) {
		return 0;
	}

	@Override
	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, IBlockState newState) {
		if (!(block instanceof IBlockMetadata)) {
			return false;
		}
		int placedMeta = ((IBlockMetadata) block).getPlacedMeta(stack, world, pos, side);
		if (placedMeta < 0) {
			return false;
		}
		if (!world.setBlockState(pos, newState, 3)) {
			return false;
		}
		if (world.getBlockState(pos).getBlock() == block) {
			TileEntityMetadata tile = TileEntityMetadata.getTile(world, pos);
			if (tile != null) {
				tile.setTileMetadata(placedMeta, false);
			}
			setTileEntityNBT(world, player, pos, stack);
			block.onBlockPlacedBy(world, pos, newState, player, stack);
		}
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getItemStackDisplayName(ItemStack itemStack) {
		return ((IBlockMetadata) block).getDisplayName(itemStack);
	}
}
