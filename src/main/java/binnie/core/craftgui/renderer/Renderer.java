package binnie.core.craftgui.renderer;

import binnie.core.BinnieCore;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.geometry.IArea;
import binnie.core.craftgui.geometry.IPoint;
import binnie.core.craftgui.geometry.Position;
import binnie.core.craftgui.geometry.TextJustification;
import binnie.core.craftgui.minecraft.GuiCraftGUI;
import binnie.core.craftgui.resource.IStyleSheet;
import binnie.core.craftgui.resource.Texture;
import java.util.List;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import org.lwjgl.opengl.GL11;

public class Renderer {
    protected GuiCraftGUI gui;
    protected int currentColor;
    protected Texture currentTexture;
    protected IStyleSheet stylesheet;

    public Renderer(GuiCraftGUI gui) {
        currentColor = 0xffffff;
        this.gui = gui;
    }

    public void preRender(IWidget widget) {
        GL11.glPushMatrix();
        GL11.glTranslatef(widget.getPosition().x(), widget.getPosition().y(), 0.0f);
        color(widget.getColor());
        if (widget.isCroppedWidget()) {
            IWidget cropRelative = (widget.getCropWidget() != null) ? widget.getCropWidget() : widget;
            IPoint pos = cropRelative.getAbsolutePosition();
            IArea cropZone = widget.getCroppedZone();
            GL11.glEnable(GL11.GL_SCISSOR_TEST);
            limitArea(new IArea(pos.add(cropZone.pos()), cropZone.size()));
        }
        GL11.glDisable(GL11.GL_DEPTH_TEST);
    }

    public void postRender(IWidget widget) {
        if (widget.isCroppedWidget()) {
            GL11.glDisable(GL11.GL_SCISSOR_TEST);
        }
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glPopMatrix();
    }

    public void color(int hex) {
        currentColor = hex;
        int a = (hex & 0xFF000000) >> 24;
        int r = (hex & 0xFF0000) >> 16;
        int g = (hex & 0xFF00) >> 8;
        int b = hex & 0xFF;

        if (a < 0) {
            a += 256;
        }

        if (a > 0 && a != 255) {
            GL11.glColor4f(r / 255.0f, g / 255.0f, b / 255.0f, a / 255.0f);
        } else {
            GL11.glColor3f(r / 255.0f, g / 255.0f, b / 255.0f);
        }
    }

    public Texture getTexture(Object key) {
        if (key instanceof Texture) {
            return (Texture) key;
        }
        return stylesheet.getTexture(key);
    }

    public void setTexture(Texture texture) {
        if (texture != currentTexture && texture != null) {
            BinnieCore.proxy.bindTexture(texture.getFilename());
        }
        color(currentColor);
    }

    public void texture(Object texture, IPoint position) {
        texture(getTexture(texture), position);
    }

    public void texture(Texture texture, IPoint position) {
        if (texture == null) {
            return;
        }

        setTexture(texture);
        IPoint point = position.sub(
                new IPoint(texture.getBorder().l(), texture.getBorder().t()));
        IArea textureArea = texture.getArea().outset(texture.getBorder());
        gui.renderTexture(point, textureArea);
    }

    public void texture(Object window, IArea area) {
        texture(getTexture(window), area);
    }

    public void texture(Texture texture, IArea area) {
        if (texture == null) {
            return;
        }

        setTexture(texture);
        IArea textureArea = texture.getArea().outset(texture.getBorder());
        IArea targetArea = area.outset(texture.getBorder());
        if (textureArea.w() == targetArea.w() && textureArea.h() == targetArea.h()) {
            gui.renderTexture(targetArea.pos(), textureArea);
        } else {
            gui.renderTexturePadded(targetArea, textureArea, texture.getTotalPadding());
        }
    }

    public void stylesheet(IStyleSheet sheet) {
        stylesheet = sheet;
    }

    public int textWidth(String text) {
        return gui.getFontRenderer().getStringWidth(text);
    }

    public int textHeight() {
        return (gui.getFontRenderer() == null) ? 0 : gui.getFontRenderer().FONT_HEIGHT;
    }

    public void text(IPoint pos, String text, int colour) {
        text(new IArea(pos, new IPoint(500.0f, 500.0f)), TextJustification.TOP_LEFT, text, colour);
    }

    public void text(IArea area, TextJustification justification, String text, int colour) {
        IPoint pos = area.pos();
        if (area.size().x() <= 0.0f) {
            return;
        }

        List<String> wrappedStrings = gui.getFontRenderer()
                .listFormattedStringToWidth(text, (int) area.size().x());
        float totalHeight = wrappedStrings.size() * textHeight();
        float posY = area.pos().y();
        if (area.size().y() > totalHeight) {
            posY += (area.size().y() - totalHeight) * justification.getYOffset();
        }

        for (String string : wrappedStrings) {
            float stringWidth = textWidth(string);
            float posX = area.size().x() - stringWidth;
            posX *= justification.getXOffset();
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            gui.getFontRenderer().drawString(string, (int) (pos.x() + posX), (int) posY, colour);
            posY += textHeight();
        }
        GL11.glColor3f(1.0f, 1.0f, 1.0f);
    }

    public void solid(IArea area, int colour) {
        gui.drawRect(
                area.pos().x(),
                area.pos().y(),
                area.pos().x() + area.size().x(),
                area.pos().y() + area.size().y(),
                0xFF000000 | colour);
    }

    public void solidAlpha(IArea area, int c1) {
        gui.drawGradientArea(
                area.pos().x(),
                area.pos().y(),
                area.pos().x() + area.size().x(),
                area.pos().y() + area.size().y(),
                c1,
                c1);
    }

    public void gradientRect(IArea area, int c1, int c2) {
        gui.drawGradientArea(
                area.pos().x(),
                area.pos().y(),
                area.pos().x() + area.size().x(),
                area.pos().y() + area.size().y(),
                c1,
                c2);
    }

    public void item(IPoint pos, ItemStack item) {
        gui.renderItem(pos, item, false);
    }

    public void item(IPoint pos, ItemStack item, boolean rotating) {
        gui.renderItem(pos, item, rotating);
    }

    public void iconBlock(IPoint pos, IIcon icon) {
        gui.renderIcon(pos, icon, TextureMap.locationBlocksTexture);
    }

    public void iconItem(IPoint pos, IIcon icon) {
        gui.renderIcon(pos, icon, TextureMap.locationItemsTexture);
    }

    public void limitArea(IArea area) {
        gui.limitArea(area);
    }

    public float textHeight(String text, float width) {
        return gui.getFontRenderer()
                        .listFormattedStringToWidth(text, (int) width)
                        .size()
                * textHeight();
    }

    public void texturePercentage(Texture texture, IArea area, Position direction, float percentage) {
        float dist = (direction == Position.TOP || direction == Position.BOTTOM)
                ? (percentage * texture.h())
                : (percentage * texture.w());
        float dim = (direction == Position.TOP || direction == Position.BOTTOM) ? texture.h() : texture.w();
        float x = area.pos().x();
        float y = area.pos().y();
        float w = area.size().x();
        float h = area.size().y();

        switch (direction) {
            case TOP:
                h *= percentage;
                break;

            case RIGHT:
                x += (1.0f - percentage) * w;
                w *= percentage;
                break;

            case LEFT:
                w *= percentage;
                break;

            case BOTTOM:
                y += h - (int) (percentage * h);
                h *= percentage;
                break;
        }
        texture(texture.crop(direction, dim - dist), new IArea(x, y, w, h));
    }
}
