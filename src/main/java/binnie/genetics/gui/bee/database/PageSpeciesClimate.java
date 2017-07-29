package binnie.genetics.gui.bee.database;

import forestry.api.apiculture.IAlleleBeeSpecies;
import forestry.api.genetics.IAlleleSpecies;

import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.ControlTextCentered;
import binnie.core.craftgui.database.DatabaseTab;
import binnie.core.craftgui.database.PageSpecies;

public class PageSpeciesClimate extends PageSpecies {
	ControlClimateBar tempBar;
	ControlClimateBar humidBar;
	ControlBiomes biomes;

	public PageSpeciesClimate(IWidget parent, DatabaseTab tab) {
		super(parent, tab);
		new ControlTextCentered(this, 8, "Climate");
		tempBar = new ControlClimateBar(this, 8, 24, 128, 12);
		humidBar = new ControlClimateBar(this, 8, 42, 128, 12, true);
		new ControlTextCentered(this, 70, "Biomes");
		biomes = new ControlBiomes(this, 8, 90, 8, 4);
	}

	@Override
	public void onValueChanged(IAlleleSpecies species) {
		tempBar.setSpecies((IAlleleBeeSpecies) species);
		humidBar.setSpecies((IAlleleBeeSpecies) species);
		biomes.setSpecies((IAlleleBeeSpecies) species);
	}
}
