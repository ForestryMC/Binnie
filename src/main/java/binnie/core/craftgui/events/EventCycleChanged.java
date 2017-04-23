// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.craftgui.events;

import binnie.core.craftgui.IWidget;

public class EventCycleChanged extends Event
{
	public int value;

	public EventCycleChanged(final IWidget origin, final int value) {
		super(origin);
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
