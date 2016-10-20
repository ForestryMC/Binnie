package binnie.craftgui.core;

import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.events.EventMouse;
import binnie.craftgui.events.EventWidget;
import org.lwjgl.input.Mouse;

import java.util.*;

public abstract class TopLevelWidget extends Widget implements ITopLevelWidget {
    IWidget mousedOverWidget;
    IWidget draggedWidget;
    IWidget focusedWidget;
    protected IPoint mousePosition;
    IPoint dragStart;

    public TopLevelWidget() {
        super(null);
        this.mousedOverWidget = null;
        this.draggedWidget = null;
        this.focusedWidget = null;
        this.mousePosition = new IPoint(0.0f, 0.0f);
        this.dragStart = IPoint.ZERO;
        this.addEventHandler(new EventMouse.Down.Handler() {
            @Override
            public void onEvent(final EventMouse.Down event) {
                TopLevelWidget.this.setDraggedWidget(TopLevelWidget.this.mousedOverWidget, event.getButton());
                TopLevelWidget.this.setFocusedWidget(TopLevelWidget.this.mousedOverWidget);
            }
        });
        this.addEventHandler(new EventMouse.Up.Handler() {
            @Override
            public void onEvent(final EventMouse.Up event) {
                TopLevelWidget.this.setDraggedWidget(null);
            }
        });
        this.addEventHandler(new EventWidget.StartDrag.Handler() {
            @Override
            public void onEvent(final EventWidget.StartDrag event) {
                TopLevelWidget.this.dragStart = TopLevelWidget.this.getRelativeMousePosition();
            }
        });
    }

    public void setMousedOverWidget(final IWidget widget) {
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

    public void setDraggedWidget(final IWidget widget) {
        this.setDraggedWidget(widget, -1);
    }

    public void setDraggedWidget(final IWidget widget, final int button) {
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

    public void setFocusedWidget(final IWidget widget) {
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
    public IWidget getMousedOverWidget() {
        return this.mousedOverWidget;
    }

    @Override
    public IWidget getDraggedWidget() {
        return this.draggedWidget;
    }

    @Override
    public IWidget getFocusedWidget() {
        return this.focusedWidget;
    }

    @Override
    public boolean isMouseOver(final IWidget widget) {
        return this.getMousedOverWidget() == widget;
    }

    @Override
    public boolean isDragged(final IWidget widget) {
        return this.getDraggedWidget() == widget;
    }

    @Override
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

    private IWidget calculateMousedOverWidget() {
        final Deque<IWidget> queue = this.calculateMousedOverWidgets();
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
        for (final IWidget widget : this.getQueuedWidgets(this)) {
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
                widgets.addAll(this.getQueuedWidgets(child));
            }
        }
        widgets.add(widget);
        return widgets;
    }

    @Override
    public void setMousePosition(final int x, final int y) {
        final float dx = x - this.mousePosition.x();
        final float dy = y - this.mousePosition.y();
        if (dx != 0.0f || dy != 0.0f) {
            if (this.getDraggedWidget() != null) {
                this.callEvent(new EventMouse.Drag(this.getDraggedWidget(), dx, dy));
            } else {
                this.callEvent(new EventMouse.Move(this, dx, dy));
            }
        }
        if (this.mousePosition.x() != x || this.mousePosition.y() != y) {
            this.mousePosition = new IPoint(x, y);
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

    @Override
    public IPoint getDragDistance() {
        return this.getRelativeMousePosition().sub(this.dragStart);
    }
}
