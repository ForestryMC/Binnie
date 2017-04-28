package binnie.botany.craftgui;

import binnie.botany.api.IColourMix;
import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.ITooltip;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.Tooltip;
import binnie.core.craftgui.WidgetAttribute;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.geometry.IPoint;
import binnie.core.craftgui.resource.Texture;
import binnie.core.craftgui.resource.minecraft.CraftGUITextureSheet;
import binnie.core.craftgui.resource.minecraft.StandardTexture;

public class ControlColourMixSymbol extends Control implements ITooltip {
	static Texture MutationPlus;
	static Texture MutationArrow;

	static {
		ControlColourMixSymbol.MutationPlus = new StandardTexture(2, 94, 16, 16, CraftGUITextureSheet.Controls2);
		ControlColourMixSymbol.MutationArrow = new StandardTexture(20, 94, 32, 16, CraftGUITextureSheet.Controls2);
	}

	IColourMix value;
	int type;

	protected ControlColourMixSymbol(IWidget parent, int x, int y, int type) {
		super(parent, x, y, 16 + type * 16, 16.0f);
		value = null;
		this.type = type;
		addAttribute(WidgetAttribute.MouseOver);
	}

	@Override
	public void onRenderBackground() {
		super.onRenderBackground();
		if (type == 0) {
			CraftGUI.Render.texture(ControlColourMixSymbol.MutationPlus, IPoint.ZERO);
		} else {
			CraftGUI.Render.texture(ControlColourMixSymbol.MutationArrow, IPoint.ZERO);
		}
	}

	public void setValue(IColourMix value) {
		this.value = value;
		setColor(16777215);
	}

	@Override
	public void getTooltip(Tooltip tooltip) {
		if (type == 1) {
			float chance = value.getChance();
			tooltip.add("Current Chance - " + chance + "%");
		}
	}
}
