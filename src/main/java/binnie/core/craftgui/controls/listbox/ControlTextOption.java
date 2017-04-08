package binnie.core.craftgui.controls.listbox;

import binnie.core.craftgui.controls.ControlText;
import binnie.core.craftgui.events.EventHandler;
import binnie.core.craftgui.events.EventWidget;
import binnie.core.craftgui.geometry.TextJustification;

public class ControlTextOption<T> extends ControlOption<T> {
	protected ControlText textWidget;

	public ControlTextOption(final ControlList<T> controlList, final T option, final String optionName, final int y) {
		super(controlList, option, y);
		this.textWidget = null;
		this.textWidget = new ControlText(this, this.getArea(), optionName, TextJustification.MiddleCenter);
		this.addEventHandler(new EventWidget.ChangeColour.Handler() {
			@Override
			public void onEvent(final EventWidget.ChangeColour event) {
				ControlTextOption.this.textWidget.setColour(ControlTextOption.this.getColour());
			}
		}.setOrigin(EventHandler.Origin.Self, this));
	}

	public ControlTextOption(final ControlList<T> controlList, final T option, final int y) {
		this(controlList, option, option.toString(), y);
	}

	public String getText() {
		return this.textWidget.getValue();
	}
}
