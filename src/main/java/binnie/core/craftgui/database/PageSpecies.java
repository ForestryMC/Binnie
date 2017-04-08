package binnie.core.craftgui.database;

import binnie.core.craftgui.IWidget;
import forestry.api.genetics.IAlleleSpecies;

public abstract class PageSpecies extends PageAbstract<IAlleleSpecies> {
	public PageSpecies(final IWidget parent, final DatabaseTab tab) {
		super(parent, tab);
	}
}
