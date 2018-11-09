package binnie.extrabees.client.gui;

import java.awt.Color;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

import binnie.extrabees.utils.AlvearyMutationHandler;

public class GuiContainerMutator extends GuiContainerAlvearyPart {

	private final String mutagens;
	private int titleS = -1;

	public GuiContainerMutator(AlvearyContainer container) {
		super(container);
		mutagens = "Possible Mutagens:";
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		int mid = i + (xSize / 2);
		if (titleS == -1) {
			titleS = mid - (fontRenderer.getStringWidth(mutagens) / 2);
		}
		fontRenderer.drawString(mutagens, titleS, j + 50, Color.DARK_GRAY.getRGB());
		int gens = AlvearyMutationHandler.getMutagens().size();
		int width = gens * 18;
		int w2 = width / 2;
		int q = 0;
		for (final Pair<ItemStack, Float> mutagen : AlvearyMutationHandler.getMutagens()) {
			Minecraft.getMinecraft().getRenderItem().renderItemIntoGUI(mutagen.getLeft(), mid - w2 + q * 18, j + 62);
			q++;
		}
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}
}
