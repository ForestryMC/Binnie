package binnie.extrabees.gui;

import forestry.api.apiculture.IAlleleBeeSpecies;
import forestry.api.genetics.IAlleleSpecies;

import binnie.core.api.gui.IWidget;
import binnie.core.gui.controls.ControlText;
import binnie.core.gui.controls.ControlTextCentered;
import binnie.core.gui.database.DatabaseConstants;
import binnie.core.gui.database.DatabaseTab;
import binnie.core.gui.database.PageSpecies;
import binnie.core.util.I18N;

public class PageSpeciesProducts extends PageSpecies {
	private final ControlText title;
	private final ControlProductsBox productsBox;
	private final ControlText title2;
	private final ControlProductsBox specialitiesBox;

	public PageSpeciesProducts(IWidget parent, DatabaseTab tab) {
		super(parent, tab);
		title = new ControlTextCentered(this, 8, I18N.localise(DatabaseConstants.BEE_PRODUCTS_KEY + ".products"));
		productsBox = new ControlProductsBox(this, 4, 20, 136, 48, ControlProductsBox.Type.PRODUCTS);
		title2 = new ControlTextCentered(this, 68, I18N.localise(DatabaseConstants.BEE_PRODUCTS_KEY + ".specialties"));
		specialitiesBox = new ControlProductsBox(this, 4, 80, 136, 48, ControlProductsBox.Type.SPECIALTIES);
	}

	@Override
	public void onValueChanged(IAlleleSpecies species) {
		productsBox.setSpecies((IAlleleBeeSpecies) species);
		specialitiesBox.setSpecies((IAlleleBeeSpecies) species);
	}
}
