package binnie.core.craftgui.minecraft;

import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.WidgetAttribute;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.events.EventHandler;
import binnie.core.craftgui.events.EventMouse;
import binnie.core.craftgui.geometry.IPoint;
import binnie.core.craftgui.resource.minecraft.CraftGUITexture;

public abstract class Dialog extends Control {
    public Dialog(IWidget parent, float w, float h) {
        super(parent, (parent.w() - w) / 2.0f, (parent.h() - h) / 2.0f, w, h);
        addAttribute(WidgetAttribute.MOUSE_OVER);
        addAttribute(WidgetAttribute.ALWAYS_ON_TOP);
        addAttribute(WidgetAttribute.BLOCK_TOOLTIP);
        initialise();
        addEventHandler(new MouseDownHandler().setOrigin(EventHandler.Origin.Any, this));
    }

    @Override
    public abstract void initialise();

    public abstract void onClose();

    @Override
    public void onRenderBackground() {
        CraftGUI.render.gradientRect(getArea().outset(400), -1442840576, -1442840576);
        CraftGUI.render.texture(CraftGUITexture.Window, getArea());
        CraftGUI.render.texture(CraftGUITexture.TabOutline, getArea().inset(4));
    }

    @Override
    public boolean isMouseOverWidget(IPoint relativeMouse) {
        return true;
    }

    private class MouseDownHandler extends EventMouse.Down.Handler {
        @Override
        public void onEvent(EventMouse.Down event) {
            if (!getArea().contains(getRelativeMousePosition())) {
                onClose();
                getParent().deleteChild(Dialog.this);
            }
        }
    }
}
