package binnie.core.gui;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.gui.events.Event;
import binnie.core.gui.events.EventHandler;
import binnie.core.gui.events.EventWidget;
import binnie.core.gui.geometry.Area;
import binnie.core.gui.geometry.Point;

public class Widget implements IWidget {
	@Nullable
	private final IWidget parent;
	@Nullable
	Area cropArea;
	@Nullable
	IWidget cropWidget;
	boolean cropped;
	int colour;
	private List<IWidget> children;
	private List<IWidgetAttribute> attributes;
	private Point position;
	private Point size;
	private Point offset;
	private Collection<EventHandler<? extends Event>> eventHandlers;
	private boolean enabled;
	private boolean visible;

	public Widget(@Nullable final IWidget parent) {
		this.children = new ArrayList<>();
		this.attributes = new ArrayList<>();
		this.position = Point.ZERO;
		this.size = Point.ZERO;
		this.offset = Point.ZERO;
		this.cropped = false;
		this.colour = 0xffffff;
		this.eventHandlers = new ArrayList<>();
		this.enabled = true;
		this.visible = true;
		this.parent = parent;
		if (parent != null) {
			parent.addChild(this);
		}
	}

	/* ATTRIBUTES */
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

	/* CHILDREN */
	@Override
	@Nullable
	public IWidget deleteChild(IWidget child) {
		if (child == null) {
			return null;
		}
		child.delete();
		this.children.remove(child);
		return child;
	}

	@Override
	public final void deleteAllChildren() {
		while (!this.children.isEmpty()) {
			this.deleteChild(this.children.get(0));
		}
	}

	@Override
	@Nullable
	public IWidget addChild(IWidget child) {
		if(child == null){
			return null;
		}
		IWidget topWidget = getTopChild();
		if (topWidget != null && topWidget.hasAttribute(Attribute.ALWAYS_ON_TOP)) {
			this.children.add(this.children.size() - 1, child);
		} else {
			this.children.add(child);
		}
		this.onAddChild(child);
		return child;
	}

	protected void onAddChild(IWidget widget) {
	}

	protected void onDeleteChild(IWidget widget) {
	}

	protected boolean hasChildren(){
		return !children.isEmpty();
	}

	@Nullable
	protected IWidget getFirstChild(){
		if(!hasChildren()){
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
	public <W> W getWidget(Class<W> widgetClass) {
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

	@Override
	@Nullable
	public IWidget getTopChild() {
		if(!hasChildren()){
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

	/* GEOMETRY */
	@Override
	public final void setPosition(Point position) {
		if (!position.equals(this.position)) {
			this.position = new Point(position);
			this.callEvent(new EventWidget.ChangePosition(this));
		}
	}

	@Override
	public final Point getPosition() {
		return this.position.add(this.offset);
	}

	@Override
	public final boolean isTopLevel() {
		return this instanceof ITopLevelWidget;
	}

	@Override
	public final Point getSize() {
		return this.size;
	}

	@Override
	public final Area getArea() {
		return new Area(Point.ZERO, this.getSize());
	}

	@Override
	public final Point getOriginalPosition() {
		return this.position;
	}

	@Override
	@Nullable
	public final Area getCroppedZone() {
		return this.cropArea;
	}

	@Override
	public final void setCroppedZone(final IWidget relative, final Area area) {
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
		return this.colour;
	}

	@Override
	public final void setColor(final int colour) {
		if (this.colour != colour) {
			this.colour = colour;
			this.callEvent(new EventWidget.ChangeColour(this));
		}
	}
	@Override
	public final boolean contains(final Point position) {
		return this.getArea().contains(position);
	}

	@Override
	public int getLevel() {
		IWidget parent = getParent();
		int level = 0;
		int index = 0;
		if(parent != null){
			List<IWidget> widgets = parent.getChildren();
			level = parent.getLevel();
			index = widgets.indexOf(this);
		}
		return level + index;
	}

	/* EVENTS */
	@Override
	public boolean canFocus() {
		return this.hasAttribute(Attribute.CAN_FOCUS);
	}

	@Override
	public <E extends Event> void addEventHandler(EventHandler<E> handler) {
		this.eventHandlers.add(handler);
	}

	@Override
	public <E extends Event> void addSelfEventHandler(final EventHandler<E> handler) {
		this.addEventHandler(handler.setOrigin(EventHandler.Origin.SELF, this));
	}

	@Override
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

		for (IWidget child : getChildren()) {
			child.receiveEvent(event);
		}
	}

	/* MOUSE */
	@Override
	public boolean canMouseOver() {
		return this.hasAttribute(Attribute.MOUSE_OVER);
	}

	@Override
	public Point getMousePosition() {
		return this.getTopParent().getAbsoluteMousePosition();
	}

	@Override
	public final Point getRelativeMousePosition() {
		IWidget parent = this.getParent();
		return parent == null ? this.getMousePosition() : parent.getRelativeMousePosition().sub(this.getPosition());
	}

	@Override
	public final boolean calculateIsMouseOver() {
		final Point mouse = this.getRelativeMousePosition();
		if (!this.cropped || this.cropArea == null) {
			return this.isMouseOverWidget(mouse);
		}
		final IWidget cropRelative = (this.cropWidget != null) ? this.cropWidget : this;
		final Point pos = Point.sub(cropRelative.getAbsolutePosition(), this.getAbsolutePosition());
		final Point size = new Point(this.cropArea.size().xPos(), this.cropArea.size().yPos());
		final boolean inCrop = mouse.xPos() > pos.xPos() && mouse.yPos() > pos.yPos() && mouse.xPos() < pos.xPos() + size.xPos() && mouse.yPos() < pos.yPos() + size.yPos();
		return inCrop && this.isMouseOverWidget(mouse);
	}

	@Override
	public boolean isMouseOverWidget(final Point relativeMouse) {
		return this.getArea().contains(relativeMouse);
	}

	@Override
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

	@SideOnly(Side.CLIENT)
	public void onRenderBackground(int guiWidth, int guiHeight) {
	}

	@SideOnly(Side.CLIENT)
	public void onRenderForeground(int guiWidth, int guiHeight) {
	}

	@SideOnly(Side.CLIENT)
	public void onRenderOverlay() {
	}

	/* STATES*/
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
	public final boolean isFocused() {
		return this.getTopParent().isFocused(this);
	}

	@Override
	public final boolean isDragged() {
		return this.getTopParent().isDragged(this);
	}

	@Override
	public boolean isChildVisible(final IWidget child) {
		return true;
	}

	@Override
	public boolean isChildEnabled(final IWidget child) {
		return true;
	}

	/* UPDATE */
	@Override
	@SideOnly(Side.CLIENT)
	public void onUpdateClient() {
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
		for (final IWidget widget : this.getChildren()) {
			if (widget.hasAttribute(Attribute.NEEDS_DELETION)) {
				deletedWidgets.add(widget);
			} else {
				widget.updateClient();
			}
		}
		for (final IWidget widget : deletedWidgets) {
			this.deleteChild(widget);
		}
	}

	/* DELETION*/
	@Override
	public final void delete() {
		this.getTopParent().widgetDeleted(this);
		this.onDelete();
	}

	@Override
	public void onDelete() {
	}

	public void scheduleDeletion() {
		this.addAttribute(Attribute.NEEDS_DELETION);
	}

	public IWidget getWidget() {
		return this;
	}
}
