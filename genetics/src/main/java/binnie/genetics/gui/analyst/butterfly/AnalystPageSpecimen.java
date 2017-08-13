package binnie.genetics.gui.analyst.butterfly;

import binnie.core.api.gui.IArea;
import net.minecraft.util.text.TextFormatting;

import forestry.api.lepidopterology.IButterfly;

import binnie.core.api.gui.IWidget;
import binnie.core.gui.controls.ControlTextCentered;
import binnie.core.util.I18N;
import binnie.genetics.gui.analyst.AnalystConstants;
import binnie.genetics.gui.analyst.ControlAnalystPage;
import binnie.genetics.gui.analyst.ControlIndividualDisplay;

public class AnalystPageSpecimen extends ControlAnalystPage {
	public AnalystPageSpecimen(IWidget parent, IArea area, IButterfly ind) {
		super(parent, area);
		setColor(3355443);
		int y = 4;
		new ControlTextCentered(this, y, TextFormatting.UNDERLINE + getTitle()).setColor(getColor());
		y += 12;
		int w = Math.round((getWidth() - 16) * ind.getSize());
		new ControlIndividualDisplay(this, (getWidth() - w) / 2, y + (getWidth() - w) / 2, w, ind);
	}

	@Override
	public String getTitle() {
		return I18N.localise(AnalystConstants.SPECIMEN_KEY + ".title");
	}
}
