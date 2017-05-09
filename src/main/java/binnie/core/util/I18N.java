package binnie.core.util;

import binnie.core.AbstractMod;
import net.minecraft.util.StatCollector;

public class I18N {
	public static String unlocalised(AbstractMod mod, String id) {
		return mod.getModID() + "." + id;
	}

	public static String localise(String key) {
		return StatCollector.translateToLocal(key.toString());
	}

	public static String localise(AbstractMod mod, String id) {
		return localise(unlocalised(mod, id));
	}

	public static String localiseOrBlank(AbstractMod mod, String id) {
		return localiseOrBlank(unlocalised(mod, id));
	}

	public static String localise(AbstractMod mod, String id, Object... objs) {
		return String.format(localise(mod, id), objs);
	}

	public static String localiseOrBlank(String key) {
		String trans = localise(key);
		return trans.equals(key) ? "" : trans;
	}

	public static boolean canLocalise(String key) {
		return !localise(key).equals(key);
	}
}
