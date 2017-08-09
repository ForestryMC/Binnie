package binnie.core.gui;

import javax.annotation.Nullable;

import binnie.core.gui.geometry.Point;

public interface ITopLevelWidget extends IWidget {
	Point getAbsoluteMousePosition();

	@Nullable
	IWidget getMousedOverWidget();

	boolean isMouseOver(final IWidget p0);

	void updateTopLevel();

	void widgetDeleted(final IWidget p0);

}
