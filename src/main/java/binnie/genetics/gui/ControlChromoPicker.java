package binnie.genetics.gui;

import binnie.Binnie;
import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.ITooltip;
import binnie.core.craftgui.Tooltip;
import binnie.core.craftgui.WidgetAttribute;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.events.EventValueChanged;
import binnie.core.craftgui.events.EventWidget;
import binnie.core.craftgui.geometry.IPoint;
import binnie.core.craftgui.resource.Texture;
import binnie.core.craftgui.resource.minecraft.StandardTexture;
import binnie.extrabees.core.ExtraBeeTexture;
import forestry.api.genetics.IChromosomeType;

public class ControlChromoPicker extends Control implements ITooltip {
    Texture Selected;
    Texture Texture;
    IChromosomeType type;
    ControlChromosome parent;

    public ControlChromoPicker(ControlChromosome parent, float x, float y, IChromosomeType chromo) {
        super(parent, x, y, 16.0f, 16.0f);
        Selected = new StandardTexture(160, 18, 16, 16, ExtraBeeTexture.GUIPunnett);
        Texture = new StandardTexture(160, 34, 16, 16, ExtraBeeTexture.GUIPunnett);
        type = chromo;
        addAttribute(WidgetAttribute.MOUSE_OVER);
        this.parent = parent;
        addSelfEventHandler(new EventWidget.StartMouseOver.Handler() {
            @Override
            public void onEvent(EventWidget.StartMouseOver event) {
                callEvent(new EventValueChanged<Object>(getWidget(), type));
            }
        });
        addSelfEventHandler(new EventWidget.EndMouseOver.Handler() {
            @Override
            public void onEvent(EventWidget.EndMouseOver event) {
                callEvent(new EventValueChanged<>(getWidget(), null));
            }
        });
    }

    @Override
    public void onRenderBackground() {
        super.onRenderBackground();
        boolean selected = isMouseOver();
        Texture text = selected ? Selected : Texture;
        CraftGUI.render.texture(text, IPoint.ZERO);
    }

    @Override
    public void getTooltip(Tooltip tooltip) {
        tooltip.add(Binnie.Genetics.getSystem(parent.getRoot()).getChromosomeName(type));
    }
}
