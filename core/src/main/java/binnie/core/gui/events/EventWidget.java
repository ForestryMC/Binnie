package binnie.core.gui.events;

import binnie.core.api.gui.IWidget;
import binnie.core.api.gui.events.Event;

public class EventWidget extends Event {
	public EventWidget(IWidget origin) {
		super(origin);
	}

	public static class Show extends EventWidget {
		public Show(IWidget origin) {
			super(origin);
		}
	}

	public static class Hide extends EventWidget {
		public Hide(IWidget origin) {
			super(origin);
		}
	}

	public static class ChangePosition extends EventWidget {
		public ChangePosition(IWidget origin) {
			super(origin);
		}
	}

	public static class ChangeSize extends EventWidget {
		public ChangeSize(IWidget origin) {
			super(origin);
		}
	}

	public static class ChangeOffset extends EventWidget {
		public ChangeOffset(IWidget origin) {
			super(origin);
		}
	}

	public static class ChangeColour extends EventWidget {
		public ChangeColour(IWidget origin) {
			super(origin);
		}
	}

	public static class StartMouseOver extends EventWidget {
		public StartMouseOver(IWidget origin) {
			super(origin);
		}
	}

	public static class EndMouseOver extends EventWidget {
		public EndMouseOver(IWidget origin) {
			super(origin);
		}
	}

	public static class StartDrag extends EventWidget {
		private final int button;

		public StartDrag(IWidget origin, int button) {
			super(origin);
			this.button = button;
		}

		public int getButton() {
			return this.button;
		}
	}

	public static class EndDrag extends EventWidget {
		public EndDrag(IWidget origin) {
			super(origin);
		}
	}

	public static class GainFocus extends EventWidget {
		public GainFocus(IWidget origin) {
			super(origin);
		}
	}

	public static class LoseFocus extends EventWidget {
		public LoseFocus(IWidget origin) {
			super(origin);
		}
	}
}
