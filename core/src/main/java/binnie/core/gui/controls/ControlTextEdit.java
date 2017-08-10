package binnie.core.gui.controls;

import net.minecraft.client.gui.GuiTextField;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.gui.Attribute;
import binnie.core.gui.CraftGUI;
import binnie.core.gui.IWidget;
import binnie.core.gui.controls.core.Control;
import binnie.core.gui.controls.core.IControlValue;
import binnie.core.gui.events.EventKey;
import binnie.core.gui.events.EventMouse;
import binnie.core.gui.events.EventTextEdit;
import binnie.core.gui.events.EventWidget;
import binnie.core.gui.resource.minecraft.CraftGUITexture;

@SideOnly(Side.CLIENT)
public class ControlTextEdit extends Control implements IControlValue<String> {
	private GuiTextField field;
	private String cachedValue;

	public ControlTextEdit(final IWidget parent, final int x, final int y, final int width, final int height) {
		super(parent, x, y, width, height);
		this.cachedValue = "";
		this.field = new GuiTextField(0, this.getWindow().getGui().getFontRenderer(), 0, 0, 10, 10);
		this.addAttribute(Attribute.CAN_FOCUS);
		this.addAttribute(Attribute.MOUSE_OVER);
		this.field.setEnableBackgroundDrawing(false);
		this.addSelfEventHandler(EventKey.Down.class, event -> {
			ControlTextEdit.this.field.textboxKeyTyped(event.getCharacter(), event.getKey());
			final String text = ControlTextEdit.this.getValue();
			if (!text.equals(ControlTextEdit.this.cachedValue)) {
				ControlTextEdit.this.cachedValue = text;
				ControlTextEdit.this.callEvent(new EventTextEdit(ControlTextEdit.this, ControlTextEdit.this.cachedValue));
				ControlTextEdit.this.onTextEdit(ControlTextEdit.this.cachedValue);
			}
		});
		this.addSelfEventHandler(EventMouse.Down.class, event -> {
			ControlTextEdit.this.field.mouseClicked(ControlTextEdit.this.getRelativeMousePosition().xPos(), ControlTextEdit.this.getRelativeMousePosition().yPos(), event.getButton());
		});
		this.addSelfEventHandler(EventWidget.GainFocus.class, event -> {
			ControlTextEdit.this.field.setFocused(true);
		});
		this.addSelfEventHandler(EventWidget.LoseFocus.class, event -> {
			ControlTextEdit.this.field.setFocused(false);
		});
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
	public void onRenderBackground(int guiWidth, int guiHeight) {
		CraftGUI.RENDER.texture(CraftGUITexture.SLOT, this.getArea());
		this.renderTextField();
	}

	protected void renderTextField() {
		this.field.width = this.getWidth();
		this.field.height = this.getHeight();
		this.field.x = (int) ((this.getHeight() - 8.0f) / 2.0f);
		this.field.y = (int) ((this.getHeight() - 8.0f) / 2.0f);
		this.field.drawTextBox();
	}
}
