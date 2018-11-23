package binnie.core.gui.controls.listbox;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.gui.Attribute;
import binnie.core.gui.CraftGUI;
import binnie.core.gui.controls.core.Control;
import binnie.core.gui.controls.core.IControlValue;
import binnie.core.gui.events.EventMouse;
import binnie.core.gui.resource.textures.CraftGUITexture;

public class ControlOption<T> extends Control implements IControlValue<T> {
	private T value;

	public ControlOption(ControlList<T> controlList, T option, int height) {
		super(controlList, 0, height, controlList.getSize().xPos(), 20);
		this.value = option;
		this.addAttribute(Attribute.MOUSE_OVER);
		this.addSelfEventHandler(EventMouse.Down.class, event -> {
			controlList.setValue(ControlOption.this.getValue());
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
	public void setValue(T value) {
		this.value = value;
	}

	public boolean isCurrentSelection() {
		return this.getValue().equals(((IControlValue) this.getParent()).getValue());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderForeground(int guiWidth, int guiHeight) {
		if (this.isCurrentSelection()) {
			CraftGUI.RENDER.texture(CraftGUITexture.OUTLINE, this.getArea());
		}
	}
}
