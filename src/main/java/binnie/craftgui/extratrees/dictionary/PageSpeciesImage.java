package binnie.craftgui.extratrees.dictionary;

import binnie.craftgui.controls.ControlTextCentered;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.minecraft.MinecraftGUI;
import binnie.craftgui.mod.database.ControlDatabaseIndividualDisplay;
import binnie.craftgui.mod.database.DatabaseTab;
import binnie.craftgui.mod.database.EnumDiscoveryState;
import binnie.craftgui.mod.database.PageSpecies;
import binnie.craftgui.window.Panel;
import forestry.api.genetics.IAlleleSpecies;

public class PageSpeciesImage extends PageSpecies {
    ControlDatabaseIndividualDisplay display;

    public PageSpeciesImage(final IWidget parent, final DatabaseTab tab) {
        super(parent, tab);
        new Panel(this, 7.0f, 25.0f, 130.0f, 120.0f, MinecraftGUI.PanelType.Gray);
        this.display = new ControlDatabaseIndividualDisplay(this, 12.0f, 25.0f, 120.0f);
        this.display.hastooltip = false;
        new ControlTextCentered(this, 8.0f, this.getValue().toString());
    }

    @Override
    public void onValueChanged(final IAlleleSpecies species) {
        this.display.setSpecies(species, EnumDiscoveryState.Show);
    }
}
