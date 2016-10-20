package binnie.craftgui.minecraft.control;

import binnie.craftgui.core.Tooltip;
import binnie.craftgui.minecraft.MinecraftTooltip;

public enum EnumHighlighting {
    Error,
    Warning,
    Help,
    ShiftClick;

    int getColour() {
        switch (this) {
            case Error: {
                return MinecraftTooltip.getOutline(MinecraftTooltip.Type.Error);
            }
            case Help: {
                return MinecraftTooltip.getOutline(Tooltip.Type.Help);
            }
            case ShiftClick: {
                return 16776960;
            }
            case Warning: {
                return MinecraftTooltip.getOutline(MinecraftTooltip.Type.Warning);
            }
            default: {
                return 0;
            }
        }
    }
}
