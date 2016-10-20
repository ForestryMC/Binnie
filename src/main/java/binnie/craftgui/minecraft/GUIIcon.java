// 
// Decompiled by Procyon v0.5.30
// 

package binnie.craftgui.minecraft;

import net.minecraft.util.IIcon;
import binnie.core.BinnieCore;
import binnie.Binnie;
import binnie.core.resource.BinnieIcon;

public enum GUIIcon
{
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
	BinnieIcon icon;

	private GUIIcon(final String path) {
		this.path = path;
	}

	public void register() {
		this.icon = Binnie.Resource.getItemIcon(BinnieCore.instance, "gui/" + this.path);
	}

	public IIcon getIcon() {
		return this.icon.getIcon();
	}
}
