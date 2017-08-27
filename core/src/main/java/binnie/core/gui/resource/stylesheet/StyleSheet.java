package binnie.core.gui.resource.stylesheet;

import java.util.HashMap;
import java.util.Map;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.gui.resource.textures.Texture;

public class StyleSheet implements IStyleSheet {
	protected final Map<Object, Texture> textures = new HashMap<>();

	public StyleSheet(Map<String, Texture> textures) {
		this.textures.putAll(textures);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasTexture(Object key) {
		return this.textures.containsKey(key.toString());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Texture getTexture(Object key) {
		return this.textures.get(key.toString());
	}
}
