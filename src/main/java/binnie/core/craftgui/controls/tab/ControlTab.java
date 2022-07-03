package binnie.core.craftgui.controls.tab;

import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.ITooltip;
import binnie.core.craftgui.Tooltip;
import binnie.core.craftgui.WidgetAttribute;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.controls.core.IControlValue;
import binnie.core.craftgui.events.EventMouse;
import binnie.core.craftgui.events.EventValueChanged;
import binnie.core.craftgui.geometry.IArea;
import binnie.core.craftgui.geometry.IPoint;
import binnie.core.craftgui.geometry.Position;
import binnie.core.craftgui.minecraft.control.ControlItemDisplay;
import binnie.core.craftgui.minecraft.control.ControlTabIcon;
import binnie.core.craftgui.resource.Texture;
import binnie.core.craftgui.resource.minecraft.CraftGUITexture;

public class ControlTab<T> extends Control implements ITooltip, IControlValue<T> {
    protected T value;

    private ControlTabBar<T> tabBar;

    public ControlTab(ControlTabBar<T> parent, float x, float y, float w, float h, T value) {
        super(parent, x, y, w, h);
        setValue(value);
        tabBar = parent;
        addAttribute(WidgetAttribute.MOUSE_OVER);
        addSelfEventHandler(new EventMouse.Down.Handler() {
            @Override
            public void onEvent(EventMouse.Down event) {
                callEvent(new EventValueChanged<Object>(getWidget(), getValue()));
            }
        });
    }

    @Override
    public void onRenderBackground() {
        Object texture = CraftGUITexture.TabDisabled;
        if (isMouseOver()) {
            texture = CraftGUITexture.TabHighlighted;
        } else if (isCurrentSelection()) {
            texture = CraftGUITexture.Tab;
        }

        Texture lTexture = CraftGUI.render.getTexture(texture);
        Position position = getTabPosition();
        Texture iTexture = lTexture.crop(position, 8.0f);
        IArea area = getArea();
        if (texture == CraftGUITexture.TabDisabled) {
            if (position == Position.TOP || position == Position.LEFT) {
                area.setPosition(area.getPosition().sub(new IPoint(4 * position.x(), 4 * position.y())));
                area.setSize(area.getSize().add(new IPoint(4 * position.x(), 4 * position.y())));
            } else {
                area.setSize(area.getSize().sub(new IPoint(4 * position.x(), 4 * position.y())));
            }
        }

        CraftGUI.render.texture(iTexture, area);
        if (this instanceof ControlTabIcon) {
            ControlTabIcon icon = (ControlTabIcon) this;
            ControlItemDisplay item = (ControlItemDisplay) getWidgets().get(0);
            if (texture == CraftGUITexture.TabDisabled) {
                item.setColor(0xaaaaaaaa);
            } else {
                item.setColor(0xffffffff);
            }

            if (icon.hasOutline()) {
                iTexture = CraftGUI.render.getTexture(CraftGUITexture.TabOutline);
                iTexture = iTexture.crop(position, 8.0f);
                CraftGUI.render.color(icon.getOutlineColor());
                CraftGUI.render.texture(iTexture, area.inset(2));
            }
        }
    }

    @Override
    public void getTooltip(Tooltip tooltip) {
        String name = getName();
        if (name != null && !name.isEmpty()) {
            tooltip.add(getName());
        }
        if (value instanceof ITooltip) {
            ((ITooltip) value).getTooltip(tooltip);
        }
    }

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public void setValue(T value) {
        this.value = value;
    }

    public Position getTabPosition() {
        return tabBar.position;
    }

    public String getName() {
        return value.toString();
    }

    public boolean isCurrentSelection() {
        return getValue() != null && getValue().equals(tabBar.getValue());
    }
}
