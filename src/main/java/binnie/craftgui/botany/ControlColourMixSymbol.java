// 
// Decompiled by Procyon v0.5.30
// 

package binnie.craftgui.botany;

import binnie.craftgui.resource.minecraft.StandardTexture;
import binnie.craftgui.resource.minecraft.CraftGUITextureSheet;
import binnie.craftgui.core.Tooltip;
import binnie.craftgui.core.Attribute;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.core.CraftGUI;
import binnie.botany.api.IColourMix;
import binnie.craftgui.resource.Texture;
import binnie.craftgui.core.ITooltip;
import binnie.craftgui.controls.core.Control;

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
