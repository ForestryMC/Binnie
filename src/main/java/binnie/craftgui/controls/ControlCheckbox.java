package binnie.craftgui.controls;

import binnie.craftgui.controls.core.Control;
import binnie.craftgui.controls.core.IControlValue;
import binnie.craftgui.core.Attribute;
import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.core.geometry.TextJustification;
import binnie.craftgui.events.EventHandler;
import binnie.craftgui.events.EventMouse;
import binnie.craftgui.events.EventValueChanged;
import binnie.craftgui.resource.minecraft.CraftGUITexture;

public class ControlCheckbox extends Control implements IControlValue<Boolean> {
    boolean value;
    String text;

    public ControlCheckbox(final IWidget parent, final float x, final float y, final boolean bool) {
        this(parent, x, y, 0.0f, "", bool);
    }

    public ControlCheckbox(final IWidget parent, final float x, final float y, final float w, final String text, final boolean bool) {
        super(parent, x, y, (w > 16.0f) ? w : 16.0f, 16.0f);
        this.text = text;
        this.value = bool;
        if (w > 16.0f) {
            new ControlText(this, new IArea(16.0f, 1.0f, w - 16.0f, 16.0f), text, TextJustification.MiddleCenter).setColour(4473924);
        }
        this.addAttribute(Attribute.MouseOver);
        this.addEventHandler(new EventMouse.Down.Handler() {
            @Override
            public void onEvent(final EventMouse.Down event) {
                ControlCheckbox.this.toggleValue();
            }
        }.setOrigin(EventHandler.Origin.Self, this));
    }

    protected void onValueChanged(final boolean value) {
    }

    @Override
    public Boolean getValue() {
        return this.value;
    }

    @Override
    public void setValue(final Boolean value) {
        this.value = value;
        this.onValueChanged(value);
        this.callEvent(new EventValueChanged<Object>(this, value));
    }

    public void toggleValue() {
        this.setValue(Boolean.valueOf(!this.getValue()));
    }

    @Override
    public void onRenderBackground() {
        Object texture = this.getValue() ? CraftGUITexture.CheckboxChecked : CraftGUITexture.Checkbox;
        if (this.isMouseOver()) {
            texture = (this.getValue() ? CraftGUITexture.CheckboxCheckedHighlighted : CraftGUITexture.CheckboxHighlighted);
        }
        CraftGUI.Render.texture(texture, IPoint.ZERO);
    }
}
