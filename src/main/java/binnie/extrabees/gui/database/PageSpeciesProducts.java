package binnie.extrabees.gui.database;

import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.ControlText;
import binnie.core.craftgui.controls.ControlTextCentered;
import binnie.core.craftgui.database.DatabaseTab;
import binnie.core.craftgui.database.PageSpecies;
import forestry.api.apiculture.IAlleleBeeSpecies;
import forestry.api.genetics.IAlleleSpecies;

public class PageSpeciesProducts extends PageSpecies {
	protected ControlText title;
	protected ControlProductsBox products;
	protected ControlText title2;
	protected ControlProductsBox specialties;

	public PageSpeciesProducts(IWidget parent, DatabaseTab tab) {
		super(parent, tab);
		title = new ControlTextCentered(this, 8.0f, "Products");
		products = new ControlProductsBox(this, 4, 20, 136, 48, ControlProductsBox.Type.Products);
		title2 = new ControlTextCentered(this, 68.0f, "Specialties");
		specialties = new ControlProductsBox(this, 4, 80, 136, 48, ControlProductsBox.Type.Specialties);
	}

	@Override
	public void onValueChanged(IAlleleSpecies species) {
		products.setSpecies((IAlleleBeeSpecies) species);
		specialties.setSpecies((IAlleleBeeSpecies) species);
	}
}
