package binnie.extratrees.gui.database;

import binnie.core.api.gui.IWidget;
import binnie.core.gui.controls.ControlTextCentered;
import binnie.core.gui.database.ControlIndividualDisplay;
import binnie.core.gui.database.DatabaseTab;
import binnie.core.gui.database.EnumDiscoveryState;
import binnie.core.gui.database.PageSpecies;
import binnie.core.gui.minecraft.MinecraftGUI;
import binnie.core.gui.window.Panel;
import forestry.api.genetics.IAlleleSpecies;

public class PageSpeciesImage extends PageSpecies {
	private final ControlIndividualDisplay display;

	public PageSpeciesImage(final IWidget parent, final DatabaseTab tab) {
		super(parent, tab);
		new Panel(this, 7, 25, 130, 120, MinecraftGUI.PanelType.GRAY);
		this.display = new ControlIndividualDisplay(this, 12, 25, 120);
		this.display.setHasTooltip(false);
		new ControlTextCentered(this, 8, this.getValue().toString());
	}

	@Override
	public void onValueChanged(final IAlleleSpecies species) {
		this.display.setSpecies(species, EnumDiscoveryState.SHOW);
	}
}
