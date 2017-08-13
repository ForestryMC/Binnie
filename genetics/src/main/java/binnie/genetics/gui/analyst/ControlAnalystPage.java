package binnie.genetics.gui.analyst;

import binnie.core.api.gui.IArea;
import binnie.core.api.gui.ITitledWidget;
import binnie.core.api.gui.IWidget;
import binnie.core.gui.controls.core.Control;

public abstract class ControlAnalystPage extends Control implements ITitledWidget {
	public ControlAnalystPage(IWidget parent, IArea area) {
		super(parent, area);
		hide();
	}

	@Override
	public void onRenderBackground(int guiWidth, int guiHeight) {
	}

}
