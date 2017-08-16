package binnie.extratrees.genetics.gui.analyst;

import binnie.core.api.gui.IArea;
import binnie.core.api.gui.ITitledWidget;
import binnie.core.gui.controls.ControlIndividualDisplay;
import binnie.core.gui.controls.core.Control;
import net.minecraft.util.text.TextFormatting;

import forestry.api.lepidopterology.IButterfly;

import binnie.core.api.gui.IWidget;
import binnie.core.gui.controls.ControlTextCentered;
import binnie.core.util.I18N;
import binnie.genetics.api.analyst.AnalystConstants;

public class AnalystPageSpecimen extends Control implements ITitledWidget {
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
