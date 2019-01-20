package binnie.core.gui.controls;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.api.gui.IWidget;
import binnie.core.gui.Attribute;
import binnie.core.gui.CraftGUI;
import binnie.core.gui.controls.core.Control;
import binnie.core.gui.controls.core.IControlValue;
import binnie.core.gui.events.EventMouse;
import binnie.core.gui.events.EventValueChanged;
import binnie.core.gui.geometry.Area;
import binnie.core.gui.geometry.Point;
import binnie.core.gui.geometry.TextJustification;
import binnie.core.gui.resource.textures.CraftGUITexture;

public abstract class ControlCheckbox extends Control implements IControlValue<Boolean> {
	private boolean checked;

	protected ControlCheckbox(IWidget parent, int x, int y, int w, String text, boolean checkedByDefault) {
		super(parent, x, y, (w > 16) ? w : 16, 16);
		this.checked = checkedByDefault;
		if (w > 16) {
			new ControlText(this, new Area(16, 1, w - 16, 16), text, TextJustification.MIDDLE_CENTER).setColor(4473924);
		}
		this.addAttribute(Attribute.MOUSE_OVER);
		this.addSelfEventHandler(EventMouse.Down.class, event -> {
			ControlCheckbox.this.toggleValue();
		});
	}

	protected abstract void onValueChanged(boolean value);

	@Override
	@Nonnull
	public Boolean getValue() {
		return this.checked;
	}

	@Override
	public void setValue(@Nullable Boolean value) {
		if (value == null) {
			value = false;
		}
		this.checked = value;
		this.onValueChanged(value);
		this.callEvent(new EventValueChanged<Object>(this, value));
	}

	public void toggleValue() {
		this.setValue(!getValue());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderBackground(int guiWidth, int guiHeight) {
		Object texture = getValue() ? CraftGUITexture.CHECKBOX_CHECKED : CraftGUITexture.CHECKBOX;
		if (this.isMouseOver()) {
			texture = (getValue() ? CraftGUITexture.CHECKBOX_CHECKED_HIGHLIGHTED : CraftGUITexture.CHECKBOX_HIGHLIGHTED);
		}
		CraftGUI.RENDER.texture(texture, Point.ZERO);
	}
}
