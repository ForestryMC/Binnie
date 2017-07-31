package binnie.core.gui.minecraft;

import net.minecraft.util.text.TextFormatting;

import binnie.core.gui.Tooltip;

public class MinecraftTooltip extends Tooltip {
	public static int getOutline(final ITooltipType type) {
		return TypeColour.valueOf(type.toString()).getOutline();
	}

	public static String getTitle(final ITooltipType type) {
		return TypeColour.valueOf(type.toString()).getTitle();
	}

	public static String getBody(final ITooltipType type) {
		return TypeColour.valueOf(type.toString()).getBody();
	}

	public enum Type implements ITooltipType {
		Error,
		Warning
	}

	private enum TypeColour {
		Standard(5243135, TextFormatting.WHITE, TextFormatting.GRAY),
		Help(5046016, TextFormatting.GREEN, TextFormatting.DARK_GREEN),
		Information(49151, TextFormatting.AQUA, TextFormatting.DARK_AQUA),
		Error(16724224, TextFormatting.RED, TextFormatting.DARK_RED),
		Warning(16752384, TextFormatting.YELLOW, TextFormatting.GOLD),
		User(9839667, TextFormatting.RED, TextFormatting.DARK_RED),
		Power(9006592, TextFormatting.YELLOW, TextFormatting.GOLD);

		int outline;
		String mainText;
		String bodyText;

		TypeColour(final int outline, final TextFormatting mainText, final TextFormatting bodyText) {
			this.outline = outline;
			this.mainText = mainText.toString();
			this.bodyText = bodyText.toString();
		}

		public int getOutline() {
			return this.outline;
		}

		public String getTitle() {
			return this.mainText;
		}

		public String getBody() {
			return this.bodyText;
		}
	}
}
