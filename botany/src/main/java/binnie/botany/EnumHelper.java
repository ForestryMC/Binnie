package binnie.botany;

import binnie.core.ModId;
import binnie.core.util.I18N;
import binnie.botany.api.IBotanyColored;
import net.minecraft.util.text.TextFormatting;

public class EnumHelper {
    public static String getLocalisedName(final IBotanyColored enumClass, boolean withColor) {
        String localisedName = I18N.localise(ModId.BOTANY, enumClass.keyGroup() + "." + enumClass.getName());
        TextFormatting color = enumClass.getColor();
        if (withColor && color != null) {
            localisedName = color + localisedName;
        }
        return localisedName;
    }
}
