package binnie.core.craftgui;

import binnie.core.craftgui.events.Event;
import binnie.core.craftgui.events.EventHandler;
import binnie.core.craftgui.events.EventWidget;
import binnie.core.craftgui.geometry.Area;
import binnie.core.craftgui.geometry.Point;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Widget implements IWidget {
	@Nullable
	private final IWidget parent;
	@Nullable
	Area cropArea;
	@Nullable
	IWidget cropWidget;
	boolean cropped;
	int colour;
	private List<IWidget> subWidgets;
	private List<IWidgetAttribute> attributes;
	private Point position;
	private Point size;
	private Point offset;
	private Collection<EventHandler<? extends Event>> globalEventHandlers;
	private boolean enabled;
	private boolean visible;

	public Widget(@Nullable final IWidget parent) {
		this.subWidgets = new ArrayList<>();
		this.attributes = new ArrayList<>();
		this.position = Point.ZERO;
		this.size = Point.ZERO;
		this.offset = Point.ZERO;
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
	public final Point pos() {
		return this.position.add(this.offset);
	}

	@Override
	public final Point size() {
		return this.size;
	}

	@Override
	public final Area area() {
		return this.getArea();
	}

	@Override
	public final Point getPosition() {
		return this.pos();
	}

	@Override
	public final void setPosition(final Point vector) {
		if (!vector.equals(this.position)) {
			this.position = new Point(vector);
			this.callEvent(new EventWidget.ChangePosition(this));
		}
	}

	@Override
	public final Area getArea() {
		return new Area(Point.ZERO, this.size());
	}

	@Override
	public final Point getOriginalPosition() {
		return this.position;
	}

	@Override
	@Nullable
	public Area getCroppedZone() {
		return this.cropArea;
	}

	@Override
	public void setCroppedZone(final IWidget relative, final Area area) {
		this.cropArea = area;
		this.cropped = true;
		this.cropWidget = relative;
	}

	@Override
	public final Point getAbsolutePosition() {
		IWidget parent = this.getParent();
		return parent == null ? this.getPosition() : parent.getAbsolutePosition().add(this.getPosition());
	}

	@Override
	public final Point getOriginalAbsolutePosition() {
		IWidget parent = this.getParent();
		return parent == null ? this.getOriginalPosition() : parent.getOriginalPosition().sub(this.getOriginalPosition());
	}

	@Override
	public final Point getSize() {
		return this.size();
	}

	@Override
	public final void setSize(final Point vector) {
		if (!vector.equals(this.size)) {
			this.size = new Point(vector);
			this.callEvent(new EventWidget.ChangeSize(this));
		}
	}

	@Override
	public final Point getOffset() {
		return this.offset;
	}

	@Override
	public final void setOffset(final Point vector) {
		if (vector != this.offset) {
			this.offset = new Point(vector);
			this.callEvent(new EventWidget.ChangeOffset(this));
		}
	}

	@Override
	public final int getColour() {
		return this.colour;
	}

	@Override
	public final void setColour(final int colour) {
		if (this.colour != colour) {
			this.colour = colour;
			this.callEvent(new EventWidget.ChangeColour(this));
		}
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
				((EventHandler<Event>) handler).onEvent(event);
			}
		}
		List<IWidget> widgets = new ArrayList<>(this.getWidgets());
		for (final IWidget child : widgets) {
			child.recieveEvent(event);
		}
	}

	@Override
	public final Point getMousePosition() {
		return this.getTopParent().getAbsoluteMousePosition();
	}

	@Override
	public final Point getRelativeMousePosition() {
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
	@SideOnly(Side.CLIENT)
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
	@SideOnly(Side.CLIENT)
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
		final Point mouse = this.getRelativeMousePosition();
		if (!this.cropped || this.cropArea == null) {
			return this.isMouseOverWidget(mouse);
		}
		final IWidget cropRelative = (this.cropWidget != null) ? this.cropWidget : this;
		final Point pos = Point.sub(cropRelative.getAbsolutePosition(), this.getAbsolutePosition());
		final Point size = new Point(this.cropArea.size().x(), this.cropArea.size().y());
		final boolean inCrop = mouse.x() > pos.x() && mouse.y() > pos.y() && mouse.x() < pos.x() + size.x() && mouse.y() < pos.y() + size.y();
		return inCrop && this.isMouseOverWidget(mouse);
	}

	@Override
	public boolean isMouseOverWidget(final Point relativeMouse) {
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
	@SideOnly(Side.CLIENT)
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

	@SideOnly(Side.CLIENT)
	public void onRenderBackground(int guiWidth, int guiHeight) {
	}

	@SideOnly(Side.CLIENT)
	public void onRenderForeground(int guiWidth, int guiHeight) {
	}

	@SideOnly(Side.CLIENT)
	public void onRenderOverlay() {
	}

	@Override
	@SideOnly(Side.CLIENT)
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
	public final boolean contains(final Point position) {
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
	public int xPos() {
		return this.pos().x();
	}

	@Override
	public int yPos() {
		return this.pos().y();
	}

	@Override
	public int width() {
		return this.size().x();
	}

	@Override
	public int height() {
		return this.size().y();
	}

	public IWidget getWidget() {
		return this;
	}
}
