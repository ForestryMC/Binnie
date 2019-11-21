package binnie.core.block;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public interface IMultipassBlock<K> {
	default AxisAlignedBB getItemBoundingBox() {
		return Block.FULL_BLOCK_AABB;
	}

	@SideOnly(Side.CLIENT)
	int getRenderPasses();

	@SideOnly(Side.CLIENT)
	K getInventoryKey(ItemStack stack);

	@SideOnly(Side.CLIENT)
	K getWorldKey(IBlockState state);

	/**
	 * pass -1 and facing null = particle Sprite
	 */
	@SideOnly(Side.CLIENT)
	TextureAtlasSprite getSprite(K key, @Nullable EnumFacing facing, int pass);
}
