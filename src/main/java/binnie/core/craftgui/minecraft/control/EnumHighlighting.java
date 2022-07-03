package binnie.core.craftgui.minecraft.control;

import binnie.core.craftgui.Tooltip;
import binnie.core.craftgui.minecraft.MinecraftTooltip;

public enum EnumHighlighting {
    ERROR,
    WARNING,
    HELP,
    SHIFT_CLICK;

    int getColour() {
        switch (this) {
            case ERROR:
                return MinecraftTooltip.getOutline(MinecraftTooltip.Type.ERROR);

            case HELP:
                return MinecraftTooltip.getOutline(Tooltip.Type.HELP);

            case SHIFT_CLICK:
                return 0xffff00;

            case WARNING:
                return MinecraftTooltip.getOutline(MinecraftTooltip.Type.WARNING);
        }
        return 0;
    }
}
