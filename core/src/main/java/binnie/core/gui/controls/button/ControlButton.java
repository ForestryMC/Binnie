package binnie.core.gui.controls.button;

import javax.annotation.Nullable;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.api.gui.IWidget;
import binnie.core.gui.Attribute;
import binnie.core.gui.CraftGUI;
import binnie.core.gui.controls.ControlText;
import binnie.core.gui.controls.core.Control;
import binnie.core.gui.events.EventButtonClicked;
import binnie.core.gui.events.EventMouse;
import binnie.core.gui.geometry.TextJustification;
import binnie.core.gui.resource.textures.CraftGUITexture;

public class ControlButton extends Control {
	@Nullable
	private ControlText textWidget;
	@Nullable
	private String text;

	public ControlButton(final IWidget parent, final int x, final int y, final int width, final int height) {
		super(parent, x, y, width, height);
		this.addAttribute(Attribute.MOUSE_OVER);
		this.addSelfEventHandler(EventMouse.Down.class, event -> {
			ControlButton.this.callEvent(new EventButtonClicked(ControlButton.this.getWidget()));
			ControlButton.this.onMouseClick(event);
		});
	}

	public ControlButton(final IWidget parent, final int x, final int y, final int width, final int height, final String text) {
		this(parent, x, y, width, height);
		this.text = text;
		this.textWidget = new ControlText(this, this.getArea(), text, TextJustification.MIDDLE_CENTER);
	}

	protected void onMouseClick(final EventMouse.Down event) {
	}

	@Override
	public void onUpdateClient() {
		if (this.textWidget != null) {
			String text = this.getText();
			if (text != null) {
				this.textWidget.setValue(text);
			}
		}
	}

	@Nullable
	public String getText() {
		return this.text;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderBackground(int guiWidth, int guiHeight) {
		Object texture = CraftGUITexture.BUTTON_DISABLED;
		if (this.isMouseOver()) {
			texture = CraftGUITexture.BUTTON_HIGHLIGHTED;
		} else if (this.isEnabled()) {
			texture = CraftGUITexture.BUTTON;
		}
		CraftGUI.RENDER.texture(texture, this.getArea());
	}
}
