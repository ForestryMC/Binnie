package binnie.botany.craftgui;

import binnie.botany.api.IColorMix;
import binnie.core.craftgui.controls.listbox.ControlList;
import binnie.core.craftgui.controls.listbox.ControlOption;

public class ControlColorMixItem extends ControlOption<IColorMix> {
	private final ControlColorDisplay itemWidget1;
	private final ControlColorDisplay itemWidget2;
	private final ControlColorDisplay itemWidget3;
	private final ControlColorMixSymbol addSymbol;
	private final ControlColorMixSymbol arrowSymbol;

	public ControlColorMixItem(ControlList<IColorMix> controlList, IColorMix option, int y) {
		super(controlList, option, y);
		IColorMix value = getValue();
		itemWidget1 = new ControlColorDisplay(this, 4, 4, value.getColorFirst());
		itemWidget2 = new ControlColorDisplay(this, 44, 4, value.getColorSecond());
		itemWidget3 = new ControlColorDisplay(this, 104, 4, value.getResult());
		addSymbol = new ControlColorMixSymbol(this, 24, 4, 0, value);
		arrowSymbol = new ControlColorMixSymbol(this, 64, 4, 1, value);
	}
}
