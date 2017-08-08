package binnie.core.gui.controls.listbox;

import binnie.core.gui.controls.ControlText;
import binnie.core.gui.events.EventWidget;
import binnie.core.gui.geometry.TextJustification;

public class ControlTextOption<T> extends ControlOption<T> {
	protected ControlText textWidget;

	public ControlTextOption(final ControlList<T> controlList, final T option, final String optionName, final int y) {
		super(controlList, option, y);
		this.textWidget = null;
		this.textWidget = new ControlText(this, this.getArea(), optionName, TextJustification.MIDDLE_CENTER);
		this.addSelfEventHandler(EventWidget.ChangeColour.class, event -> {
			ControlTextOption.this.textWidget.setColor(ControlTextOption.this.getColor());
		});
	}

	public ControlTextOption(final ControlList<T> controlList, final T option, final int y) {
		this(controlList, option, option.toString(), y);
	}

	public String getText() {
		return this.textWidget.getValue();
	}
}
