// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.craftgui.resource;

import java.util.HashMap;
import java.util.Map;

public class StyleSheet implements IStyleSheet
{
	protected Map<Object, Texture> textures;

	public StyleSheet() {
		textures = new HashMap<Object, Texture>();
	}

	@Override
	public Texture getTexture(Object key) {
		if (!textures.containsKey(key)) {
			return StyleSheetManager.getTexture(key);
		}
		return textures.get(key);
	}
}
