package binnie.core.craftgui.database;

import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.ControlTextCentered;
import binnie.core.util.I18N;
import forestry.api.genetics.IAlleleSpecies;

public class PageSpeciesMutations extends PageSpecies {
    private ControlMutationBox list;

    public PageSpeciesMutations(IWidget parent, DatabaseTab tab) {
        super(parent, tab);
        new ControlTextCentered(this, 8.0f, I18N.localise("binniecore.gui.database.furtherMutations"));
        list = new ControlMutationBox(this, 4, 20, 136, 152, ControlMutationBox.Type.FURTHER);
    }

    @Override
    public void onValueChanged(IAlleleSpecies species) {
        list.setSpecies(species);
    }
}
