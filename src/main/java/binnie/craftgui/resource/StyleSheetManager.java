package binnie.craftgui.resource;

import binnie.craftgui.core.CraftGUI;

public class StyleSheetManager {
	static IStyleSheet defaultSS = new DefaultStyleSheet();

	public static Texture getTexture(final Object key) {
		return StyleSheetManager.defaultSS.getTexture(key);
	}

	public static IStyleSheet getDefault() {
		return StyleSheetManager.defaultSS;
	}

	private static class DefaultStyleSheet implements IStyleSheet {
		@Override
		public Texture getTexture(final Object key) {
			return CraftGUI.ResourceManager.getTexture(key.toString());
		}
	}
}
