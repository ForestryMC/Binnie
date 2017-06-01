package binnie.extrabees.client.gui;

import binnie.extrabees.utils.AlvearyMutationHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.tuple.Pair;

import java.awt.Color;

public class GuiContainerMutator extends GuiContainerAlvearyPart {

	private String mutagens;
	private int titleS = -1;

	public GuiContainerMutator(AbstractAlvearyContainer container) {
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
			titleS = mid - (font.getStringWidth(mutagens) / 2);
		}
		font.drawString(mutagens, titleS, j + 50, Color.DARK_GRAY.getRGB());
		int gens = AlvearyMutationHandler.getMutagens().size();
		int width = gens * 18;
		int w2 = width / 2;
		int q = 0;
		for (final Pair<ItemStack, Float> mutagen : AlvearyMutationHandler.getMutagens()) {
			Minecraft.getMinecraft().getRenderItem().renderItemIntoGUI(mutagen.getLeft(), mid - w2 + q * 18, j + 62);
			q++;
		}
	}
}
