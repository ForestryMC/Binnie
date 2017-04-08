package binnie.botany.craftgui;

import binnie.botany.api.IColourMix;
import binnie.core.craftgui.controls.listbox.ControlList;
import binnie.core.craftgui.controls.listbox.ControlOption;

public class ControlColourMixItem extends ControlOption<IColourMix> {
	private final ControlColourDisplay itemWidget1;
	private final ControlColourDisplay itemWidget2;
	private final ControlColourDisplay itemWidget3;
	private final ControlColourMixSymbol addSymbol;
	private final ControlColourMixSymbol arrowSymbol;

	public ControlColourMixItem(final ControlList<IColourMix> controlList, final IColourMix option, final int y) {
		super(controlList, option, y);
		IColourMix value = this.getValue();
		this.itemWidget1 = new ControlColourDisplay(this, 4, 4, value.getColourFirst());
		this.itemWidget2 = new ControlColourDisplay(this, 44, 4, value.getColourSecond());
		this.itemWidget3 = new ControlColourDisplay(this, 104, 4, value.getResult());
		this.addSymbol = new ControlColourMixSymbol(this, 24, 4, 0, value);
		this.arrowSymbol = new ControlColourMixSymbol(this, 64, 4, 1, value);
	}
}
