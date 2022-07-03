package binnie.botany.craftgui;

import binnie.botany.api.IColourMix;
import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.ITooltip;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.Tooltip;
import binnie.core.craftgui.WidgetAttribute;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.geometry.IPoint;
import binnie.core.craftgui.resource.Texture;
import binnie.core.craftgui.resource.minecraft.CraftGUITextureSheet;
import binnie.core.craftgui.resource.minecraft.StandardTexture;
import binnie.core.util.I18N;

public class ControlColourMixSymbol extends Control implements ITooltip {
    protected static Texture MutationPlus = new StandardTexture(2, 94, 16, 16, CraftGUITextureSheet.Controls2);
    protected static Texture MutationArrow = new StandardTexture(20, 94, 32, 16, CraftGUITextureSheet.Controls2);

    protected IColourMix value;
    protected int type;

    protected ControlColourMixSymbol(IWidget parent, int x, int y, int type) {
        super(parent, x, y, 16 + type * 16, 16.0f);
        this.type = type;
        value = null;
        addAttribute(WidgetAttribute.MOUSE_OVER);
    }

    @Override
    public void onRenderBackground() {
        super.onRenderBackground();
        if (type == 0) {
            CraftGUI.render.texture(ControlColourMixSymbol.MutationPlus, IPoint.ZERO);
        } else {
            CraftGUI.render.texture(ControlColourMixSymbol.MutationArrow, IPoint.ZERO);
        }
    }

    public void setValue(IColourMix value) {
        this.value = value;
        setColor(0xffffff);
    }

    @Override
    public void getTooltip(Tooltip tooltip) {
        if (type == 1) {
            float chance = value.getChance();
            tooltip.add(I18N.localise("botany.gui.controls.colorMixSymbol.chance", chance));
        }
    }
}
