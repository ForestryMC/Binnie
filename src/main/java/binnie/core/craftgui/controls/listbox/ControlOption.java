package binnie.core.craftgui.controls.listbox;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.craftgui.Attribute;
import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.controls.core.IControlValue;
import binnie.core.craftgui.events.EventMouse;
import binnie.core.craftgui.resource.minecraft.CraftGUITexture;

public class ControlOption<T> extends Control implements IControlValue<T> {
	private T value;

	public ControlOption(final ControlList<T> controlList, final T option) {
		this(controlList, option, 16);
	}

	public ControlOption(final ControlList<T> controlList, final T option, final int height) {
		super(controlList, 0, height, controlList.getSize().x(), 20);
		this.value = option;
		this.addAttribute(Attribute.MouseOver);
		this.addSelfEventHandler(new EventMouse.Down.Handler() {
			@Override
			public void onEvent(final EventMouse.Down event) {
				controlList.setValue(ControlOption.this.getValue());
			}
		});
	}

	@Override
	public void onUpdateClient() {
		int colour = 0xa0a0a0;
		if (this.isCurrentSelection()) {
			colour = 0xffffff;
		}
		this.setColor(colour);
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
		return this.getValue().equals(((IControlValue) this.getParent()).getValue());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderForeground(int guiWidth, int guiHeight) {
		if (this.isCurrentSelection()) {
			CraftGUI.render.texture(CraftGUITexture.Outline, this.getArea());
		}
	}
}
