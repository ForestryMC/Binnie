package binnie.core.language;

import binnie.core.AbstractMod;
import binnie.core.ManagerBase;
import net.minecraft.util.StatCollector;

import java.util.HashMap;
import java.util.Map;

public class ManagerLanguage extends ManagerBase {
	private Map<Object, String> objNames;

	public ManagerLanguage() {
		objNames = new HashMap<>();
	}

	public void addObjectName(Object obj, String name) {
		objNames.put(obj, name);
	}

	public String unlocalised(AbstractMod mod, String id) {
		return mod.getModID() + "." + id;
	}

	public String localise(Object key) {
		String loc = StatCollector.translateToLocal(key.toString());
		if (loc.equals(key.toString())) {
			if (objNames.containsKey(key)) {
				return localise(objNames.get(key));
			}
			return key.toString();
		}
		return loc;
	}

	public String localise(AbstractMod mod, String id) {
		return localise(unlocalised(mod, id));
	}

	public String localiseOrBlank(AbstractMod mod, String id) {
		return localiseOrBlank(unlocalised(mod, id));
	}

	public String localise(AbstractMod mod, String id, Object... objs) {
		return String.format(localise(mod, id), objs);
	}

	public String localiseOrBlank(Object key) {
		String trans = localise(key);
		return trans.equals(key) ? "" : trans;
	}

	public boolean canLocalise(Object key) {
		String trans = localise(key);
		return !trans.equals(key);
	}
}
