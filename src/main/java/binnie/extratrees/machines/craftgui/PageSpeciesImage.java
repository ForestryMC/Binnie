package binnie.extratrees.machines.craftgui;

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
	ControlDatabaseIndividualDisplay display;

	public PageSpeciesImage(final IWidget parent, final DatabaseTab tab) {
		super(parent, tab);
		new Panel(this, 7, 25, 130, 120, MinecraftGUI.PanelType.Gray);
		this.display = new ControlDatabaseIndividualDisplay(this, 12, 25, 120);
		this.display.hastooltip = false;
		new ControlTextCentered(this, 8, this.getValue().toString());
	}

	@Override
	public void onValueChanged(final IAlleleSpecies species) {
		this.display.setSpecies(species, EnumDiscoveryState.Show);
	}
}
