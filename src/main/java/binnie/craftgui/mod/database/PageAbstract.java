package binnie.craftgui.mod.database;

import binnie.craftgui.controls.page.ControlPage;
import binnie.craftgui.core.IWidget;

public abstract class PageAbstract<T> extends ControlPage<DatabaseTab> {
    public PageAbstract(final IWidget parent, final DatabaseTab tab) {
        super(parent, 0.0f, 0.0f, parent.getSize().x(), parent.getSize().y(), tab);
    }

    public abstract void onValueChanged(final T p0);
}
