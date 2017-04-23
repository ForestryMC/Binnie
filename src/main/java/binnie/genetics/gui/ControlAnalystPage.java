// 
// Decompiled by Procyon v0.5.30
// 

package binnie.genetics.gui;

import java.text.DecimalFormat;
import binnie.core.craftgui.geometry.IArea;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.core.Control;

public abstract class ControlAnalystPage extends Control
{
	public ControlAnalystPage(final IWidget parent, final IArea area) {
		super(parent, area);
		this.hide();
	}

	@Override
	public void onRenderBackground() {
	}

	public abstract String getTitle();

	protected String getTimeString(final float time) {
		final float seconds = time / 20.0f;
		final float minutes = seconds / 60.0f;
		final float hours = minutes / 60.0f;
		final DecimalFormat df = new DecimalFormat("#.0");
		if (hours > 1.0f) {
			return df.format(hours) + " hours";
		}
		if (minutes > 1.0f) {
			return df.format(minutes) + " min.";
		}
		return df.format(seconds) + " sec.";
	}
}
