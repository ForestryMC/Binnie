package binnie.core.gui;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import binnie.core.api.gui.IArea;
import binnie.core.api.gui.IPoint;
import binnie.core.api.gui.ITopLevelWidget;
import binnie.core.api.gui.IWidget;
import binnie.core.api.gui.IWidgetAttribute;
import binnie.core.api.gui.RenderStage;
import binnie.core.api.gui.events.EventHandlerOrigin;
import binnie.core.api.gui.events.OnEventHandler;
import binnie.core.gui.geometry.Point;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.api.gui.events.Event;
import binnie.core.gui.events.EventHandler;
import binnie.core.gui.events.EventWidget;
import binnie.core.gui.geometry.Area;

public class Widget implements IWidget {
	@Nullable
	private final IWidget parent;
	@Nullable
	private IArea cropArea;
	@Nullable
	private IWidget cropWidget;
	private boolean cropped;
	private int color;
	private List<IWidget> children;
	private List<IWidgetAttribute> attributes;
	private IPoint position;
	private IPoint size;
	private IPoint offset;
	private Collection<EventHandler<? extends Event>> eventHandlers;
	private boolean visible;

	public Widget(@Nullable final IWidget parent) {
		this.children = new ArrayList<>();
		this.attributes = new ArrayList<>();
		this.position = Point.ZERO;
		this.size = Point.ZERO;
		this.offset = Point.ZERO;
		this.cropped = false;
		this.color = 0xffffff;
		this.eventHandlers = new ArrayList<>();
		this.visible = true;
		this.parent = parent;
		if (parent != null) {
			parent.addChild(this);
		}
	}

	/* ATTRIBUTES */
	@Override
	public boolean hasAttribute(final IWidgetAttribute attribute) {
		return this.attributes.contains(attribute);
	}

	public boolean addAttribute(final IWidgetAttribute attribute) {
		return this.attributes.add(attribute);
	}

	/* CHILDREN */
	@Override
	public void deleteChild(IWidget child) {
		if (child == null) {
			return;
		}
		child.delete();
		this.children.remove(child);
	}

	public final void deleteAllChildren() {
		while (!this.children.isEmpty()) {
			this.deleteChild(this.children.get(0));
		}
	}

	@Override
	public void addChild(IWidget child) {
		if(child == null){
			return;
		}
		IWidget topWidget = getTopChild();
		if (topWidget != null && topWidget.hasAttribute(Attribute.ALWAYS_ON_TOP)) {
			this.children.add(this.children.size() - 1, child);
		} else {
			this.children.add(child);
		}
	}

	@Nullable
	protected IWidget getFirstChild(){
		if(children.isEmpty()){
			return null;
		}
		return children.get(0);
	}

	@Override
	public final List<IWidget> getChildren() {
		return this.children;
	}

	@Override
	@Nullable
	public <W extends IWidget> W getWidget(Class<W> widgetClass) {
		for (IWidget child : this.getChildren()) {
			if (widgetClass.isInstance(child)) {
				return widgetClass.cast(child);
			}
			W found = child.getWidget(widgetClass);
			if (found != null) {
				return found;
			}
		}
		return null;
	}

	@Nullable
	public IWidget getTopChild() {
		if(children.isEmpty()){
			return null;
		}
		int childrenCount = getChildren().size();
		return children.get(childrenCount - 1);
	}

	/* PARENT */
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

	/* GEOMETRY */
	@Override
	public final void setPosition(IPoint position) {
		if (!position.equals(this.position)) {
			this.position = new Point(position);
			this.callEvent(new EventWidget.ChangePosition(this));
		}
	}

	@Override
	public final IPoint getPosition() {
		return this.position.add(this.offset);
	}

	@Override
	public final IPoint getSize() {
		return this.size;
	}

	@Override
	public final IArea getArea() {
		return new Area(Point.ZERO, this.getSize());
	}

	@Override
	@Nullable
	public final IArea getCroppedZone() {
		return this.cropArea;
	}

	@Override
	public final void setCroppedZone(final IWidget relative, final IArea area) {
		this.cropArea = area;
		this.cropped = true;
		this.cropWidget = relative;
	}

	@Override
	public final IPoint getAbsolutePosition() {
		IWidget parent = this.getParent();
		return parent == null ? this.getPosition() : parent.getAbsolutePosition().add(this.getPosition());
	}

	public final void setSize(final IPoint vector) {
		if (!vector.equals(this.size)) {
			this.size = new Point(vector);
			this.callEvent(new EventWidget.ChangeSize(this));
		}
	}

	@Override
	public final void setOffset(final IPoint vector) {
		if (vector != this.offset) {
			this.offset = new Point(vector);
			this.callEvent(new EventWidget.ChangeOffset(this));
		}
	}

	public final int getXPos() {
		return this.getPosition().xPos();
	}

	@Override
	public final int getYPos() {
		return getPosition().yPos();
	}

	@Override
	public final int getWidth() {
		return this.getSize().xPos();
	}

	@Override
	public final int getHeight() {
		return this.getSize().yPos();
	}

	@Override
	public final int getColor() {
		return this.color;
	}

	@Override
	public final void setColor(final int colour) {
		if (this.color != colour) {
			this.color = colour;
			this.callEvent(new EventWidget.ChangeColour(this));
		}
	}

	/* EVENTS */
	@Override
	public boolean canFocus() {
		return this.hasAttribute(Attribute.CAN_FOCUS);
	}

	@Override
	public <E extends Event> void addEventHandler(Class<? super E> eventClass, OnEventHandler<E> handler) {
		this.eventHandlers.add(new EventHandler<>(eventClass, handler));
	}

	@Override
	public <E extends Event> void addEventHandler(Class<? super E> eventClass, EventHandlerOrigin origin, IWidget relative, OnEventHandler<E> handler) {
		this.eventHandlers.add(new EventHandler<>(eventClass, origin, relative, handler));
	}

	@Override
	public <E extends Event> void addSelfEventHandler(Class<? super E> eventClass, OnEventHandler<E> handler) {
		this.eventHandlers.add(new EventHandler<>(eventClass, EventHandlerOrigin.SELF, this, handler));
	}

	public void callEvent(final Event event) {
		this.getTopParent().receiveEvent(event);
	}

	/* MOUSE */
	@Override
	@SuppressWarnings("unchecked")
	public void receiveEvent(Event event) {
		for (EventHandler<? extends Event> handler : this.eventHandlers) {
			if (handler.handles(event)) {
				((EventHandler<Event>) handler).onEvent(event);
			}
		}

		List<IWidget> children = new LinkedList<>(getChildren());
		for (IWidget child : children) {
			child.receiveEvent(event);
		}
	}

	/* MOUSE */
	@Override
	public boolean canMouseOver() {
		return this.hasAttribute(Attribute.MOUSE_OVER);
	}

	public IPoint getMousePosition() {
		return this.getTopParent().getAbsoluteMousePosition();
	}

	@Override
	public final IPoint getRelativeMousePosition() {
		IWidget parent = this.getParent();
		return parent == null ? this.getMousePosition() : parent.getRelativeMousePosition().sub(this.getPosition());
	}

	@Override
	public final boolean calculateIsMouseOver() {
		final IPoint mouse = this.getRelativeMousePosition();
		if (!this.cropped || this.cropArea == null) {
			return this.isMouseOverWidget(mouse);
		}
		final IWidget cropRelative = (this.cropWidget != null) ? this.cropWidget : this;
		final IPoint pos = Point.sub(cropRelative.getAbsolutePosition(), this.getAbsolutePosition());
		final IPoint size = new Point(this.cropArea.size().xPos(), this.cropArea.size().yPos());
		final boolean inCrop = mouse.xPos() > pos.xPos() && mouse.yPos() > pos.yPos() && mouse.xPos() < pos.xPos() + size.xPos() && mouse.yPos() < pos.yPos() + size.yPos();
		return inCrop && this.isMouseOverWidget(mouse);
	}

	public boolean isMouseOverWidget(final IPoint relativeMouse) {
		return this.getArea().contains(relativeMouse);
	}

	public final boolean isMouseOver() {
		return this.getTopParent().isMouseOver(this);
	}

	/* CROPPED */
	@Override
	public boolean isCroppedWidet() {
		return this.cropped;
	}

	@Override
	public final IWidget getCropWidget() {
		return (this.cropWidget == null) ? this : this.cropWidget;
	}

	/* RENDERING*/
	@Override
	@SideOnly(Side.CLIENT)
	public final void render(int guiWidth, int guiHeight) {
		if (!isVisible()) {
			return;
		}
		CraftGUI.RENDER.preRender(this, guiWidth, guiHeight);
		this.onRender(RenderStage.PRE_CHILDREN, guiWidth, guiHeight);
		for (final IWidget widget : this.getChildren()) {
			widget.render(guiWidth, guiHeight);
		}
		for (final IWidget widget : this.getChildren()) {
			CraftGUI.RENDER.preRender(widget, guiWidth, guiHeight);
			widget.onRender(RenderStage.POST_SIBLINGS, guiWidth, guiHeight);
			CraftGUI.RENDER.postRender(widget);
		}
		this.onRender(RenderStage.POST_CHILDREN, guiWidth, guiHeight);
		CraftGUI.RENDER.postRender(this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onRender(final RenderStage stage, int guiWidth, int guiHeight) {
		if (stage == RenderStage.PRE_CHILDREN) {
			this.onRenderBackground(guiWidth, guiHeight);
		}
		if (stage == RenderStage.POST_CHILDREN) {
			this.onRenderForeground(guiWidth, guiHeight);
		}
		if (stage == RenderStage.POST_SIBLINGS) {
			this.onRenderOverlay();
		}
	}

	/**
	 * Called before the children of this widget are rendered.
	 */
	@SideOnly(Side.CLIENT)
	public void onRenderBackground(int guiWidth, int guiHeight) {
	}

	/**
	 * Called after the children of this widget are rendered.
	 */
	@SideOnly(Side.CLIENT)
	public void onRenderForeground(int guiWidth, int guiHeight) {
	}

	/**
	 * Called after the siblings of this widget are rendered.
	 */
	@SideOnly(Side.CLIENT)
	public void onRenderOverlay() {
	}

	/* STATES*/
	@Override
	public boolean isEnabled() {
		IWidget parent = this.getParent();
		if (parent != null && !parent.isEnabled()) {
			return false;
		}
		return true;
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
	public boolean isChildVisible(final IWidget child) {
		return true;
	}

	/* UPDATE */

	/**
	 * Called if this widget get updated on the client side.
	 */
	@SideOnly(Side.CLIENT)
	public void onUpdateClient() {
	}

	/**
	 * Called to update this widget on the client side.
	 */
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
		for (final IWidget widget : this.getChildren()) {
			widget.updateClient();
		}
	}

	/* DELETION*/
	@Override
	public final void delete() {
		this.getTopParent().widgetDeleted(this);
	}

	//TODO: Why not use this ?
	public IWidget getWidget() {
		return this;
	}
}
