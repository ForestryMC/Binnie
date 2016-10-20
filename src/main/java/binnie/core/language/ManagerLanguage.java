package binnie.core.language;

import binnie.core.AbstractMod;
import binnie.core.ManagerBase;
import net.minecraft.util.text.translation.I18n;

import java.util.HashMap;
import java.util.Map;

public class ManagerLanguage extends ManagerBase {
    private Map<Object, String> objNames;

    public ManagerLanguage() {
        this.objNames = new HashMap<Object, String>();
    }

    public void addObjectName(final Object obj, final String name) {
        this.objNames.put(obj, name);
    }

    public String unlocalised(final AbstractMod mod, final String id) {
        return mod.getModID() + "." + id;
    }

    public String localise(final Object key) {
        final String loc = I18n.translateToLocal(key.toString());
        if (loc.equals(key.toString())) {
            return this.objNames.containsKey(key) ? this.localise(this.objNames.get(key)) : key.toString();
        }
        return loc;
    }

    public String localise(final AbstractMod mod, final String id) {
        return this.localise(this.unlocalised(mod, id));
    }

    public String localiseOrBlank(final AbstractMod mod, final String id) {
        return this.localiseOrBlank(this.unlocalised(mod, id));
    }

    public String localise(final AbstractMod mod, final String id, final Object... objs) {
        return String.format(this.localise(mod, id), objs);
    }

    public String localiseOrBlank(final Object key) {
        final String trans = this.localise(key);
        return trans.equals(key) ? "" : trans;
    }

    public boolean canLocalise(final Object key) {
        final String trans = this.localise(key);
        return !trans.equals(key);
    }
}
