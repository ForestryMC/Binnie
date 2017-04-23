// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extrabees.gui.database;

import forestry.api.apiculture.IAlleleBeeSpecies;
import forestry.api.genetics.IAlleleSpecies;
import binnie.core.craftgui.controls.ControlTextCentered;
import binnie.core.craftgui.database.DatabaseTab;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.ControlText;
import binnie.core.craftgui.database.PageSpecies;

public class PageSpeciesProducts extends PageSpecies
{
	ControlText pageSpeciesProducts_Title;
	ControlProductsBox pageSpeciesProducts_Products;
	ControlText pageSpeciesProducts_Title2;
	ControlProductsBox pageSpeciesProducts_Specialties;

	public PageSpeciesProducts(final IWidget parent, final DatabaseTab tab) {
		super(parent, tab);
		this.pageSpeciesProducts_Title = new ControlTextCentered(this, 8.0f, "Products");
		this.pageSpeciesProducts_Products = new ControlProductsBox(this, 4, 20, 136, 48, ControlProductsBox.Type.Products);
		this.pageSpeciesProducts_Title2 = new ControlTextCentered(this, 68.0f, "Specialties");
		this.pageSpeciesProducts_Specialties = new ControlProductsBox(this, 4, 80, 136, 48, ControlProductsBox.Type.Specialties);
	}

	@Override
	public void onValueChanged(final IAlleleSpecies species) {
		this.pageSpeciesProducts_Products.setSpecies((IAlleleBeeSpecies) species);
		this.pageSpeciesProducts_Specialties.setSpecies((IAlleleBeeSpecies) species);
	}
}
