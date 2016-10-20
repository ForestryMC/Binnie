package binnie.craftgui.resource;

import binnie.craftgui.core.CraftGUI;

public class StyleSheetManager {
    static IStyleSheet defaultSS;

    public static Texture getTexture(final Object key) {
        return StyleSheetManager.defaultSS.getTexture(key);
    }

    public static IStyleSheet getDefault() {
        return StyleSheetManager.defaultSS;
    }

    static {
        StyleSheetManager.defaultSS = new DefaultStyleSheet();
    }

    private static class DefaultStyleSheet implements IStyleSheet {
        @Override
        public Texture getTexture(final Object key) {
            return CraftGUI.ResourceManager.getTexture(key.toString());
        }
    }
}
