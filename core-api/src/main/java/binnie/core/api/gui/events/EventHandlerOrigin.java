package binnie.core.api.gui.events;

import binnie.core.api.gui.IWidget;

public enum EventHandlerOrigin {
	ANY,
	SELF,
	PARENT,
	DIRECT_CHILD;

	public boolean isOrigin(IWidget origin, IWidget test) {
		switch (this) {
			case ANY: {
				return true;
			}
			case DIRECT_CHILD: {
				return test.getChildren().contains(origin);
			}
			case PARENT: {
				return test.getParent() == origin;
			}
			case SELF: {
				return test == origin;
			}
			default: {
				return false;
			}
		}
	}
}
