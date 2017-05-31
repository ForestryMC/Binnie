package binnie.core.craftgui;

import binnie.core.craftgui.events.Event;
import binnie.core.craftgui.events.EventHandler;
import binnie.core.craftgui.geometry.Area;
import binnie.core.craftgui.geometry.Point;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public interface IWidget {
	@Nullable
	IWidget getParent();

	void deleteChild(final IWidget p0);

	void deleteAllChildren();

	ITopLevelWidget getTopParent();

	boolean isTopLevel();

	Point getPosition();

	void setPosition(final Point p0);

	Point pos();

	Point getSize();

	void setSize(final Point p0);

	Point size();

	Point getOriginalPosition();

	Point getAbsolutePosition();

	Point getOriginalAbsolutePosition();

	Point getOffset();

	void setOffset(final Point p0);

	Area getArea();

	Area area();

	Point getMousePosition();

	Point getRelativeMousePosition();

	int getColour();

	void setColour(final int p0);

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

	boolean isChildVisible(final IWidget p0);

	boolean isChildEnabled(final IWidget p0);

	boolean canMouseOver();

	boolean canFocus();

	IWidget addWidget(final IWidget p0);

	List<IWidget> getWidgets();

	void callEvent(final Event p0);

	void recieveEvent(final Event p0);

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

	List<IWidgetAttribute> getAttributes();

	boolean hasAttribute(final IWidgetAttribute p0);

	boolean addAttribute(final IWidgetAttribute p0);

	<E extends Event> void addEventHandler(final EventHandler<E> p0);

	<E extends Event> void addSelfEventHandler(final EventHandler<E> p0);

	boolean contains(final Point point);

	int xPos();

	int yPos();

	int width();

	int height();

	@SideOnly(Side.CLIENT)
	void onRender(final RenderStage stage, int guiWidth, int guiHeight);
}
