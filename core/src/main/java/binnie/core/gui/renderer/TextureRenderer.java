package binnie.core.gui.renderer;

import javax.annotation.Nullable;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;

import net.minecraftforge.fml.client.config.GuiUtils;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.lwjgl.opengl.GL11;

import binnie.core.BinnieCore;
import binnie.core.gui.IWidget;
import binnie.core.gui.geometry.Area;
import binnie.core.gui.geometry.Border;
import binnie.core.gui.geometry.Point;
import binnie.core.gui.geometry.Position;
import binnie.core.gui.resource.IStyleSheet;
import binnie.core.gui.resource.Texture;

@SideOnly(Side.CLIENT)
public class TextureRenderer {
	private IStyleSheet styleSheet;

	private static void renderTexturePadded(final Area area, final Area texture, final Border padding) {
		int borderLeft = padding.l();
		int borderRight = padding.r();
		int borderTop = padding.t();
		int borderBottom = padding.b();
		final int posX = area.pos().xPos();
		final int posY = area.pos().yPos();
		final int width = area.size().xPos();
		final int height = area.size().yPos();
		final int textWidth = texture.width();
		final int textHeight = texture.height();
		final int u = texture.xPos();
		final int v = texture.yPos();
		if (borderTop + borderBottom > height) {
			borderTop = height / 2;
			borderBottom = height / 2;
		}
		if (borderLeft + borderRight > width) {
			borderLeft = width / 2;
			borderRight = width / 2;
		}
		final Point origin = area.pos();
		GuiUtils.drawTexturedModalRect(posX, posY, u, v, borderLeft, borderTop, 0);
		GuiUtils.drawTexturedModalRect(posX + width - borderRight, posY, u + textWidth - borderRight, v, borderRight, borderTop, 0);
		GuiUtils.drawTexturedModalRect(posX, posY + height - borderBottom, u, v + textHeight - borderBottom, borderLeft, borderBottom, 0);
		GuiUtils.drawTexturedModalRect(posX + width - borderRight, posY + height - borderBottom, u + textWidth - borderRight, v + textHeight - borderBottom, borderRight, borderBottom, 0);
		int texturingWidth;
		for (int currentXPos = borderLeft; currentXPos < width - borderRight; currentXPos += texturingWidth) {
			final int distanceXRemaining = width - borderRight - currentXPos;
			texturingWidth = textWidth - borderLeft - borderRight;
			if (texturingWidth > distanceXRemaining) {
				texturingWidth = distanceXRemaining;
			}
			if (texturingWidth <= 0) {
				break;
			}
			GuiUtils.drawTexturedModalRect(posX + currentXPos, posY, u + borderLeft, v, texturingWidth, borderTop, 0);
			GuiUtils.drawTexturedModalRect(posX + currentXPos, posY + height - borderBottom, u + borderLeft, v + textHeight - borderBottom, texturingWidth, borderBottom, 0);
			int texturingHeight;
			for (int currentYPos = borderTop; currentYPos < height - borderBottom; currentYPos += texturingHeight) {
				final int distanceYRemaining = height - borderBottom - currentYPos;
				texturingHeight = textHeight - borderTop - borderBottom;
				if (texturingHeight > distanceYRemaining) {
					texturingHeight = distanceYRemaining;
				}
				if (texturingHeight <= 0) {
					break;
				}
				GuiUtils.drawTexturedModalRect(posX + currentXPos, posY + currentYPos, u + borderLeft, v + borderTop, texturingWidth, texturingHeight, 0);
			}
		}
		int texturingHeight2;
		for (int currentYPos2 = borderTop; currentYPos2 < height - borderBottom; currentYPos2 += texturingHeight2) {
			final int distanceYRemaining2 = height - borderBottom - currentYPos2;
			texturingHeight2 = textHeight - borderTop - borderBottom;
			if (texturingHeight2 > distanceYRemaining2) {
				texturingHeight2 = distanceYRemaining2;
			}
			if (texturingHeight2 <= 0) {
				break;
			}
			GuiUtils.drawTexturedModalRect(posX, posY + currentYPos2, u, v + borderTop, borderLeft, texturingHeight2, 0);
			GuiUtils.drawTexturedModalRect(posX + width - borderRight, posY + currentYPos2, u + textWidth - borderRight, v + borderTop, borderRight, texturingHeight2, 0);
		}
	}

	public void setStyleSheet(IStyleSheet styleSheet) {
		this.styleSheet = styleSheet;
	}

	public final void preRender(final IWidget widget, int guiWidth, int guiHeight) {
		GlStateManager.pushMatrix();
		GlStateManager.translate(widget.getPosition().xPos(), widget.getPosition().yPos(), 0.0f);
		RenderUtil.setColour(widget.getColor());
		if (widget.isCroppedWidet()) {
			final IWidget cropRelative = (widget.getCropWidget() != null) ? widget.getCropWidget() : widget;
			final Point pos = cropRelative.getAbsolutePosition();
			final Area cropZone = widget.getCroppedZone();
			GL11.glEnable(GL11.GL_SCISSOR_TEST);
			this.limitArea(new Area(pos.add(cropZone.pos()), cropZone.size()), guiWidth, guiHeight);
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

	public Texture getTexture(final Object key) {
		if (key instanceof Texture) {
			return (Texture) key;
		}
		return this.styleSheet.getTexture(key);
	}

	public void setTexture(@Nullable final Texture texture) {
		if (texture != null) {
			BinnieCore.getBinnieProxy().bindTexture(texture.getFilename().getResourceLocation());
		}
	}

	public void texture(final Object texture, final Point position) {
		this.texture(this.getTexture(texture), position);
	}

	public void texture(@Nullable final Texture texture, final Point position) {
		if (texture == null) {
			return;
		}
		this.setTexture(texture);
		final Point point = position.sub(new Point(texture.getBorder().l(), texture.getBorder().t()));
		final Area textureArea = texture.getArea().outset(texture.getBorder());
		GuiUtils.drawTexturedModalRect(point.xPos(), point.yPos(), textureArea.pos().xPos(), textureArea.pos().yPos(), textureArea.size().xPos(), textureArea.size().yPos(), 0);
	}

	public void texture(final Object window, final Area area) {
		this.texture(this.getTexture(window), area);
	}

	public void texture(@Nullable final Texture texture, final Area area) {
		if (texture == null) {
			return;
		}
		this.setTexture(texture);
		final Area textureArea = texture.getArea().outset(texture.getBorder());
		final Area targetArea = area.outset(texture.getBorder());
		if (textureArea.width() == targetArea.width() && textureArea.height() == targetArea.height()) {
			final Point position = targetArea.pos();
			GuiUtils.drawTexturedModalRect(position.xPos(), position.yPos(), textureArea.pos().xPos(), textureArea.pos().yPos(), textureArea.size().xPos(), textureArea.size().yPos(), 0);
		} else {
			renderTexturePadded(targetArea, textureArea, texture.getTotalPadding());
		}
	}

	public void limitArea(final Area area, int guiWidth, int guiHeight) {
		float x = area.pos().xPos();
		float y = area.pos().yPos();
		float w = area.size().xPos();
		float h = area.size().yPos();
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
		FontRenderer fontRenderer = minecraft.fontRenderer;
		return fontRenderer.listFormattedStringToWidth(text, width).size() * RenderUtil.getTextHeight();
	}

	public void texturePercentage(final Texture texture, final Area area, final Position direction, final float percentage) {
		final int dist = (direction == Position.Top || direction == Position.BOTTOM) ? Math.round(percentage * texture.height()) : Math.round(percentage * texture.width());
		final int dim = (direction == Position.Top || direction == Position.BOTTOM) ? texture.height() : texture.width();
		int x = area.pos().xPos();
		int y = area.pos().yPos();
		int w = area.size().xPos();
		int h = area.size().yPos();
		switch (direction) {
			case Top: {
				h *= percentage;
				break;
			}
			case RIGHT: {
				x += (1.0f - percentage) * w;
				w *= percentage;
				break;
			}
			case LEFT: {
				w *= percentage;
				break;
			}
			case BOTTOM: {
				y += h - (int) (percentage * h);
				h *= percentage;
				break;
			}
		}
		this.texture(texture.crop(direction, dim - dist), new Area(x, y, w, h));
	}
}
