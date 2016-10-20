package binnie.extrabees.gui.punnett;

import binnie.craftgui.controls.core.Control;
import binnie.craftgui.controls.core.IControlValue;
import binnie.craftgui.core.*;
import forestry.api.genetics.IChromosomeType;

public class ControlChromosome extends Control implements IControlValue<IChromosomeType>, ITooltip {
    IChromosomeType value;

    protected ControlChromosome(final IWidget parent, final float x, final float y, final IChromosomeType type) {
        super(parent, x, y, 16.0f, 16.0f);
        this.setValue(type);
        this.addAttribute(Attribute.MouseOver);
    }

    @Override
    public IChromosomeType getValue() {
        return this.value;
    }

    @Override
    public void setValue(final IChromosomeType value) {
        this.value = value;
    }

    @Override
    public void onRenderBackground() {
        CraftGUI.Render.texture(ExtraBeeGUITexture.Chromosome, this.getArea());
        CraftGUI.Render.colour(16711680);
        CraftGUI.Render.texture(ExtraBeeGUITexture.Chromosome2, this.getArea());
    }

    @Override
    public void getTooltip(final Tooltip tooltip) {
        if (this.value != null) {
            tooltip.add(this.value.getName());
        }
    }
}
