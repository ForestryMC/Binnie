package binnie.genetics.gui.butterfly;

import binnie.craftgui.controls.ControlTextCentered;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.Area;
import binnie.genetics.Genetics;
import binnie.genetics.gui.ControlAnalystPage;
import binnie.genetics.gui.ControlIndividualDisplay;
import forestry.api.lepidopterology.IButterfly;
import net.minecraft.util.text.TextFormatting;

public class AnalystPageSpecimen extends ControlAnalystPage {
	public AnalystPageSpecimen(final IWidget parent, final Area area, final IButterfly ind) {
		super(parent, area);
		this.setColour(3355443);
		int y = 4;
		new ControlTextCentered(this, y, TextFormatting.UNDERLINE + getTitle()).setColour(this.getColour());
		y += 12;
		final int w = Math.round((this.width() - 16) * ind.getSize());
		new ControlIndividualDisplay(this, (this.width() - w) / 2, y + (this.width() - w) / 2, w, ind);
	}

	@Override
	public String getTitle() {
		return Genetics.proxy.localise("gui.analyst.specimen.title");
	}
}
