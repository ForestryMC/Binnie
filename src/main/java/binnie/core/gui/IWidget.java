package binnie.core.gui;

import javax.annotation.Nullable;
import java.util.List;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.gui.events.Event;
import binnie.core.gui.events.EventHandler;
import binnie.core.gui.geometry.Area;
import binnie.core.gui.geometry.Point;

public interface IWidget {
	/**
	 *
	 * @return the parent of this widget
	 */
	@Nullable
	IWidget getParent();

	ITopLevelWidget getTopParent();

	/**
	 * @return true if this widget is the {@link #getTopParent()}
	 */
	boolean isTopLevel();

	/**
	 * @return the position of this widget
	 */
	Point getPosition();

	Point getOriginalPosition();

	Point getAbsolutePosition();

	Point getOriginalAbsolutePosition();

	void setPosition(Point position);

	/**
	 * @return the size of this widget
	 */
	Point getSize();

	void setSize(Point size);

	/**
	 * @return the offset of this widget
	 */
	Point getOffset();

	void setOffset(final Point p0);

	/**
	 * @return the area of this widget
	 */
	Area getArea();

	/**
	 * @return the position of the mouse
	 */
	Point getMousePosition();

	Point getRelativeMousePosition();

	int getXPos();

	int getYPos();

	int getWidth();

	int getHeight();

	int getColor();

	void setColor(int color);

	@SideOnly(Side.CLIENT)
	void render(int guiWidth, int guiHeight);

	@SideOnly(Side.CLIENT)
	void updateClient();

	void enable();

	void disable();

	void show();

	void hide();

	boolean calculateIsMouseOver();

	boolean isEnabled();

	boolean isVisible();

	boolean isFocused();

	boolean isMouseOver();

	boolean isDragged();

	boolean canMouseOver();

	boolean canFocus();

	/* CHILDREN */
	boolean isChildVisible(IWidget child);

	boolean isChildEnabled(IWidget child);

	IWidget addChild(IWidget child);

	List<IWidget> getChildren();

	void deleteChild(IWidget child);

	void deleteAllChildren();

	/* EVENTS*/

	/**
	 * Calls an event
	 */
	void callEvent(Event event);

	/**
	 * Called if this widget receives an event
	 */
	void receiveEvent(Event event);

	/**
	 * Adds an event handler to this widget
	 */
	<E extends Event> void addEventHandler(EventHandler<E> eventHandler);

	/**
	 * Adds an event handler to this widget and sets his origin to self
	 */
	<E extends Event> void addSelfEventHandler(EventHandler<E> eventHandler);

	@SideOnly(Side.CLIENT)
	void onUpdateClient();

	void delete();

	void onDelete();

	@Nullable
	<T> T getWidget(final Class<T> widgetClass);

	@Nullable
	Area getCroppedZone();

	void setCroppedZone(final IWidget p0, final Area p1);

	boolean isCroppedWidet();

	IWidget getCropWidget();

	boolean isMouseOverWidget(final Point p0);

	int getLevel();

	boolean isDescendant(final IWidget p0);

	/* ATTRIBUTES */

	/**
	 * @return a list with all attributes that this widget has.
	 */
	List<IWidgetAttribute> getAttributes();

	/**
	 *
	 * @return true if this widget has this attribute
	 */
	boolean hasAttribute(IWidgetAttribute attribute);

	/**
	 * Adds an attribute to this widget
	 */
	boolean addAttribute(IWidgetAttribute attribute);

	boolean contains(Point point);


	@SideOnly(Side.CLIENT)
	void onRender(RenderStage stage, int guiWidth, int guiHeight);
}
