package binnie.core.craftgui.database;

import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.ControlTextCentered;
import binnie.core.util.I18N;
import forestry.api.genetics.IAlleleSpecies;

public class PageSpeciesResultant extends PageSpecies {
    private ControlMutationBox list;

    public PageSpeciesResultant(IWidget parent, DatabaseTab tab) {
        super(parent, tab);
        new ControlTextCentered(this, 8.0f, I18N.localise("binniecore.gui.database.resultantMutations"));
        list = new ControlMutationBox(this, 4, 20, 136, 152, ControlMutationBox.Type.RESULTANT);
    }

    @Override
    public void onValueChanged(IAlleleSpecies species) {
        list.setSpecies(species);
    }
}
