package binnie.core.gui.database;

import binnie.core.api.gui.IWidget;
import binnie.core.gui.controls.page.ControlPage;

public abstract class PageAbstract<T> extends ControlPage<DatabaseTab> {
	public PageAbstract(IWidget parent, DatabaseTab tab) {
		super(parent, 0, 0, parent.getSize().xPos(), parent.getSize().yPos(), tab);
	}

	public abstract void onValueChanged(T p0);
}
