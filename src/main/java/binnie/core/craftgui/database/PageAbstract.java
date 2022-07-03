package binnie.core.craftgui.database;

import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.page.ControlPage;

public abstract class PageAbstract<T> extends ControlPage<DatabaseTab> {
    public PageAbstract(IWidget parent, DatabaseTab tab) {
        super(parent, 0.0f, 0.0f, parent.getSize().x(), parent.getSize().y(), tab);
    }

    public abstract void onValueChanged(T value);
}
