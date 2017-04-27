package binnie.genetics.gui;

import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.geometry.IArea;

import java.text.DecimalFormat;

public abstract class ControlAnalystPage extends Control {
	public ControlAnalystPage(IWidget parent, IArea area) {
		super(parent, area);
		hide();
	}

	@Override
	public void onRenderBackground() {
	}

	public abstract String getTitle();

	protected String getTimeString(float time) {
		float seconds = time / 20.0f;
		float minutes = seconds / 60.0f;
		float hours = minutes / 60.0f;
		DecimalFormat df = new DecimalFormat("#.0");
		if (hours > 1.0f) {
			return df.format(hours) + " hours";
		}
		if (minutes > 1.0f) {
			return df.format(minutes) + " min.";
		}
		return df.format(seconds) + " sec.";
	}
}
