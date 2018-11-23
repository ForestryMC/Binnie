package binnie.core.gui.database;

import forestry.api.genetics.IAlleleSpecies;

import binnie.core.api.gui.IWidget;

public abstract class PageSpecies extends PageAbstract<IAlleleSpecies> {
	public PageSpecies(IWidget parent, DatabaseTab tab) {
		super(parent, tab);
	}
}
