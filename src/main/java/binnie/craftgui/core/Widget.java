package binnie.craftgui.core;

import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.events.Event;
import binnie.craftgui.events.EventHandler;
import binnie.craftgui.events.EventWidget;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Widget implements IWidget {
	@Nullable
	private final IWidget parent;
	private List<IWidget> subWidgets;
	private List<IWidgetAttribute> attributes;
	private IPoint position;
	private IPoint size;
	private IPoint offset;
	@Nullable
	IArea cropArea;
	@Nullable
	IWidget cropWidget;
	boolean cropped;
	int colour;
	private Collection<EventHandler<? extends Event>> globalEventHandlers;
	private boolean enabled;
	private boolean visible;

	public Widget(@Nullable final IWidget parent) {
		this.subWidgets = new ArrayList<>();
		this.attributes = new ArrayList<>();
		this.position = IPoint.ZERO;
		this.size = IPoint.ZERO;
		this.offset = IPoint.ZERO;
		this.cropped = false;
		this.colour = 16777215;
		this.globalEventHandlers = new ArrayList<>();
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
	@Nullable
	public IWidget getParent() {
		return this.parent;
	}

	@Override
	public final ITopLevelWidget getTopParent() {
		if (this.parent == null) {
			return (ITopLevelWidget) this;
		} else {
			return this.parent.getTopParent();
		}
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
	@Nullable
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
		IWidget parent = this.getParent();
		return parent == null ? this.getPosition() : parent.getAbsolutePosition().add(this.getPosition());
	}

	@Override
	public final IPoint getOriginalAbsolutePosition() {
		IWidget parent = this.getParent();
		return parent == null ? this.getOriginalPosition() : parent.getOriginalPosition().sub(this.getOriginalPosition());
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
	public <E extends Event> void addEventHandler(final EventHandler<E> handler) {
		this.globalEventHandlers.add(handler);
	}

	@Override
	public <E extends Event> void addSelfEventHandler(final EventHandler<E> handler) {
		this.addEventHandler(handler.setOrigin(EventHandler.Origin.Self, this));
	}

	@Override
	public final void callEvent(final Event event) {
		this.getTopParent().recieveEvent(event);
	}

	@Override
	@SuppressWarnings("unchecked")
	public final void recieveEvent(final Event event) {
		for (final EventHandler<? extends Event> handler : this.globalEventHandlers) {
			if (handler.handles(event)) {
				((EventHandler<Event>)handler).onEvent(event);
			}
		}
		for (final IWidget child : this.getWidgets()) {
			child.recieveEvent(event);
		}
	}

	@Override
	public final IPoint getMousePosition() {
		return this.getTopParent().getAbsoluteMousePosition();
	}

	@Override
	public final IPoint getRelativeMousePosition() {
		IWidget parent = this.getParent();
		return parent == null ? this.getMousePosition() : parent.getRelativeMousePosition().sub(this.getPosition());
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
	public final void render(int guiWidth, int guiHeight) {
		if (this.isVisible()) {
			CraftGUI.render.preRender(this, guiWidth, guiHeight);
			this.onRender(RenderStage.PreChildren, guiWidth, guiHeight);
			for (final IWidget widget : this.getWidgets()) {
				widget.render(guiWidth, guiHeight);
			}
			for (final IWidget widget : this.getWidgets()) {
				CraftGUI.render.preRender(widget, guiWidth, guiHeight);
				widget.onRender(RenderStage.PostSiblings, guiWidth, guiHeight);
				CraftGUI.render.postRender(widget);
			}
			this.onRender(RenderStage.PostChildren, guiWidth, guiHeight);
			CraftGUI.render.postRender(this);
		}
	}

	@Override
	public final void updateClient() {
		if (!this.isVisible()) {
			return;
		}
		if (this.getTopParent() == this) {
			((ITopLevelWidget) this).updateTopLevel();
		}
		this.onUpdateClient();
		final List<IWidget> deletedWidgets = new ArrayList<>();
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
		if (!this.cropped || this.cropArea == null) {
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
			IWidget parent = this.getParent();
			if (parent != null) {
				if (!parent.isEnabled() || !parent.isChildEnabled(this)) {
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
			IWidget parent = this.getParent();
			if (parent != null) {
				if (!parent.isVisible() || !parent.isChildVisible(this)) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public final boolean isFocused() {
		return this.getTopParent().isFocused(this);
	}

	@Override
	public final boolean isDragged() {
		return this.getTopParent().isDragged(this);
	}

	@Override
	public final boolean isMouseOver() {
		return this.getTopParent().isMouseOver(this);
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
	public void onRender(final RenderStage stage, int guiWidth, int guiHeight) {
		if (stage == RenderStage.PreChildren) {
			this.onRenderBackground(guiWidth, guiHeight);
		}
		if (stage == RenderStage.PostChildren) {
			this.onRenderForeground(guiWidth, guiHeight);
		}
		if (stage == RenderStage.PostSiblings) {
			this.onRenderOverlay();
		}
	}

	public void onRenderBackground(int guiWidth, int guiHeight) {
	}

	public void onRenderForeground(int guiWidth, int guiHeight) {
	}

	public void onRenderOverlay() {
	}

	@Override
	public void onUpdateClient() {
	}

	@Override
	public final void delete() {
		this.getTopParent().widgetDeleted(this);
		this.onDelete();
	}

	@Override
	public void onDelete() {
	}

	@Override
	@Nullable
	public <T> T getWidget(final Class<T> widgetClass) {
		for (final IWidget child : this.getWidgets()) {
			if (widgetClass.isInstance(child)) {
				return widgetClass.cast(child);
			}
			final T found = child.getWidget(widgetClass);
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
		IWidget parentWidget = this;
		while (parentWidget != widget) {
			parentWidget = parentWidget.getParent();
			if (parentWidget == null) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int x() {
		return this.pos().x();
	}

	@Override
	public int y() {
		return this.pos().y();
	}

	@Override
	public int w() {
		return this.size().x();
	}

	@Override
	public int h() {
		return this.size().y();
	}

	public IWidget getWidget() {
		return this;
	}
}
