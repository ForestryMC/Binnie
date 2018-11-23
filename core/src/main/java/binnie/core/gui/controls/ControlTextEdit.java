package binnie.core.gui.controls;

import net.minecraft.client.gui.GuiTextField;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.api.gui.IWidget;
import binnie.core.gui.Attribute;
import binnie.core.gui.CraftGUI;
import binnie.core.gui.controls.core.Control;
import binnie.core.gui.controls.core.IControlValue;
import binnie.core.gui.events.EventKey;
import binnie.core.gui.events.EventMouse;
import binnie.core.gui.events.EventTextEdit;
import binnie.core.gui.events.EventWidget;
import binnie.core.gui.resource.textures.CraftGUITexture;

@SideOnly(Side.CLIENT)
public class ControlTextEdit extends Control implements IControlValue<String> {
	private final GuiTextField field;
	private String cachedValue;

	public ControlTextEdit(IWidget parent, int x, int y, int width, int height) {
		super(parent, x, y, width, height);
		this.cachedValue = "";
		this.field = new GuiTextField(0, this.getWindow().getGui().getFontRenderer(), 0, 0, 10, 10);
		this.addAttribute(Attribute.CAN_FOCUS);
		this.addAttribute(Attribute.MOUSE_OVER);
		this.field.setEnableBackgroundDrawing(false);
		this.addSelfEventHandler(EventKey.Down.class, event -> {
			this.field.textboxKeyTyped(event.getCharacter(), event.getKey());
			String text = this.getValue();
			if (!text.equals(this.cachedValue)) {
				this.cachedValue = text;
				this.callEvent(new EventTextEdit(ControlTextEdit.this, this.cachedValue));
				this.onTextEdit(this.cachedValue);
			}
		});
		this.addSelfEventHandler(EventMouse.Down.class, event -> {
			this.field.mouseClicked(this.getRelativeMousePosition().xPos(), this.getRelativeMousePosition().yPos(), event.getButton());
		});
		this.addSelfEventHandler(EventWidget.GainFocus.class, event -> {
			this.field.setFocused(true);
		});
		this.addSelfEventHandler(EventWidget.LoseFocus.class, event -> {
			this.field.setFocused(false);
		});
	}

	@Override
	public String getValue() {
		return (this.field.getText() == null) ? "" : this.field.getText();
	}

	@Override
	public void setValue(String value) {
		if (!this.getValue().equals(value)) {
			this.field.setText(value);
		}
	}

	protected void onTextEdit(String value) {
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
