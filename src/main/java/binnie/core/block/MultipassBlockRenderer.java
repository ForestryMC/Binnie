// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.block;

import binnie.core.BinnieCore;
import net.minecraft.world.IBlockAccess;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.block.Block;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.Tessellator;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class MultipassBlockRenderer implements ISimpleBlockRenderingHandler
{
	public static MultipassBlockRenderer instance;
	private static int layer;

	public MultipassBlockRenderer() {
		MultipassBlockRenderer.instance = this;
	}

	private void setColour(final Tessellator tess, final int colour) {
		final float var6 = (colour >> 16 & 0xFF) / 255.0f;
		final float var7 = (colour >> 8 & 0xFF) / 255.0f;
		final float var8 = (colour & 0xFF) / 255.0f;
		GL11.glColor3f(var6, var7, var8);
	}

	public static int getLayer() {
		return MultipassBlockRenderer.layer;
	}

	@Override
	public void renderInventoryBlock(final Block block, final int meta, final int modelID, final RenderBlocks renderer) {
		block.setBlockBoundsForItemRender();
		renderer.setRenderBoundsFromBlock(block);
		GL11.glTranslatef(-0.5f, -0.5f, -0.5f);
		MultipassBlockRenderer.layer = 0;
		while (MultipassBlockRenderer.layer < ((IMultipassBlock) block).getNumberOfPasses()) {
			this.renderItem(block, renderer, meta);
			++MultipassBlockRenderer.layer;
		}
		MultipassBlockRenderer.layer = 0;
	}

	@Override
	public boolean renderWorldBlock(final IBlockAccess world, final int x, final int y, final int z, final Block block, final int modelId, final RenderBlocks renderer) {
		boolean r = true;
		MultipassBlockRenderer.layer = 0;
		while (MultipassBlockRenderer.layer < ((IMultipassBlock) block).getNumberOfPasses()) {
			r = renderer.renderStandardBlock(block, x, y, z);
			++MultipassBlockRenderer.layer;
		}
		MultipassBlockRenderer.layer = 0;
		return r;
	}

	@Override
	public boolean shouldRender3DInInventory(final int i) {
		return true;
	}

	@Override
	public int getRenderId() {
		return BinnieCore.multipassRenderID;
	}

	public void renderItem(final Block block, final RenderBlocks renderer, final int meta) {
		this.setColor(((IMultipassBlock) block).colorMultiplier(meta));
		final Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0f, -1.0f, 0.0f);
		renderer.renderFaceYNeg(block, 0.0, 0.0, 0.0, renderer.getBlockIconFromSideAndMetadata(block, 0, meta));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0f, 1.0f, 0.0f);
		renderer.renderFaceYPos(block, 0.0, 0.0, 0.0, renderer.getBlockIconFromSideAndMetadata(block, 1, meta));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0f, 0.0f, -1.0f);
		renderer.renderFaceZNeg(block, 0.0, 0.0, 0.0, renderer.getBlockIconFromSideAndMetadata(block, 2, meta));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0f, 0.0f, 1.0f);
		renderer.renderFaceZPos(block, 0.0, 0.0, 0.0, renderer.getBlockIconFromSideAndMetadata(block, 3, meta));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(-1.0f, 0.0f, 0.0f);
		renderer.renderFaceXNeg(block, 0.0, 0.0, 0.0, renderer.getBlockIconFromSideAndMetadata(block, 4, meta));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(1.0f, 0.0f, 0.0f);
		renderer.renderFaceXPos(block, 0.0, 0.0, 0.0, renderer.getBlockIconFromSideAndMetadata(block, 5, meta));
		tessellator.draw();
	}

	public void setColor(final int l) {
		final float f = (l >> 16 & 0xFF) / 255.0f;
		final float f2 = (l >> 8 & 0xFF) / 255.0f;
		final float f3 = (l & 0xFF) / 255.0f;
		GL11.glColor3f(f, f2, f3);
	}

	static {
		MultipassBlockRenderer.layer = 0;
	}
}
