package binnie.core.gui.events;

import binnie.core.gui.IWidget;

public class EventWidget extends Event {
	public EventWidget(final IWidget origin) {
		super(origin);
	}

	public static class Enable extends EventWidget {
		public Enable(final IWidget origin) {
			super(origin);
		}
	}

	public static class Disable extends EventWidget {
		public Disable(final IWidget origin) {
			super(origin);
		}
	}

	public static class Show extends EventWidget {
		public Show(final IWidget origin) {
			super(origin);
		}
	}

	public static class Hide extends EventWidget {
		public Hide(final IWidget origin) {
			super(origin);
		}
	}

	public static class ChangePosition extends EventWidget {
		public ChangePosition(final IWidget origin) {
			super(origin);
		}
	}

	public static class ChangeSize extends EventWidget {
		public ChangeSize(final IWidget origin) {
			super(origin);
		}
	}

	public static class ChangeOffset extends EventWidget {
		public ChangeOffset(final IWidget origin) {
			super(origin);
		}
	}

	public static class ChangeColour extends EventWidget {
		public ChangeColour(final IWidget origin) {
			super(origin);
		}
	}

	public static class StartMouseOver extends EventWidget {
		public StartMouseOver(final IWidget origin) {
			super(origin);
		}
	}

	public static class EndMouseOver extends EventWidget {
		public EndMouseOver(final IWidget origin) {
			super(origin);
		}
	}

	public static class StartDrag extends EventWidget {
		int button;

		public StartDrag(final IWidget origin, final int button) {
			super(origin);
			this.button = button;
		}

		public int getButton() {
			return this.button;
		}
	}

	public static class EndDrag extends EventWidget {
		public EndDrag(final IWidget origin) {
			super(origin);
		}
	}

	public static class GainFocus extends EventWidget {
		public GainFocus(final IWidget origin) {
			super(origin);
		}
	}

	public static class LoseFocus extends EventWidget {
		public LoseFocus(final IWidget origin) {
			super(origin);
		}
	}
}
