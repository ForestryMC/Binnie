// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.craftgui.minecraft.control;

import binnie.core.craftgui.Tooltip;
import binnie.core.craftgui.minecraft.MinecraftTooltip;

public enum EnumHighlighting
{
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
