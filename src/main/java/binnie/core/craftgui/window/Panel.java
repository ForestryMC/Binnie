package binnie.core.craftgui.window;

import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.geometry.IArea;
import binnie.core.craftgui.minecraft.MinecraftGUI;
import binnie.core.craftgui.resource.minecraft.CraftGUITexture;

public class Panel extends Control {
    protected IPanelType type;

    public Panel(IWidget parent, float x, float y, float width, float height, IPanelType type) {
        super(parent, x, y, width, height);
        this.type = type;
    }

    public Panel(IWidget parent, IArea area, IPanelType type) {
        this(parent, area.x(), area.y(), area.w(), area.h(), type);
    }

    @Override
    public void onRenderBackground() {
        IPanelType panelType = getType();
        if (panelType instanceof MinecraftGUI.PanelType) {
            switch ((MinecraftGUI.PanelType) panelType) {
                case Black:
                    CraftGUI.render.texture(CraftGUITexture.PanelBlack, getArea());
                    break;

                case Gray:
                    CraftGUI.render.texture(CraftGUITexture.PanelGray, getArea());
                    break;

                case Tinted:
                    CraftGUI.render.texture(CraftGUITexture.PanelTinted, getArea());
                    break;

                case Outline:
                    CraftGUI.render.texture(CraftGUITexture.Outline, getArea());
                    break;

                case TabOutline:
                    CraftGUI.render.texture(CraftGUITexture.TabOutline, getArea());
                    break;
            }
        }
    }

    public IPanelType getType() {
        return type;
    }

    public interface IPanelType {}
}
