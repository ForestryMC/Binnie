package binnie.core.gui.renderer;

import javax.annotation.Nullable;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;

import net.minecraftforge.fml.client.config.GuiUtils;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.BinnieCore;
import binnie.core.api.gui.Alignment;
import binnie.core.api.gui.IArea;
import binnie.core.api.gui.IBorder;
import binnie.core.api.gui.IPoint;
import binnie.core.api.gui.ITexture;
import binnie.core.api.gui.IWidget;
import binnie.core.gui.geometry.Area;
import binnie.core.gui.resource.stylesheet.IStyleSheet;
import binnie.core.gui.resource.stylesheet.StyleSheetManager;

import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class TextureRenderer {
	private IStyleSheet styleSheet;

	private static void renderTexturePadded(IArea area, IArea texture, IBorder padding) {
		int borderLeft = padding.getLeft();
		int borderRight = padding.getRight();
		int borderTop = padding.getTop();
		int borderBottom = padding.getBottom();
		int posX = area.pos().xPos();
		int posY = area.pos().yPos();
		int width = area.size().xPos();
		int height = area.size().yPos();
		int textWidth = texture.width();
		int textHeight = texture.height();
		int u = texture.xPos();
		int v = texture.yPos();
		if (borderTop + borderBottom > height) {
			borderTop = height / 2;
			borderBottom = height / 2;
		}
		if (borderLeft + borderRight > width) {
			borderLeft = width / 2;
			borderRight = width / 2;
		}
		GuiUtils.drawTexturedModalRect(posX, posY, u, v, borderLeft, borderTop, 0);
		GuiUtils.drawTexturedModalRect(posX + width - borderRight, posY, u + textWidth - borderRight, v, borderRight, borderTop, 0);
		GuiUtils.drawTexturedModalRect(posX, posY + height - borderBottom, u, v + textHeight - borderBottom, borderLeft, borderBottom, 0);
		GuiUtils.drawTexturedModalRect(posX + width - borderRight, posY + height - borderBottom, u + textWidth - borderRight, v + textHeight - borderBottom, borderRight, borderBottom, 0);
		int texturingWidth;
		for (int currentXPos = borderLeft; currentXPos < width - borderRight; currentXPos += texturingWidth) {
			int distanceXRemaining = width - borderRight - currentXPos;
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
				int distanceYRemaining = height - borderBottom - currentYPos;
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
			int distanceYRemaining2 = height - borderBottom - currentYPos2;
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

	public final void preRender(IWidget widget, int guiWidth, int guiHeight) {
		GlStateManager.pushMatrix();
		GlStateManager.translate(widget.getPosition().xPos(), widget.getPosition().yPos(), 0.0f);
		RenderUtil.setColour(widget.getColor());
		if (widget.isCroppedWidet()) {
			IWidget cropRelative = (widget.getCropWidget() != null) ? widget.getCropWidget() : widget;
			IPoint pos = cropRelative.getAbsolutePosition();
			IArea cropZone = widget.getCroppedZone();
			GL11.glEnable(GL11.GL_SCISSOR_TEST);
			this.limitArea(new Area(pos.add(cropZone.pos()), cropZone.size()), guiWidth, guiHeight);
		}
		GlStateManager.disableDepth();
	}

	public final void postRender(IWidget widget) {
		if (widget.isCroppedWidet()) {
			GL11.glDisable(GL11.GL_SCISSOR_TEST);
		}
		GlStateManager.enableDepth();
		GlStateManager.popMatrix();
	}

	public ITexture getTexture(Object key) {
		if (key instanceof ITexture) {
			return (ITexture) key;
		}
		if (!styleSheet.hasTexture(key)) {
			return StyleSheetManager.getDefaultTexture(key);
		}
		return this.styleSheet.getTexture(key);
	}

	public void setTexture(@Nullable ITexture texture) {
		if (texture != null) {
			BinnieCore.getBinnieProxy().bindTexture(texture.getResourceLocation());
		}
	}

	public void texture(Object texture, IPoint position) {
		this.texture(this.getTexture(texture), position);
	}

	public void texture(@Nullable ITexture texture, IPoint position) {
		if (texture == null) {
			return;
		}
		this.setTexture(texture);
		IPoint point = position.sub(texture.getBorder().getLeft(), texture.getBorder().getTop());
		IArea textureArea = texture.getArea().outset(texture.getBorder());
		GuiUtils.drawTexturedModalRect(point.xPos(), point.yPos(), textureArea.pos().xPos(), textureArea.pos().yPos(), textureArea.size().xPos(), textureArea.size().yPos(), 0);
	}

	public void texture(Object window, IArea area) {
		this.texture(this.getTexture(window), area);
	}

	public void texture(@Nullable ITexture texture, IArea area) {
		if (texture == null) {
			return;
		}
		this.setTexture(texture);
		IArea textureArea = texture.getArea().outset(texture.getBorder());
		IArea targetArea = area.outset(texture.getBorder());
		if (textureArea.width() == targetArea.width() && textureArea.height() == targetArea.height()) {
			IPoint position = targetArea.pos();
			GuiUtils.drawTexturedModalRect(position.xPos(), position.yPos(), textureArea.pos().xPos(), textureArea.pos().yPos(), textureArea.size().xPos(), textureArea.size().yPos(), 0);
		} else {
			renderTexturePadded(targetArea, textureArea, texture.getTotalPadding());
		}
	}

	public void limitArea(IArea area, int guiWidth, int guiHeight) {
		float x = area.pos().xPos();
		float y = area.pos().yPos();
		float w = area.size().xPos();
		float h = area.size().yPos();
		y = guiHeight - (y + h);
		Minecraft minecraft = Minecraft.getMinecraft();
		float scaleX = guiWidth / (float) minecraft.displayWidth;
		float scaleY = guiHeight / (float) minecraft.displayHeight;
		x += 0.0f;
		y += 0.0f;
		w += 0.0f;
		h += 0.0f;
		GL11.glScissor((int) (x / scaleX), (int) (y / scaleY), (int) (w / scaleX), (int) (h / scaleY));
	}

	public int textHeight(String text, int width) {
		Minecraft minecraft = Minecraft.getMinecraft();
		FontRenderer fontRenderer = minecraft.fontRenderer;
		return fontRenderer.listFormattedStringToWidth(text, width).size() * RenderUtil.getTextHeight();
	}

	public void texturePercentage(ITexture texture, IArea area, Alignment direction, float percentage) {
		int dist = (direction == Alignment.TOP || direction == Alignment.BOTTOM) ? Math.round(percentage * texture.height()) : Math.round(percentage * texture.width());
		int dim = (direction == Alignment.TOP || direction == Alignment.BOTTOM) ? texture.height() : texture.width();
		int x = area.pos().xPos();
		int y = area.pos().yPos();
		int w = area.size().xPos();
		int h = area.size().yPos();
		switch (direction) {
			case TOP: {
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
