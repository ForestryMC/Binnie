package binnie.core.craftgui.minecraft;

import binnie.Binnie;
import binnie.core.BinnieCore;
import binnie.core.resource.BinnieIcon;
import net.minecraft.util.IIcon;

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
    BinnieIcon icon;

    GUIIcon(String path) {
        this.path = path;
    }

    public void register() {
        icon = Binnie.Resource.getItemIcon(BinnieCore.instance, "gui/" + path);
    }

    public IIcon getIcon() {
        return icon.getIcon();
    }
}
