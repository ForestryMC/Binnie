package binnie.core.craftgui.minecraft;

import binnie.core.BinnieCore;
import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.Tooltip;
import binnie.core.craftgui.events.EventKey;
import binnie.core.craftgui.events.EventMouse;
import binnie.core.craftgui.geometry.IArea;
import binnie.core.craftgui.geometry.IBorder;
import binnie.core.craftgui.geometry.IPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

@SideOnly(Side.CLIENT)
public class GuiCraftGUI extends GuiContainer {
	private Window window;
	private ItemStack draggedItem;

	public GuiCraftGUI(Window window) {
		super(window.getContainer());
		this.window = window;
		resize(window.getSize());
	}

	@Override
	public void updateScreen() {
		window.updateClient();
	}

	public Minecraft getMinecraft() {
		return mc;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
		// ignored
	}

	@Override
	public void initGui() {
		super.initGui();
		mc.thePlayer.openContainer = inventorySlots;
		guiLeft = (width - xSize) / 2;
		guiTop = (height - ySize) / 2;
		window.setSize(new IPoint(xSize, ySize));
		window.setPosition(new IPoint(guiLeft, guiTop));
		window.initGui();
	}

	public ItemStack getDraggedItem() {
		return draggedItem;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float par3) {
		window.setMousePosition(mouseX - (int) window.getPosition().x(), mouseY - (int) window.getPosition().y());
		drawDefaultBackground();

		GL11.glDisable(32826);
		RenderHelper.disableStandardItemLighting();
		GL11.glDisable(2896);
		GL11.glDisable(2929);
		zLevel = 10.0f;
		GuiScreen.itemRender.zLevel = zLevel;

		window.render();

		RenderHelper.enableGUIStandardItemLighting();
		GL11.glPushMatrix();
		GL11.glEnable(32826);
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0f, 240.0f);
		InventoryPlayer playerInventory = mc.thePlayer.inventory;
		draggedItem = playerInventory.getItemStack();
		if (draggedItem != null) {
			renderItem(new IPoint(mouseX - 8, mouseY - 8), draggedItem, 200, false);
			renderItem(new IPoint(mouseX - 8, mouseY - 8), draggedItem, 200, false);
		}
		GL11.glDisable(32826);
		GL11.glPopMatrix();
		GL11.glDisable(2896);
		GL11.glDisable(2929);
		MinecraftTooltip tooltip = new MinecraftTooltip();
		if (isHelpMode()) {
			tooltip.setType(Tooltip.Type.HELP);
			window.getHelpTooltip(tooltip);
		} else {
			tooltip.setType(Tooltip.Type.STANDARD);
			window.getTooltip(tooltip);
		}
		if (tooltip.exists()) {
			renderTooltip(new IPoint(mouseX, mouseY), tooltip);
		}
		zLevel = 0.0f;
		GL11.glEnable(2896);
		GL11.glEnable(2929);
	}

	public void renderTooltip(IPoint mousePosition, MinecraftTooltip tooltip) {
		int mouseX = (int) mousePosition.x();
		int mouseY = (int) mousePosition.y();
		FontRenderer font = getFontRenderer();
		GL11.glDisable(32826);
		RenderHelper.disableStandardItemLighting();
		GL11.glDisable(2896);
		GL11.glDisable(2929);
		int k = 0;
		List<String> strings = new ArrayList<String>();
		for (String string : tooltip.getList()) {
			if (string != null) {
				if (!string.contains("~~~")) {
					strings.addAll(font.listFormattedStringToWidth(string, tooltip.maxWidth));
				} else {
					strings.add(string);
				}
			}
		}
		for (String s : strings) {
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
		if (i1 + k > width) {
			i1 -= 28 + k;
		}
		if (j1 + k2 + 6 > height) {
			j1 = height - k2 - 6;
		}
		zLevel = 300.0f;
		GuiScreen.itemRender.zLevel = 300.0f;
		int l2 = -267386864;
		int j2;
		int i2 = j2 = 1342177280 + MinecraftTooltip.getOutline(tooltip.getType());
		drawGradientRect(i1 - 3, j1 - 4, i1 + k + 3, j1 - 3, l2, l2);
		drawGradientRect(i1 - 3, j1 + k2 + 3, i1 + k + 3, j1 + k2 + 4, l2, l2);
		drawGradientRect(i1 - 3, j1 - 3, i1 + k + 3, j1 + k2 + 3, l2, l2);
		drawGradientRect(i1 - 4, j1 - 3, i1 - 3, j1 + k2 + 3, l2, l2);
		drawGradientRect(i1 + k + 3, j1 - 3, i1 + k + 4, j1 + k2 + 3, l2, l2);
		drawGradientRect(i1 - 3, j1 - 3 + 1, i1 - 3 + 1, j1 + k2 + 3 - 1, i2, j2);
		drawGradientRect(i1 + k + 2, j1 - 3 + 1, i1 + k + 3, j1 + k2 + 3 - 1, i2, j2);
		drawGradientRect(i1 - 3, j1 - 3, i1 + k + 3, j1 - 3 + 1, i2, i2);
		drawGradientRect(i1 - 3, j1 + k2 + 2, i1 + k + 3, j1 + k2 + 3, j2, j2);
		for (int k3 = 0; k3 < strings.size(); ++k3) {
			String s2 = strings.get(k3);
			if (k3 == 0) {
				s2 = MinecraftTooltip.getTitle(tooltip.getType()) + s2;
			} else {
				s2 = MinecraftTooltip.getBody(tooltip.getType()) + s2;
			}
			if (s2.contains("~~~")) {
				String split = s2.split("~~~")[1];
				try {
					NBTTagCompound nbt = (NBTTagCompound) JsonToNBT.func_150315_a(split);
					ItemStack stack = ItemStack.loadItemStackFromNBT(nbt);
					GL11.glPushMatrix();
					GL11.glTranslatef(i1, j1 - 1.5f, 0.0f);
					GL11.glScalef(0.6f, 0.6f, 1.0f);
					renderItem(new IPoint(0.0f, 0.0f), stack, false);
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
		zLevel = 0.0f;
		GuiScreen.itemRender.zLevel = 0.0f;
		GL11.glEnable(2896);
		GL11.glEnable(2929);
		RenderHelper.enableStandardItemLighting();
		GL11.glEnable(32826);
	}

	@Override
	protected void mouseClicked(int x, int y, int button) {
		IWidget origin = window;
		if (window.getMousedOverWidget() != null) {
			origin = window.getMousedOverWidget();
		}
		window.callEvent(new EventMouse.Down(origin, x, y, button));
	}

	public boolean isShiftDown() {
		return Keyboard.isKeyDown(mc.gameSettings.keyBindSneak.getKeyCode());
	}

	@Override
	protected void keyTyped(char c, int key) {
		if (key == 1 || (key == mc.gameSettings.keyBindInventory.getKeyCode() && window.getFocusedWidget() == null)) {
			mc.thePlayer.closeScreen();
		}
		IWidget origin = (window.getFocusedWidget() == null) ? window : window.getFocusedWidget();
		window.callEvent(new EventKey.Down(origin, c, key));
	}

	@Override
	protected void mouseMovedOrUp(int x, int y, int button) {
		IWidget origin = (window.getMousedOverWidget() == null) ? window : window.getMousedOverWidget();
		if (button == -1) {
			float dx = Mouse.getEventDX() * width / (float) mc.displayWidth;
			float dy = -(Mouse.getEventDY() * height / (float) mc.displayHeight);
		} else {
			window.callEvent(new EventMouse.Up(origin, x, y, button));
		}
	}

	@Override
	public void handleMouseInput() {
		super.handleMouseInput();
		int dWheel = Mouse.getDWheel();
		IWidget origin = (window.getFocusedWidget() == null) ? window : window.getFocusedWidget();
		if (dWheel != 0) {
			window.callEvent(new EventMouse.Wheel(window, dWheel));
		}
	}

	@Override
	public void onGuiClosed() {
		window.onClose();
	}

	public void renderTexturedRect(float x, float y, float u, float v, float w, float h) {
		drawTexturedModalRect((int) x, (int) y, (int) u, (int) v, (int) w, (int) h);
	}

	public void renderTexture(IPoint position, IArea textureArea) {
		drawTexturedModalRect((int) position.x(), (int) position.y(), (int) textureArea.pos().x(), (int) textureArea.pos().y(), (int) textureArea.size().x(), (int) textureArea.size().y());
	}

	private void renderTexturedRect(IArea area, IPoint uv) {
		renderTexturedRect(area.pos().x(), area.pos().y(), uv.x(), uv.y(), area.size().x(), area.size().y());
	}

	public void renderTexturePadded(IArea area, IArea texture, IBorder padding) {
		int borderLeft = (int) padding.l();
		int borderRight = (int) padding.r();
		int borderTop = (int) padding.t();
		int borderBottom = (int) padding.b();
		int posX = (int) area.pos().x();
		int posY = (int) area.pos().y();
		int width = (int) area.size().x();
		int height = (int) area.size().y();
		int textWidth = (int) texture.w();
		int textHeight = (int) texture.h();
		int u = (int) texture.x();
		int v = (int) texture.y();
		if (borderTop + borderBottom > height) {
			borderTop = height / 2;
			borderBottom = height / 2;
		}
		if (borderLeft + borderRight > width) {
			borderLeft = width / 2;
			borderRight = width / 2;
		}
		IPoint origin = area.pos();
		drawTexturedModalRect(posX, posY, u, v, borderLeft, borderTop);
		drawTexturedModalRect(posX + width - borderRight, posY, u + textWidth - borderRight, v, borderRight, borderTop);
		drawTexturedModalRect(posX, posY + height - borderBottom, u, v + textHeight - borderBottom, borderLeft, borderBottom);
		drawTexturedModalRect(posX + width - borderRight, posY + height - borderBottom, u + textWidth - borderRight, v + textHeight - borderBottom, borderRight, borderBottom);
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
			drawTexturedModalRect(posX + currentXPos, posY, u + borderLeft, v, texturingWidth, borderTop);
			drawTexturedModalRect(posX + currentXPos, posY + height - borderBottom, u + borderLeft, v + textHeight - borderBottom, texturingWidth, borderBottom);
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
				drawTexturedModalRect(posX + currentXPos, posY + currentYPos, u + borderLeft, v + borderTop, texturingWidth, texturingHeight);
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
			drawTexturedModalRect(posX, posY + currentYPos2, u, v + borderTop, borderLeft, texturingHeight2);
			drawTexturedModalRect(posX + width - borderRight, posY + currentYPos2, u + textWidth - borderRight, v + borderTop, borderRight, texturingHeight2);
		}
	}

	public void drawGradientArea(float p_73733_1_, float p_73733_2_, float p_73733_3_, float p_73733_4_, int p_73733_5_, int p_73733_6_) {
		float f = (p_73733_5_ >> 24 & 0xFF) / 255.0f;
		float f2 = (p_73733_5_ >> 16 & 0xFF) / 255.0f;
		float f3 = (p_73733_5_ >> 8 & 0xFF) / 255.0f;
		float f4 = (p_73733_5_ & 0xFF) / 255.0f;
		float f5 = (p_73733_6_ >> 24 & 0xFF) / 255.0f;
		float f6 = (p_73733_6_ >> 16 & 0xFF) / 255.0f;
		float f7 = (p_73733_6_ >> 8 & 0xFF) / 255.0f;
		float f8 = (p_73733_6_ & 0xFF) / 255.0f;
		GL11.glDisable(3553);
		GL11.glEnable(3042);
		GL11.glDisable(3008);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		GL11.glShadeModel(7425);
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.setColorRGBA_F(f2, f3, f4, f);
		tessellator.addVertex(p_73733_3_, p_73733_2_, zLevel);
		tessellator.addVertex(p_73733_1_, p_73733_2_, zLevel);
		tessellator.setColorRGBA_F(f6, f7, f8, f5);
		tessellator.addVertex(p_73733_1_, p_73733_4_, zLevel);
		tessellator.addVertex(p_73733_3_, p_73733_4_, zLevel);
		tessellator.draw();
		GL11.glShadeModel(7424);
		GL11.glDisable(3042);
		GL11.glEnable(3008);
		GL11.glEnable(3553);
	}

	public void renderItem(IPoint pos, ItemStack item, boolean rotating) {
		renderItem(pos, item, (int) zLevel + 3, rotating);
	}

	private void renderItem(IPoint pos, ItemStack item, int zLevel, boolean rotating) {
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0f, 240.0f);
		GL11.glPushMatrix();
		RenderHelper.enableGUIStandardItemLighting();
		GL11.glEnable(32826);
		GL11.glEnable(2929);
		FontRenderer font = item.getItem().getFontRenderer(item);
		if (font == null) {
			font = getFontRenderer();
		}
		if (item != null) {
			BinnieCore.proxy.getMinecraftInstance();
			float phase = Minecraft.getSystemTime() / 20.0f;
			GL11.glPushMatrix();
			if (rotating) {
				GL11.glTranslatef(8.0f, 8.0f, 0.0f);
				GL11.glRotatef(phase, 0.0f, -0.866f, 0.5f);
				GL11.glTranslatef(-8.0f, -8.0f, -67.1f);
			}
			GuiScreen.itemRender.renderItemAndEffectIntoGUI(font, mc.renderEngine, item, (int) pos.x(), (int) pos.y());
			GL11.glPopMatrix();
			GuiScreen.itemRender.renderItemOverlayIntoGUI(font, mc.renderEngine, item, (int) pos.x(), (int) pos.y(), null);
		}
		GL11.glClear(256);
		GL11.glEnable(3042);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		RenderHelper.disableStandardItemLighting();
		CraftGUI.Render.colour(-1);
		GL11.glEnable(32826);
		GL11.glPopMatrix();
	}

	public void renderIcon(IPoint pos, IIcon icon, ResourceLocation map) {
		if (icon == null) {
			return;
		}
		GL11.glPushMatrix();
		GL11.glEnable(32826);
		BinnieCore.proxy.bindTexture(map);
		GuiScreen.itemRender.zLevel = zLevel;
		GuiScreen.itemRender.renderIcon((int) pos.x(), (int) pos.y(), icon, 16, 16);
		GL11.glEnable(32826);
		GL11.glPopMatrix();
	}

	public boolean isHelpMode() {
		return Keyboard.isKeyDown(15);
	}

	public FontRenderer getFontRenderer() {
		return fontRendererObj;
	}

	public void resize(IPoint size) {
		xSize = (int) size.x();
		ySize = (int) size.y();
		guiLeft = (width - xSize) / 2;
		guiTop = (height - ySize) / 2;
		window.setPosition(new IPoint(guiLeft, guiTop));
	}

	public void limitArea(IArea area) {
		float x = area.pos().x();
		float y = area.pos().y();
		float w = area.size().x();
		float h = area.size().y();
		y = height - (y + h);
		float k = xSize;
		float scaleX = width / (float) mc.displayWidth;
		float scaleY = height / (float) mc.displayHeight;
		x += 0.0f;
		y += 0.0f;
		w += 0.0f;
		h += 0.0f;
		GL11.glScissor((int) (x / scaleX), (int) (y / scaleY), (int) (w / scaleX), (int) (h / scaleY));
	}

	public int getZLevel() {
		return (int) zLevel;
	}

	public void drawRect(float p_73734_0_, float p_73734_1_, float p_73734_2_, float p_73734_3_, int p_73734_4_) {
		if (p_73734_0_ < p_73734_2_) {
			float j1 = p_73734_0_;
			p_73734_0_ = p_73734_2_;
			p_73734_2_ = j1;
		}
		if (p_73734_1_ < p_73734_3_) {
			float j1 = p_73734_1_;
			p_73734_1_ = p_73734_3_;
			p_73734_3_ = j1;
		}
		float f3 = (p_73734_4_ >> 24 & 0xFF) / 255.0f;
		float f4 = (p_73734_4_ >> 16 & 0xFF) / 255.0f;
		float f5 = (p_73734_4_ >> 8 & 0xFF) / 255.0f;
		float f6 = (p_73734_4_ & 0xFF) / 255.0f;
		Tessellator tessellator = Tessellator.instance;
		GL11.glEnable(3042);
		GL11.glDisable(3553);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		GL11.glColor4f(f4, f5, f6, f3);
		tessellator.startDrawingQuads();
		tessellator.addVertex(p_73734_0_, p_73734_3_, 0.0);
		tessellator.addVertex(p_73734_2_, p_73734_3_, 0.0);
		tessellator.addVertex(p_73734_2_, p_73734_1_, 0.0);
		tessellator.addVertex(p_73734_0_, p_73734_1_, 0.0);
		tessellator.draw();
		GL11.glEnable(3553);
		GL11.glDisable(3042);
	}
}
