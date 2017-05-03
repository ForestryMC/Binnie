package binnie.extratrees.alcohol.drink;

import binnie.core.BinnieCore;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.alcohol.Glassware;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import org.lwjgl.opengl.GL11;

public class CocktailRenderer implements IItemRenderer {
	private void renderCocktail(RenderBlocks renderBlocks, ItemStack item, float p, float q, float r) {
		RenderItem renderItem = new RenderItem();
		GL11.glEnable(3042);
		GL11.glBlendFunc(770, 771);
		Glassware glass = ExtraTrees.drink.getGlassware(item);
		FluidStack fluid = ExtraTrees.drink.getFluid(item);
		IDrinkLiquid drink = (fluid == null) ? null : DrinkManager.getLiquid(fluid.getFluid());
		setColor(16777215, 0.8f);
		renderItem.renderIcon(0, 0, glass.glass, 16, 16);

		if (drink != null) {
			setColor(drink.getColour(), 1.2f * drink.getTransparency() + 0.3f);
			IIcon icon = glass.contents;
			float amount = fluid.amount / glass.getVolume();
			float level = glass.getContentHeight() * (1.0f - amount);
			float gapAtTop = 1.0f - (glass.getContentBottom() + glass.getContentHeight());
			float x = 0.0f;
			float y = 16.0f * (gapAtTop + level);
			float w = 16.0f;
			float h = 16.0f - y;
			float minV = icon.getInterpolatedV(y);
			float maxV = icon.getInterpolatedV(16.0);
			Tessellator tessellator = Tessellator.instance;
			tessellator.startDrawingQuads();
			tessellator.addVertexWithUV(x + 0.0f, y + h, renderItem.zLevel, icon.getMinU(), maxV);
			tessellator.addVertexWithUV(x + w, y + h, renderItem.zLevel, icon.getMaxU(), maxV);
			tessellator.addVertexWithUV(x + w, y + 0.0f, renderItem.zLevel, icon.getMaxU(), minV);
			tessellator.addVertexWithUV(x + 0.0f, y + 0.0f, renderItem.zLevel, icon.getMinU(), minV);
			tessellator.draw();
		}
		GL11.glDisable(3042);
	}

	private void setColor(int i1, float alpha) {
		float f = (i1 >> 16 & 0xFF) / 255.0f;
		float f2 = (i1 >> 8 & 0xFF) / 255.0f;
		float f3 = (i1 & 0xFF) / 255.0f;
		GL11.glColor4f(f, f2, f3, (alpha > 1.0f) ? 1.0f : alpha);
	}

	private void renderFace(Tessellator tessellator, ForgeDirection dir) {
		switch (dir) {
			case DOWN:
				tessellator.addVertex(0.0, 0.0, 0.0);
				tessellator.addVertex(0.0, 0.0, 1.0);
				tessellator.addVertex(1.0, 0.0, 1.0);
				tessellator.addVertex(1.0, 0.0, 0.0);
				break;
		}
	}

	@Override
	public boolean handleRenderType(ItemStack item, IItemRenderer.ItemRenderType type) {
		switch (type) {
			case ENTITY:
			case EQUIPPED:
			case INVENTORY:
			case EQUIPPED_FIRST_PERSON:
				return true;
		}
		return false;
	}

	@Override
	public boolean shouldUseRenderHelper(IItemRenderer.ItemRenderType type, ItemStack item, IItemRenderer.ItemRendererHelper helper) {
		return helper == IItemRenderer.ItemRendererHelper.ENTITY_ROTATION
			|| helper == IItemRenderer.ItemRendererHelper.ENTITY_BOBBING;
	}

	@Override
	public void renderItem(IItemRenderer.ItemRenderType type, ItemStack item, Object... data) {
		switch (type) {
			case ENTITY:
			case EQUIPPED:
			case EQUIPPED_FIRST_PERSON: {
				if (type == IItemRenderer.ItemRenderType.ENTITY) {
					GL11.glTranslatef(-0.5f, -0.25f, -0.0f);
				}
				TextureManager texturemanager = BinnieCore.proxy.getMinecraftInstance().getTextureManager();
				Glassware glass = ExtraTrees.drink.getGlassware(item);
				FluidStack fluid = ExtraTrees.drink.getFluid(item);
				IIcon iicon = glass.glass;
				float f = iicon.getMinU();
				float f2 = iicon.getMaxU();
				float f3 = iicon.getMinV();
				float f4 = iicon.getMaxV();
				texturemanager.bindTexture(texturemanager.getResourceLocation(item.getItemSpriteNumber()));
				TextureUtil.func_152777_a(false, false, 1.0f);
				setColor(0xffffff, 0.8f);
				IDrinkLiquid drink = (fluid == null) ? null : DrinkManager.getLiquid(fluid.getFluid());
				setColor(0xffffff, 0.8f);
				ItemRenderer.renderItemIn2D(Tessellator.instance, f2, f3, f, f4, iicon.getIconWidth(), iicon.getIconHeight(), 0.0625f);
				if (drink != null) {
					setColor(drink.getColour(), 1.2f * drink.getTransparency() + 0.3f);
					iicon = glass.contents;
					IIcon icon = glass.contents;
					float amount = fluid.amount / glass.getVolume();
					float level = glass.getContentHeight() * (1.0f - amount);
					float gapAtTop = 1.0f - (glass.getContentBottom() + glass.getContentHeight());
					float x = 0.0f;
					float y = 1.0f * (gapAtTop + level);
					float w = 1.0f;
					float h = 1.0f - y;
					float minV = icon.getInterpolatedV(y * 16.0f);
					float maxV = icon.getInterpolatedV(16.0);
					float minU = icon.getMinU();
					float maxU = icon.getMaxU();
					Tessellator tessellator = Tessellator.instance;
					tessellator.setNormal(0.0f, 0.0f, -1.0f);
					tessellator.startDrawingQuads();
					tessellator.addVertexWithUV(0.0, 1.0 - y, -0.03125, maxU, minV);
					tessellator.addVertexWithUV(1.0, 1.0 - y, -0.03125, minU, minV);
					tessellator.addVertexWithUV(1.0, 0.0, -0.03125, minU, maxV);
					tessellator.addVertexWithUV(0.0, 0.0, -0.03125, maxU, maxV);
					tessellator.draw();
					tessellator.setNormal(0.0f, 0.0f, 1.0f);
					tessellator.startDrawingQuads();
					tessellator.addVertexWithUV(0.0, 1.0 - y, -0.03125, maxU, minV);
					tessellator.addVertexWithUV(0.0, 0.0, -0.03125, maxU, maxV);
					tessellator.addVertexWithUV(1.0, 0.0, -0.03125, minU, maxV);
					tessellator.addVertexWithUV(1.0, 1.0 - y, -0.03125, minU, minV);
					tessellator.draw();
					break;
				}
				break;
			}
			case INVENTORY: {
				renderCocktail((RenderBlocks) data[0], item, -0.5f, -0.5f, -0.5f);
				break;
			}
		}
	}

	public void renderItemIn2DPercentage(Tessellator tesselator, float maxU, float minV, float minU, float maxV, int width, int height, float depth, float percent) {
		maxV = minV + (maxV - minV) * 1.0f;
		tesselator.startDrawingQuads();
		tesselator.setNormal(0.0f, 0.0f, 1.0f);
		tesselator.addVertexWithUV(0.0, 0.0, 0.0, maxU, maxV);
		tesselator.addVertexWithUV(1.0, 0.0, 0.0, minU, maxV);
		tesselator.addVertexWithUV(1.0, 1.0, 0.0, minU, minV);
		tesselator.addVertexWithUV(0.0, 1.0, 0.0, maxU, minV);
		tesselator.draw();
		tesselator.startDrawingQuads();
		tesselator.setNormal(0.0f, 0.0f, -1.0f);
		tesselator.addVertexWithUV(0.0, percent, 0.0f - depth, maxU, minV);
		tesselator.addVertexWithUV(1.0, percent, 0.0f - depth, minU, minV);
		tesselator.addVertexWithUV(1.0, 0.0, 0.0f - depth, minU, maxV);
		tesselator.addVertexWithUV(0.0, 0.0, 0.0f - depth, maxU, maxV);
		tesselator.draw();
		float f5 = 0.5f * (maxU - minU) / width;
		float f6 = 0.5f * (maxV - minV) / height;
		tesselator.startDrawingQuads();
		tesselator.setNormal(-1.0f, 0.0f, 0.0f);
		for (int k = 0; k < width; ++k) {
			float f7 = k / width;
			float f8 = maxU + (minU - maxU) * f7 - f5;
			tesselator.addVertexWithUV(f7, 0.0, 0.0f - depth, f8, maxV);
			tesselator.addVertexWithUV(f7, 0.0, 0.0, f8, maxV);
			tesselator.addVertexWithUV(f7, 1.0, 0.0, f8, minV);
			tesselator.addVertexWithUV(f7, 1.0, 0.0f - depth, f8, minV);
		}
		tesselator.draw();
		tesselator.startDrawingQuads();
		tesselator.setNormal(1.0f, 0.0f, 0.0f);
		for (int k = 0; k < width; ++k) {
			float f7 = k / width;
			float f8 = maxU + (minU - maxU) * f7 - f5;
			float f9 = f7 + 1.0f / width;
			tesselator.addVertexWithUV(f9, 1.0, 0.0f - depth, f8, minV);
			tesselator.addVertexWithUV(f9, 1.0, 0.0, f8, minV);
			tesselator.addVertexWithUV(f9, 0.0, 0.0, f8, maxV);
			tesselator.addVertexWithUV(f9, 0.0, 0.0f - depth, f8, maxV);
		}
		tesselator.draw();
		tesselator.startDrawingQuads();
		tesselator.setNormal(0.0f, 1.0f, 0.0f);
		for (int k = 0; k < height; ++k) {
			float f7 = k / height;
			float f8 = maxV + (minV - maxV) * f7 - f6;
			float f9 = f7 + 1.0f / height;
			tesselator.addVertexWithUV(0.0, f9, 0.0, maxU, f8);
			tesselator.addVertexWithUV(1.0, f9, 0.0, minU, f8);
			tesselator.addVertexWithUV(1.0, f9, 0.0f - depth, minU, f8);
			tesselator.addVertexWithUV(0.0, f9, 0.0f - depth, maxU, f8);
		}
		tesselator.draw();
		tesselator.startDrawingQuads();
		tesselator.setNormal(0.0f, -1.0f, 0.0f);
		for (int k = 0; k < height; ++k) {
			float f7 = k / height;
			float f8 = maxV + (minV - maxV) * f7 - f6;
			tesselator.addVertexWithUV(1.0, f7, 0.0, minU, f8);
			tesselator.addVertexWithUV(0.0, f7, 0.0, maxU, f8);
			tesselator.addVertexWithUV(0.0, f7, 0.0f - depth, maxU, f8);
			tesselator.addVertexWithUV(1.0, f7, 0.0f - depth, minU, f8);
		}
		tesselator.draw();
	}
}
