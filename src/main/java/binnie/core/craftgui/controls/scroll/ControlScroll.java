// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.craftgui.controls.scroll;

import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.core.Control;

public class ControlScroll extends Control
{
	private IControlScrollable scrollWidget;

	public ControlScroll(final IWidget parent, final float x, final float y, final float width, final float height, final IControlScrollable scrollWidget) {
		super(parent, x, y, width, height);
		this.scrollWidget = scrollWidget;
		new ControlScrollBar(this);
	}

	public IControlScrollable getScrollableWidget() {
		return scrollWidget;
	}
}
