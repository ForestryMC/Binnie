package binnie.extrabees.gui.database;

import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.ControlTextCentered;
import binnie.core.craftgui.database.DatabaseTab;
import binnie.core.craftgui.database.PageSpecies;
import binnie.core.util.I18N;
import forestry.api.apiculture.IAlleleBeeSpecies;
import forestry.api.genetics.IAlleleSpecies;

public class PageSpeciesClimate extends PageSpecies {
    protected ControlClimateBar tempBar;
    protected ControlClimateBar humidBar;

    public PageSpeciesClimate(IWidget parent, DatabaseTab tab) {
        super(parent, tab);
        new ControlTextCentered(this, 8.0f, I18N.localise("extrabees.gui.database.tab.species.climate"));
        tempBar = new ControlClimateBar(this, 8, 24, 128, 12);
        humidBar = new ControlClimateBar(this, 8, 42, 128, 12, true);
    }

    @Override
    public void onValueChanged(IAlleleSpecies species) {
        tempBar.setSpecies((IAlleleBeeSpecies) species);
        humidBar.setSpecies((IAlleleBeeSpecies) species);
    }
}
