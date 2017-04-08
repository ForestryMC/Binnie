package binnie.core.craftgui.controls;

import binnie.core.craftgui.Attribute;
import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.controls.core.IControlValue;
import binnie.core.craftgui.events.EventHandler;
import binnie.core.craftgui.events.EventMouse;
import binnie.core.craftgui.events.EventValueChanged;
import binnie.core.craftgui.geometry.Area;
import binnie.core.craftgui.geometry.Point;
import binnie.core.craftgui.geometry.TextJustification;
import binnie.core.craftgui.resource.minecraft.CraftGUITexture;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ControlCheckbox extends Control implements IControlValue<Boolean> {
	private boolean value;
	private String text;

	public ControlCheckbox(final IWidget parent, final int x, final int y, final boolean bool) {
		this(parent, x, y, 0, "", bool);
	}

	public ControlCheckbox(final IWidget parent, final int x, final int y, final int w, final String text, final boolean bool) {
		super(parent, x, y, (w > 16) ? w : 16, 16);
		this.text = text;
		this.value = bool;
		if (w > 16) {
			new ControlText(this, new Area(16, 1, w - 16, 16), text, TextJustification.MiddleCenter).setColour(4473924);
		}
		this.addAttribute(Attribute.MouseOver);
		this.addEventHandler(new EventMouse.Down.Handler() {
			@Override
			public void onEvent(final EventMouse.Down event) {
				ControlCheckbox.this.toggleValue();
			}
		}.setOrigin(EventHandler.Origin.Self, this));
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
		Object texture = this.getValue() ? CraftGUITexture.CheckboxChecked : CraftGUITexture.Checkbox;
		if (this.isMouseOver()) {
			texture = (this.getValue() ? CraftGUITexture.CheckboxCheckedHighlighted : CraftGUITexture.CheckboxHighlighted);
		}
		CraftGUI.render.texture(texture, Point.ZERO);
	}
}
