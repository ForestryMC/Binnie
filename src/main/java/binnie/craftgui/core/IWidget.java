package binnie.craftgui.core;

import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.events.Event;
import binnie.craftgui.events.EventHandler;
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

	IPoint getPosition();

	IPoint pos();

	void setPosition(final IPoint p0);

	IPoint getSize();

	IPoint size();

	void setSize(final IPoint p0);

	IPoint getOriginalPosition();

	IPoint getAbsolutePosition();

	IPoint getOriginalAbsolutePosition();

	IPoint getOffset();

	IArea getArea();

	IArea area();

	void setOffset(final IPoint p0);

	IPoint getMousePosition();

	IPoint getRelativeMousePosition();

	void setColour(final int p0);

	int getColour();

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
	IArea getCroppedZone();

	void setCroppedZone(final IWidget p0, final IArea p1);

	boolean isCroppedWidet();

	IWidget getCropWidget();

	boolean isMouseOverWidget(final IPoint p0);

	int getLevel();

	boolean isDescendant(final IWidget p0);

	List<IWidgetAttribute> getAttributes();

	boolean hasAttribute(final IWidgetAttribute p0);

	boolean addAttribute(final IWidgetAttribute p0);

	<E extends Event> void addEventHandler(final EventHandler<E> p0);

	<E extends Event> void addSelfEventHandler(final EventHandler<E> p0);

	boolean contains(final IPoint point);

	int xPos();

	int yPos();

	int width();

	int height();

	@SideOnly(Side.CLIENT)
	void onRender(final RenderStage stage, int guiWidth, int guiHeight);
}
