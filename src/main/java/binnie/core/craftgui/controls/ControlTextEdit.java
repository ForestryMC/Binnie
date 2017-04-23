// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.craftgui.controls;

import binnie.core.craftgui.Attribute;
import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.controls.core.IControlValue;
import binnie.core.craftgui.events.EventHandler;
import binnie.core.craftgui.events.EventKey;
import binnie.core.craftgui.events.EventMouse;
import binnie.core.craftgui.events.EventTextEdit;
import binnie.core.craftgui.events.EventWidget;
import binnie.core.craftgui.resource.minecraft.CraftGUITexture;
import net.minecraft.client.gui.GuiTextField;

public class ControlTextEdit extends Control implements IControlValue<String>
{
	GuiTextField field;
	private String cachedValue;

	public ControlTextEdit(final IWidget parent, final float x, final float y, final float width, final float height) {
		super(parent, x, y, width, height);
		cachedValue = "";
		field = new GuiTextField(getWindow().getGui().getFontRenderer(), 0, 0, 10, 10);
		addAttribute(Attribute.CanFocus);
		addAttribute(Attribute.MouseOver);
		field.setEnableBackgroundDrawing(false);
		addEventHandler(new EventKey.Down.Handler() {
			@Override
			public void onEvent(final EventKey.Down event) {
				field.textboxKeyTyped(event.getCharacter(), event.getKey());
				final String text = getValue();
				if (!text.equals(cachedValue)) {
					cachedValue = text;
					callEvent(new EventTextEdit(ControlTextEdit.this, cachedValue));
					onTextEdit(cachedValue);
				}
			}
		}.setOrigin(EventHandler.Origin.Self, this));
		addEventHandler(new EventMouse.Down.Handler() {
			@Override
			public void onEvent(final EventMouse.Down event) {
				field.mouseClicked((int) getRelativeMousePosition().x(), (int) getRelativeMousePosition().y(), event.getButton());
			}
		}.setOrigin(EventHandler.Origin.Self, this));
		addEventHandler(new EventWidget.GainFocus.Handler() {
			@Override
			public void onEvent(final EventWidget.GainFocus event) {
				field.setFocused(true);
			}
		}.setOrigin(EventHandler.Origin.Self, this));
		addEventHandler(new EventWidget.LoseFocus.Handler() {
			@Override
			public void onEvent(final EventWidget.LoseFocus event) {
				field.setFocused(false);
			}
		}.setOrigin(EventHandler.Origin.Self, this));
	}

	@Override
	public String getValue() {
		return (field.getText() == null) ? "" : field.getText();
	}

	@Override
	public void setValue(final String value) {
		if (!getValue().equals(value)) {
			field.setText(value);
			field.setCursorPosition(0);
		}
	}

	@Override
	public void onUpdateClient() {
	}

	protected void onTextEdit(final String value) {
	}

	@Override
	public void onRenderBackground() {
		CraftGUI.Render.texture(CraftGUITexture.Slot, getArea());
		renderTextField();
	}

	protected void renderTextField() {
		field.width = (int) w();
		field.height = (int) h();
		field.xPosition = (int) ((h() - 8.0f) / 2.0f);
		field.yPosition = (int) ((h() - 8.0f) / 2.0f);
		field.drawTextBox();
	}
}
