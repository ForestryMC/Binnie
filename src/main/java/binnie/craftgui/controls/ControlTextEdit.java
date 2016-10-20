package binnie.craftgui.controls;

import binnie.craftgui.controls.core.Control;
import binnie.craftgui.controls.core.IControlValue;
import binnie.craftgui.core.Attribute;
import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.events.*;
import binnie.craftgui.resource.minecraft.CraftGUITexture;
import net.minecraft.client.gui.GuiTextField;

public class ControlTextEdit extends Control implements IControlValue<String> {
    GuiTextField field;
    private String cachedValue;

    public ControlTextEdit(final IWidget parent, final float x, final float y, final float width, final float height) {
        super(parent, x, y, width, height);
        this.cachedValue = "";
        this.field = new GuiTextField(0, this.getWindow().getGui().getFontRenderer(), 0, 0, 10, 10);
        this.addAttribute(Attribute.CanFocus);
        this.addAttribute(Attribute.MouseOver);
        this.field.setEnableBackgroundDrawing(false);
        this.addEventHandler(new EventKey.Down.Handler() {
            @Override
            public void onEvent(final EventKey.Down event) {
                ControlTextEdit.this.field.textboxKeyTyped(event.getCharacter(), event.getKey());
                final String text = ControlTextEdit.this.getValue();
                if (!text.equals(ControlTextEdit.this.cachedValue)) {
                    ControlTextEdit.this.cachedValue = text;
                    ControlTextEdit.this.callEvent(new EventTextEdit(ControlTextEdit.this, ControlTextEdit.this.cachedValue));
                    ControlTextEdit.this.onTextEdit(ControlTextEdit.this.cachedValue);
                }
            }
        }.setOrigin(EventHandler.Origin.Self, this));
        this.addEventHandler(new EventMouse.Down.Handler() {
            @Override
            public void onEvent(final EventMouse.Down event) {
                ControlTextEdit.this.field.mouseClicked((int) ControlTextEdit.this.getRelativeMousePosition().x(), (int) ControlTextEdit.this.getRelativeMousePosition().y(), event.getButton());
            }
        }.setOrigin(EventHandler.Origin.Self, this));
        this.addEventHandler(new EventWidget.GainFocus.Handler() {
            @Override
            public void onEvent(final EventWidget.GainFocus event) {
                ControlTextEdit.this.field.setFocused(true);
            }
        }.setOrigin(EventHandler.Origin.Self, this));
        this.addEventHandler(new EventWidget.LoseFocus.Handler() {
            @Override
            public void onEvent(final EventWidget.LoseFocus event) {
                ControlTextEdit.this.field.setFocused(false);
            }
        }.setOrigin(EventHandler.Origin.Self, this));
    }

    @Override
    public String getValue() {
        return (this.field.getText() == null) ? "" : this.field.getText();
    }

    @Override
    public void setValue(final String value) {
        if (!this.getValue().equals(value)) {
            this.field.setText(value);
            this.field.setCursorPosition(0);
        }
    }

    @Override
    public void onUpdateClient() {
    }

    protected void onTextEdit(final String value) {
    }

    @Override
    public void onRenderBackground() {
        CraftGUI.Render.texture(CraftGUITexture.Slot, this.getArea());
        this.renderTextField();
    }

    protected void renderTextField() {
        this.field.width = (int) this.w();
        this.field.height = (int) this.h();
        this.field.xPosition = (int) ((this.h() - 8.0f) / 2.0f);
        this.field.yPosition = (int) ((this.h() - 8.0f) / 2.0f);
        this.field.drawTextBox();
    }
}
