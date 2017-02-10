package binnie.craftgui.controls.listbox;

import binnie.craftgui.controls.core.Control;
import binnie.craftgui.controls.core.IControlValue;
import binnie.craftgui.core.Attribute;
import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.events.EventMouse;
import binnie.craftgui.resource.minecraft.CraftGUITexture;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
		int colour = 10526880;
		if (this.isCurrentSelection()) {
			colour = 16777215;
		}
		this.setColour(colour);
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
