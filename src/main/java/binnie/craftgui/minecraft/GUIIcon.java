package binnie.craftgui.minecraft;

import binnie.Binnie;
import binnie.core.BinnieCore;
import binnie.core.resource.BinnieSprite;

public enum GUIIcon {
	ARROW_UP("arrow-up"),
	ARROW_DOWN("arrow-down"),
	ArrowLeft("arrow-left"),
	ARROW_RIGHT("arrow-right"),
	ARROW_UP_LEFT("arrow-upleft"),
	ArrowUpRight("arrow-upright"),
	ArrowRightUp("arrow-rightup"),
	ArrowRightDown("arrow-rightdown"),
	ArrowDownRight("arrow-downright"),
	ArrowDownLeft("arrow-downleft"),
	ARROW_LEFT_DOWN("arrow-leftdown"),
	ArrowLeftUp("arrow-leftup");

	String path;
	BinnieSprite icon;

	GUIIcon(final String path) {
		this.path = path;
	}

	public void register() {
		this.icon = Binnie.RESOURCE.getItemSprite(BinnieCore.getInstance(), "gui/" + this.path);
	}

	public BinnieSprite getIcon() {
		return this.icon;
	}
}
