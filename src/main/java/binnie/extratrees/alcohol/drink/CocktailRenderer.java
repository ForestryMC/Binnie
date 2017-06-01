package binnie.extratrees.alcohol.drink;

public class CocktailRenderer //implements IItemRenderer
{
	/*private void renderCocktail(final RenderBlocks renderBlocks, final ItemStack item, final float p, final float q, final float r) {
		final RenderItem renderItem = new RenderItem();
		GlStateManager.enableBlend();
		GL11.glBlendFunc(770, 771);
		final Glassware glass = ExtraTrees.drink.getGlassware(item);
		final FluidStack fluid = ExtraTrees.drink.getFluid(item);
		final IDrinkLiquid drink = (fluid == null) ? null : DrinkManager.getLiquid(fluid.getFluid());
		this.setColor(16777215, 0.8f);
		renderItem.renderIcon(0, 0, glass.glass, 16, 16);
		if (drink != null) {
			this.setColor(drink.getColour(), 1.2f * drink.getTransparency() + 0.3f);
			final IIcon icon = glass.contents;
			final float amount = fluid.amount / glass.getVolume();
			final float level = glass.getContentHeight() * (1.0f - amount);
			final float gapAtTop = 1.0f - (glass.getContentBottom() + glass.getContentHeight());
			final float x = 0.0f;
			final float y = 16.0f * (gapAtTop + level);
			final float w = 16.0f;
			final float h = 16.0f - y;
			final float minV = icon.getInterpolatedV(y);
			final float maxV = icon.getInterpolatedV(16.0);
			final Tessellator tessellator = Tessellator.instance;
			tessellator.startDrawingQuads();
			tessellator.addVertexWithUV(x + 0.0f, y + h, renderItem.zLevel, icon.getMinU(), maxV);
			tessellator.addVertexWithUV(x + w, y + h, renderItem.zLevel, icon.getMaxU(), maxV);
			tessellator.addVertexWithUV(x + w, y + 0.0f, renderItem.zLevel, icon.getMaxU(), minV);
			tessellator.addVertexWithUV(x + 0.0f, y + 0.0f, renderItem.zLevel, icon.getMinU(), minV);
			tessellator.draw();
		}
		GlStateManager.disableBlend();
	}

	private void setColor(final int i1, final float alpha) {
		final float f = (i1 >> 16 & 0xFF) / 255.0f;
		final float f2 = (i1 >> 8 & 0xFF) / 255.0f;
		final float f3 = (i1 & 0xFF) / 255.0f;
		GL11.glColor4f(f, f2, f3, (alpha > 1.0f) ? 1.0f : alpha);
	}

	private void renderFace(final Tessellator tessellator, final ForgeDirection dir) {
		switch (dir) {
		case DOWN: {
			tessellator.addVertex(0.0, 0.0, 0.0);
			tessellator.addVertex(0.0, 0.0, 1.0);
			tessellator.addVertex(1.0, 0.0, 1.0);
			tessellator.addVertex(1.0, 0.0, 0.0);
			break;
		}
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
		case EQUIPPED_FIRST_PERSON: {
			return true;
		}
		default: {
			return false;
		}
		}
	}

	@Override
	public boolean shouldUseRenderHelper(final IItemRenderer.ItemRenderType type, final ItemStack item, final IItemRenderer.ItemRendererHelper helper) {
		return helper == IItemRenderer.ItemRendererHelper.ENTITY_ROTATION || helper == IItemRenderer.ItemRendererHelper.ENTITY_BOBBING;
	}

	@Override
	public void renderItem(final IItemRenderer.ItemRenderType type, final ItemStack item, final Object... data) {
		switch (type) {
		case ENTITY:
		case EQUIPPED:
		case EQUIPPED_FIRST_PERSON: {
			if (type == IItemRenderer.ItemRenderType.ENTITY) {
				GlStateManager.translate(-0.5f, -0.25f, -0.0f);
			}
			final TextureManager texturemanager = BinnieCore.proxy.getMinecraftInstance().getTextureManager();
			final Glassware glass = ExtraTrees.drink.getGlassware(item);
			final FluidStack fluid = ExtraTrees.drink.getFluid(item);
			IIcon iicon = glass.glass;
			final float f = iicon.getMinU();
			final float f2 = iicon.getMaxU();
			final float f3 = iicon.getMinV();
			final float f4 = iicon.getMaxV();
			texturemanager.bindTexture(texturemanager.getResourceLocation(item.getItemSpriteNumber()));
			TextureUtil.func_152777_a(false, false, 1.0f);
			this.setColor(16777215, 0.8f);
			final IDrinkLiquid drink = (fluid == null) ? null : DrinkManager.getLiquid(fluid.getFluid());
			this.setColor(16777215, 0.8f);
			ItemRenderer.renderItemIn2D(Tessellator.instance, f2, f3, f, f4, iicon.getIconWidth(), iicon.getIconHeight(), 0.0625f);
			if (drink != null) {
				this.setColor(drink.getColour(), 1.2f * drink.getTransparency() + 0.3f);
				iicon = glass.contents;
				final IIcon icon = glass.contents;
				final float amount = fluid.amount / glass.getVolume();
				final float level = glass.getContentHeight() * (1.0f - amount);
				final float gapAtTop = 1.0f - (glass.getContentBottom() + glass.getContentHeight());
				final float x = 0.0f;
				final float y = 1.0f * (gapAtTop + level);
				final float w = 1.0f;
				final float h = 1.0f - y;
				final float minV = icon.getInterpolatedV(y * 16.0f);
				final float maxV = icon.getInterpolatedV(16.0);
				final float minU = icon.getMinU();
				final float maxU = icon.getMaxU();
				final Tessellator tessellator = Tessellator.instance;
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
			this.renderCocktail((RenderBlocks) data[0], item, -0.5f, -0.5f, -0.5f);
			break;
		}
		}
	}

	public void renderItemIn2DPercentage(final Tessellator tesselator, final float maxU, final float minV, final float minU, float maxV, final int width, final int height, final float depth, final float percent) {
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
		final float f5 = 0.5f * (maxU - minU) / width;
		final float f6 = 0.5f * (maxV - minV) / height;
		tesselator.startDrawingQuads();
		tesselator.setNormal(-1.0f, 0.0f, 0.0f);
		for (int k = 0; k < width; ++k) {
			final float f7 = k / width;
			final float f8 = maxU + (minU - maxU) * f7 - f5;
			tesselator.addVertexWithUV(f7, 0.0, 0.0f - depth, f8, maxV);
			tesselator.addVertexWithUV(f7, 0.0, 0.0, f8, maxV);
			tesselator.addVertexWithUV(f7, 1.0, 0.0, f8, minV);
			tesselator.addVertexWithUV(f7, 1.0, 0.0f - depth, f8, minV);
		}
		tesselator.draw();
		tesselator.startDrawingQuads();
		tesselator.setNormal(1.0f, 0.0f, 0.0f);
		for (int k = 0; k < width; ++k) {
			final float f7 = k / width;
			final float f8 = maxU + (minU - maxU) * f7 - f5;
			final float f9 = f7 + 1.0f / width;
			tesselator.addVertexWithUV(f9, 1.0, 0.0f - depth, f8, minV);
			tesselator.addVertexWithUV(f9, 1.0, 0.0, f8, minV);
			tesselator.addVertexWithUV(f9, 0.0, 0.0, f8, maxV);
			tesselator.addVertexWithUV(f9, 0.0, 0.0f - depth, f8, maxV);
		}
		tesselator.draw();
		tesselator.startDrawingQuads();
		tesselator.setNormal(0.0f, 1.0f, 0.0f);
		for (int k = 0; k < height; ++k) {
			final float f7 = k / height;
			final float f8 = maxV + (minV - maxV) * f7 - f6;
			final float f9 = f7 + 1.0f / height;
			tesselator.addVertexWithUV(0.0, f9, 0.0, maxU, f8);
			tesselator.addVertexWithUV(1.0, f9, 0.0, minU, f8);
			tesselator.addVertexWithUV(1.0, f9, 0.0f - depth, minU, f8);
			tesselator.addVertexWithUV(0.0, f9, 0.0f - depth, maxU, f8);
		}
		tesselator.draw();
		tesselator.startDrawingQuads();
		tesselator.setNormal(0.0f, -1.0f, 0.0f);
		for (int k = 0; k < height; ++k) {
			final float f7 = k / height;
			final float f8 = maxV + (minV - maxV) * f7 - f6;
			tesselator.addVertexWithUV(1.0, f7, 0.0, minU, f8);
			tesselator.addVertexWithUV(0.0, f7, 0.0, maxU, f8);
			tesselator.addVertexWithUV(0.0, f7, 0.0f - depth, maxU, f8);
			tesselator.addVertexWithUV(1.0, f7, 0.0f - depth, minU, f8);
		}
		tesselator.draw();
	}*/
}
