package binnie.genetics.gui.analyst.butterfly;

import net.minecraft.util.text.TextFormatting;

import forestry.api.lepidopterology.IButterfly;

import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.ControlTextCentered;
import binnie.core.craftgui.geometry.Area;
import binnie.core.util.I18N;
import binnie.genetics.gui.analyst.ControlAnalystPage;
import binnie.genetics.gui.analyst.ControlIndividualDisplay;

public class AnalystPageSpecimen extends ControlAnalystPage {
	public AnalystPageSpecimen(IWidget parent, Area area, IButterfly ind) {
		super(parent, area);
		setColor(3355443);
		int y = 4;
		new ControlTextCentered(this, y, TextFormatting.UNDERLINE + getTitle()).setColor(getColor());
		y += 12;
		int w = Math.round((width() - 16) * ind.getSize());
		new ControlIndividualDisplay(this, (width() - w) / 2, y + (width() - w) / 2, w, ind);
	}

	@Override
	public String getTitle() {
		return I18N.localise("genetics.gui.analyst.specimen.title");
	}
}
