package binnie.extrabees.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;

/**
 * Created by Elec332 on 13-5-2017.
 */
public class GuiContainerStimulator extends GuiContainerAlvearyPart {

	public GuiContainerStimulator(ContainerStimulator container) {
		super(container);
		this.container = container;
	}

	private ContainerStimulator container;

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		Minecraft.getMinecraft().getTextureManager().bindTexture(container.background);
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		float fill = container.power / (float) container.maxPower;
		float bar = fill * 70;
		GlStateManager.color(1, 1, 1, 1);
		drawTexturedModalRect(i + 71, j + 38, 0, 185, (int) bar, 14);
		drawTexturedModalRect(i + 71, j + 38, 0, 165, 70, 14);
	}

}
