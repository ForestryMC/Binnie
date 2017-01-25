package binnie.craftgui.minecraft;

import binnie.Binnie;
import binnie.core.BinnieCore;
import binnie.core.resource.BinnieSprite;

public enum GUIIcon {
	ArrowUp("arrow-up"),
	ArrowDown("arrow-down"),
	ArrowLeft("arrow-left"),
	ArrowRight("arrow-right"),
	ArrowUpLeft("arrow-upleft"),
	ArrowUpRight("arrow-upright"),
	ArrowRightUp("arrow-rightup"),
	ArrowRightDown("arrow-rightdown"),
	ArrowDownRight("arrow-downright"),
	ArrowDownLeft("arrow-downleft"),
	ArrowLeftDown("arrow-leftdown"),
	ArrowLeftUp("arrow-leftup");

	String path;
	BinnieSprite icon;

	GUIIcon(final String path) {
		this.path = path;
	}

	public void register() {
		this.icon = Binnie.RESOURCE.getItemSprite(BinnieCore.instance, "gui/" + this.path);
	}

	public BinnieSprite getIcon() {
		return this.icon;
	}
}
