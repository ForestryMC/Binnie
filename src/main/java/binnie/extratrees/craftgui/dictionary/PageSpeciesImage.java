// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extratrees.craftgui.dictionary;

import binnie.core.craftgui.database.EnumDiscoveryState;
import forestry.api.genetics.IAlleleSpecies;
import binnie.core.craftgui.controls.ControlTextCentered;
import binnie.core.craftgui.minecraft.MinecraftGUI;
import binnie.core.craftgui.window.Panel;
import binnie.core.craftgui.database.DatabaseTab;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.database.ControlDatabaseIndividualDisplay;
import binnie.core.craftgui.database.PageSpecies;

public class PageSpeciesImage extends PageSpecies
{
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
