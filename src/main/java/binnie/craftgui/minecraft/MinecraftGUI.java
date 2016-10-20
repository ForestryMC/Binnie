package binnie.craftgui.minecraft;

import binnie.craftgui.window.Panel;

public class MinecraftGUI {
    public enum PanelType implements Panel.IPanelType {
        Black,
        Gray,
        Tinted,
        Coloured,
        Outline,
        TabOutline;
    }
}
