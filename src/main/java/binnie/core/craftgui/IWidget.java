package binnie.core.craftgui;

import binnie.core.craftgui.events.Event;
import binnie.core.craftgui.events.EventHandler;
import binnie.core.craftgui.geometry.IArea;
import binnie.core.craftgui.geometry.IPoint;
import java.util.List;

public interface IWidget {
    IWidget getParent();

    void deleteChild(IWidget child);

    void deleteAllChildren();

    ITopLevelWidget getSuperParent();

    boolean isTopLevel();

    IPoint getPosition();

    IPoint pos();

    void setPosition(IPoint position);

    IPoint getSize();

    IPoint size();

    void setSize(IPoint size);

    IPoint getOriginalPosition();

    IPoint getAbsolutePosition();

    IPoint getOriginalAbsolutePosition();

    IPoint getOffset();

    IArea getArea();

    IArea area();

    void setOffset(IPoint offset);

    IPoint getMousePosition();

    IPoint getRelativeMousePosition();

    void setColor(int color);

    int getColor();

    void render();

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

    boolean isChildVisible(IWidget child);

    boolean isChildEnabled(IWidget child);

    boolean canMouseOver();

    boolean canFocus();

    IWidget addWidget(IWidget widget);

    List<IWidget> getWidgets();

    void callEvent(Event event);

    void recieveEvent(Event event);

    void onUpdateClient();

    void delete();

    void onDelete();

    <T> T getWidget(Class<T> p0);

    IArea getCroppedZone();

    void setCroppedZone(IWidget relative, IArea area);

    boolean isCroppedWidget();

    IWidget getCropWidget();

    boolean isMouseOverWidget(IPoint p0);

    int getLevel();

    boolean isDescendant(IWidget widget);

    List<WidgetAttribute> getAttributes();

    boolean hasAttribute(WidgetAttribute attribute);

    boolean addAttribute(WidgetAttribute attribute);

    <E extends Event> void addEventHandler(EventHandler<E> handler);

    <E extends Event> void addSelfEventHandler(EventHandler<E> handler);

    boolean contains(IPoint p0);

    float x();

    float y();

    float w();

    float h();

    void onRender(RenderStage stage);
}
