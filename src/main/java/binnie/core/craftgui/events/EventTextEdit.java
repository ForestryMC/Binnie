// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.craftgui.events;

import binnie.core.craftgui.IWidget;

public class EventTextEdit extends EventValueChanged<String>
{
	public EventTextEdit(final IWidget origin, final String text) {
		super(origin, text);
	}

	public abstract static class Handler extends EventHandler<EventTextEdit>
	{
		public Handler() {
			super(EventTextEdit.class);
		}
	}
}
