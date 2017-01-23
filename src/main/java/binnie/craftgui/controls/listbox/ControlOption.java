package binnie.craftgui.controls.listbox;

import binnie.craftgui.controls.core.Control;
import binnie.craftgui.controls.core.IControlValue;
import binnie.craftgui.core.Attribute;
import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.events.EventMouse;
import binnie.craftgui.resource.minecraft.CraftGUITexture;

public class ControlOption<T> extends Control implements IControlValue<T> {
	T value;

	@Override
	public void onUpdateClient() {
		if (this.getValue() == null) {
			return;
		}
		int colour = 10526880;
		if (this.isCurrentSelection()) {
			colour = 16777215;
		}
		this.setColour(colour);
	}

	public ControlOption(final ControlList<T> controlList, final T option) {
		this(controlList, option, 16);
	}

	public ControlOption(final ControlList<T> controlList, final T option, final int height) {
		super(controlList, 0.0f, height, controlList.getSize().x(), 20.0f);
		this.value = option;
		if (this.value != null) {
			this.addAttribute(Attribute.MouseOver);
		}
		this.addSelfEventHandler(new EventMouse.Down.Handler() {
			@Override
			public void onEvent(final EventMouse.Down event) {
				((IControlValue) ControlOption.this.getParent()).setValue(ControlOption.this.getValue());
			}
		});
	}

	@Override
	public T getValue() {
		return this.value;
	}

	@Override
	public void setValue(final T value) {
		this.value = value;
	}

	public boolean isCurrentSelection() {
		return this.getValue() != null && this.getValue().equals(((IControlValue) this.getParent()).getValue());
	}

	@Override
	public void onRenderForeground() {
		if (this.isCurrentSelection()) {
			CraftGUI.render.texture(CraftGUITexture.Outline, this.getArea());
		}
	}
}
