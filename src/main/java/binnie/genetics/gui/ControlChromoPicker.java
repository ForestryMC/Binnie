package binnie.genetics.gui;

import binnie.Binnie;
import binnie.craftgui.controls.core.Control;
import binnie.craftgui.core.Attribute;
import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.core.ITooltip;
import binnie.craftgui.core.Tooltip;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.events.EventValueChanged;
import binnie.craftgui.events.EventWidget;
import binnie.craftgui.resource.Texture;
import binnie.craftgui.resource.minecraft.StandardTexture;
import binnie.extrabees.core.ExtraBeeTexture;
import forestry.api.genetics.IChromosomeType;

public class ControlChromoPicker extends Control implements ITooltip {
    Texture Selected;
    Texture Texture;
    IChromosomeType type;
    ControlChromosome parent;

    public ControlChromoPicker(final ControlChromosome parent, final float x, final float y, final IChromosomeType chromo) {
        super(parent, x, y, 16.0f, 16.0f);
        this.Selected = new StandardTexture(160, 18, 16, 16, ExtraBeeTexture.GUIPunnett);
        this.Texture = new StandardTexture(160, 34, 16, 16, ExtraBeeTexture.GUIPunnett);
        this.type = chromo;
        this.addAttribute(Attribute.MouseOver);
        this.parent = parent;
        this.addSelfEventHandler(new EventWidget.StartMouseOver.Handler() {
            @Override
            public void onEvent(final EventWidget.StartMouseOver event) {
                ControlChromoPicker.this.callEvent(new EventValueChanged<Object>(ControlChromoPicker.this.getWidget(), ControlChromoPicker.this.type));
            }
        });
        this.addSelfEventHandler(new EventWidget.EndMouseOver.Handler() {
            @Override
            public void onEvent(final EventWidget.EndMouseOver event) {
                ControlChromoPicker.this.callEvent(new EventValueChanged<Object>(ControlChromoPicker.this.getWidget(), null));
            }
        });
    }

    @Override
    public void onRenderBackground() {
        super.onRenderBackground();
        final boolean selected = this.isMouseOver();
        final Texture text = selected ? this.Selected : this.Texture;
        CraftGUI.Render.texture(text, IPoint.ZERO);
    }

    @Override
    public void getTooltip(final Tooltip tooltip) {
        tooltip.add(Binnie.Genetics.getSystem(this.parent.getRoot()).getChromosomeName(this.type));
    }
}
