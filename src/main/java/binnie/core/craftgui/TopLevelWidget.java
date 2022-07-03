package binnie.core.craftgui;

import binnie.core.craftgui.events.EventMouse;
import binnie.core.craftgui.events.EventWidget;
import binnie.core.craftgui.geometry.IPoint;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.List;
import java.util.ListIterator;
import org.lwjgl.input.Mouse;

public abstract class TopLevelWidget extends Widget implements ITopLevelWidget {
    protected IWidget mousedOverWidget;
    protected IWidget draggedWidget;
    protected IWidget focusedWidget;
    protected IPoint mousePosition;
    protected IPoint dragStart;

    public TopLevelWidget() {
        super(null);
        mousePosition = new IPoint(0.0f, 0.0f);
        dragStart = IPoint.ZERO;

        addEventHandler(new EventMouse.Down.Handler() {
            @Override
            public void onEvent(EventMouse.Down event) {
                setDraggedWidget(mousedOverWidget, event.getButton());
                setFocusedWidget(mousedOverWidget);
            }
        });

        addEventHandler(new EventMouse.Up.Handler() {
            @Override
            public void onEvent(EventMouse.Up event) {
                setDraggedWidget(null);
            }
        });

        addEventHandler(new EventWidget.StartDrag.Handler() {
            @Override
            public void onEvent(EventWidget.StartDrag event) {
                dragStart = getRelativeMousePosition();
            }
        });
    }

    public void setMousedOverWidget(IWidget widget) {
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

    public void setDraggedWidget(IWidget widget) {
        setDraggedWidget(widget, -1);
    }

    public void setDraggedWidget(IWidget widget, int button) {
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

    public void setFocusedWidget(IWidget widget) {
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
    public boolean isMouseOver(IWidget widget) {
        return getMousedOverWidget() == widget;
    }

    @Override
    public boolean isDragged(IWidget widget) {
        return getDraggedWidget() == widget;
    }

    @Override
    public boolean isFocused(IWidget widget) {
        return getFocusedWidget() == widget;
    }

    @Override
    public void updateTopLevel() {
        setMousedOverWidget(calculateMousedOverWidget());
        if (getFocusedWidget() != null
                && (!getFocusedWidget().isVisible() || !getFocusedWidget().isEnabled())) {
            setFocusedWidget(null);
        }

        if (!Mouse.isButtonDown(0)) {
            if (draggedWidget != null) {
                setDraggedWidget(null);
            }
        }
    }

    private IWidget calculateMousedOverWidget() {
        Deque<IWidget> queue = calculateMousedOverWidgets();
        while (!queue.isEmpty()) {
            IWidget widget = queue.removeFirst();
            if (widget.isEnabled() && widget.isVisible()) {
                if (!widget.canMouseOver()) {
                    continue;
                }
                if (widget.isEnabled()
                        && widget.isVisible()
                        && widget.canMouseOver()
                        && widget.calculateIsMouseOver()) {
                    return widget;
                }
            }
        }
        return null;
    }

    public Deque<IWidget> calculateMousedOverWidgets() {
        Deque<IWidget> list = new ArrayDeque<>();
        for (IWidget widget : getQueuedWidgets(this)) {
            if (widget.calculateIsMouseOver()) {
                list.addLast(widget);
            }
        }
        return list;
    }

    private Collection<IWidget> getQueuedWidgets(IWidget widget) {
        List<IWidget> widgets = new ArrayList<>();
        boolean addChildren = true;
        if (widget.isCroppedWidget()) {
            addChildren =
                    widget.getCroppedZone().contains(widget.getCropWidget().getRelativeMousePosition());
        }

        if (addChildren) {
            ListIterator<IWidget> li =
                    widget.getWidgets().listIterator(widget.getWidgets().size());
            while (li.hasPrevious()) {
                IWidget child = li.previous();
                widgets.addAll(getQueuedWidgets(child));
            }
        }
        widgets.add(widget);
        return widgets;
    }

    @Override
    public void setMousePosition(int x, int y) {
        float dx = x - mousePosition.x();
        float dy = y - mousePosition.y();
        if (dx != 0.0f || dy != 0.0f) {
            if (getDraggedWidget() != null) {
                callEvent(new EventMouse.Drag(getDraggedWidget(), dx, dy));
            } else {
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
    public void widgetDeleted(IWidget widget) {
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
