// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.craftgui;

import binnie.core.craftgui.geometry.IPoint;

public interface ITopLevelWidget extends IWidget
{
	void setMousePosition(final int p0, final int p1);

	IPoint getAbsoluteMousePosition();

	IWidget getFocusedWidget();

	IWidget getMousedOverWidget();

	IWidget getDraggedWidget();

	boolean isFocused(final IWidget p0);

	boolean isMouseOver(final IWidget p0);

	boolean isDragged(final IWidget p0);

	void updateTopLevel();

	void widgetDeleted(final IWidget p0);

	IPoint getDragDistance();
}
