package binnie.core.gui.database;

import forestry.api.genetics.IClassification;

import binnie.core.api.gui.IWidget;

abstract class PageBranch extends PageAbstract<IClassification> {
	public PageBranch(IWidget parent, DatabaseTab tab) {
		super(parent, tab);
	}
}
