package binnie.core.gui.renderer;

import com.google.common.base.Preconditions;

import javax.annotation.Nullable;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import net.minecraftforge.fml.client.config.GuiUtils;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.core.ForestryAPI;

import binnie.core.BinnieCore;
import binnie.core.gui.geometry.Area;
import binnie.core.gui.geometry.Point;
import binnie.core.gui.geometry.TextJustification;

@SideOnly(Side.CLIENT)
public class RenderUtil {

	private RenderUtil() {

	}

	public static void drawItem(final Point pos, final ItemStack itemStack) {
		drawItem(pos, itemStack, false);
	}

	public static void drawItem(final Point pos, final ItemStack itemStack, final boolean rotating) {
		GlStateManager.pushAttrib();
		Preconditions.checkNotNull(itemStack);
		Minecraft minecraft = Minecraft.getMinecraft();
		net.minecraft.client.renderer.RenderHelper.enableGUIStandardItemLighting();
		FontRenderer font = getFontRenderer(minecraft, itemStack);

		if (rotating) {
			GlStateManager.pushMatrix();
			final float phase = Minecraft.getSystemTime() / 20;
			/** {@link net.minecraft.client.renderer.RenderItem#setupGuiTransform(int, int, boolean)} It adds 100 to zLevel from
			 * {@link net.minecraft.client.renderer.RenderItem#renderItemAndEffectIntoGUI(net.minecraft.entity.EntityLivingBase, net.minecraft.item.ItemStack, int, int)}.
			 * So z=150
			 **/
			GlStateManager.translate(8, 8, 150);
			GlStateManager.rotate(phase, 0, -0.866f, 0.5f);
			GlStateManager.translate(-8, -8, -150);
		}

		minecraft.getRenderItem().renderItemAndEffectIntoGUI(null, itemStack, pos.xPos(), pos.yPos());
		if (rotating) {
			GlStateManager.popMatrix();
		}
		minecraft.getRenderItem().renderItemOverlayIntoGUI(font, itemStack, pos.xPos(), pos.yPos(), null);

		net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();
		GlStateManager.popAttrib();
	}

	private static FontRenderer getFontRenderer(Minecraft minecraft, ItemStack ingredient) {
		FontRenderer fontRenderer = ingredient.getItem().getFontRenderer(ingredient);
		if (fontRenderer == null) {
			fontRenderer = minecraft.fontRendererObj;
		}
		return fontRenderer;
	}

	public static void drawTexture(double xCoord, double yCoord, TextureAtlasSprite textureSprite, int maskTop, int maskRight, double zLevel) {
		double uMin = textureSprite.getMinU();
		double uMax = textureSprite.getMaxU();
		double vMin = textureSprite.getMinV();
		double vMax = textureSprite.getMaxV();
		uMax = uMax - maskRight / 16.0 * (uMax - uMin);
		vMax = vMax - maskTop / 16.0 * (vMax - vMin);

		Tessellator tessellator = Tessellator.getInstance();
		VertexBuffer vertexBuffer = tessellator.getBuffer();
		vertexBuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
		vertexBuffer.pos(xCoord, yCoord + 16, zLevel).tex(uMin, vMax).endVertex();
		vertexBuffer.pos(xCoord + 16 - maskRight, yCoord + 16, zLevel).tex(uMax, vMax).endVertex();
		vertexBuffer.pos(xCoord + 16 - maskRight, yCoord + maskTop, zLevel).tex(uMax, vMin).endVertex();
		vertexBuffer.pos(xCoord, yCoord + maskTop, zLevel).tex(uMin, vMin).endVertex();
		tessellator.draw();
	}

	public static void setColour(final int hexColour) {
		int a = (hexColour & 0xFF000000) >> 24;
		final int r = (hexColour & 0xFF0000) >> 16;
		final int g = (hexColour & 0xFF00) >> 8;
		final int b = hexColour & 0xFF;
		if (a < 0) {
			a += 256;
		}
		if (a > 0 && a != 255) {
			GlStateManager.color(r / 255.0f, g / 255.0f, b / 255.0f, a / 255.0f);
		} else {
			GlStateManager.color(r / 255.0f, g / 255.0f, b / 255.0f);
		}
	}

	public static int getTextWidth(final String text) {
		FontRenderer fontRendererObj = Minecraft.getMinecraft().fontRendererObj;
		return fontRendererObj.getStringWidth(text);
	}

	public static int getTextHeight() {
		FontRenderer fontRendererObj = Minecraft.getMinecraft().fontRendererObj;
		return (fontRendererObj == null) ? 0 : fontRendererObj.FONT_HEIGHT;
	}

	public static void drawText(final Point pos, final String text, final int colour) {
		drawText(new Area(pos, new Point(500, 500)), TextJustification.TOP_LEFT, text, colour);
	}

	public static void drawText(final Area area, final TextJustification justification, final String text, final int colour) {
		final Point pos = area.pos();
		if (area.size().xPos() <= 0.0f) {
			return;
		}
		FontRenderer fontRendererObj = Minecraft.getMinecraft().fontRendererObj;
		final List<String> wrappedStrings = fontRendererObj.listFormattedStringToWidth(text, area.size().xPos());
		final float totalHeight = wrappedStrings.size() * getTextHeight();
		float posY = area.pos().yPos();
		if (area.size().yPos() > totalHeight) {
			posY += (area.size().yPos() - totalHeight) * justification.getYOffset();
		}
		for (final String string : wrappedStrings) {
			final float stringWidth = getTextWidth(string);
			float posX = area.size().xPos() - stringWidth;
			posX *= justification.getXOffset();
			GlStateManager.disableDepth();
			fontRendererObj.drawString(string, (int) (pos.xPos() + posX), (int) posY, colour);
			posY += getTextHeight();
		}
		GlStateManager.color(1.0f, 1.0f, 1.0f);
	}

	public static void drawSolidRect(float left, float top, float right, float bottom, final int color) {
		GuiUtils.drawGradientRect(0, (int) left, (int) top, (int) right, (int) bottom, color, color);
	}

	public static void drawSolidRect(final Area area, final int colour) {
		drawSolidRect(area.pos().xPos(), area.pos().yPos(), area.pos().xPos() + area.size().xPos(), area.pos().yPos() + area.size().yPos(), 0xFF000000 | colour);
	}

	public static void drawSolidRectWithAlpha(final Area area, final int color) {
		drawSolidRect(area.pos().xPos(), area.pos().yPos(), area.pos().xPos() + area.size().xPos(), area.pos().yPos() + area.size().yPos(), color);
	}

	public static void drawGradientRect(final Area area, final int startColor, final int endColor) {
		GuiUtils.drawGradientRect(0, area.pos().xPos(), area.pos().yPos(), area.pos().xPos() + area.size().xPos(), area.pos().yPos() + area.size().yPos(), startColor, endColor);
	}

	public static void drawSprite(final Point pos, @Nullable final TextureAtlasSprite icon) {
		if (icon != null) {
			BinnieCore.getBinnieProxy().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
			drawTexture(pos.xPos(), pos.yPos(), icon, 0, 0, 0);
		}
	}

	public static void drawGuiSprite(final Point pos, @Nullable final TextureAtlasSprite icon) {
		if (icon != null) {
			BinnieCore.getBinnieProxy().bindTexture(ForestryAPI.textureManager.getGuiTextureMap());
			drawTexture(pos.xPos(), pos.yPos(), icon, 0, 0, 0);
		}
	}

	public static void drawFluid(Point pos, @Nullable FluidStack fluid) {
		GlStateManager.enableBlend();
		GlStateManager.enableAlpha();

		Minecraft minecraft = Minecraft.getMinecraft();
		if (fluid != null) {
			Fluid fluid1 = fluid.getFluid();
			if (fluid1 != null) {
				TextureAtlasSprite fluidStillSprite = getStillFluidSprite(minecraft, fluid1);

				int fluidColor = fluid1.getColor(fluid);

				minecraft.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
				setColour(fluidColor);
				drawTexture(pos.xPos(), pos.yPos(), fluidStillSprite, 0, 0, 100);
			}
		}

		GlStateManager.color(1, 1, 1, 1);
		GlStateManager.disableAlpha();
		GlStateManager.disableBlend();
	}

	private static TextureAtlasSprite getStillFluidSprite(Minecraft minecraft, Fluid fluid) {
		TextureMap textureMapBlocks = minecraft.getTextureMapBlocks();
		ResourceLocation fluidStill = fluid.getStill();
		TextureAtlasSprite fluidStillSprite = null;
		if (fluidStill != null) {
			fluidStillSprite = textureMapBlocks.getTextureExtry(fluidStill.toString());
		}
		if (fluidStillSprite == null) {
			fluidStillSprite = textureMapBlocks.getMissingSprite();
		}
		return fluidStillSprite;
	}
}
