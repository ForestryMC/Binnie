package binnie.core.gui.controls.scroll;

import binnie.core.api.gui.IWidget;
import binnie.core.gui.controls.core.Control;

public class ControlScroll extends Control {
	private final IControlScrollable scrollWidget;

	public ControlScroll(IWidget parent, int x, int y, int width, int height, IControlScrollable scrollWidget) {
		super(parent, x, y, width, height);
		this.scrollWidget = scrollWidget;
		new ControlScrollBar(this);
	}

	public IControlScrollable getScrollableWidget() {
		return this.scrollWidget;
	}
}
