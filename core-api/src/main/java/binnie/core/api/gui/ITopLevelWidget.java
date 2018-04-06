package binnie.core.api.gui;

import javax.annotation.Nullable;

public interface ITopLevelWidget extends IWidget {
	IPoint getAbsoluteMousePosition();

	@Nullable
	IWidget getMousedOverWidget();

	boolean isMouseOver(final IWidget p0);

	void updateTopLevel();

	void widgetDeleted(final IWidget p0);

}
