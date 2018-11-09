package binnie.extrabees.client.gui;

import com.google.common.collect.Lists;

import java.awt.Color;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.TextureManager;

import net.minecraftforge.fml.client.config.GuiUtils;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiContainerAlvearyPart<C extends AlvearyContainer> extends GuiContainer {

	protected final C container;
	private int titleS = -1;

	public GuiContainerAlvearyPart(C container) {
		super(container);
		this.container = container;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		TextureManager textureManager = mc.getTextureManager();
		textureManager.bindTexture(container.background);

		drawTexturedModalRect(guiLeft, guiTop, 0, 0, container.getDimension().width, container.getDimension().height);
		int titleX = guiLeft + (xSize - fontRenderer.getStringWidth(container.title)) / 2;
		fontRenderer.drawString(container.title, titleX, guiTop + 8, Color.DARK_GRAY.getRGB());
		if (mouseX > guiLeft + 6 && mouseX < guiLeft + 23 && mouseY > guiTop + 5 && mouseY < guiTop + 22) {
			GuiUtils.drawHoveringText(Lists.newArrayList(container.tooltip), mouseX, mouseY, width, height, 150, fontRenderer);
		}
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);

		if (mouseX > guiLeft + 6 && mouseX < guiLeft + 23 && mouseY > guiTop + 5 && mouseY < guiTop + 22) {
			GuiUtils.drawHoveringText(Lists.newArrayList(container.tooltip), mouseX, mouseY, width, height, 150, fontRenderer);
		}
	}
}
