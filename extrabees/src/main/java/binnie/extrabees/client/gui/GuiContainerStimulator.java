package binnie.extrabees.client.gui;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureManager;

public class GuiContainerStimulator extends GuiContainerAlvearyPart<ContainerStimulator> {

	public GuiContainerStimulator(ContainerStimulator container) {
		super(container);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		TextureManager textureManager = mc.getTextureManager();
		textureManager.bindTexture(container.background);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		float energyScaled = container.getEnergyScaled(70);
		drawTexturedModalRect(guiLeft + 71, guiTop + 38, 0, 185, (int) energyScaled, 14);
		drawTexturedModalRect(guiLeft + 71, guiTop + 38, 0, 165, 70, 14);
	}
}
