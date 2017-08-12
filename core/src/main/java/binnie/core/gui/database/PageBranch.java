package binnie.core.gui.database;

import forestry.api.genetics.IClassification;

import binnie.core.gui.IWidget;

abstract class PageBranch extends PageAbstract<IClassification> {
	public PageBranch(final IWidget parent, final DatabaseTab tab) {
		super(parent, tab);
	}
}
