package binnie.extratrees.machines.craftgui;

import forestry.api.genetics.IAlleleSpecies;

import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.ControlTextCentered;
import binnie.core.craftgui.minecraft.MinecraftGUI;
import binnie.core.craftgui.window.Panel;
import binnie.genetics.gui.database.ControlDatabaseIndividualDisplay;
import binnie.genetics.gui.database.DatabaseTab;
import binnie.genetics.gui.database.EnumDiscoveryState;
import binnie.genetics.gui.database.PageSpecies;

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
