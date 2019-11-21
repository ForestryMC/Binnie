package binnie.core.proxy;

import binnie.core.ModId;
import binnie.core.util.Log;
import net.minecraft.client.resources.I18n;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class I18NClient implements I18NProxy {

	private final boolean devEnvironment = Launch.blackboard.get("fml.deobfuscatedEnvironment") == Boolean.TRUE;

	public String localiseOrBlank(String key) {
		String trans = localise(key);
		return trans.equals(key) ? "" : trans;
	}

	public String localise(String key) {
		if (I18n.hasKey(key)) {
			return I18n.format(key);
		}
		if (devEnvironment) {
			Log.warning("Key not localized: " + key);
		}
		return key;
	}

	public String localise(ModId modId, String path, Object... format) {
		return localise(modId.getDomain() + '.' + path, format);
	}

	public String localise(ResourceLocation key) {
		return this.localise(key.getNamespace() + '.' + key.getPath());
	}

	public boolean canLocalise(String key) {
		return I18n.hasKey(key);
	}

	public String localise(String key, Object... format) {
		if (I18n.hasKey(key)) {
			return I18n.format(key, format);
		}
		if (devEnvironment) {
			Log.warning("Key(format) not localized: " + key);
		}
		return key;
	}

	public String localise(ResourceLocation key, Object... format) {
		return this.localise(key.getNamespace() + '.' + key.getPath(), format);
	}
}
