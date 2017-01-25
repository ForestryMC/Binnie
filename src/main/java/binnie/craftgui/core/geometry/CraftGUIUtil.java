package binnie.craftgui.core.geometry;

import binnie.craftgui.controls.core.IControlValue;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.events.EventValueChanged;
import com.google.common.base.Preconditions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.client.config.GuiUtils;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nonnull;

public class CraftGUIUtil {
	public static void alignToWidget(final IWidget target, final IWidget relativeTo) {
		final IPoint startPos = target.getAbsolutePosition();
		final IPoint endPos = relativeTo.getAbsolutePosition();
		moveWidget(target, endPos.sub(startPos));
	}

	public static void moveWidget(final IWidget target, final IPoint movement) {
		target.setPosition(target.getPosition().add(movement));
	}

	public static void horizontalGrid(final int px, final int py, final IWidget... widgets) {
		horizontalGrid(px, py, TextJustification.MiddleCenter, 0, widgets);
	}

	public static void horizontalGrid(final int px, final int py, final TextJustification just, final int spacing, final IWidget... widgets) {
		int x = 0;
		int h = 0;
		for (final IWidget widget : widgets) {
			h = Math.max(h, widget.getSize().y());
		}
		for (final IWidget widget : widgets) {
			widget.setPosition(new IPoint(px + x, py + Math.round((h - widget.getSize().y()) * just.getYOffset())));
			x += widget.getSize().x() + spacing;
		}
	}

	public static void verticalGrid(final int px, final int py, final IWidget... widgets) {
		horizontalGrid(px, py, TextJustification.MiddleCenter, 0, widgets);
	}

	public static void verticalGrid(final int px, final int py, final TextJustification just, final int spacing, final IWidget... widgets) {
		int y = 0;
		int w = 0;
		for (final IWidget widget : widgets) {
			w = Math.max(w, widget.getSize().x());
		}
		for (final IWidget widget : widgets) {
			widget.setPosition(new IPoint(px + Math.round((w - widget.getSize().x()) * just.getXOffset()), py + y));
			y += widget.getSize().y() + spacing;
		}
	}

	public static <T> void linkWidgets(final IControlValue<T> tab, final IControlValue<T> target) {
		tab.addSelfEventHandler(new EventValueChanged.Handler() {
			@Override
			public void onEvent(final EventValueChanged event) {
				target.setValue((T) event.getValue());
			}
		});
		target.addSelfEventHandler(new EventValueChanged.Handler() {
			@Override
			public void onEvent(final EventValueChanged event) {
				tab.setValue((T) event.getValue());
			}
		});
	}

	public static void renderTexturePadded(final IArea area, final IArea texture, final IBorder padding) {
		int borderLeft = padding.l();
		int borderRight = padding.r();
		int borderTop = padding.t();
		int borderBottom = padding.b();
		final int posX = area.pos().x();
		final int posY = area.pos().y();
		final int width = area.size().x();
		final int height = area.size().y();
		final int textWidth = texture.w();
		final int textHeight = texture.h();
		final int u = texture.x();
		final int v = texture.y();
		if (borderTop + borderBottom > height) {
			borderTop = height / 2;
			borderBottom = height / 2;
		}
		if (borderLeft + borderRight > width) {
			borderLeft = width / 2;
			borderRight = width / 2;
		}
		final IPoint origin = area.pos();
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

	public static void drawRect(float left, float top, float right, float bottom, final int color) {
		GuiUtils.drawGradientRect(0, (int) left, (int) top, (int) right, (int) bottom, color, color);
	}

	public static void renderItem(final IPoint pos, final ItemStack itemStack) {
		renderItem(pos, itemStack, false);
	}

	public static void renderItem(final IPoint pos, final ItemStack itemStack, final boolean rotating) {
		Preconditions.checkNotNull(itemStack);
		Minecraft minecraft = Minecraft.getMinecraft();
		RenderHelper.enableGUIStandardItemLighting();
		FontRenderer font = getFontRenderer(minecraft, itemStack);

		if (rotating) {
			GlStateManager.pushMatrix();
			final float phase = Minecraft.getSystemTime() / 20;
			GL11.glTranslatef(8, 8, 0);
			GL11.glRotatef(phase, 0, -0.866f, 0.5f);
			GL11.glTranslatef(-8, -8, -67.1f);
		}

		minecraft.getRenderItem().renderItemAndEffectIntoGUI(null, itemStack, pos.x(), pos.y());
		minecraft.getRenderItem().renderItemOverlayIntoGUI(font, itemStack, pos.x(), pos.y(), null);

		if (rotating) {
			GlStateManager.popMatrix();
		}

		GlStateManager.disableBlend();
		RenderHelper.disableStandardItemLighting();
	}

	@Nonnull
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

}
