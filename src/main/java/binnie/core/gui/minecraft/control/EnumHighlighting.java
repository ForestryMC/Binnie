package binnie.core.gui.minecraft.control;

import binnie.core.gui.Tooltip;
import binnie.core.gui.minecraft.MinecraftTooltip;

public enum EnumHighlighting {
	ERROR,
	WARNING,
	HELP,
	SHIFT_CLICK;

	int getColour() {
		switch (this) {
			case ERROR: {
				return MinecraftTooltip.getOutline(MinecraftTooltip.Type.Error);
			}
			case HELP: {
				return MinecraftTooltip.getOutline(Tooltip.Type.HELP);
			}
			case SHIFT_CLICK: {
				return 16776960;
			}
			case WARNING: {
				return MinecraftTooltip.getOutline(MinecraftTooltip.Type.Warning);
			}
			default: {
				return 0;
			}
		}
	}
}
