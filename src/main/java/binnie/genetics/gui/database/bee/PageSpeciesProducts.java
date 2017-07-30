package binnie.genetics.gui.database.bee;

import forestry.api.apiculture.IAlleleBeeSpecies;
import forestry.api.genetics.IAlleleSpecies;

import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.ControlText;
import binnie.core.craftgui.controls.ControlTextCentered;
import binnie.genetics.gui.database.DatabaseTab;
import binnie.genetics.gui.database.PageSpecies;

public class PageSpeciesProducts extends PageSpecies {
	ControlText title;
	ControlProductsBox productsBox;
	ControlText title2;
	ControlProductsBox specialitiesBox;

	public PageSpeciesProducts(IWidget parent, DatabaseTab tab) {
		super(parent, tab);
		title = new ControlTextCentered(this, 8, "Products");
		productsBox = new ControlProductsBox(this, 4, 20, 136, 48, ControlProductsBox.Type.PRODUCTS);
		title2 = new ControlTextCentered(this, 68, "Specialties");
		specialitiesBox = new ControlProductsBox(this, 4, 80, 136, 48, ControlProductsBox.Type.SPECIALTIES);
	}

	@Override
	public void onValueChanged(IAlleleSpecies species) {
		productsBox.setSpecies((IAlleleBeeSpecies) species);
		specialitiesBox.setSpecies((IAlleleBeeSpecies) species);
	}
}
