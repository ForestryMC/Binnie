package binnie.core.gui.database;

import binnie.core.gui.IWidget;
import binnie.core.gui.controls.page.ControlPage;

public abstract class PageAbstract<T> extends ControlPage<DatabaseTab> {
	public PageAbstract(final IWidget parent, final DatabaseTab tab) {
		super(parent, 0, 0, parent.getSize().xPos(), parent.getSize().yPos(), tab);
	}

	public abstract void onValueChanged(final T p0);
}
