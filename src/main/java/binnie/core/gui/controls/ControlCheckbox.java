package binnie.core.gui.controls;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.gui.Attribute;
import binnie.core.gui.CraftGUI;
import binnie.core.gui.IWidget;
import binnie.core.gui.controls.core.Control;
import binnie.core.gui.controls.core.IControlValue;
import binnie.core.gui.events.EventHandler;
import binnie.core.gui.events.EventMouse;
import binnie.core.gui.events.EventValueChanged;
import binnie.core.gui.geometry.Area;
import binnie.core.gui.geometry.Point;
import binnie.core.gui.geometry.TextJustification;
import binnie.core.gui.resource.minecraft.CraftGUITexture;

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
			new ControlText(this, new Area(16, 1, w - 16, 16), text, TextJustification.MIDDLE_CENTER).setColor(4473924);
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
		CraftGUI.RENDER.texture(texture, Point.ZERO);
	}
}
