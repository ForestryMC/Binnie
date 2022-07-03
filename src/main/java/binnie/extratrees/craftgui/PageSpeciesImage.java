package binnie.extratrees.craftgui;

import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.ControlTextCentered;
import binnie.core.craftgui.database.ControlDatabaseIndividualDisplay;
import binnie.core.craftgui.database.DatabaseTab;
import binnie.core.craftgui.database.EnumDiscoveryState;
import binnie.core.craftgui.database.PageSpecies;
import binnie.core.craftgui.minecraft.MinecraftGUI;
import binnie.core.craftgui.window.Panel;
import forestry.api.genetics.IAlleleSpecies;

public class PageSpeciesImage extends PageSpecies {
    protected ControlDatabaseIndividualDisplay display;

    public PageSpeciesImage(IWidget parent, DatabaseTab tab) {
        super(parent, tab);
        new Panel(this, 7.0f, 25.0f, 130.0f, 120.0f, MinecraftGUI.PanelType.Gray);
        display = new ControlDatabaseIndividualDisplay(this, 12.0f, 25.0f, 120.0f);
        display.hastooltip = false;
        new ControlTextCentered(this, 8.0f, getValue().toString());
    }

    @Override
    public void onValueChanged(IAlleleSpecies species) {
        display.setSpecies(species, EnumDiscoveryState.SHOW);
    }
}
