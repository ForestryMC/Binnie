package binnie.craftgui.mod.database;

import binnie.core.genetics.BreedingSystem;
import binnie.craftgui.controls.ControlText;
import binnie.craftgui.controls.ControlTextCentered;
import binnie.craftgui.controls.core.Control;
import binnie.craftgui.core.IWidget;

public class PageBreederStats extends Control {
    String player;

    public PageBreederStats(final IWidget parent, final int w, final int h, final String player) {
        super(parent, 0.0f, 0.0f, w, h);
        this.player = player;
        final ControlText pageBranchOverview_branchName = new ControlTextCentered(this, 8.0f, "§nStats§r");
        final BreedingSystem system = ((WindowAbstractDatabase) this.getSuperParent()).getBreedingSystem();
    }
}
