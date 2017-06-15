package binnie.core.craftgui.minecraft;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;

import net.minecraftforge.fml.client.config.GuiUtils;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.Tooltip;
import binnie.core.craftgui.events.EventKey;
import binnie.core.craftgui.events.EventMouse;
import binnie.core.craftgui.geometry.Point;
import binnie.core.craftgui.renderer.RenderUtil;

@SideOnly(Side.CLIENT)
public class GuiCraftGUI extends GuiContainer {
	Point mousePos;
	private Window window;
	private ItemStack draggedItem;

	public GuiCraftGUI(final Window window) {
		super(window.getContainer());
		this.mousePos = Point.ZERO;
		this.window = window;
		this.draggedItem = ItemStack.EMPTY;
		this.resize(window.getSize());
	}

	@Override
	public void updateScreen() {
		this.window.updateClient();
	}

	public Minecraft getMinecraft() {
		return this.mc;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(final float var1, final int var2, final int var3) {
	}

	@Override
	public void initGui() {
		super.initGui();
		this.mc.player.openContainer = this.inventorySlots;
		this.guiLeft = (this.width - this.xSize) / 2;
		this.guiTop = (this.height - this.ySize) / 2;
		this.window.setSize(new Point(this.xSize, this.ySize));
		this.window.setPosition(new Point(this.guiLeft, this.guiTop));
		this.window.initGui();
	}

	public ItemStack getDraggedItem() {
		return this.draggedItem;
	}

	@Override
	public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
		this.window.setMousePosition(mouseX - this.window.getPosition().x(), mouseY - this.window.getPosition().y());
		this.drawDefaultBackground();
		GlStateManager.disableRescaleNormal();
		RenderHelper.disableStandardItemLighting();
		GlStateManager.disableLighting();
		GlStateManager.disableDepth();
		this.zLevel = 10.0f;
		//GuiScreen.itemRender.zLevel = this.zLevel;
		this.window.render(this.width, this.height);


		GlStateManager.pushMatrix();
		GlStateManager.enableRescaleNormal();
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0f, 240.0f);
		final InventoryPlayer playerInventory = this.mc.player.inventory;
		this.draggedItem = playerInventory.getItemStack();
		if (!this.draggedItem.isEmpty()) {
			RenderUtil.drawItem(new Point(mouseX - 8, mouseY - 8), this.draggedItem, false);
		}
		RenderHelper.enableGUIStandardItemLighting();
		GlStateManager.disableRescaleNormal();
		GlStateManager.popMatrix();
		GlStateManager.disableLighting();
		GlStateManager.disableDepth();
		final MinecraftTooltip tooltip = new MinecraftTooltip();
		if (this.isHelpMode()) {
			tooltip.setType(Tooltip.Type.Help);
			this.window.getHelpTooltip(tooltip);
		} else {
			tooltip.setType(Tooltip.Type.Standard);
			this.window.getTooltip(tooltip);
		}
		if (tooltip.exists()) {
			this.renderTooltip(new Point(mouseX, mouseY), tooltip);
		}
		this.zLevel = 0.0f;
		GlStateManager.enableLighting();
		GlStateManager.enableDepth();
	}

	public void renderTooltip(final Point mousePosition, final MinecraftTooltip tooltip) {
		final int mouseX = mousePosition.x();
		final int mouseY = mousePosition.y();
		final FontRenderer font = this.getFontRenderer();

		boolean containsItemRender = false;

		int k = 0;
		final List<String> strings = new ArrayList<>();
		for (final String string : tooltip.getList()) {
			if (string != null) {
				if (!string.contains("~~~")) {
					strings.addAll(font.listFormattedStringToWidth(string, tooltip.maxWidth));
				} else {
					strings.add(string);
					containsItemRender = true;
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

		if (!containsItemRender) {
			ItemStack itemStack = tooltip.getItemStack();
			GuiUtils.drawHoveringText(itemStack, strings, mouseX, mouseY, width, height, tooltip.maxWidth, font);
		} else {
			GlStateManager.disableRescaleNormal();
			RenderHelper.disableStandardItemLighting();
			GlStateManager.disableLighting();
			GlStateManager.disableDepth();

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
						final ItemStack stack = new ItemStack(nbt);
						GlStateManager.pushMatrix();
						GlStateManager.translate(i1, j1 - 1.5f, 0.0f);
						GlStateManager.scale(0.6f, 0.6f, 1.0f);
						RenderUtil.drawItem(Point.ZERO, stack, false);
						GlStateManager.popMatrix();
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
			GlStateManager.enableLighting();
			GlStateManager.enableDepth();
			RenderHelper.enableStandardItemLighting();
			GlStateManager.enableRescaleNormal();
		}
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
			this.mc.player.closeScreen();
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

	public boolean isHelpMode() {
		return Keyboard.isKeyDown(15);
	}

	public FontRenderer getFontRenderer() {
		return this.fontRendererObj;
	}

	public void resize(final Point size) {
		this.xSize = size.x();
		this.ySize = size.y();
		this.guiLeft = (this.width - this.xSize) / 2;
		this.guiTop = (this.height - this.ySize) / 2;
		this.window.setPosition(new Point(this.guiLeft, this.guiTop));
	}
}
