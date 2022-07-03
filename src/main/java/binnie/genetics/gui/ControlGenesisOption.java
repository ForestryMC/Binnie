package binnie.genetics.gui;

import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.controls.listbox.ControlList;
import binnie.core.craftgui.controls.listbox.ControlOption;
import binnie.core.craftgui.geometry.IArea;
import binnie.core.craftgui.geometry.TextJustification;
import binnie.core.genetics.Gene;

public class ControlGenesisOption extends ControlOption<Gene> {
    public ControlGenesisOption(ControlList<Gene> parent, Gene gene, int y) {
        super(parent, gene, y);
    }

    String getAlleleName() {
        return getValue().getAlleleName();
    }

    String getChromosomeName() {
        return getValue().getShortChromosomeName();
    }

    @Override
    public void onRenderBackground() {
        super.onRenderBackground();
        CraftGUI.render.text(
                new IArea(0.0f, 0.0f, 70.0f, 22.0f), TextJustification.MIDDLE_CENTER, getChromosomeName(), getColor());
        CraftGUI.render.text(
                new IArea(75.0f, 0.0f, 80.0f, 22.0f), TextJustification.MIDDLE_CENTER, getAlleleName(), getColor());
        CraftGUI.render.solid(new IArea(70.0f, 2.0f, 1.0f, 16.0f), 0xff000000 + getColor());
    }
}
