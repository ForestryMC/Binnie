package binnie.craftgui.core;

import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.events.Event;
import binnie.craftgui.events.EventHandler;
import binnie.craftgui.events.EventWidget;

import java.util.ArrayList;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.List;

public class Widget implements IWidget {
    private IWidget parent;
    private List<IWidget> subWidgets;
    private List<IWidgetAttribute> attributes;
    private IPoint position;
    private IPoint size;
    private IPoint offset;
    IArea cropArea;
    IWidget cropWidget;
    boolean cropped;
    int colour;
    private Collection<EventHandler> globalEventHandlers;
    private boolean enabled;
    private boolean visible;

    public Widget(final IWidget parent) {
        this.parent = null;
        this.subWidgets = new ArrayList<IWidget>();
        this.attributes = new ArrayList<IWidgetAttribute>();
        this.position = new IPoint(0.0f, 0.0f);
        this.size = new IPoint(0.0f, 0.0f);
        this.offset = new IPoint(0.0f, 0.0f);
        this.cropped = false;
        this.colour = 16777215;
        this.globalEventHandlers = new ArrayList<EventHandler>();
        this.enabled = true;
        this.visible = true;
        this.parent = parent;
        if (parent != null) {
            parent.addWidget(this);
        }
    }

    @Override
    public List<IWidgetAttribute> getAttributes() {
        return this.attributes;
    }

    @Override
    public boolean hasAttribute(final IWidgetAttribute attribute) {
        return this.attributes.contains(attribute);
    }

    @Override
    public boolean addAttribute(final IWidgetAttribute attribute) {
        return this.attributes.add(attribute);
    }

    @Override
    public final void deleteChild(final IWidget child) {
        if (child == null) {
            return;
        }
        child.delete();
        this.subWidgets.remove(child);
    }

    @Override
    public final void deleteAllChildren() {
        while (!this.subWidgets.isEmpty()) {
            this.deleteChild(this.subWidgets.get(0));
        }
    }

    @Override
    public final IWidget getParent() {
        return this.parent;
    }

    @Override
    public final ITopLevelWidget getSuperParent() {
        return (ITopLevelWidget) (this.isTopLevel() ? this : this.parent.getSuperParent());
    }

    @Override
    public final IWidget addWidget(final IWidget widget) {
        if (this.subWidgets.size() != 0 && this.subWidgets.get(this.subWidgets.size() - 1).hasAttribute(Attribute.AlwaysOnTop)) {
            this.subWidgets.add(this.subWidgets.size() - 1, widget);
        } else {
            this.subWidgets.add(widget);
        }
        this.onAddChild(widget);
        return widget;
    }

    protected void onAddChild(final IWidget widget) {
    }

    @Override
    public final List<IWidget> getWidgets() {
        return this.subWidgets;
    }

    @Override
    public final boolean isTopLevel() {
        return this instanceof ITopLevelWidget;
    }

    @Override
    public final IPoint pos() {
        return this.position.add(this.offset);
    }

    @Override
    public final IPoint size() {
        return this.size;
    }

    @Override
    public final IArea area() {
        return this.getArea();
    }

    @Override
    public final IPoint getPosition() {
        return this.pos();
    }

    @Override
    public final IArea getArea() {
        return new IArea(IPoint.ZERO, this.size());
    }

    @Override
    public final IPoint getOriginalPosition() {
        return this.position;
    }

    @Override
    public IArea getCroppedZone() {
        return this.cropArea;
    }

    @Override
    public void setCroppedZone(final IWidget relative, final IArea area) {
        this.cropArea = area;
        this.cropped = true;
        this.cropWidget = relative;
    }

    @Override
    public final IPoint getAbsolutePosition() {
        return this.isTopLevel() ? this.getPosition() : this.getParent().getAbsolutePosition().add(this.getPosition());
    }

    @Override
    public final IPoint getOriginalAbsolutePosition() {
        return this.isTopLevel() ? this.getOriginalPosition() : this.getParent().getOriginalPosition().sub(this.getOriginalPosition());
    }

    @Override
    public final IPoint getSize() {
        return this.size();
    }

    @Override
    public final IPoint getOffset() {
        return this.offset;
    }

    @Override
    public final void setPosition(final IPoint vector) {
        if (!vector.equals(this.position)) {
            this.position = new IPoint(vector);
            this.callEvent(new EventWidget.ChangePosition(this));
        }
    }

    @Override
    public final void setSize(final IPoint vector) {
        if (!vector.equals(this.size)) {
            this.size = new IPoint(vector);
            this.callEvent(new EventWidget.ChangeSize(this));
        }
    }

    @Override
    public final void setOffset(final IPoint vector) {
        if (vector != this.offset) {
            this.offset = new IPoint(vector);
            this.callEvent(new EventWidget.ChangeOffset(this));
        }
    }

    @Override
    public final void setColour(final int colour) {
        if (this.colour != colour) {
            this.colour = colour;
            this.callEvent(new EventWidget.ChangeColour(this));
        }
    }

    @Override
    public final int getColour() {
        return this.colour;
    }

    @Override
    public boolean canMouseOver() {
        return this.hasAttribute(Attribute.MouseOver);
    }

    @Override
    public boolean canFocus() {
        return this.hasAttribute(Attribute.CanFocus);
    }

    @Override
    public void addEventHandler(final EventHandler handler) {
        this.globalEventHandlers.add(handler);
    }

    @Override
    public void addSelfEventHandler(final EventHandler handler) {
        this.addEventHandler(handler.setOrigin(EventHandler.Origin.Self, this));
    }

    @Override
    public final void callEvent(final Event event) {
        this.getSuperParent().recieveEvent(event);
    }

    @Override
    public final void recieveEvent(final Event event) {
        for (final EventHandler handler : this.globalEventHandlers) {
            if (handler.handles(event)) {
                handler.onEvent(event);
            }
        }
        try {
            for (final IWidget child : this.getWidgets()) {
                child.recieveEvent(event);
            }
        } catch (ConcurrentModificationException e) {
        }
    }

    @Override
    public final IPoint getMousePosition() {
        return this.getSuperParent().getAbsoluteMousePosition();
    }

    @Override
    public final IPoint getRelativeMousePosition() {
        return this.isTopLevel() ? this.getMousePosition() : this.getParent().getRelativeMousePosition().sub(this.getPosition());
    }

    @Override
    public boolean isCroppedWidet() {
        return this.cropped;
    }

    @Override
    public final IWidget getCropWidget() {
        return (this.cropWidget == null) ? this : this.cropWidget;
    }

    @Override
    public final void render() {
        if (this.isVisible()) {
            CraftGUI.Render.preRender(this);
            this.onRender(RenderStage.PreChildren);
            for (final IWidget widget : this.getWidgets()) {
                widget.render();
            }
            for (final IWidget widget : this.getWidgets()) {
                CraftGUI.Render.preRender(widget);
                widget.onRender(RenderStage.PostSiblings);
                CraftGUI.Render.postRender(widget);
            }
            this.onRender(RenderStage.PostChildren);
            CraftGUI.Render.postRender(this);
        }
    }

    @Override
    public final void updateClient() {
        if (!this.isVisible()) {
            return;
        }
        if (this.getSuperParent() == this) {
            ((ITopLevelWidget) this).updateTopLevel();
        }
        this.onUpdateClient();
        final List<IWidget> deletedWidgets = new ArrayList<IWidget>();
        for (final IWidget widget : this.getWidgets()) {
            if (widget.hasAttribute(Attribute.NeedsDeletion)) {
                deletedWidgets.add(widget);
            } else {
                widget.updateClient();
            }
        }
        for (final IWidget widget : deletedWidgets) {
            this.deleteChild(widget);
        }
    }

    @Override
    public final boolean calculateIsMouseOver() {
        final IPoint mouse = this.getRelativeMousePosition();
        if (!this.cropped) {
            return this.isMouseOverWidget(mouse);
        }
        final IWidget cropRelative = (this.cropWidget != null) ? this.cropWidget : this;
        final IPoint pos = IPoint.sub(cropRelative.getAbsolutePosition(), this.getAbsolutePosition());
        final IPoint size = new IPoint(this.cropArea.size().x(), this.cropArea.size().y());
        final boolean inCrop = mouse.x() > pos.x() && mouse.y() > pos.y() && mouse.x() < pos.x() + size.x() && mouse.y() < pos.y() + size.y();
        return inCrop && this.isMouseOverWidget(mouse);
    }

    @Override
    public boolean isMouseOverWidget(final IPoint relativeMouse) {
        return this.getArea().contains(relativeMouse);
    }

    @Override
    public final void enable() {
        this.enabled = true;
        this.callEvent(new EventWidget.Enable(this));
    }

    @Override
    public final void disable() {
        this.enabled = false;
        this.callEvent(new EventWidget.Disable(this));
    }

    @Override
    public final void show() {
        this.visible = true;
        this.callEvent(new EventWidget.Show(this));
    }

    @Override
    public final void hide() {
        this.visible = false;
        this.callEvent(new EventWidget.Hide(this));
    }

    @Override
    public boolean isEnabled() {
        if (this.enabled) {
            if (!this.isTopLevel()) {
                if (!this.getParent().isEnabled() || !this.getParent().isChildEnabled(this)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public final boolean isVisible() {
        if (this.visible) {
            if (!this.isTopLevel()) {
                if (!this.getParent().isVisible() || !this.getParent().isChildVisible(this)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public final boolean isFocused() {
        return this.getSuperParent().isFocused(this);
    }

    @Override
    public final boolean isDragged() {
        return this.getSuperParent().isDragged(this);
    }

    @Override
    public final boolean isMouseOver() {
        return this.getSuperParent().isMouseOver(this);
    }

    @Override
    public boolean isChildVisible(final IWidget child) {
        return true;
    }

    @Override
    public boolean isChildEnabled(final IWidget child) {
        return true;
    }

    @Override
    public void onRender(final RenderStage stage) {
        if (stage == RenderStage.PreChildren) {
            this.onRenderBackground();
        }
        if (stage == RenderStage.PostChildren) {
            this.onRenderForeground();
        }
        if (stage == RenderStage.PostSiblings) {
            this.onRenderOverlay();
        }
    }

    public void onRenderBackground() {
    }

    public void onRenderForeground() {
    }

    public void onRenderOverlay() {
    }

    @Override
    public void onUpdateClient() {
    }

    @Override
    public final void delete() {
        this.getSuperParent().widgetDeleted(this);
        this.onDelete();
    }

    @Override
    public void onDelete() {
    }

    @Override
    public <T> T getWidget(final Class<T> x) {
        for (final IWidget child : this.getWidgets()) {
            if (x.isInstance(child)) {
                return (T) child;
            }
            final T found = child.getWidget(x);
            if (found != null) {
                return found;
            }
        }
        return null;
    }

    @Override
    public final boolean contains(final IPoint position) {
        return this.getArea().contains(position);
    }

    public void scheduleDeletion() {
        this.addAttribute(Attribute.NeedsDeletion);
    }

    @Override
    public int getLevel() {
        final int level = (this.getParent() == null) ? 0 : this.getParent().getLevel();
        final int index = (this.getParent() == null) ? 0 : this.getParent().getWidgets().indexOf(this);
        return level + index;
    }

    @Override
    public boolean isDescendant(final IWidget widget) {
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
        return this.pos().x();
    }

    @Override
    public float y() {
        return this.pos().y();
    }

    @Override
    public float w() {
        return this.size().x();
    }

    @Override
    public float h() {
        return this.size().y();
    }

    public IWidget getWidget() {
        return this;
    }
}
