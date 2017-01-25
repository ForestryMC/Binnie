package binnie.craftgui.core.renderer;

import binnie.core.BinnieCore;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.CraftGUIUtil;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.core.geometry.Position;
import binnie.craftgui.core.geometry.TextJustification;
import binnie.craftgui.resource.IStyleSheet;
import binnie.craftgui.resource.Texture;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.client.config.GuiUtils;
import org.lwjgl.opengl.GL11;

import java.util.List;

public class Renderer {
	private IStyleSheet stylesheet;

	public final void preRender(final IWidget widget, int guiWidth, int guiHeight) {
		GlStateManager.pushMatrix();
		GlStateManager.translate(widget.getPosition().x(), widget.getPosition().y(), 0.0f);
		this.colour(widget.getColour());
		if (widget.isCroppedWidet()) {
			final IWidget cropRelative = (widget.getCropWidget() != null) ? widget.getCropWidget() : widget;
			final IPoint pos = cropRelative.getAbsolutePosition();
			final IArea cropZone = widget.getCroppedZone();
			GL11.glEnable(GL11.GL_SCISSOR_TEST);
			this.limitArea(new IArea(pos.add(cropZone.pos()), cropZone.size()), guiWidth, guiHeight);

		}
		GlStateManager.disableDepth();
	}

	public final void postRender(final IWidget widget) {
		if (widget.isCroppedWidet()) {
			GL11.glDisable(GL11.GL_SCISSOR_TEST);
		}
		GlStateManager.enableDepth();
		GlStateManager.popMatrix();
	}

	public void colour(final int hex) {
		int a = (hex & 0xFF000000) >> 24;
		final int r = (hex & 0xFF0000) >> 16;
		final int g = (hex & 0xFF00) >> 8;
		final int b = hex & 0xFF;
		if (a < 0) {
			a += 256;
		}
		if (a > 0 && a != 255) {
			GlStateManager.color(r / 255.0f, g / 255.0f, b / 255.0f, a / 255.0f);
		} else {
			GlStateManager.color(r / 255.0f, g / 255.0f, b / 255.0f);
		}
	}

	public Texture getTexture(final Object key) {
		if (key instanceof Texture) {
			return (Texture) key;
		}
		return this.stylesheet.getTexture(key);
	}

	public void setTexture(final Texture texture) {
		if (texture != null) {
			BinnieCore.proxy.bindTexture(texture.getFilename());
		}
	}

	public void texture(final Object texture, final IPoint position) {
		this.texture(this.getTexture(texture), position);
	}

	public void texture(final Texture texture, final IPoint position) {
		if (texture == null) {
			return;
		}
		this.setTexture(texture);
		final IPoint point = position.sub(new IPoint(texture.getBorder().l(), texture.getBorder().t()));
		final IArea textureArea = texture.getArea().outset(texture.getBorder());
		GuiUtils.drawTexturedModalRect(point.x(), point.y(), textureArea.pos().x(), textureArea.pos().y(), textureArea.size().x(), textureArea.size().y(), 0);
	}

	public void texture(final Object window, final IArea area) {
		this.texture(this.getTexture(window), area);
	}

	public void texture(final Texture texture, final IArea area) {
		if (texture == null) {
			return;
		}
		this.setTexture(texture);
		final IArea textureArea = texture.getArea().outset(texture.getBorder());
		final IArea targetArea = area.outset(texture.getBorder());
		if (textureArea.w() == targetArea.w() && textureArea.h() == targetArea.h()) {
			final IPoint position = targetArea.pos();
			GuiUtils.drawTexturedModalRect(position.x(), position.y(), textureArea.pos().x(), textureArea.pos().y(), textureArea.size().x(), textureArea.size().y(), 0);
		} else {
			CraftGUIUtil.renderTexturePadded(targetArea, textureArea, texture.getTotalPadding());
		}
	}

	public void stylesheet(final IStyleSheet sheet) {
		this.stylesheet = sheet;
	}

	public int textWidth(final String text) {
		FontRenderer fontRendererObj = Minecraft.getMinecraft().fontRendererObj;
		return fontRendererObj.getStringWidth(text);
	}

	public int textHeight() {
		FontRenderer fontRendererObj = Minecraft.getMinecraft().fontRendererObj;
		return (fontRendererObj == null) ? 0 : fontRendererObj.FONT_HEIGHT;
	}

	public void text(final IPoint pos, final String text, final int colour) {
		this.text(new IArea(pos, new IPoint(500, 500)), TextJustification.TopLeft, text, colour);
	}

	public void text(final IArea area, final TextJustification justification, final String text, final int colour) {
		final IPoint pos = area.pos();
		if (area.size().x() <= 0.0f) {
			return;
		}
		FontRenderer fontRendererObj = Minecraft.getMinecraft().fontRendererObj;
		final List<String> wrappedStrings = fontRendererObj.listFormattedStringToWidth(text, area.size().x());
		final float totalHeight = wrappedStrings.size() * this.textHeight();
		float posY = area.pos().y();
		if (area.size().y() > totalHeight) {
			posY += (area.size().y() - totalHeight) * justification.getYOffset();
		}
		for (final String string : wrappedStrings) {
			final float stringWidth = this.textWidth(string);
			float posX = area.size().x() - stringWidth;
			posX *= justification.getXOffset();
			GlStateManager.disableDepth();
			fontRendererObj.drawString(string, (int) (pos.x() + posX), (int) posY, colour);
			posY += this.textHeight();
		}
		GlStateManager.color(1.0f, 1.0f, 1.0f);
	}

	public void solid(final IArea area, final int colour) {
		CraftGUIUtil.drawRect(area.pos().x(), area.pos().y(), area.pos().x() + area.size().x(), area.pos().y() + area.size().y(), 0xFF000000 | colour);
	}

	public void solidAlpha(final IArea area, final int color) {
		CraftGUIUtil.drawRect(area.pos().x(), area.pos().y(), area.pos().x() + area.size().x(), area.pos().y() + area.size().y(), color);
	}

	public void gradientRect(final IArea area, final int startColor, final int endColor) {
		GuiUtils.drawGradientRect(0, area.pos().x(), area.pos().y(), area.pos().x() + area.size().x(), area.pos().y() + area.size().y(), startColor, endColor);
	}

	public void sprite(final IPoint pos, final TextureAtlasSprite icon) {
		if (icon != null) {
			BinnieCore.proxy.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
			CraftGUIUtil.drawTexture(pos.x(), pos.y(), icon, 0, 0, 0);
		}
	}
	
	public void fluid(IPoint pos, FluidStack fluid){
		GlStateManager.enableBlend();
		GlStateManager.enableAlpha();

		Minecraft minecraft = Minecraft.getMinecraft();
		if (fluid != null) {
			Fluid fluid1 = fluid.getFluid();
			if (fluid1 != null) {
				TextureAtlasSprite fluidStillSprite = getStillFluidSprite(minecraft, fluid1);

				int fluidColor = fluid1.getColor(fluid);

				minecraft.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
				colour(fluidColor);
				CraftGUIUtil.drawTexture(pos.x(), pos.y(), fluidStillSprite, 0, 0, 100);
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

	public void limitArea(final IArea area, int guiWidth, int guiHeight) {
		float x = area.pos().x();
		float y = area.pos().y();
		float w = area.size().x();
		float h = area.size().y();
		y = guiHeight - (y + h);
		Minecraft minecraft = Minecraft.getMinecraft();
		final float scaleX = guiWidth / (float) minecraft.displayWidth;
		final float scaleY = guiHeight / (float) minecraft.displayHeight;
		x += 0.0f;
		y += 0.0f;
		w += 0.0f;
		h += 0.0f;
		GL11.glScissor((int) (x / scaleX), (int) (y / scaleY), (int) (w / scaleX), (int) (h / scaleY));
	}

	public int textHeight(final String text, final int width) {
		Minecraft minecraft = Minecraft.getMinecraft();
		FontRenderer fontRenderer = minecraft.fontRendererObj;
		return fontRenderer.listFormattedStringToWidth(text, width).size() * this.textHeight();
	}

	public void texturePercentage(final Texture texture, final IArea area, final Position direction, final float percentage) {
		final int dist = (direction == Position.Top || direction == Position.Bottom) ? Math.round(percentage * texture.h()) : Math.round(percentage * texture.w());
		final int dim = (direction == Position.Top || direction == Position.Bottom) ? texture.h() : texture.w();
		int x = area.pos().x();
		int y = area.pos().y();
		int w = area.size().x();
		int h = area.size().y();
		switch (direction) {
			case Top: {
				h *= percentage;
				break;
			}
			case Right: {
				x += (1.0f - percentage) * w;
				w *= percentage;
				break;
			}
			case Left: {
				w *= percentage;
				break;
			}
			case Bottom: {
				y += h - (int) (percentage * h);
				h *= percentage;
				break;
			}
		}
		this.texture(texture.crop(direction, dim - dist), new IArea(x, y, w, h));
	}

	public void test(final IWidget widget) {
	}
}
