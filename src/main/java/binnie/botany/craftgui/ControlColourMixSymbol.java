package binnie.botany.craftgui;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.botany.api.IColourMix;
import binnie.core.craftgui.Attribute;
import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.ITooltip;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.Tooltip;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.geometry.Point;
import binnie.core.craftgui.resource.Texture;
import binnie.core.craftgui.resource.minecraft.CraftGUITextureSheet;
import binnie.core.craftgui.resource.minecraft.StandardTexture;

public class ControlColourMixSymbol extends Control implements ITooltip {
	static Texture MutationPlus = new StandardTexture(2, 94, 16, 16, CraftGUITextureSheet.Controls2);
	static Texture MutationArrow = new StandardTexture(20, 94, 32, 16, CraftGUITextureSheet.Controls2);
	IColourMix value;
	int type;

	protected ControlColourMixSymbol(final IWidget parent, final int x, final int y, final int type, final IColourMix value) {
		super(parent, x, y, 16 + type * 16, 16);
		this.value = value;
		this.type = type;
		this.addAttribute(Attribute.MouseOver);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderBackground(int guiWidth, int guiHeight) {
		super.onRenderBackground(guiWidth, guiHeight);
		if (this.type == 0) {
			CraftGUI.render.texture(ControlColourMixSymbol.MutationPlus, Point.ZERO);
		} else {
			CraftGUI.render.texture(ControlColourMixSymbol.MutationArrow, Point.ZERO);
		}
	}

	public void setValue(final IColourMix value) {
		this.value = value;
		this.setColour(0xffffff);
	}

	@Override
	public void getTooltip(final Tooltip tooltip) {
		if (this.type == 1) {
			final float chance = this.value.getChance();
			tooltip.add("Current Chance - " + chance + "%");
		}
	}
}
