// 
// Decompiled by Procyon v0.5.30
// 

package binnie.botany.craftgui;

import binnie.core.craftgui.resource.minecraft.StandardTexture;
import binnie.core.craftgui.resource.minecraft.CraftGUITextureSheet;
import binnie.core.craftgui.Tooltip;
import binnie.core.craftgui.Attribute;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.geometry.IPoint;
import binnie.core.craftgui.CraftGUI;
import binnie.botany.api.IColourMix;
import binnie.core.craftgui.resource.Texture;
import binnie.core.craftgui.ITooltip;
import binnie.core.craftgui.controls.core.Control;

public class ControlColourMixSymbol extends Control implements ITooltip
{
	static Texture MutationPlus;
	static Texture MutationArrow;
	IColourMix value;
	int type;

	@Override
	public void onRenderBackground() {
		super.onRenderBackground();
		if (this.type == 0) {
			CraftGUI.Render.texture(ControlColourMixSymbol.MutationPlus, IPoint.ZERO);
		}
		else {
			CraftGUI.Render.texture(ControlColourMixSymbol.MutationArrow, IPoint.ZERO);
		}
	}

	protected ControlColourMixSymbol(final IWidget parent, final int x, final int y, final int type) {
		super(parent, x, y, 16 + type * 16, 16.0f);
		this.value = null;
		this.type = type;
		this.addAttribute(Attribute.MouseOver);
	}

	public void setValue(final IColourMix value) {
		this.value = value;
		this.setColour(16777215);
	}

	@Override
	public void getTooltip(final Tooltip tooltip) {
		if (this.type == 1) {
			final float chance = this.value.getChance();
			tooltip.add("Current Chance - " + chance + "%");
		}
	}

	static {
		ControlColourMixSymbol.MutationPlus = new StandardTexture(2, 94, 16, 16, CraftGUITextureSheet.Controls2);
		ControlColourMixSymbol.MutationArrow = new StandardTexture(20, 94, 32, 16, CraftGUITextureSheet.Controls2);
	}
}
