package binnie.core.craftgui.controls.listbox;

import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.WidgetAttribute;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.controls.core.IControlValue;
import binnie.core.craftgui.events.EventMouse;
import binnie.core.craftgui.resource.minecraft.CraftGUITexture;

public class ControlOption<T> extends Control implements IControlValue<T> {
    protected T value;

    public ControlOption(ControlList<T> controlList, T option) {
        this(controlList, option, 16);
    }

    public ControlOption(ControlList<T> controlList, T option, int height) {
        super(controlList, 0.0f, height, controlList.getSize().x(), 20.0f);
        value = option;
        if (value != null) {
            addAttribute(WidgetAttribute.MOUSE_OVER);
        }
        addSelfEventHandler(mouseHandler);
    }

    @Override
    public void onUpdateClient() {
        if (getValue() == null) {
            return;
        }

        int color = 0xa0a0a0;
        if (isCurrentSelection()) {
            color = 0xffffff;
        }
        setColor(color);
    }

    private EventMouse.Down.Handler mouseHandler = new EventMouse.Down.Handler() {
        @Override
        public void onEvent(EventMouse.Down event) {
            ((IControlValue) getParent()).setValue(getValue());
        }
    };

    @Override
    public void onRenderForeground() {
        if (isCurrentSelection()) {
            CraftGUI.render.texture(CraftGUITexture.Outline, getArea());
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

    public boolean isCurrentSelection() {
        return getValue() != null && getValue().equals(((IControlValue) getParent()).getValue());
    }
}
