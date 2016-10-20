// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extratrees.block;

import net.minecraft.util.IIcon;
import net.minecraft.block.Block;
import org.lwjgl.opengl.GL11;
import net.minecraft.item.ItemBlock;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraftforge.client.IItemRenderer;

public class StairItemRenderer implements IItemRenderer
{
	private void renderStairBlock(final RenderBlocks renderBlocks, final ItemStack item, final float f, final float g, final float h) {
		final Tessellator tessellator = Tessellator.instance;
		final Block block = ((ItemBlock) item.getItem()).field_150939_a;
		final IIcon textureIndex = PlankType.ExtraTreePlanks.values()[item.getItemDamage()].getIcon();
		for (int i = 0; i < 2; ++i) {
			if (i == 0) {
				renderBlocks.setRenderBounds(0.0, 0.0, 0.0, 1.0, 1.0, 0.5);
			}
			if (i == 1) {
				renderBlocks.setRenderBounds(0.0, 0.0, 0.5, 1.0, 0.5, 1.0);
			}
			GL11.glTranslatef(-0.5f, -0.5f, -0.5f);
			tessellator.startDrawingQuads();
			tessellator.setNormal(0.0f, -1.0f, 0.0f);
			renderBlocks.renderFaceYNeg(block, 0.0, 0.0, 0.0, textureIndex);
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setNormal(0.0f, 1.0f, 0.0f);
			renderBlocks.renderFaceYPos(block, 0.0, 0.0, 0.0, textureIndex);
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setNormal(0.0f, 0.0f, -1.0f);
			renderBlocks.renderFaceXNeg(block, 0.0, 0.0, 0.0, textureIndex);
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setNormal(0.0f, 0.0f, 1.0f);
			renderBlocks.renderFaceXPos(block, 0.0, 0.0, 0.0, textureIndex);
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setNormal(-1.0f, 0.0f, 0.0f);
			renderBlocks.renderFaceZNeg(block, 0.0, 0.0, 0.0, textureIndex);
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setNormal(1.0f, 0.0f, 0.0f);
			renderBlocks.renderFaceZPos(block, 0.0, 0.0, 0.0, textureIndex);
			tessellator.draw();
			GL11.glTranslatef(0.5f, 0.5f, 0.5f);
		}
	}

	@Override
	public boolean handleRenderType(final ItemStack item, final IItemRenderer.ItemRenderType type) {
		switch (type) {
		case ENTITY: {
			return true;
		}
		case EQUIPPED: {
			return true;
		}
		case INVENTORY: {
			return true;
		}
		default: {
			return false;
		}
		}
	}

	@Override
	public boolean shouldUseRenderHelper(final IItemRenderer.ItemRenderType type, final ItemStack item, final IItemRenderer.ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(final IItemRenderer.ItemRenderType type, final ItemStack item, final Object... data) {
		switch (type) {
		case ENTITY: {
			this.renderStairBlock((RenderBlocks) data[0], item, -0.5f, -0.5f, -0.5f);
			break;
		}
		case EQUIPPED: {
			this.renderStairBlock((RenderBlocks) data[0], item, 0.0f, 0.0f, 0.0f);
			break;
		}
		case INVENTORY: {
			this.renderStairBlock((RenderBlocks) data[0], item, -0.5f, -0.5f, -0.5f);
			break;
		}
		}
	}
}
