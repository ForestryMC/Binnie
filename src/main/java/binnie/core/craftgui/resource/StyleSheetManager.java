package binnie.core.craftgui.resource;

import binnie.core.craftgui.CraftGUI;

public class StyleSheetManager {
    static IStyleSheet defaultSS = new DefaultStyleSheet();

    public static Texture getTexture(Object key) {
        return StyleSheetManager.defaultSS.getTexture(key);
    }

    public static IStyleSheet getDefault() {
        return StyleSheetManager.defaultSS;
    }

    private static class DefaultStyleSheet implements IStyleSheet {
        @Override
        public Texture getTexture(Object key) {
            return CraftGUI.resourceManager.getTexture(key.toString());
        }
    }
}
