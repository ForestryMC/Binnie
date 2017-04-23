// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.craftgui.controls.button;

import binnie.core.craftgui.Attribute;
import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.ControlText;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.events.EventButtonClicked;
import binnie.core.craftgui.events.EventHandler;
import binnie.core.craftgui.events.EventMouse;
import binnie.core.craftgui.geometry.TextJustification;
import binnie.core.craftgui.resource.minecraft.CraftGUITexture;

public class ControlButton extends Control
{
	private ControlText textWidget;
	private String text;

	public ControlButton(final IWidget parent, final float x, final float y, final float width, final float height) {
		super(parent, x, y, width, height);
		addAttribute(Attribute.MouseOver);
		addEventHandler(new EventMouse.Down.Handler() {
			@Override
			public void onEvent(final EventMouse.Down event) {
				callEvent(new EventButtonClicked(getWidget()));
				onMouseClick(event);
			}
		}.setOrigin(EventHandler.Origin.Self, this));
	}

	protected void onMouseClick(final EventMouse.Down event) {
	}

	public ControlButton(final IWidget parent, final float x, final float y, final float width, final float height, final String text) {
		this(parent, x, y, width, height);
		this.text = text;
		textWidget = new ControlText(this, getArea(), text, TextJustification.MiddleCenter);
	}

	@Override
	public void onUpdateClient() {
		if (textWidget != null) {
			textWidget.setValue(getText());
		}
	}

	public String getText() {
		return text;
	}

	@Override
	public void onRenderBackground() {
		Object texture = CraftGUITexture.ButtonDisabled;
		if (isMouseOver()) {
			texture = CraftGUITexture.ButtonHighlighted;
		}
		else if (isEnabled()) {
			texture = CraftGUITexture.Button;
		}
		CraftGUI.Render.texture(texture, getArea());
	}
}
