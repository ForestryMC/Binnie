package binnie.core.craftgui.resource.minecraft;

import binnie.core.BinnieCore;
import binnie.core.craftgui.CraftGUI;
import binnie.core.resource.BinnieResource;
import binnie.core.resource.IBinnieTexture;

public enum CraftGUITextureSheet implements IBinnieTexture {
    Controls2("controls"),
    Panel2("panels"),
    Slots("slots");

    String name;

    CraftGUITextureSheet(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public BinnieResource getTexture() {
        if (BinnieCore.proxy.isServer()) {
            return null;
        }
        return CraftGUI.resourceManager.getTextureSheet(name).getTexture();
    }
}
