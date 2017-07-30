package binnie.genetics.gui.analyst;

import java.text.DecimalFormat;

import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.geometry.Area;

public abstract class ControlAnalystPage extends Control {
	public ControlAnalystPage(IWidget parent, Area area) {
		super(parent, area);
		hide();
	}

	@Override
	public void onRenderBackground(int guiWidth, int guiHeight) {
	}

	public abstract String getTitle();

	protected String getTimeString(int time) {
		int seconds = time / 20;
		int minutes = seconds / 60;
		int hours = minutes / 60;
		DecimalFormat df = new DecimalFormat("#.0");
		if (hours > 1) {
			return df.format(hours) + " hours";
		}
		if (minutes > 1) {
			return df.format(minutes) + " min.";
		}
		return df.format(seconds) + " sec.";
	}
}
