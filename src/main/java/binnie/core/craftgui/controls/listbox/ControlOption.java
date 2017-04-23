// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.craftgui.controls.listbox;

import binnie.core.craftgui.Attribute;
import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.controls.core.IControlValue;
import binnie.core.craftgui.events.EventMouse;
import binnie.core.craftgui.resource.minecraft.CraftGUITexture;

public class ControlOption<T> extends Control implements IControlValue<T>
{
	T value;

	@Override
	public void onUpdateClient() {
		if (getValue() == null) {
			return;
		}
		int colour = 10526880;
		if (isCurrentSelection()) {
			colour = 16777215;
		}
		setColour(colour);
	}

	public ControlOption(final ControlList<T> controlList, final T option) {
		this(controlList, option, 16);
	}

	public ControlOption(final ControlList<T> controlList, final T option, final int height) {
		super(controlList, 0.0f, height, controlList.getSize().x(), 20.0f);
		value = option;
		if (value != null) {
			addAttribute(Attribute.MouseOver);
		}
		addSelfEventHandler(new EventMouse.Down.Handler() {
			@Override
			public void onEvent(final EventMouse.Down event) {
				((IControlValue) getParent()).setValue(getValue());
			}
		});
	}

	@Override
	public T getValue() {
		return value;
	}

	@Override
	public void setValue(final T value) {
		this.value = value;
	}

	public boolean isCurrentSelection() {
		return getValue() != null && getValue().equals(((IControlValue) getParent()).getValue());
	}

	@Override
	public void onRenderForeground() {
		if (isCurrentSelection()) {
			CraftGUI.Render.texture(CraftGUITexture.Outline, getArea());
		}
	}
}
