package binnie.core.proxy;

import net.minecraft.util.ResourceLocation;

import binnie.core.ModId;


public interface I18NProxy {
	String localiseOrBlank(String key);

	String localise(String key);

	String localise(ModId modId, String path, Object... format);

	String localise(ResourceLocation key);

	boolean canLocalise(String key);

	String localise(String key, Object... format);

	String localise(ResourceLocation key, Object... format);
}
