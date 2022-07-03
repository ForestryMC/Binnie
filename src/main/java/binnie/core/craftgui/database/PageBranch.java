package binnie.core.craftgui.database;

import binnie.core.craftgui.IWidget;
import forestry.api.genetics.IClassification;

abstract class PageBranch extends PageAbstract<IClassification> {
    public PageBranch(IWidget parent, DatabaseTab tab) {
        super(parent, tab);
    }
}
