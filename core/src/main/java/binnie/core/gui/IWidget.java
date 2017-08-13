package binnie.core.gui;

import javax.annotation.Nullable;
import java.util.List;

import binnie.core.api.gui.IArea;
import binnie.core.api.gui.IPoint;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.gui.events.Event;
import binnie.core.gui.events.EventHandler;

public interface IWidget {
	/**
	 *
	 * @return the parent of this widget
	 */
	@Nullable
	IWidget getParent();

	ITopLevelWidget getTopParent();

	/**
	 * @return the position of this widget
	 */
	IPoint getPosition();

	IPoint getAbsolutePosition();

	void setPosition(IPoint position);

	/**
	 * @return the size of this widget
	 */
	IPoint getSize();

	void setOffset(final IPoint p0);

	/**
	 * @return the area of this widget
	 */
	IArea getArea();

	IPoint getRelativeMousePosition();

	int getYPos();

	int getWidth();

	int getHeight();

	int getColor();

	void setColor(int color);

	@SideOnly(Side.CLIENT)
	void render(int guiWidth, int guiHeight);

	@SideOnly(Side.CLIENT)
	void updateClient();

	void show();

	void hide();

	boolean calculateIsMouseOver();

	boolean isEnabled();

	boolean isVisible();

	boolean canMouseOver();

	boolean canFocus();

	/* CHILDREN */
	boolean isChildVisible(IWidget child);

	void addChild(IWidget child);

	List<IWidget> getChildren();

	@Nullable
	<W> W getWidget(Class<W> widgetClass);

	void deleteChild(IWidget child);

	/* EVENTS*/

	/**
	 * Called if this widget receives an event
	 */
	void receiveEvent(Event event);

	<E extends Event> void addEventHandler(Class<? super E> eventClass, EventHandler.OnEventHandler<E> handler);

	<E extends Event> void addEventHandler(Class<? super E> eventClass, EventHandler.Origin origin, IWidget relative, EventHandler.OnEventHandler<E> handler);

	/**
	 * Adds an event handler to this widget and sets his origin to self
	 */
	<E extends Event> void addSelfEventHandler(Class<? super E> eventClass, EventHandler.OnEventHandler<E> handler);

	void delete();

	@Nullable
	IArea getCroppedZone();

	void setCroppedZone(IWidget widget, IArea area);

	boolean isCroppedWidet();

	IWidget getCropWidget();

	/* ATTRIBUTES */

	/**
	 *
	 * @return true if this widget has this attribute
	 */
	boolean hasAttribute(IWidgetAttribute attribute);

	@SideOnly(Side.CLIENT)
	void onRender(RenderStage stage, int guiWidth, int guiHeight);
}
