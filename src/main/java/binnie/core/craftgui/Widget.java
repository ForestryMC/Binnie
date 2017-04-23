// 
// Decompiled by Procyon v0.5.30
// 

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

public class Widget implements IWidget
{
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
		subWidgets = new ArrayList<IWidget>();
		attributes = new ArrayList<IWidgetAttribute>();
		position = new IPoint(0.0f, 0.0f);
		size = new IPoint(0.0f, 0.0f);
		offset = new IPoint(0.0f, 0.0f);
		cropped = false;
		colour = 16777215;
		globalEventHandlers = new ArrayList<EventHandler>();
		enabled = true;
		visible = true;
		this.parent = parent;
		if (parent != null) {
			parent.addWidget(this);
		}
	}

	@Override
	public List<IWidgetAttribute> getAttributes() {
		return attributes;
	}

	@Override
	public boolean hasAttribute(final IWidgetAttribute attribute) {
		return attributes.contains(attribute);
	}

	@Override
	public boolean addAttribute(final IWidgetAttribute attribute) {
		return attributes.add(attribute);
	}

	@Override
	public final void deleteChild(final IWidget child) {
		if (child == null) {
			return;
		}
		child.delete();
		subWidgets.remove(child);
	}

	@Override
	public final void deleteAllChildren() {
		while (!subWidgets.isEmpty()) {
			deleteChild(subWidgets.get(0));
		}
	}

	@Override
	public final IWidget getParent() {
		return parent;
	}

	@Override
	public final ITopLevelWidget getSuperParent() {
		return (ITopLevelWidget) (isTopLevel() ? this : parent.getSuperParent());
	}

	@Override
	public final IWidget addWidget(final IWidget widget) {
		if (subWidgets.size() != 0 && subWidgets.get(subWidgets.size() - 1).hasAttribute(Attribute.AlwaysOnTop)) {
			subWidgets.add(subWidgets.size() - 1, widget);
		}
		else {
			subWidgets.add(widget);
		}
		onAddChild(widget);
		return widget;
	}

	protected void onAddChild(final IWidget widget) {
	}

	@Override
	public final List<IWidget> getWidgets() {
		return subWidgets;
	}

	@Override
	public final boolean isTopLevel() {
		return this instanceof ITopLevelWidget;
	}

	@Override
	public final IPoint pos() {
		return position.add(offset);
	}

	@Override
	public final IPoint size() {
		return size;
	}

	@Override
	public final IArea area() {
		return getArea();
	}

	@Override
	public final IPoint getPosition() {
		return pos();
	}

	@Override
	public final IArea getArea() {
		return new IArea(IPoint.ZERO, size());
	}

	@Override
	public final IPoint getOriginalPosition() {
		return position;
	}

	@Override
	public IArea getCroppedZone() {
		return cropArea;
	}

	@Override
	public void setCroppedZone(final IWidget relative, final IArea area) {
		cropArea = area;
		cropped = true;
		cropWidget = relative;
	}

	@Override
	public final IPoint getAbsolutePosition() {
		return isTopLevel() ? getPosition() : getParent().getAbsolutePosition().add(getPosition());
	}

	@Override
	public final IPoint getOriginalAbsolutePosition() {
		return isTopLevel() ? getOriginalPosition() : getParent().getOriginalPosition().sub(getOriginalPosition());
	}

	@Override
	public final IPoint getSize() {
		return size();
	}

	@Override
	public final IPoint getOffset() {
		return offset;
	}

	@Override
	public final void setPosition(final IPoint vector) {
		if (!vector.equals(position)) {
			position = new IPoint(vector);
			callEvent(new EventWidget.ChangePosition(this));
		}
	}

	@Override
	public final void setSize(final IPoint vector) {
		if (!vector.equals(size)) {
			size = new IPoint(vector);
			callEvent(new EventWidget.ChangeSize(this));
		}
	}

	@Override
	public final void setOffset(final IPoint vector) {
		if (vector != offset) {
			offset = new IPoint(vector);
			callEvent(new EventWidget.ChangeOffset(this));
		}
	}

	@Override
	public final void setColour(final int colour) {
		if (this.colour != colour) {
			this.colour = colour;
			callEvent(new EventWidget.ChangeColour(this));
		}
	}

	@Override
	public final int getColour() {
		return colour;
	}

	@Override
	public boolean canMouseOver() {
		return hasAttribute(Attribute.MouseOver);
	}

	@Override
	public boolean canFocus() {
		return hasAttribute(Attribute.CanFocus);
	}

	@Override
	public void addEventHandler(final EventHandler handler) {
		globalEventHandlers.add(handler);
	}

	@Override
	public void addSelfEventHandler(final EventHandler handler) {
		addEventHandler(handler.setOrigin(EventHandler.Origin.Self, this));
	}

	@Override
	public final void callEvent(final Event event) {
		getSuperParent().recieveEvent(event);
	}

	@Override
	public final void recieveEvent(final Event event) {
		for (final EventHandler handler : globalEventHandlers) {
			if (handler.handles(event)) {
				handler.onEvent(event);
			}
		}
		try {
			for (final IWidget child : getWidgets()) {
				child.recieveEvent(event);
			}
		} catch (ConcurrentModificationException e) {
		}
	}

	@Override
	public final IPoint getMousePosition() {
		return getSuperParent().getAbsoluteMousePosition();
	}

	@Override
	public final IPoint getRelativeMousePosition() {
		return isTopLevel() ? getMousePosition() : getParent().getRelativeMousePosition().sub(getPosition());
	}

	@Override
	public boolean isCroppedWidet() {
		return cropped;
	}

	@Override
	public final IWidget getCropWidget() {
		return (cropWidget == null) ? this : cropWidget;
	}

	@Override
	public final void render() {
		if (isVisible()) {
			CraftGUI.Render.preRender(this);
			onRender(RenderStage.PreChildren);
			for (final IWidget widget : getWidgets()) {
				widget.render();
			}
			for (final IWidget widget : getWidgets()) {
				CraftGUI.Render.preRender(widget);
				widget.onRender(RenderStage.PostSiblings);
				CraftGUI.Render.postRender(widget);
			}
			onRender(RenderStage.PostChildren);
			CraftGUI.Render.postRender(this);
		}
	}

	@Override
	public final void updateClient() {
		if (!isVisible()) {
			return;
		}
		if (getSuperParent() == this) {
			((ITopLevelWidget) this).updateTopLevel();
		}
		onUpdateClient();
		final List<IWidget> deletedWidgets = new ArrayList<IWidget>();
		for (final IWidget widget : getWidgets()) {
			if (widget.hasAttribute(Attribute.NeedsDeletion)) {
				deletedWidgets.add(widget);
			}
			else {
				widget.updateClient();
			}
		}
		for (final IWidget widget : deletedWidgets) {
			deleteChild(widget);
		}
	}

	@Override
	public final boolean calculateIsMouseOver() {
		final IPoint mouse = getRelativeMousePosition();
		if (!cropped) {
			return isMouseOverWidget(mouse);
		}
		final IWidget cropRelative = (cropWidget != null) ? cropWidget : this;
		final IPoint pos = IPoint.sub(cropRelative.getAbsolutePosition(), getAbsolutePosition());
		final IPoint size = new IPoint(cropArea.size().x(), cropArea.size().y());
		final boolean inCrop = mouse.x() > pos.x() && mouse.y() > pos.y() && mouse.x() < pos.x() + size.x() && mouse.y() < pos.y() + size.y();
		return inCrop && isMouseOverWidget(mouse);
	}

	@Override
	public boolean isMouseOverWidget(final IPoint relativeMouse) {
		return getArea().contains(relativeMouse);
	}

	@Override
	public final void enable() {
		enabled = true;
		callEvent(new EventWidget.Enable(this));
	}

	@Override
	public final void disable() {
		enabled = false;
		callEvent(new EventWidget.Disable(this));
	}

	@Override
	public final void show() {
		visible = true;
		callEvent(new EventWidget.Show(this));
	}

	@Override
	public final void hide() {
		visible = false;
		callEvent(new EventWidget.Hide(this));
	}

	@Override
	public boolean isEnabled() {
		if (enabled) {
			if (!isTopLevel()) {
				if (!getParent().isEnabled() || !getParent().isChildEnabled(this)) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public final boolean isVisible() {
		if (visible) {
			if (!isTopLevel()) {
				if (!getParent().isVisible() || !getParent().isChildVisible(this)) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public final boolean isFocused() {
		return getSuperParent().isFocused(this);
	}

	@Override
	public final boolean isDragged() {
		return getSuperParent().isDragged(this);
	}

	@Override
	public final boolean isMouseOver() {
		return getSuperParent().isMouseOver(this);
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
			onRenderBackground();
		}
		if (stage == RenderStage.PostChildren) {
			onRenderForeground();
		}
		if (stage == RenderStage.PostSiblings) {
			onRenderOverlay();
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
		getSuperParent().widgetDeleted(this);
		onDelete();
	}

	@Override
	public void onDelete() {
	}

	@Override
	public <T> T getWidget(final Class<T> x) {
		for (final IWidget child : getWidgets()) {
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
		return getArea().contains(position);
	}

	public void scheduleDeletion() {
		addAttribute(Attribute.NeedsDeletion);
	}

	@Override
	public int getLevel() {
		final int level = (getParent() == null) ? 0 : getParent().getLevel();
		final int index = (getParent() == null) ? 0 : getParent().getWidgets().indexOf(this);
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
