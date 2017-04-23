// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.craftgui;

import binnie.core.craftgui.events.EventMouse;
import binnie.core.craftgui.events.EventWidget;
import binnie.core.craftgui.geometry.IPoint;
import org.lwjgl.input.Mouse;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.List;
import java.util.ListIterator;

public abstract class TopLevelWidget extends Widget implements ITopLevelWidget
{
	IWidget mousedOverWidget;
	IWidget draggedWidget;
	IWidget focusedWidget;
	protected IPoint mousePosition;
	IPoint dragStart;

	public TopLevelWidget() {
		super(null);
		mousedOverWidget = null;
		draggedWidget = null;
		focusedWidget = null;
		mousePosition = new IPoint(0.0f, 0.0f);
		dragStart = IPoint.ZERO;
		addEventHandler(new EventMouse.Down.Handler() {
			@Override
			public void onEvent(final EventMouse.Down event) {
				setDraggedWidget(mousedOverWidget, event.getButton());
				setFocusedWidget(mousedOverWidget);
			}
		});
		addEventHandler(new EventMouse.Up.Handler() {
			@Override
			public void onEvent(final EventMouse.Up event) {
				setDraggedWidget(null);
			}
		});
		addEventHandler(new EventWidget.StartDrag.Handler() {
			@Override
			public void onEvent(final EventWidget.StartDrag event) {
				dragStart = getRelativeMousePosition();
			}
		});
	}

	public void setMousedOverWidget(final IWidget widget) {
		if (mousedOverWidget == widget) {
			return;
		}
		if (mousedOverWidget != null) {
			callEvent(new EventWidget.EndMouseOver(mousedOverWidget));
		}
		mousedOverWidget = widget;
		if (mousedOverWidget != null) {
			callEvent(new EventWidget.StartMouseOver(mousedOverWidget));
		}
	}

	public void setDraggedWidget(final IWidget widget) {
		setDraggedWidget(widget, -1);
	}

	public void setDraggedWidget(final IWidget widget, final int button) {
		if (draggedWidget == widget) {
			return;
		}
		if (draggedWidget != null) {
			callEvent(new EventWidget.EndDrag(draggedWidget));
		}
		draggedWidget = widget;
		if (draggedWidget != null) {
			callEvent(new EventWidget.StartDrag(draggedWidget, button));
		}
	}

	public void setFocusedWidget(final IWidget widget) {
		IWidget newWidget = widget;
		if (focusedWidget == newWidget) {
			return;
		}
		if (newWidget != null && !newWidget.canFocus()) {
			newWidget = null;
		}
		if (focusedWidget != null) {
			callEvent(new EventWidget.LoseFocus(focusedWidget));
		}
		focusedWidget = newWidget;
		if (focusedWidget != null) {
			callEvent(new EventWidget.GainFocus(focusedWidget));
		}
	}

	@Override
	public IWidget getMousedOverWidget() {
		return mousedOverWidget;
	}

	@Override
	public IWidget getDraggedWidget() {
		return draggedWidget;
	}

	@Override
	public IWidget getFocusedWidget() {
		return focusedWidget;
	}

	@Override
	public boolean isMouseOver(final IWidget widget) {
		return getMousedOverWidget() == widget;
	}

	@Override
	public boolean isDragged(final IWidget widget) {
		return getDraggedWidget() == widget;
	}

	@Override
	public boolean isFocused(final IWidget widget) {
		return getFocusedWidget() == widget;
	}

	@Override
	public void updateTopLevel() {
		setMousedOverWidget(calculateMousedOverWidget());
		if (getFocusedWidget() != null && (!getFocusedWidget().isVisible() || !getFocusedWidget().isEnabled())) {
			setFocusedWidget(null);
		}
		if (!Mouse.isButtonDown(0)) {
			if (draggedWidget != null) {
				setDraggedWidget(null);
			}
		}
	}

	private IWidget calculateMousedOverWidget() {
		final Deque<IWidget> queue = calculateMousedOverWidgets();
		while (!queue.isEmpty()) {
			final IWidget widget = queue.removeFirst();
			if (widget.isEnabled() && widget.isVisible()) {
				if (!widget.canMouseOver()) {
					continue;
				}
				if (widget.isEnabled() && widget.isVisible() && widget.canMouseOver() && widget.calculateIsMouseOver()) {
					return widget;
				}
				continue;
			}
		}
		return null;
	}

	public Deque<IWidget> calculateMousedOverWidgets() {
		final Deque<IWidget> list = new ArrayDeque<IWidget>();
		for (final IWidget widget : getQueuedWidgets(this)) {
			if (widget.calculateIsMouseOver()) {
				list.addLast(widget);
			}
		}
		return list;
	}

	private Collection<IWidget> getQueuedWidgets(final IWidget widget) {
		final List<IWidget> widgets = new ArrayList<IWidget>();
		boolean addChildren = true;
		if (widget.isCroppedWidet()) {
			addChildren = widget.getCroppedZone().contains(widget.getCropWidget().getRelativeMousePosition());
		}
		if (addChildren) {
			final ListIterator<IWidget> li = widget.getWidgets().listIterator(widget.getWidgets().size());
			while (li.hasPrevious()) {
				final IWidget child = li.previous();
				widgets.addAll(getQueuedWidgets(child));
			}
		}
		widgets.add(widget);
		return widgets;
	}

	@Override
	public void setMousePosition(final int x, final int y) {
		final float dx = x - mousePosition.x();
		final float dy = y - mousePosition.y();
		if (dx != 0.0f || dy != 0.0f) {
			if (getDraggedWidget() != null) {
				callEvent(new EventMouse.Drag(getDraggedWidget(), dx, dy));
			}
			else {
				callEvent(new EventMouse.Move(this, dx, dy));
			}
		}
		if (mousePosition.x() != x || mousePosition.y() != y) {
			mousePosition = new IPoint(x, y);
			setMousedOverWidget(calculateMousedOverWidget());
		}
	}

	@Override
	public IPoint getAbsoluteMousePosition() {
		return mousePosition;
	}

	@Override
	public void widgetDeleted(final IWidget widget) {
		if (isMouseOver(widget)) {
			setMousedOverWidget(null);
		}
		if (isDragged(widget)) {
			setDraggedWidget(null);
		}
		if (isFocused(widget)) {
			setFocusedWidget(null);
		}
	}

	@Override
	public IPoint getDragDistance() {
		return getRelativeMousePosition().sub(dragStart);
	}
}
