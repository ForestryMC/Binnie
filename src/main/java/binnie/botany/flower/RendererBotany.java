// 
// Decompiled by Procyon v0.5.30
// 

package binnie.botany.flower;

import net.minecraft.util.IIcon;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.block.Block;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RendererBotany implements ISimpleBlockRenderingHandler
{
	public static int renderID;
	public static int pass;

	@Override
	public void renderInventoryBlock(final Block block, final int metadata, final int modelID, final RenderBlocks renderer) {
	}

	@Override
	public boolean renderWorldBlock(final IBlockAccess world, final int x, final int y, final int z, final Block block, final int modelId, final RenderBlocks renderer) {
		final Tessellator tessellator = Tessellator.instance;
		tessellator.setBrightness(block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z));
		double d1 = x;
		final double d2 = y;
		double d3 = z;
		long i1 = x * 3129871 ^ z * 116129781L;
		i1 = i1 * i1 * 42317861L + i1 * 11L;
		d1 += ((i1 >> 16 & 0xFL) / 15.0f - 0.5) * 0.3;
		d3 += ((i1 >> 24 & 0xFL) / 15.0f - 0.5) * 0.3;
		final int rot = (int) (i1 % 4L);
		for (int j = 0; j < 3; ++j) {
			RendererBotany.pass = j;
			final int l = block.colorMultiplier(renderer.blockAccess, x, y, z);
			float f = (l >> 16 & 0xFF) / 255.0f;
			float f2 = (l >> 8 & 0xFF) / 255.0f;
			float f3 = (l & 0xFF) / 255.0f;
			if (EntityRenderer.anaglyphEnable) {
				final float f4 = (f * 30.0f + f2 * 59.0f + f3 * 11.0f) / 100.0f;
				final float f5 = (f * 30.0f + f2 * 70.0f) / 100.0f;
				final float f6 = (f * 30.0f + f3 * 70.0f) / 100.0f;
				f = f4;
				f2 = f5;
				f3 = f6;
			}
			tessellator.setColorOpaque_F(f, f2, f3);
			final IIcon iicon = block.getIcon(world, x, y, z, 0);
			renderer.drawCrossedSquares(iicon, d1, d2, d3, 1.0f);
		}
		return false;
	}

	@Override
	public boolean shouldRender3DInInventory(final int i) {
		return false;
	}

	@Override
	public int getRenderId() {
		return RendererBotany.renderID;
	}
}
