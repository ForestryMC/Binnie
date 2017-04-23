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
import binnie.core.craftgui.events.EventMouse;
import binnie.core.craftgui.events.EventValueChanged;
import binnie.core.craftgui.geometry.IArea;
import binnie.core.craftgui.geometry.IPoint;
import binnie.core.craftgui.geometry.TextJustification;
import binnie.core.craftgui.resource.minecraft.CraftGUITexture;

public class ControlCheckbox extends Control implements IControlValue<Boolean>
{
	boolean value;
	String text;

	public ControlCheckbox(final IWidget parent, final float x, final float y, final boolean bool) {
		this(parent, x, y, 0.0f, "", bool);
	}

	public ControlCheckbox(final IWidget parent, final float x, final float y, final float w, final String text, final boolean bool) {
		super(parent, x, y, (w > 16.0f) ? w : 16.0f, 16.0f);
		this.text = text;
		value = bool;
		if (w > 16.0f) {
			new ControlText(this, new IArea(16.0f, 1.0f, w - 16.0f, 16.0f), text, TextJustification.MiddleCenter).setColour(4473924);
		}
		addAttribute(Attribute.MouseOver);
		addEventHandler(new EventMouse.Down.Handler() {
			@Override
			public void onEvent(final EventMouse.Down event) {
				toggleValue();
			}
		}.setOrigin(EventHandler.Origin.Self, this));
	}

	protected void onValueChanged(final boolean value) {
	}

	@Override
	public Boolean getValue() {
		return value;
	}

	@Override
	public void setValue(final Boolean value) {
		this.value = value;
		onValueChanged(value);
		callEvent(new EventValueChanged<Object>(this, value));
	}

	public void toggleValue() {
		setValue(Boolean.valueOf(!getValue()));
	}

	@Override
	public void onRenderBackground() {
		Object texture = getValue() ? CraftGUITexture.CheckboxChecked : CraftGUITexture.Checkbox;
		if (isMouseOver()) {
			texture = (getValue() ? CraftGUITexture.CheckboxCheckedHighlighted : CraftGUITexture.CheckboxHighlighted);
		}
		CraftGUI.Render.texture(texture, IPoint.ZERO);
	}
}
