package binnie.core.gui.minecraft.control;

import binnie.core.gui.Tooltip;
import binnie.core.gui.minecraft.MinecraftTooltip;

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
				return MinecraftTooltip.getOutline(Tooltip.Type.HELP);
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
