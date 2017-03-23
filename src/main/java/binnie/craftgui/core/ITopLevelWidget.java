package binnie.craftgui.core;

import binnie.craftgui.core.geometry.Point;

import javax.annotation.Nullable;

public interface ITopLevelWidget extends IWidget {
	void setMousePosition(final int p0, final int p1);

	Point getAbsoluteMousePosition();

	@Nullable
	IWidget getFocusedWidget();

	@Nullable
	IWidget getMousedOverWidget();

	@Nullable
	IWidget getDraggedWidget();

	boolean isFocused(final IWidget p0);

	boolean isMouseOver(final IWidget p0);

	boolean isDragged(final IWidget p0);

	void updateTopLevel();

	void widgetDeleted(final IWidget p0);

	Point getDragDistance();
}
