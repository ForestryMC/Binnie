package binnie.core.language;

import binnie.core.AbstractMod;
import binnie.core.ManagerBase;
import net.minecraft.util.StatCollector;

import java.util.HashMap;
import java.util.Map;

public class ManagerLanguage extends ManagerBase {
	private Map<Object, String> objNames;

	public ManagerLanguage() {
		objNames = new HashMap<Object, String>();
	}

	public void addObjectName(final Object obj, final String name) {
		objNames.put(obj, name);
	}

	public String unlocalised(final AbstractMod mod, final String id) {
		return mod.getModID() + "." + id;
	}

	public String localise(final Object key) {
		final String loc = StatCollector.translateToLocal(key.toString());
		if (loc.equals(key.toString())) {
			if (objNames.containsKey(key)) {
				return localise(objNames.get(key));
			}
			return key.toString();
		}
		return loc;
	}

	public String localise(final AbstractMod mod, final String id) {
		return localise(unlocalised(mod, id));
	}

	public String localiseOrBlank(final AbstractMod mod, final String id) {
		return localiseOrBlank(unlocalised(mod, id));
	}

	public String localise(final AbstractMod mod, final String id, final Object... objs) {
		return String.format(localise(mod, id), objs);
	}

	public String localiseOrBlank(final Object key) {
		final String trans = localise(key);
		return trans.equals(key) ? "" : trans;
	}

	public boolean canLocalise(final Object key) {
		final String trans = localise(key);
		return !trans.equals(key);
	}
}
