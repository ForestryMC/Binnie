package binnie.core.craftgui.minecraft;

import binnie.core.craftgui.Tooltip;
import net.minecraft.util.EnumChatFormatting;

public class MinecraftTooltip extends Tooltip {
    public static int getOutline(ITooltipType type) {
        return TypeColor.valueOf(type.toString()).getOutline();
    }

    public static String getTitle(ITooltipType type) {
        return TypeColor.valueOf(type.toString()).getTitle();
    }

    public static String getBody(ITooltipType type) {
        return TypeColor.valueOf(type.toString()).getBody();
    }

    public enum Type implements ITooltipType {
        ERROR,
        WARNING
    }

    private enum TypeColor {
        STANDARD(0x5000ff, EnumChatFormatting.WHITE, EnumChatFormatting.GRAY),
        HELP(0x4cff00, EnumChatFormatting.GREEN, EnumChatFormatting.DARK_GREEN),
        INFORMATION(0x00bfff, EnumChatFormatting.AQUA, EnumChatFormatting.DARK_AQUA),
        ERROR(0xff3100, EnumChatFormatting.RED, EnumChatFormatting.DARK_RED),
        WARNING(0xff9f00, EnumChatFormatting.YELLOW, EnumChatFormatting.GOLD),
        USER(0x962433, EnumChatFormatting.RED, EnumChatFormatting.DARK_RED),
        POWER(0x896e00, EnumChatFormatting.YELLOW, EnumChatFormatting.GOLD);

        int outline;
        String mainText;
        String bodyText;

        TypeColor(int outline, EnumChatFormatting mainText, EnumChatFormatting bodyText) {
            this.outline = outline;
            this.mainText = mainText.toString();
            this.bodyText = bodyText.toString();
        }

        public int getOutline() {
            return outline;
        }

        public String getTitle() {
            return mainText;
        }

        public String getBody() {
            return bodyText;
        }
    }
}
