package binnie.extrabees.client.gui;

import com.google.common.collect.Lists;

import java.awt.Color;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;

import net.minecraftforge.fml.client.config.GuiUtils;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiContainerAlvearyPart extends GuiContainer {

	@SuppressWarnings("all")
	protected FontRenderer font;
	protected AbstractAlvearyContainer container;
	private int titleS = -1;

	public GuiContainerAlvearyPart(AbstractAlvearyContainer container) {
		super(container);
		this.container = container;
		font = Minecraft.getMinecraft().fontRendererObj;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		Minecraft.getMinecraft().getTextureManager().bindTexture(container.background);
		if (titleS == -1) {
			titleS = (i + (xSize / 2)) - (font.getStringWidth(container.title) / 2);
		}

		drawTexturedModalRect(i, j, 0, 0, container.dimension.width, container.dimension.height);
		font.drawString(container.title, titleS, j + 8, Color.DARK_GRAY.getRGB());
		if (mouseX > i + 6 && mouseX < i + 23 && mouseY > j + 5 && mouseY < j + 22) {
			GuiUtils.drawHoveringText(Lists.newArrayList(container.tooltip), mouseX, mouseY, width, height, 150, font);
		}
	}
}
