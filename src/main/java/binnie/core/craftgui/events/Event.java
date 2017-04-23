// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.craftgui.events;

import binnie.core.craftgui.IWidget;

public class Event
{
	IWidget origin;

	public Event(final IWidget origin) {
		this.origin = origin;
	}

	public IWidget getOrigin() {
		return origin;
	}

	public boolean isOrigin(final IWidget widget) {
		return origin == widget;
	}
}
