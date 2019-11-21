package binnie.core.gui.database;

import binnie.core.api.gui.IWidget;
import forestry.api.genetics.IClassification;

abstract class PageBranch extends PageAbstract<IClassification> {
	public PageBranch(final IWidget parent, final DatabaseTab tab) {
		super(parent, tab);
	}
}
