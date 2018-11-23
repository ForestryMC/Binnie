package binnie.core.gui.minecraft;

import net.minecraft.util.text.TextFormatting;

import binnie.core.gui.Tooltip;

public class MinecraftTooltip extends Tooltip {
	public static int getOutline(ITooltipType type) {
		return TypeColour.valueOf(type.toString()).getOutline();
	}

	public static String getTitle(ITooltipType type) {
		return TypeColour.valueOf(type.toString()).getTitle();
	}

	public static String getBody(ITooltipType type) {
		return TypeColour.valueOf(type.toString()).getBody();
	}

	public enum Type implements ITooltipType {
		ERROR,
		WARNING
	}

	private enum TypeColour {
		STANDARD(5243135, TextFormatting.WHITE, TextFormatting.GRAY),
		HELP(5046016, TextFormatting.GREEN, TextFormatting.DARK_GREEN),
		INFORMATION(49151, TextFormatting.AQUA, TextFormatting.DARK_AQUA),
		ERROR(16724224, TextFormatting.RED, TextFormatting.DARK_RED),
		WARNING(16752384, TextFormatting.YELLOW, TextFormatting.GOLD),
		USER(9839667, TextFormatting.RED, TextFormatting.DARK_RED),
		POWER(9006592, TextFormatting.YELLOW, TextFormatting.GOLD);

		private final int outline;
		private final String mainText;
		private final String bodyText;

		TypeColour(int outline, TextFormatting mainText, TextFormatting bodyText) {
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
