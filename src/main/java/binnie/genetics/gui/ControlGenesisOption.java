package binnie.genetics.gui;

import binnie.core.craftgui.controls.listbox.ControlList;
import binnie.core.craftgui.controls.listbox.ControlOption;
import binnie.core.craftgui.geometry.Area;
import binnie.core.craftgui.geometry.TextJustification;
import binnie.core.craftgui.renderer.RenderUtil;
import binnie.core.genetics.Gene;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ControlGenesisOption extends ControlOption<Gene> {
	public ControlGenesisOption(final ControlList<Gene> parent, final Gene gene, final int y) {
		super(parent, gene, y);
	}

	String getAlleleName() {
		return this.getValue().getAlleleName();
	}

	String getChromosomeName() {
		return this.getValue().getShortChromosomeName();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderBackground(int guiWidth, int guiHeight) {
		super.onRenderBackground(guiWidth, guiHeight);
		RenderUtil.drawText(new Area(0, 0, 70, 22), TextJustification.MiddleCenter, this.getChromosomeName(), this.getColour());
		RenderUtil.drawText(new Area(75, 0, 80, 22), TextJustification.MiddleCenter, this.getAlleleName(), this.getColour());
		RenderUtil.drawSolidRect(new Area(70, 2, 1, 16), -16777216 + this.getColour());
	}
}
