package binnie.core.gui.minecraft;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.util.ITooltipFlag;
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

import binnie.core.gui.IWidget;
import binnie.core.gui.Tooltip;
import binnie.core.gui.events.EventKey;
import binnie.core.gui.events.EventMouse;
import binnie.core.gui.geometry.Point;
import binnie.core.gui.renderer.RenderUtil;

@SideOnly(Side.CLIENT)
public class GuiCraftGUI extends GuiContainer {
	private Point mousePos;
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
		this.window.setMousePosition(mouseX - this.window.getPosition().xPos(), mouseY - this.window.getPosition().yPos());
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
			GlStateManager.translate(0, 0, 200);
			RenderUtil.drawItem(new Point(mouseX - 8, mouseY - 8), this.draggedItem, false);
		}
		RenderHelper.enableGUIStandardItemLighting();
		GlStateManager.disableRescaleNormal();
		GlStateManager.popMatrix();
		GlStateManager.disableLighting();
		GlStateManager.disableDepth();
		if(draggedItem.isEmpty()) {
			final MinecraftTooltip tooltip = new MinecraftTooltip();
			if (this.isHelpMode()) {
				tooltip.setType(Tooltip.Type.HELP);
				this.window.getHelpTooltip(tooltip);
			} else {
				tooltip.setType(Tooltip.Type.STANDARD);
				ITooltipFlag tooltipFlag = this.mc.gameSettings.advancedItemTooltips ? ITooltipFlag.TooltipFlags.ADVANCED : ITooltipFlag.TooltipFlags.NORMAL;
				this.window.getTooltip(tooltip, tooltipFlag);
			}
			if (tooltip.exists()) {
				this.renderTooltip(new Point(mouseX, mouseY), tooltip);
			}
		}
		this.zLevel = 0.0f;
		GlStateManager.enableLighting();
		GlStateManager.enableDepth();
	}

	public void renderTooltip(final Point mousePosition, final MinecraftTooltip tooltip) {
		final int mouseX = mousePosition.xPos();
		final int mouseY = mousePosition.yPos();
		final FontRenderer font = this.getFontRenderer();
		ItemStack itemStack = tooltip.getItemStack();

		boolean containsItemRender = false;

		List<String> textLines = new ArrayList<>();
		for (final String string : tooltip.getList()) {
			if (string != null) {
				if (!string.contains(Tooltip.NBT_SEPARATOR)) {
					textLines.addAll(font.listFormattedStringToWidth(string, tooltip.maxWidth));
				} else {
					textLines.add(string);
					containsItemRender = true;
				}
			}
		}
		int tooltipTextWidth = 0;
		for (String textLine : textLines) {
			int textLineWidth = font.getStringWidth(textLine);
			if (textLine.contains(Tooltip.NBT_SEPARATOR)) {
				textLineWidth = 12 + font.getStringWidth(textLine.replaceAll(Tooltip.NBT_SEPARATOR + "(.*?)" + Tooltip.NBT_SEPARATOR, ""));
			}
			if (textLineWidth > tooltipTextWidth) {
				tooltipTextWidth =  textLineWidth;
			}
		}

		if (!containsItemRender) {
			GuiUtils.drawHoveringText(itemStack, textLines, mouseX, mouseY, width, height, tooltip.maxWidth, font);
		} else {
			drawHoveringText(itemStack, textLines, mouseX, mouseY, font, tooltipTextWidth, tooltip);
		}
	}

	private void drawHoveringText(@Nonnull ItemStack itemStack, List<String> textLines, int mouseX, int mouseY, FontRenderer font, int tooltipTextWidth, MinecraftTooltip tooltip){
		GlStateManager.disableRescaleNormal();
		RenderHelper.disableStandardItemLighting();
		GlStateManager.disableLighting();
		GlStateManager.disableDepth();

		boolean needsWrap = false;

		int maxTextWidth = tooltip.maxWidth;
		int titleLinesCount = 1;
		int tooltipX = mouseX + 12;
		if (tooltipX + tooltipTextWidth + 4 > width) {
			tooltipX = mouseX - 16 - tooltipTextWidth;
			if (tooltipX < 4) { // if the tooltip doesn't fit on the screen
				if (mouseX > width / 2) {
					tooltipTextWidth = mouseX - 12 - 8;
				} else {
					tooltipTextWidth = width - 16 - mouseX;
				}
				needsWrap = true;
			}
		}

		if (maxTextWidth > 0 && tooltipTextWidth > maxTextWidth) {
			tooltipTextWidth = maxTextWidth;
			needsWrap = true;
		}

		if (needsWrap) {
			int wrappedTooltipWidth = 0;
			List<String> wrappedTextLines = new ArrayList<String>();
			for (int i = 0; i < textLines.size(); i++) {
				String textLine = textLines.get(i);
				List<String> wrappedLine = font.listFormattedStringToWidth(textLine, tooltipTextWidth);
				if (i == 0) {
					titleLinesCount = wrappedLine.size();
				}

				for (String line : wrappedLine) {
					int lineWidth = font.getStringWidth(line);
					if (textLine.contains(Tooltip.NBT_SEPARATOR)) {
						lineWidth = 12 + font.getStringWidth(textLine.replaceAll(Tooltip.NBT_SEPARATOR + "(.*?)" + Tooltip.NBT_SEPARATOR, ""));
					}
					if (lineWidth > wrappedTooltipWidth) {
						wrappedTooltipWidth = lineWidth;
					}
					wrappedTextLines.add(line);
				}
			}
			tooltipTextWidth = wrappedTooltipWidth;
			textLines = wrappedTextLines;

			if (mouseX > width / 2) {
				tooltipX = mouseX - 16 - tooltipTextWidth;
			} else {
				tooltipX = mouseX + 12;
			}
		}

		int tooltipY = mouseY - 12;
		int tooltipHeight = 8;

		if (textLines.size() > 1) {
			tooltipHeight += 2 + (textLines.size() - 1) * 10;
			if (textLines.size() > titleLinesCount) {
				tooltipHeight += 2; // gap between title lines and next lines
			}
		}
		/*if (tooltipX + tooltipTextWidth > this.width) {
			tooltipX -= 28 + tooltipTextWidth;
		}*/
		if (tooltipY + tooltipHeight + 6 > this.height) {
			tooltipY = this.height - tooltipHeight - 6;
		}
		this.zLevel = 300.0f;
		final int backgroundColor = 0xF0100010;
		drawGradientRect(tooltipX - 3, tooltipY - 4, tooltipX + tooltipTextWidth + 3, tooltipY - 3, backgroundColor, backgroundColor);
		drawGradientRect(tooltipX - 3, tooltipY + tooltipHeight + 3, tooltipX + tooltipTextWidth + 3, tooltipY + tooltipHeight + 4, backgroundColor, backgroundColor);
		drawGradientRect(tooltipX - 3, tooltipY - 3, tooltipX + tooltipTextWidth + 3, tooltipY + tooltipHeight + 3, backgroundColor, backgroundColor);
		drawGradientRect(tooltipX - 4, tooltipY - 3, tooltipX - 3, tooltipY + tooltipHeight + 3, backgroundColor, backgroundColor);
		drawGradientRect(tooltipX + tooltipTextWidth + 3, tooltipY - 3, tooltipX + tooltipTextWidth + 4, tooltipY + tooltipHeight + 3, backgroundColor, backgroundColor);
		final int borderColorStart = 0x50000000 + MinecraftTooltip.getOutline(tooltip.getType());
		final int borderColorEnd = (borderColorStart & 0xFEFEFE) >> 1 | borderColorStart & 0xFF000000;
		drawGradientRect(tooltipX - 3, tooltipY - 3 + 1, tooltipX - 3 + 1, tooltipY + tooltipHeight + 3 - 1, borderColorStart, borderColorEnd);
		drawGradientRect(tooltipX + tooltipTextWidth + 2, tooltipY - 3 + 1, tooltipX + tooltipTextWidth + 3, tooltipY + tooltipHeight + 3 - 1, borderColorStart, borderColorEnd);
		drawGradientRect(tooltipX - 3, tooltipY - 3, tooltipX + tooltipTextWidth + 3, tooltipY - 3 + 1, borderColorStart, borderColorStart);
		drawGradientRect(tooltipX - 3, tooltipY + tooltipHeight + 2, tooltipX + tooltipTextWidth + 3, tooltipY + tooltipHeight + 3, borderColorEnd, borderColorEnd);
		for (int lineNumber = 0; lineNumber < textLines.size(); ++lineNumber) {
			String line = textLines.get(lineNumber);
			if (lineNumber == 0) {
				line = MinecraftTooltip.getTitle(tooltip.getType()) + line;
			} else {
				line = MinecraftTooltip.getBody(tooltip.getType()) + line;
			}
			if (line.contains(Tooltip.NBT_SEPARATOR)) {
				drawItem(line, tooltipX, tooltipY);
				line = "   " + line.replaceAll(Tooltip.NBT_SEPARATOR + "(.*?)" + Tooltip.NBT_SEPARATOR, "");
			}
			font.drawStringWithShadow(line, tooltipX, tooltipY, -1);
			if (lineNumber + 1 == titleLinesCount) {
				tooltipY += 2;
			}
			tooltipY += 10;
		}
		this.zLevel = 0.0f;
		GlStateManager.enableLighting();
		GlStateManager.enableDepth();
		RenderHelper.enableStandardItemLighting();
		GlStateManager.enableRescaleNormal();
	}

	private void drawItem(String line, int tooltipX, int tooltipY){
		String itemTag = line.split(Tooltip.NBT_SEPARATOR)[1];
		try {
			NBTTagCompound nbt = JsonToNBT.getTagFromJson(itemTag);
			ItemStack stack = new ItemStack(nbt);
			GlStateManager.pushMatrix();
			GlStateManager.translate(tooltipX, tooltipY - 1.5f, 0.0f);
			GlStateManager.scale(0.6f, 0.6f, 1.0f);
			RenderUtil.drawItem(Point.ZERO, stack, false);
			GlStateManager.popMatrix();
		} catch (NBTException e) {
			e.printStackTrace();
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
		if (button != 0) {
			this.window.callEvent(new EventMouse.Up(origin, x, y, button));
		}
	}

	@Override
	public void handleMouseInput() throws IOException {
		super.handleMouseInput();
		final int dWheel = Mouse.getDWheel();
		if (dWheel != 0) {
			this.window.callEvent(new EventMouse.Wheel(this.window, dWheel));
		}
	}

	@Override
	public void onGuiClosed() {
		this.window.onClose();
		if (mc.player != null) {
			inventorySlots.onContainerClosed(mc.player);
		}
	}

	public boolean isHelpMode() {
		return Keyboard.isKeyDown(15);
	}

	public FontRenderer getFontRenderer() {
		return this.fontRenderer;
	}

	public void resize(final Point size) {
		this.xSize = size.xPos();
		this.ySize = size.yPos();
		this.guiLeft = (this.width - this.xSize) / 2;
		this.guiTop = (this.height - this.ySize) / 2;
		this.window.setPosition(new Point(this.guiLeft, this.guiTop));
	}
}
