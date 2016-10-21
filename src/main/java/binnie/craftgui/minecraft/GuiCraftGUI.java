package binnie.craftgui.minecraft;

import binnie.core.BinnieCore;
import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.Tooltip;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.core.geometry.IBorder;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.events.EventKey;
import binnie.craftgui.events.EventMouse;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SideOnly(Side.CLIENT)
public class GuiCraftGUI extends GuiContainer {
    IPoint mousePos;
    private Window window;
    private ItemStack draggedItem;

    @Override
    public void updateScreen() {
        this.window.updateClient();
    }

    public Minecraft getMinecraft() {
        return this.mc;
    }

    public GuiCraftGUI(final Window window) {
        super(window.getContainer());
        this.mousePos = new IPoint(0.0f, 0.0f);
        this.window = window;
        this.resize(window.getSize());
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(final float var1, final int var2, final int var3) {
    }

    @Override
    public void initGui() {
        super.initGui();
        this.mc.thePlayer.openContainer = this.inventorySlots;
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
        this.window.setSize(new IPoint(this.xSize, this.ySize));
        this.window.setPosition(new IPoint(this.guiLeft, this.guiTop));
        this.window.initGui();
    }

    public ItemStack getDraggedItem() {
        return this.draggedItem;
    }

    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float par3) {
        this.window.setMousePosition(mouseX - (int) this.window.getPosition().x(), mouseY - (int) this.window.getPosition().y());
        this.drawDefaultBackground();

        GL11.glDisable(32826);
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(2896);
        GL11.glDisable(2929);
        this.zLevel = 10.0f;
        //GuiScreen.itemRender.zLevel = this.zLevel;

        this.window.render();

        RenderHelper.enableGUIStandardItemLighting();
        GL11.glPushMatrix();
        GL11.glEnable(32826);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0f, 240.0f);
        final InventoryPlayer playerInventory = this.mc.thePlayer.inventory;
        this.draggedItem = playerInventory.getItemStack();
        if (this.draggedItem != null) {
            this.renderItem(new IPoint(mouseX - 8, mouseY - 8), this.draggedItem, 200, false);
            this.renderItem(new IPoint(mouseX - 8, mouseY - 8), this.draggedItem, 200, false);
        }
        GL11.glDisable(32826);
        GL11.glPopMatrix();
        GL11.glDisable(2896);
        GL11.glDisable(2929);
        final MinecraftTooltip tooltip = new MinecraftTooltip();
        if (this.isHelpMode()) {
            tooltip.setType(Tooltip.Type.Help);
            this.window.getHelpTooltip(tooltip);
        } else {
            tooltip.setType(Tooltip.Type.Standard);
            this.window.getTooltip(tooltip);
        }
        if (tooltip.exists()) {
            this.renderTooltip(new IPoint(mouseX, mouseY), tooltip);
        }
        this.zLevel = 0.0f;
        GL11.glEnable(2896);
        GL11.glEnable(2929);
    }

    public void renderTooltip(final IPoint mousePosition, final MinecraftTooltip tooltip) {
        final int mouseX = (int) mousePosition.x();
        final int mouseY = (int) mousePosition.y();
        final FontRenderer font = this.getFontRenderer();
        GL11.glDisable(32826);
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(2896);
        GL11.glDisable(2929);
        int k = 0;
        final List<String> strings = new ArrayList<>();
        for (final String string : tooltip.getList()) {
            if (string != null) {
                if (!string.contains("~~~")) {
                    strings.addAll(font.listFormattedStringToWidth(string, tooltip.maxWidth));
                } else {
                    strings.add(string);
                }
            }
        }
        for (final String s : strings) {
            int l = font.getStringWidth(s);
            if (s.contains("~~~")) {
                l = 12 + font.getStringWidth(s.replaceAll("~~~(.*?)~~~", ""));
            }
            if (l > k) {
                k = l;
            }
        }
        int i1 = mouseX + 12;
        int j1 = mouseY - 12;
        int k2 = 8;
        if (strings.size() > 1) {
            k2 += 2 + (strings.size() - 1) * 10;
        }
        if (i1 + k > this.width) {
            i1 -= 28 + k;
        }
        if (j1 + k2 + 6 > this.height) {
            j1 = this.height - k2 - 6;
        }
        this.zLevel = 300.0f;
        //GuiScreen.itemRender.zLevel = 300.0f;
        final int l2 = -267386864;
        final int j2;
        final int i2 = j2 = 1342177280 + MinecraftTooltip.getOutline(tooltip.getType());
        this.drawGradientRect(i1 - 3, j1 - 4, i1 + k + 3, j1 - 3, l2, l2);
        this.drawGradientRect(i1 - 3, j1 + k2 + 3, i1 + k + 3, j1 + k2 + 4, l2, l2);
        this.drawGradientRect(i1 - 3, j1 - 3, i1 + k + 3, j1 + k2 + 3, l2, l2);
        this.drawGradientRect(i1 - 4, j1 - 3, i1 - 3, j1 + k2 + 3, l2, l2);
        this.drawGradientRect(i1 + k + 3, j1 - 3, i1 + k + 4, j1 + k2 + 3, l2, l2);
        this.drawGradientRect(i1 - 3, j1 - 3 + 1, i1 - 3 + 1, j1 + k2 + 3 - 1, i2, j2);
        this.drawGradientRect(i1 + k + 2, j1 - 3 + 1, i1 + k + 3, j1 + k2 + 3 - 1, i2, j2);
        this.drawGradientRect(i1 - 3, j1 - 3, i1 + k + 3, j1 - 3 + 1, i2, i2);
        this.drawGradientRect(i1 - 3, j1 + k2 + 2, i1 + k + 3, j1 + k2 + 3, j2, j2);
        for (int k3 = 0; k3 < strings.size(); ++k3) {
            String s2 = strings.get(k3);
            if (k3 == 0) {
                s2 = MinecraftTooltip.getTitle(tooltip.getType()) + s2;
            } else {
                s2 = MinecraftTooltip.getBody(tooltip.getType()) + s2;
            }
            if (s2.contains("~~~")) {
                final String split = s2.split("~~~")[1];
                try {
                    final NBTTagCompound nbt = JsonToNBT.getTagFromJson(split);
                    final ItemStack stack = ItemStack.loadItemStackFromNBT(nbt);
                    GL11.glPushMatrix();
                    GL11.glTranslatef(i1, j1 - 1.5f, 0.0f);
                    GL11.glScalef(0.6f, 0.6f, 1.0f);
                    this.renderItem(new IPoint(0.0f, 0.0f), stack, false);
                    GL11.glPopMatrix();
                } catch (NBTException e) {
                    e.printStackTrace();
                }
                s2 = "   " + s2.replaceAll("~~~(.*?)~~~", "");
            }
            font.drawStringWithShadow(s2, i1, j1, -1);
            if (k3 == 0) {
                j1 += 2;
            }
            j1 += 10;
        }
        this.zLevel = 0.0f;
        //GuiScreen.itemRender.zLevel = 0.0f;
        GL11.glEnable(2896);
        GL11.glEnable(2929);
        RenderHelper.enableStandardItemLighting();
        GL11.glEnable(32826);
    }

    @Override
    protected void mouseClicked(final int x, final int y, final int button) {
        IWidget origin = this.window;
        if (this.window.getMousedOverWidget() != null) {
            origin = this.window.getMousedOverWidget();
        }
        this.window.callEvent(new EventMouse.Down(origin, x, y, button));
    }

    public boolean isShiftDown() {
        return Keyboard.isKeyDown(this.mc.gameSettings.keyBindSneak.getKeyCode());
    }

    @Override
    protected void keyTyped(final char c, final int key) {
        if (key == 1 || (key == this.mc.gameSettings.keyBindInventory.getKeyCode() && this.window.getFocusedWidget() == null)) {
            this.mc.thePlayer.closeScreen();
        }
        final IWidget origin = (this.window.getFocusedWidget() == null) ? this.window : this.window.getFocusedWidget();
        this.window.callEvent(new EventKey.Down(origin, c, key));
    }


    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        //super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
        mouseMovedOrUp(mouseX, mouseY, clickedMouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        //super.mouseReleased(mouseX, mouseY, state);
        mouseMovedOrUp(mouseX, mouseY, -1);
    }

    //@Override
    protected void mouseMovedOrUp(final int x, final int y, final int button) {
        final IWidget origin = (this.window.getMousedOverWidget() == null) ? this.window : this.window.getMousedOverWidget();
        if (button == -1) {
            final float dx = Mouse.getEventDX() * this.width / (float) this.mc.displayWidth;
            final float dy = -(Mouse.getEventDY() * this.height / (float) this.mc.displayHeight);
        } else {
            this.window.callEvent(new EventMouse.Up(origin, x, y, button));
        }
    }


    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        final int dWheel = Mouse.getDWheel();
        final IWidget origin = (this.window.getFocusedWidget() == null) ? this.window : this.window.getFocusedWidget();
        if (dWheel != 0) {
            this.window.callEvent(new EventMouse.Wheel(this.window, dWheel));
        }
    }

    @Override
    public void onGuiClosed() {
        this.window.onClose();
    }

    public void renderTexturedRect(final float x, final float y, final float u, final float v, final float w, final float h) {
        this.drawTexturedModalRect((int) x, (int) y, (int) u, (int) v, (int) w, (int) h);
    }

    public void renderTexture(final IPoint position, final IArea textureArea) {
        this.drawTexturedModalRect((int) position.x(), (int) position.y(), (int) textureArea.pos().x(), (int) textureArea.pos().y(), (int) textureArea.size().x(), (int) textureArea.size().y());
    }

    private void renderTexturedRect(final IArea area, final IPoint uv) {
        this.renderTexturedRect(area.pos().x(), area.pos().y(), uv.x(), uv.y(), area.size().x(), area.size().y());
    }

    public void renderTexturePadded(final IArea area, final IArea texture, final IBorder padding) {
        int borderLeft = (int) padding.l();
        int borderRight = (int) padding.r();
        int borderTop = (int) padding.t();
        int borderBottom = (int) padding.b();
        final int posX = (int) area.pos().x();
        final int posY = (int) area.pos().y();
        final int width = (int) area.size().x();
        final int height = (int) area.size().y();
        final int textWidth = (int) texture.w();
        final int textHeight = (int) texture.h();
        final int u = (int) texture.x();
        final int v = (int) texture.y();
        if (borderTop + borderBottom > height) {
            borderTop = height / 2;
            borderBottom = height / 2;
        }
        if (borderLeft + borderRight > width) {
            borderLeft = width / 2;
            borderRight = width / 2;
        }
        final IPoint origin = area.pos();
        this.drawTexturedModalRect(posX, posY, u, v, borderLeft, borderTop);
        this.drawTexturedModalRect(posX + width - borderRight, posY, u + textWidth - borderRight, v, borderRight, borderTop);
        this.drawTexturedModalRect(posX, posY + height - borderBottom, u, v + textHeight - borderBottom, borderLeft, borderBottom);
        this.drawTexturedModalRect(posX + width - borderRight, posY + height - borderBottom, u + textWidth - borderRight, v + textHeight - borderBottom, borderRight, borderBottom);
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
            this.drawTexturedModalRect(posX + currentXPos, posY, u + borderLeft, v, texturingWidth, borderTop);
            this.drawTexturedModalRect(posX + currentXPos, posY + height - borderBottom, u + borderLeft, v + textHeight - borderBottom, texturingWidth, borderBottom);
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
                this.drawTexturedModalRect(posX + currentXPos, posY + currentYPos, u + borderLeft, v + borderTop, texturingWidth, texturingHeight);
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
            this.drawTexturedModalRect(posX, posY + currentYPos2, u, v + borderTop, borderLeft, texturingHeight2);
            this.drawTexturedModalRect(posX + width - borderRight, posY + currentYPos2, u + textWidth - borderRight, v + borderTop, borderRight, texturingHeight2);
        }
    }

    public void drawGradientArea(final float p_73733_1_, final float p_73733_2_, final float p_73733_3_, final float p_73733_4_, final int p_73733_5_, final int p_73733_6_) {
        final float f = (p_73733_5_ >> 24 & 0xFF) / 255.0f;
        final float f2 = (p_73733_5_ >> 16 & 0xFF) / 255.0f;
        final float f3 = (p_73733_5_ >> 8 & 0xFF) / 255.0f;
        final float f4 = (p_73733_5_ & 0xFF) / 255.0f;
        final float f5 = (p_73733_6_ >> 24 & 0xFF) / 255.0f;
        final float f6 = (p_73733_6_ >> 16 & 0xFF) / 255.0f;
        final float f7 = (p_73733_6_ >> 8 & 0xFF) / 255.0f;
        final float f8 = (p_73733_6_ & 0xFF) / 255.0f;
        GL11.glDisable(3553);
        GL11.glEnable(3042);
        GL11.glDisable(3008);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glShadeModel(7425);
//		final Tessellator tessellator = Tessellator.instance;
//		tessellator.startDrawingQuads();
//		tessellator.setColorRGBA_F(f2, f3, f4, f);
//		tessellator.addVertex(p_73733_3_, p_73733_2_, this.zLevel);
//		tessellator.addVertex(p_73733_1_, p_73733_2_, this.zLevel);
//		tessellator.setColorRGBA_F(f6, f7, f8, f5);
//		tessellator.addVertex(p_73733_1_, p_73733_4_, this.zLevel);
//		tessellator.addVertex(p_73733_3_, p_73733_4_, this.zLevel);
//		tessellator.draw();
        GL11.glShadeModel(7424);
        GL11.glDisable(3042);
        GL11.glEnable(3008);
        GL11.glEnable(3553);
    }

    public void renderItem(final IPoint pos, final ItemStack item, final boolean rotating) {
        this.renderItem(pos, item, (int) this.zLevel + 3, rotating);
    }

    private void renderItem(final IPoint pos, final ItemStack item, final int zLevel, final boolean rotating) {
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0f, 240.0f);
        GL11.glPushMatrix();
        RenderHelper.enableGUIStandardItemLighting();
        GL11.glEnable(32826);
        GL11.glEnable(2929);
        FontRenderer font = item.getItem().getFontRenderer(item);
        if (font == null) {
            font = this.getFontRenderer();
        }
        if (item != null) {
            BinnieCore.proxy.getMinecraftInstance();
            final float phase = Minecraft.getSystemTime() / 20.0f;
            GL11.glPushMatrix();
            if (rotating) {
                GL11.glTranslatef(8.0f, 8.0f, 0.0f);
                GL11.glRotatef(phase, 0.0f, -0.866f, 0.5f);
                GL11.glTranslatef(-8.0f, -8.0f, -67.1f);
            }
            Minecraft.getMinecraft().getRenderItem().renderItemAndEffectIntoGUI(item, (int) pos.x(), (int) pos.y());
            GL11.glPopMatrix();
            Minecraft.getMinecraft().getRenderItem().renderItemOverlayIntoGUI(font, item, (int) pos.x(), (int) pos.y(), (String) null);
        }
        GL11.glClear(256);
        GL11.glEnable(3042);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        RenderHelper.disableStandardItemLighting();
        CraftGUI.Render.colour(-1);
        GL11.glEnable(32826);
        GL11.glPopMatrix();
    }

    public void renderIcon(final IPoint pos, final TextureAtlasSprite icon, final ResourceLocation map) {
        if (icon == null) {
            return;
        }
        GL11.glPushMatrix();
        GL11.glEnable(32826);
        BinnieCore.proxy.bindTexture(map);
        drawTexture((int) pos.x(), (int) pos.y(), icon, 0, 0, zLevel);
        GL11.glEnable(32826);
        GL11.glPopMatrix();
    }

    private static void drawTexture(double xCoord, double yCoord, TextureAtlasSprite textureSprite, int maskTop, int maskRight, double zLevel) {
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

    public boolean isHelpMode() {
        return Keyboard.isKeyDown(15);
    }

    public FontRenderer getFontRenderer() {
        return this.fontRendererObj;
    }

    public void resize(final IPoint size) {
        this.xSize = (int) size.x();
        this.ySize = (int) size.y();
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
        this.window.setPosition(new IPoint(this.guiLeft, this.guiTop));
    }

    public void limitArea(final IArea area) {
        float x = area.pos().x();
        float y = area.pos().y();
        float w = area.size().x();
        float h = area.size().y();
        y = this.height - (y + h);
        final float k = this.xSize;
        final float scaleX = this.width / (float) this.mc.displayWidth;
        final float scaleY = this.height / (float) this.mc.displayHeight;
        x += 0.0f;
        y += 0.0f;
        w += 0.0f;
        h += 0.0f;
        GL11.glScissor((int) (x / scaleX), (int) (y / scaleY), (int) (w / scaleX), (int) (h / scaleY));
    }

    public int getZLevel() {
        return (int) this.zLevel;
    }

    public void drawRect(float p_73734_0_, float p_73734_1_, float p_73734_2_, float p_73734_3_, final int p_73734_4_) {
        if (p_73734_0_ < p_73734_2_) {
            final float j1 = p_73734_0_;
            p_73734_0_ = p_73734_2_;
            p_73734_2_ = j1;
        }
        if (p_73734_1_ < p_73734_3_) {
            final float j1 = p_73734_1_;
            p_73734_1_ = p_73734_3_;
            p_73734_3_ = j1;
        }
        final float f3 = (p_73734_4_ >> 24 & 0xFF) / 255.0f;
        final float f4 = (p_73734_4_ >> 16 & 0xFF) / 255.0f;
        final float f5 = (p_73734_4_ >> 8 & 0xFF) / 255.0f;
        final float f6 = (p_73734_4_ & 0xFF) / 255.0f;
        final Tessellator tessellator = Tessellator.getInstance();
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glColor4f(f4, f5, f6, f3);
        tessellator.getBuffer().begin(GL11.GL_QUADS, new VertexFormat());
        tessellator.getBuffer().
                pos(p_73734_0_, p_73734_3_, 0.0)
                .pos(p_73734_2_, p_73734_3_, 0.0)
                .pos(p_73734_2_, p_73734_1_, 0.0)
                .pos(p_73734_0_, p_73734_1_, 0.0).endVertex();
        tessellator.draw();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
    }
}
