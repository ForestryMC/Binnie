package binnie.core.craftgui.database;

import binnie.core.craftgui.IWidget;
import forestry.api.genetics.IAlleleSpecies;

public abstract class PageSpecies extends PageAbstract<IAlleleSpecies> {
    public PageSpecies(IWidget parent, DatabaseTab tab) {
        super(parent, tab);
    }
}
