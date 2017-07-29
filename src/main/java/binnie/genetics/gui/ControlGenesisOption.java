package binnie.genetics.gui;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.craftgui.controls.listbox.ControlList;
import binnie.core.craftgui.controls.listbox.ControlOption;
import binnie.core.craftgui.geometry.Area;
import binnie.core.craftgui.geometry.TextJustification;
import binnie.core.craftgui.renderer.RenderUtil;
import binnie.core.genetics.Gene;

public class ControlGenesisOption extends ControlOption<Gene> {
	public ControlGenesisOption(ControlList<Gene> parent, Gene gene, int y) {
		super(parent, gene, y);
	}

	String getAlleleName() {
		return getValue().getAlleleName();
	}

	String getChromosomeName() {
		return getValue().getShortChromosomeName();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderBackground(int guiWidth, int guiHeight) {
		super.onRenderBackground(guiWidth, guiHeight);
		RenderUtil.drawText(new Area(0, 0, 70, 22), TextJustification.MIDDLE_CENTER, getChromosomeName(), getColor());
		RenderUtil.drawText(new Area(75, 0, 80, 22), TextJustification.MIDDLE_CENTER, getAlleleName(), getColor());
		RenderUtil.drawSolidRect(new Area(70, 2, 1, 16), -16777216 + getColor());
	}
}
