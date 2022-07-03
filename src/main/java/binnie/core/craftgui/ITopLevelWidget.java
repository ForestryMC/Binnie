package binnie.core.craftgui;

import binnie.core.craftgui.geometry.IPoint;

public interface ITopLevelWidget extends IWidget {
    void setMousePosition(int x, int y);

    IPoint getAbsoluteMousePosition();

    IWidget getFocusedWidget();

    IWidget getMousedOverWidget();

    IWidget getDraggedWidget();

    boolean isFocused(IWidget widget);

    boolean isMouseOver(IWidget widget);

    boolean isDragged(IWidget widget);

    void updateTopLevel();

    void widgetDeleted(IWidget widget);

    IPoint getDragDistance();
}
