package binnie.core.gui;

import javax.annotation.Nullable;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.List;
import java.util.ListIterator;

import binnie.core.api.gui.IArea;
import binnie.core.api.gui.IPoint;
import binnie.core.api.gui.ITopLevelWidget;
import binnie.core.api.gui.IWidget;
import binnie.core.gui.geometry.Point;
import org.lwjgl.input.Mouse;

import binnie.core.gui.events.EventMouse;
import binnie.core.gui.events.EventWidget;

public abstract class TopLevelWidget extends Widget implements ITopLevelWidget {
	protected IPoint mousePosition;
	@Nullable
	IWidget mousedOverWidget;
	@Nullable
	IWidget draggedWidget;
	@Nullable
	IWidget focusedWidget;
	IPoint dragStart;

	public TopLevelWidget() {
		super(null);
		this.mousedOverWidget = null;
		this.draggedWidget = null;
		this.focusedWidget = null;
		this.mousePosition = Point.ZERO;
		this.dragStart = Point.ZERO;
		this.addEventHandler(EventMouse.Down.class, event -> {
			TopLevelWidget.this.setDraggedWidget(TopLevelWidget.this.mousedOverWidget, event.getButton());
			TopLevelWidget.this.setFocusedWidget(TopLevelWidget.this.mousedOverWidget);
		});
		this.addEventHandler(EventMouse.Up.class, event -> TopLevelWidget.this.setDraggedWidget(null));
		this.addEventHandler(EventWidget.StartDrag.class, event -> {
			TopLevelWidget.this.dragStart = TopLevelWidget.this.getRelativeMousePosition();
		});
	}

	public void setDraggedWidget(@Nullable final IWidget widget, final int button) {
		if (this.draggedWidget == widget) {
			return;
		}
		if (this.draggedWidget != null) {
			this.callEvent(new EventWidget.EndDrag(this.draggedWidget));
		}
		this.draggedWidget = widget;
		if (this.draggedWidget != null) {
			this.callEvent(new EventWidget.StartDrag(this.draggedWidget, button));
		}
	}

	@Override
	@Nullable
	public IWidget getMousedOverWidget() {
		return this.mousedOverWidget;
	}

	public void setMousedOverWidget(@Nullable final IWidget widget) {
		if (this.mousedOverWidget == widget) {
			return;
		}
		if (this.mousedOverWidget != null) {
			this.callEvent(new EventWidget.EndMouseOver(this.mousedOverWidget));
		}
		this.mousedOverWidget = widget;
		if (this.mousedOverWidget != null) {
			this.callEvent(new EventWidget.StartMouseOver(this.mousedOverWidget));
		}
	}

	@Nullable
	public IWidget getDraggedWidget() {
		return this.draggedWidget;
	}

	public void setDraggedWidget(@Nullable final IWidget widget) {
		this.setDraggedWidget(widget, -1);
	}

	@Nullable
	public IWidget getFocusedWidget() {
		return this.focusedWidget;
	}

	public void setFocusedWidget(@Nullable final IWidget widget) {
		IWidget newWidget = widget;
		if (this.focusedWidget == newWidget) {
			return;
		}
		if (newWidget != null && !newWidget.canFocus()) {
			newWidget = null;
		}
		if (this.focusedWidget != null) {
			this.callEvent(new EventWidget.LoseFocus(this.focusedWidget));
		}
		this.focusedWidget = newWidget;
		if (this.focusedWidget != null) {
			this.callEvent(new EventWidget.GainFocus(this.focusedWidget));
		}
	}

	@Override
	public boolean isMouseOver(final IWidget widget) {
		return this.getMousedOverWidget() == widget;
	}

	public boolean isDragged(final IWidget widget) {
		return this.getDraggedWidget() == widget;
	}

	public boolean isFocused(final IWidget widget) {
		return this.getFocusedWidget() == widget;
	}

	@Override
	public void updateTopLevel() {
		this.setMousedOverWidget(this.calculateMousedOverWidget());
		if (this.getFocusedWidget() != null && (!this.getFocusedWidget().isVisible() || !this.getFocusedWidget().isEnabled())) {
			this.setFocusedWidget(null);
		}
		if (!Mouse.isButtonDown(0)) {
			if (this.draggedWidget != null) {
				this.setDraggedWidget(null);
			}
		}
	}

	@Nullable
	private IWidget calculateMousedOverWidget() {
		final Deque<IWidget> queue = this.calculateMousedOverWidgets();
		while (!queue.isEmpty()) {
			final IWidget widget = queue.removeFirst();
			if (widget.isEnabled() && widget.isVisible() && widget.canMouseOver() && widget.calculateIsMouseOver()) {
				return widget;
			}
		}
		return null;
	}

	public Deque<IWidget> calculateMousedOverWidgets() {
		final Deque<IWidget> list = new ArrayDeque<>();
		for (final IWidget widget : this.getQueuedWidgets(this)) {
			if (widget.calculateIsMouseOver()) {
				list.addLast(widget);
			}
		}
		return list;
	}

	private Collection<IWidget> getQueuedWidgets(final IWidget widget) {
		List<IWidget> widgets = new ArrayList<>();
		boolean addChildren = true;
		IArea croppedZone = widget.getCroppedZone();
		if (croppedZone != null) {
			addChildren = croppedZone.contains(widget.getCropWidget().getRelativeMousePosition());
		}
		if (addChildren) {
			ListIterator<IWidget> iterator = widget.getChildren().listIterator(widget.getChildren().size());
			while (iterator.hasPrevious()) {
				final IWidget child = iterator.previous();
				widgets.addAll(this.getQueuedWidgets(child));
			}
		}
		widgets.add(widget);
		return widgets;
	}

	public void setMousePosition(final int x, final int y) {
		final float dx = x - this.mousePosition.xPos();
		final float dy = y - this.mousePosition.yPos();
		if (dx != 0.0f || dy != 0.0f) {
			if (this.getDraggedWidget() != null) {
				this.callEvent(new EventMouse.Drag(this.getDraggedWidget(), dx, dy));
			} else {
				this.callEvent(new EventMouse.Move(this, dx, dy));
			}
		}
		if (this.mousePosition.xPos() != x || this.mousePosition.yPos() != y) {
			this.mousePosition = new Point(x, y);
			this.setMousedOverWidget(this.calculateMousedOverWidget());
		}
	}

	@Override
	public IPoint getAbsoluteMousePosition() {
		return this.mousePosition;
	}

	@Override
	public void widgetDeleted(final IWidget widget) {
		if (this.isMouseOver(widget)) {
			this.setMousedOverWidget(null);
		}
		if (this.isDragged(widget)) {
			this.setDraggedWidget(null);
		}
		if (this.isFocused(widget)) {
			this.setFocusedWidget(null);
		}
	}
}
