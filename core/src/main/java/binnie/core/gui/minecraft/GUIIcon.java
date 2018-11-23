package binnie.core.gui.minecraft;

import binnie.core.Binnie;
import binnie.core.BinnieCore;
import binnie.core.resource.BinnieSprite;

public enum GUIIcon {
	ARROW_UP("arrow-up"),
	ARROW_DOWN("arrow-down"),
	ARROW_LEFT("arrow-left"),
	ARROW_RIGHT("arrow-right"),
	ARROW_UP_LEFT("arrow-upleft"),
	ARROW_UP_RIGHT("arrow-upright"),
	ARROW_RIGHT_UP("arrow-rightup"),
	ARROW_RIGHT_DOWN("arrow-rightdown"),
	ARROW_DOWN_RIGHT("arrow-downright"),
	ARROW_DOWN_LEFT("arrow-downleft"),
	ARROW_LEFT_DOWN("arrow-leftdown"),
	ARROW_LEFT_UP("arrow-leftup");

	private final String path;
	private BinnieSprite icon;

	GUIIcon(String path) {
		this.path = path;
	}

	public void register() {
		this.icon = Binnie.RESOURCE.getItemSprite(BinnieCore.getInstance(), "gui/" + this.path);
	}

	public BinnieSprite getIcon() {
		return this.icon;
	}
}
