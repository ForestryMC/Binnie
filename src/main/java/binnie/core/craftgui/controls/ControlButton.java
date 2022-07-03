package binnie.core.craftgui.controls;

import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.WidgetAttribute;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.events.EventButtonClicked;
import binnie.core.craftgui.events.EventHandler;
import binnie.core.craftgui.events.EventMouse;
import binnie.core.craftgui.geometry.TextJustification;
import binnie.core.craftgui.resource.minecraft.CraftGUITexture;

public class ControlButton extends Control {
    private ControlText textWidget;
    private String text;

    public ControlButton(IWidget parent, float x, float y, float width, float height) {
        super(parent, x, y, width, height);
        addAttribute(WidgetAttribute.MOUSE_OVER);
        mouseHandler.setOrigin(EventHandler.Origin.Self, this);
        addEventHandler(mouseHandler);
    }

    public ControlButton(IWidget parent, float x, float y, float width, float height, String text) {
        this(parent, x, y, width, height);
        this.text = text;
        textWidget = new ControlText(this, getArea(), text, TextJustification.MIDDLE_CENTER);
    }

    private EventMouse.Down.Handler mouseHandler = new EventMouse.Down.Handler() {
        @Override
        public void onEvent(EventMouse.Down event) {
            callEvent(new EventButtonClicked(getWidget()));
            onMouseClick(event);
        }
    };

    protected void onMouseClick(EventMouse.Down event) {
        // ignored
    }

    @Override
    public void onUpdateClient() {
        if (textWidget != null) {
            textWidget.setValue(getText());
        }
    }

    @Override
    public void onRenderBackground() {
        Object texture = CraftGUITexture.ButtonDisabled;
        if (isMouseOver()) {
            texture = CraftGUITexture.ButtonHighlighted;
        } else if (isEnabled()) {
            texture = CraftGUITexture.Button;
        }
        CraftGUI.render.texture(texture, getArea());
    }

    public String getText() {
        return text;
    }
}
