package binnie.craftgui.resource.minecraft;

import binnie.core.BinnieCore;
import binnie.core.resource.BinnieResource;
import binnie.core.resource.IBinnieTexture;
import binnie.craftgui.core.CraftGUI;

public enum CraftGUITextureSheet implements IBinnieTexture {
    Controls2("controls"),
    Panel2("panels"),
    Slots("slots");

    String name;

    CraftGUITextureSheet(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public BinnieResource getTexture() {
        if (BinnieCore.proxy.isServer()) {
            return null;
        }
        return CraftGUI.ResourceManager.getTextureSheet(this.name).getTexture();
    }
}
