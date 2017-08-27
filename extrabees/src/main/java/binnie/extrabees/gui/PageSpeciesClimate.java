package binnie.extrabees.gui;

import forestry.api.apiculture.IAlleleBeeSpecies;
import forestry.api.genetics.IAlleleSpecies;

import binnie.core.api.gui.IWidget;
import binnie.core.gui.controls.ControlTextCentered;
import binnie.core.gui.database.DatabaseTab;
import binnie.core.gui.database.PageSpecies;

public class PageSpeciesClimate extends PageSpecies {
	private final ControlClimateBar tempBar;
	private final ControlClimateBar humidBar;
	private final ControlBiomes biomes;

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
