package binnie.core.craftgui.database;

import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.ControlTextCentered;
import binnie.core.craftgui.events.EventValueChanged;
import binnie.core.util.I18N;
import cpw.mods.fml.common.Mod;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IClassification;

public class PageBranchSpecies extends PageBranch {
    private ControlSpeciesBox speciesList;

    @Mod.EventHandler
    public void onHandleEvent(EventValueChanged<IAlleleSpecies> event) {
        // ignored
    }

    public PageBranchSpecies(IWidget parent, DatabaseTab tab) {
        super(parent, tab);
        new ControlTextCentered(this, 8.0f, I18N.localise("binniecore.gui.database.species"));
        addEventHandler(new ValueHandler());
        speciesList = new ControlSpeciesBox(this, 4.0f, 20.0f, 136.0f, 152.0f);
    }

    @Override
    public void onValueChanged(IClassification branch) {
        speciesList.setBranch(branch);
    }

    private class ValueHandler extends EventValueChanged.Handler {
        @Override
        public void onEvent(EventValueChanged event) {
            if (event.isOrigin(speciesList)) {
                ((WindowAbstractDatabase) getSuperParent()).gotoSpecies((IAlleleSpecies) event.getValue());
            }
        }
    }
}
