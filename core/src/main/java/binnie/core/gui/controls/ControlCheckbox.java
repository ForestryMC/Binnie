package binnie.core.gui.controls;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.gui.Attribute;
import binnie.core.gui.CraftGUI;
import binnie.core.api.gui.IWidget;
import binnie.core.gui.controls.core.Control;
import binnie.core.gui.controls.core.IControlValue;
import binnie.core.gui.events.EventMouse;
import binnie.core.gui.events.EventValueChanged;
import binnie.core.gui.geometry.Area;
import binnie.core.gui.geometry.Point;
import binnie.core.gui.geometry.TextJustification;
import binnie.core.gui.resource.textures.CraftGUITexture;

public class ControlCheckbox extends Control implements IControlValue<Boolean> {
	private boolean value;

	public ControlCheckbox(final IWidget parent, final int x, final int y, final int w, final String text, final boolean bool) {
		super(parent, x, y, (w > 16) ? w : 16, 16);
		this.value = bool;
		if (w > 16) {
			new ControlText(this, new Area(16, 1, w - 16, 16), text, TextJustification.MIDDLE_CENTER).setColor(4473924);
		}
		this.addAttribute(Attribute.MOUSE_OVER);
		this.addSelfEventHandler(EventMouse.Down.class, event -> {
			ControlCheckbox.this.toggleValue();
		});
	}

	protected void onValueChanged(final boolean value) {
	}

	@Override
	public Boolean getValue() {
		return this.value;
	}

	@Override
	public void setValue(final Boolean value) {
		this.value = value;
		this.onValueChanged(value);
		this.callEvent(new EventValueChanged<Object>(this, value));
	}

	public void toggleValue() {
		this.setValue(!this.getValue());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderBackground(int guiWidth, int guiHeight) {
		Object texture = this.getValue() ? CraftGUITexture.CHECKBOX_CHECKED : CraftGUITexture.CHECKBOX;
		if (this.isMouseOver()) {
			texture = (this.getValue() ? CraftGUITexture.CHECKBOX_CHECKED_HIGHLIGHTED : CraftGUITexture.CHECKBOX_HIGHLIGHTED);
		}
		CraftGUI.RENDER.texture(texture, Point.ZERO);
	}
}
