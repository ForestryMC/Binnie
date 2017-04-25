// 
// Decompiled by Procyon v0.5.30
// 

package binnie.genetics.gui;

import binnie.core.craftgui.controls.ControlTextCentered;
import forestry.api.lepidopterology.IButterfly;
import binnie.core.craftgui.geometry.IArea;
import binnie.core.craftgui.IWidget;

public class AnalystPageSpecimen extends ControlAnalystPage
{
	public AnalystPageSpecimen(final IWidget parent, final IArea area, final IButterfly ind) {
		super(parent, area);
		this.setColor(3355443);
		int y = 4;
		new ControlTextCentered(this, y, "§nSpecimen").setColor(this.getColor());
		y += 12;
		final float w = (this.w() - 16.0f) * ind.getSize();
		new ControlIndividualDisplay(this, (this.w() - w) / 2.0f, y + (this.w() - w) / 2.0f, w, ind);
	}

	@Override
	public String getTitle() {
		return "Specimen";
	}
}
