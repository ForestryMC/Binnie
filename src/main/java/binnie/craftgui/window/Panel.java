package binnie.craftgui.window;

import binnie.craftgui.controls.core.Control;
import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.minecraft.MinecraftGUI;
import binnie.craftgui.resource.minecraft.CraftGUITexture;

public class Panel extends Control {
    IPanelType type;

    public Panel(final IWidget parent, final float x, final float y, final float width, final float height, final IPanelType type) {
        super(parent, x, y, width, height);
        this.type = type;
    }

    public Panel(final IWidget parent, final IArea area, final IPanelType type) {
        this(parent, area.x(), area.y(), area.w(), area.h(), type);
    }

    public IPanelType getType() {
        return this.type;
    }

    @Override
    public void onRenderBackground() {
        final IPanelType panelType = this.getType();
        if (panelType instanceof MinecraftGUI.PanelType) {
            switch ((MinecraftGUI.PanelType) panelType) {
                case Black: {
                    CraftGUI.Render.texture(CraftGUITexture.PanelBlack, this.getArea());
                    break;
                }
                case Gray: {
                    CraftGUI.Render.texture(CraftGUITexture.PanelGray, this.getArea());
                    break;
                }
                case Tinted: {
                    CraftGUI.Render.texture(CraftGUITexture.PanelTinted, this.getArea());
                    break;
                }
                case Outline: {
                    CraftGUI.Render.texture(CraftGUITexture.Outline, this.getArea());
                    break;
                }
                case TabOutline: {
                    CraftGUI.Render.texture(CraftGUITexture.TabOutline, this.getArea());
                    break;
                }
            }
        }
    }

    public interface IPanelType {
    }
}
