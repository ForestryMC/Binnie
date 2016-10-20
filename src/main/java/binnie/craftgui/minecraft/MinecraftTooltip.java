// 
// Decompiled by Procyon v0.5.30
// 

package binnie.craftgui.minecraft;

import net.minecraft.util.EnumChatFormatting;
import binnie.craftgui.core.Tooltip;

public class MinecraftTooltip extends Tooltip
{
	public static int getOutline(final ITooltipType type) {
		return TypeColour.valueOf(type.toString()).getOutline();
	}

	public static String getTitle(final ITooltipType type) {
		return TypeColour.valueOf(type.toString()).getTitle();
	}

	public static String getBody(final ITooltipType type) {
		return TypeColour.valueOf(type.toString()).getBody();
	}

	public enum Type implements ITooltipType
	{
		Error,
		Warning;
	}

	private enum TypeColour
	{
		Standard(5243135, EnumChatFormatting.WHITE, EnumChatFormatting.GRAY),
		Help(5046016, EnumChatFormatting.GREEN, EnumChatFormatting.DARK_GREEN),
		Information(49151, EnumChatFormatting.AQUA, EnumChatFormatting.DARK_AQUA),
		Error(16724224, EnumChatFormatting.RED, EnumChatFormatting.DARK_RED),
		Warning(16752384, EnumChatFormatting.YELLOW, EnumChatFormatting.GOLD),
		User(9839667, EnumChatFormatting.RED, EnumChatFormatting.DARK_RED),
		Power(9006592, EnumChatFormatting.YELLOW, EnumChatFormatting.GOLD);

		int outline;
		String mainText;
		String bodyText;

		private TypeColour(final int outline, final EnumChatFormatting mainText, final EnumChatFormatting bodyText) {
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
