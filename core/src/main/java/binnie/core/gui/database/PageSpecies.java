package binnie.core.gui.database;

import binnie.core.api.gui.IWidget;
import forestry.api.genetics.IAlleleSpecies;

public abstract class PageSpecies extends PageAbstract<IAlleleSpecies> {
	public PageSpecies(final IWidget parent, final DatabaseTab tab) {
		super(parent, tab);
	}
}
