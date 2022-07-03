package binnie.extrabees.gui.database.product;

import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.ControlTextCentered;
import binnie.core.craftgui.database.DatabaseTab;
import binnie.core.craftgui.database.PageSpecies;
import binnie.core.util.I18N;
import forestry.api.apiculture.IAlleleBeeSpecies;
import forestry.api.genetics.IAlleleSpecies;

public class PageSpeciesProducts extends PageSpecies {
    protected ControlProductsBox products;
    protected ControlProductsBox specialties;

    public PageSpeciesProducts(IWidget parent, DatabaseTab tab) {
        super(parent, tab);
        new ControlTextCentered(this, 8.0f, I18N.localise("extrabees.gui.database.tab.species.products"));
        products = new ControlProductsBox(this, 4, 20, 136, 48, ProductType.PRODUCTS);

        new ControlTextCentered(this, 68.0f, I18N.localise("extrabees.gui.database.tab.species.specialties"));
        specialties = new ControlProductsBox(this, 4, 80, 136, 48, ProductType.SPECIALTIES);
    }

    @Override
    public void onValueChanged(IAlleleSpecies species) {
        products.setSpecies((IAlleleBeeSpecies) species);
        specialties.setSpecies((IAlleleBeeSpecies) species);
    }
}
