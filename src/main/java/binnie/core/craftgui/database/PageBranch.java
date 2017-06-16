package binnie.core.craftgui.database;

import forestry.api.genetics.IClassification;

import binnie.core.craftgui.IWidget;

abstract class PageBranch extends PageAbstract<IClassification> {
	public PageBranch(final IWidget parent, final DatabaseTab tab) {
		super(parent, tab);
	}
}
