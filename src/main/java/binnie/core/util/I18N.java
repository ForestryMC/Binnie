package binnie.core.util;

import net.minecraft.util.StatCollector;

public class I18N {
    public static String localise(String key) {
        return StatCollector.translateToLocal(key);
    }

    public static String localise(String key, Object... objs) {
        return String.format(StatCollector.translateToLocal(key), objs);
    }

    public static String localiseOrBlank(String key) {
        String trans = localise(key);
        return trans.equals(key) ? "" : trans;
    }

    public static boolean canLocalise(String key) {
        return !localise(key).equals(key);
    }
}
