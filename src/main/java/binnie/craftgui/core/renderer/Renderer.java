package binnie.craftgui.core.renderer;

import binnie.core.BinnieCore;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.core.geometry.Position;
import binnie.craftgui.core.geometry.TextJustification;
import binnie.craftgui.minecraft.GuiCraftGUI;
import binnie.craftgui.resource.IStyleSheet;
import binnie.craftgui.resource.Texture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

import java.util.List;

public class Renderer {
    GuiCraftGUI gui;
    int currentColour;
    Texture currentTexture;
    IStyleSheet stylesheet;

    public Renderer(final GuiCraftGUI gui) {
        this.currentColour = 16777215;
        this.gui = gui;
    }

    public final void preRender(final IWidget widget) {
        GL11.glPushMatrix();
        GL11.glTranslatef(widget.getPosition().x(), widget.getPosition().y(), 0.0f);
        this.colour(widget.getColour());
        if (widget.isCroppedWidet()) {
            final IWidget cropRelative = (widget.getCropWidget() != null) ? widget.getCropWidget() : widget;
            final IPoint pos = cropRelative.getAbsolutePosition();
            final IArea cropZone = widget.getCroppedZone();
            GL11.glEnable(3089);
            this.limitArea(new IArea(pos.add(cropZone.pos()), cropZone.size()));

        }
        GL11.glDisable(2929);
    }

    public final void postRender(final IWidget widget) {
        if (widget.isCroppedWidet()) {
            GL11.glDisable(3089);
        }
        GL11.glEnable(2929);
        GL11.glPopMatrix();
    }

    public void colour(final int hex) {
        this.currentColour = hex;
        int a = (hex & 0xFF000000) >> 24;
        final int r = (hex & 0xFF0000) >> 16;
        final int g = (hex & 0xFF00) >> 8;
        final int b = hex & 0xFF;
        if (a < 0) {
            a += 256;
        }
        if (a > 0 && a != 255) {
            GL11.glColor4f(r / 255.0f, g / 255.0f, b / 255.0f, a / 255.0f);
            GL11.glEnable(3042);
        } else {
            GL11.glColor3f(r / 255.0f, g / 255.0f, b / 255.0f);
        }
    }

    public Texture getTexture(final Object key) {
        if (key instanceof Texture) {
            return (Texture) key;
        }
        return this.stylesheet.getTexture(key);
    }

    public void setTexture(final Texture texture) {
        if (texture != this.currentTexture && texture != null) {
            BinnieCore.proxy.bindTexture(texture.getFilename());
        }
        this.colour(this.currentColour);
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
        this.gui.renderTexture(point, textureArea);
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
            this.gui.renderTexture(targetArea.pos(), textureArea);
        } else {
            this.gui.renderTexturePadded(targetArea, textureArea, texture.getTotalPadding());
        }
    }

    public void stylesheet(final IStyleSheet sheet) {
        this.stylesheet = sheet;
    }

    public int textWidth(final String text) {
        return this.gui.getFontRenderer().getStringWidth(text);
    }

    public int textHeight() {
        return (this.gui.getFontRenderer() == null) ? 0 : this.gui.getFontRenderer().FONT_HEIGHT;
    }

    public void text(final IPoint pos, final String text, final int colour) {
        this.text(new IArea(pos, new IPoint(500.0f, 500.0f)), TextJustification.TopLeft, text, colour);
    }

    public void text(final IArea area, final TextJustification justification, final String text, final int colour) {
        final IPoint pos = area.pos();
        if (area.size().x() <= 0.0f) {
            return;
        }
        final List<String> wrappedStrings = this.gui.getFontRenderer().listFormattedStringToWidth(text, (int) area.size().x());
        final float totalHeight = wrappedStrings.size() * this.textHeight();
        float posY = area.pos().y();
        if (area.size().y() > totalHeight) {
            posY += (area.size().y() - totalHeight) * justification.getYOffset();
        }
        for (final String string : wrappedStrings) {
            final float stringWidth = this.textWidth(string);
            float posX = area.size().x() - stringWidth;
            posX *= justification.getXOffset();
            GL11.glDisable(2929);
            this.gui.getFontRenderer().drawString(string, (int) (pos.x() + posX), (int) posY, colour);
            posY += this.textHeight();
        }
        GL11.glColor3f(1.0f, 1.0f, 1.0f);
    }

    public void solid(final IArea area, final int colour) {
        this.gui.drawRect(area.pos().x(), area.pos().y(), area.pos().x() + area.size().x(), area.pos().y() + area.size().y(), 0xFF000000 | colour);
    }

    public void solidAlpha(final IArea area, final int c1) {
        this.gui.drawGradientArea(area.pos().x(), area.pos().y(), area.pos().x() + area.size().x(), area.pos().y() + area.size().y(), c1, c1);
    }

    public void gradientRect(final IArea area, final int c1, final int c2) {
        this.gui.drawGradientArea(area.pos().x(), area.pos().y(), area.pos().x() + area.size().x(), area.pos().y() + area.size().y(), c1, c2);
    }

    public void item(final IPoint pos, final ItemStack item) {
        this.gui.renderItem(pos, item, false);
    }

    public void item(final IPoint pos, final ItemStack item, final boolean rotating) {
        this.gui.renderItem(pos, item, rotating);
    }

    public void iconBlock(final IPoint pos, final TextureAtlasSprite icon) {
        this.gui.renderIcon(pos, icon, TextureMap.LOCATION_BLOCKS_TEXTURE);
    }

    public void iconItem(final IPoint pos, final TextureAtlasSprite icon) {
        this.gui.renderIcon(pos, icon, TextureMap.LOCATION_BLOCKS_TEXTURE);
    }

    public void limitArea(final IArea area) {
        this.gui.limitArea(area);
    }

    public float textHeight(final String text, final float width) {
        return this.gui.getFontRenderer().listFormattedStringToWidth(text, (int) width).size() * this.textHeight();
    }

    public void texturePercentage(final Texture texture, final IArea area, final Position direction, final float percentage) {
        final float dist = (direction == Position.Top || direction == Position.Bottom) ? (percentage * texture.h()) : (percentage * texture.w());
        final float dim = (direction == Position.Top || direction == Position.Bottom) ? texture.h() : texture.w();
        float x = area.pos().x();
        float y = area.pos().y();
        float w = area.size().x();
        float h = area.size().y();
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
