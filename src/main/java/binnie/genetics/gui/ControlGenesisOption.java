package binnie.genetics.gui;

import binnie.core.genetics.Gene;
import binnie.craftgui.controls.listbox.ControlList;
import binnie.craftgui.controls.listbox.ControlOption;
import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.core.geometry.TextJustification;

public class ControlGenesisOption extends ControlOption<Gene> {
    public ControlGenesisOption(final ControlList<Gene> parent, final Gene gene, final int y) {
        super(parent, gene, y);
    }

    String getAlleleName() {
        return this.getValue().getAlleleName();
    }

    String getChromosomeName() {
        return this.getValue().getShortChromosomeName();
    }

    @Override
    public void onRenderBackground() {
        super.onRenderBackground();
        CraftGUI.Render.text(new IArea(0.0f, 0.0f, 70.0f, 22.0f), TextJustification.MiddleCenter, this.getChromosomeName(), this.getColour());
        CraftGUI.Render.text(new IArea(75.0f, 0.0f, 80.0f, 22.0f), TextJustification.MiddleCenter, this.getAlleleName(), this.getColour());
        CraftGUI.Render.solid(new IArea(70.0f, 2.0f, 1.0f, 16.0f), -16777216 + this.getColour());

    }
}
