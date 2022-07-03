package binnie.core.craftgui;

import binnie.core.craftgui.events.Event;
import binnie.core.craftgui.events.EventHandler;
import binnie.core.craftgui.events.EventWidget;
import binnie.core.craftgui.geometry.IArea;
import binnie.core.craftgui.geometry.IPoint;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.List;

public class Widget implements IWidget {
    protected IArea cropArea;
    protected IWidget cropWidget;
    protected boolean cropped;
    protected int color;

    private IWidget parent;
    private List<IWidget> subWidgets;
    private List<WidgetAttribute> attributes;
    private IPoint position;
    private IPoint size;
    private IPoint offset;
    private Collection<EventHandler> globalEventHandlers;
    private boolean enabled;
    private boolean visible;

    public Widget(IWidget parent) {
        this.parent = null;
        subWidgets = new ArrayList<>();
        attributes = new ArrayList<>();
        position = new IPoint(0.0f, 0.0f);
        size = new IPoint(0.0f, 0.0f);
        offset = new IPoint(0.0f, 0.0f);
        cropped = false;
        color = 0xffffff;
        globalEventHandlers = new ArrayList<>();
        enabled = true;
        visible = true;

        this.parent = parent;
        if (parent != null) {
            parent.addWidget(this);
        }
    }

    @Override
    public List<WidgetAttribute> getAttributes() {
        return attributes;
    }

    @Override
    public boolean hasAttribute(WidgetAttribute attribute) {
        return attributes.contains(attribute);
    }

    @Override
    public boolean addAttribute(WidgetAttribute attribute) {
        return attributes.add(attribute);
    }

    @Override
    public void deleteChild(IWidget child) {
        if (child == null) {
            return;
        }

        child.delete();
        subWidgets.remove(child);
    }

    @Override
    public void deleteAllChildren() {
        while (!subWidgets.isEmpty()) {
            deleteChild(subWidgets.get(0));
        }
    }

    @Override
    public IWidget getParent() {
        return parent;
    }

    @Override
    public ITopLevelWidget getSuperParent() {
        return (ITopLevelWidget) (isTopLevel() ? this : parent.getSuperParent());
    }

    @Override
    public IWidget addWidget(IWidget widget) {
        if (subWidgets.size() != 0
                && subWidgets.get(subWidgets.size() - 1).hasAttribute(WidgetAttribute.ALWAYS_ON_TOP)) {
            subWidgets.add(subWidgets.size() - 1, widget);
        } else {
            subWidgets.add(widget);
        }
        onAddChild(widget);
        return widget;
    }

    protected void onAddChild(IWidget widget) {
        // ignored
    }

    @Override
    public List<IWidget> getWidgets() {
        return subWidgets;
    }

    @Override
    public boolean isTopLevel() {
        return this instanceof ITopLevelWidget;
    }

    @Override
    public IPoint pos() {
        return position.add(offset);
    }

    @Override
    public IPoint size() {
        return size;
    }

    @Override
    public IArea area() {
        return getArea();
    }

    @Override
    public IPoint getPosition() {
        return pos();
    }

    @Override
    public IArea getArea() {
        return new IArea(IPoint.ZERO, size());
    }

    @Override
    public IPoint getOriginalPosition() {
        return position;
    }

    @Override
    public IArea getCroppedZone() {
        return cropArea;
    }

    @Override
    public void setCroppedZone(IWidget relative, IArea area) {
        cropArea = area;
        cropped = true;
        cropWidget = relative;
    }

    @Override
    public IPoint getAbsolutePosition() {
        return isTopLevel() ? getPosition() : getParent().getAbsolutePosition().add(getPosition());
    }

    @Override
    public IPoint getOriginalAbsolutePosition() {
        return isTopLevel()
                ? getOriginalPosition()
                : getParent().getOriginalPosition().sub(getOriginalPosition());
    }

    @Override
    public IPoint getSize() {
        return size();
    }

    @Override
    public IPoint getOffset() {
        return offset;
    }

    @Override
    public void setPosition(IPoint position) {
        if (!position.equals(this.position)) {
            this.position = new IPoint(position);
            callEvent(new EventWidget.ChangePosition(this));
        }
    }

    @Override
    public void setSize(IPoint size) {
        if (!size.equals(this.size)) {
            this.size = new IPoint(size);
            callEvent(new EventWidget.ChangeSize(this));
        }
    }

    @Override
    public void setOffset(IPoint offset) {
        if (offset != this.offset) {
            this.offset = new IPoint(offset);
            callEvent(new EventWidget.ChangeOffset(this));
        }
    }

    @Override
    public void setColor(int color) {
        if (this.color != color) {
            this.color = color;
            callEvent(new EventWidget.ChangeColour(this));
        }
    }

    @Override
    public int getColor() {
        return color;
    }

    @Override
    public boolean canMouseOver() {
        return hasAttribute(WidgetAttribute.MOUSE_OVER);
    }

    @Override
    public boolean canFocus() {
        return hasAttribute(WidgetAttribute.CAN_FOCUS);
    }

    @Override
    public void addEventHandler(EventHandler handler) {
        globalEventHandlers.add(handler);
    }

    @Override
    public void addSelfEventHandler(EventHandler handler) {
        addEventHandler(handler.setOrigin(EventHandler.Origin.Self, this));
    }

    @Override
    public void callEvent(Event event) {
        getSuperParent().recieveEvent(event);
    }

    @Override
    public void recieveEvent(Event event) {
        for (EventHandler handler : globalEventHandlers) {
            if (handler.handles(event)) {
                handler.onEvent(event);
            }
        }

        try {
            for (IWidget child : getWidgets()) {
                child.recieveEvent(event);
            }
        } catch (ConcurrentModificationException e) {
            // ignored
        }
    }

    @Override
    public IPoint getMousePosition() {
        return getSuperParent().getAbsoluteMousePosition();
    }

    @Override
    public IPoint getRelativeMousePosition() {
        return isTopLevel()
                ? getMousePosition()
                : getParent().getRelativeMousePosition().sub(getPosition());
    }

    @Override
    public boolean isCroppedWidget() {
        return cropped;
    }

    @Override
    public IWidget getCropWidget() {
        return (cropWidget == null) ? this : cropWidget;
    }

    @Override
    public void render() {
        if (!isVisible()) {
            return;
        }

        CraftGUI.render.preRender(this);
        onRender(RenderStage.PreChildren);

        for (IWidget widget : getWidgets()) {
            widget.render();
            CraftGUI.render.preRender(widget);
            widget.onRender(RenderStage.PostSiblings);
            CraftGUI.render.postRender(widget);
        }

        onRender(RenderStage.PostChildren);
        CraftGUI.render.postRender(this);
    }

    @Override
    public void updateClient() {
        if (!isVisible()) {
            return;
        }

        if (getSuperParent() == this) {
            ((ITopLevelWidget) this).updateTopLevel();
        }

        onUpdateClient();
        List<IWidget> deletedWidgets = new ArrayList<>();
        for (IWidget widget : getWidgets()) {
            if (widget.hasAttribute(WidgetAttribute.NEEDS_DELETION)) {
                deletedWidgets.add(widget);
            } else {
                widget.updateClient();
            }
        }

        for (IWidget widget : deletedWidgets) {
            deleteChild(widget);
        }
    }

    @Override
    public boolean calculateIsMouseOver() {
        IPoint mouse = getRelativeMousePosition();
        if (!cropped) {
            return isMouseOverWidget(mouse);
        }

        IWidget cropRelative = (cropWidget != null) ? cropWidget : this;
        IPoint pos = IPoint.sub(cropRelative.getAbsolutePosition(), getAbsolutePosition());
        IPoint size = new IPoint(cropArea.size().x(), cropArea.size().y());
        return mouse.x() > pos.x()
                && mouse.y() > pos.y()
                && mouse.x() < pos.x() + size.x()
                && mouse.y() < pos.y() + size.y()
                && isMouseOverWidget(mouse);
    }

    @Override
    public boolean isMouseOverWidget(IPoint relativeMouse) {
        return getArea().contains(relativeMouse);
    }

    @Override
    public void enable() {
        enabled = true;
        callEvent(new EventWidget.Enable(this));
    }

    @Override
    public void disable() {
        enabled = false;
        callEvent(new EventWidget.Disable(this));
    }

    @Override
    public void show() {
        visible = true;
        callEvent(new EventWidget.Show(this));
    }

    @Override
    public void hide() {
        visible = false;
        callEvent(new EventWidget.Hide(this));
    }

    @Override
    public boolean isEnabled() {
        if (!enabled) {
            return false;
        }
        if (isTopLevel()) {
            return true;
        }
        return getParent().isEnabled() && getParent().isChildEnabled(this);
    }

    @Override
    public boolean isVisible() {
        if (!visible) {
            return false;
        }
        if (isTopLevel()) {
            return true;
        }
        return getParent().isVisible() && getParent().isChildVisible(this);
    }

    @Override
    public boolean isFocused() {
        return getSuperParent().isFocused(this);
    }

    @Override
    public boolean isDragged() {
        return getSuperParent().isDragged(this);
    }

    @Override
    public boolean isMouseOver() {
        return getSuperParent().isMouseOver(this);
    }

    @Override
    public boolean isChildVisible(IWidget child) {
        return true;
    }

    @Override
    public boolean isChildEnabled(IWidget child) {
        return true;
    }

    @Override
    public void onRender(RenderStage stage) {
        if (stage == RenderStage.PreChildren) {
            onRenderBackground();
        } else if (stage == RenderStage.PostChildren) {
            onRenderForeground();
        } else if (stage == RenderStage.PostSiblings) {
            onRenderOverlay();
        }
    }

    public void onRenderBackground() {
        // ignored
    }

    public void onRenderForeground() {
        // ignored
    }

    public void onRenderOverlay() {
        // ignored
    }

    @Override
    public void onUpdateClient() {
        // ignored
    }

    @Override
    public void delete() {
        getSuperParent().widgetDeleted(this);
        onDelete();
    }

    @Override
    public void onDelete() {}

    @Override
    public <T> T getWidget(Class<T> x) {
        for (IWidget child : getWidgets()) {
            if (x.isInstance(child)) {
                return (T) child;
            }

            T found = child.getWidget(x);
            if (found != null) {
                return found;
            }
        }
        return null;
    }

    @Override
    public boolean contains(IPoint position) {
        return getArea().contains(position);
    }

    @Override
    public int getLevel() {
        int level = (getParent() == null) ? 0 : getParent().getLevel();
        int index = (getParent() == null) ? 0 : getParent().getWidgets().indexOf(this);
        return level + index;
    }

    @Override
    public boolean isDescendant(IWidget widget) {
        IWidget clss = this;
        while (clss != widget) {
            clss = clss.getParent();
            if (clss == null) {
                return false;
            }
        }
        return true;
    }

    @Override
    public float x() {
        return pos().x();
    }

    @Override
    public float y() {
        return pos().y();
    }

    @Override
    public float w() {
        return size().x();
    }

    @Override
    public float h() {
        return size().y();
    }

    public IWidget getWidget() {
        return this;
    }
}
