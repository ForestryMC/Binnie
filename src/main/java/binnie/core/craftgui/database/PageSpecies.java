package binnie.core.craftgui.database;

import forestry.api.genetics.IAlleleSpecies;

import binnie.core.craftgui.IWidget;

public abstract class PageSpecies extends PageAbstract<IAlleleSpecies> {
	public PageSpecies(final IWidget parent, final DatabaseTab tab) {
		super(parent, tab);
	}
}
