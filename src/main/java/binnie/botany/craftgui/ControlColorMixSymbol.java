package binnie.botany.craftgui;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.botany.api.IColorMix;
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
import binnie.core.util.I18N;

public class ControlColorMixSymbol extends Control implements ITooltip {
	static Texture MutationPlus = new StandardTexture(2, 94, 16, 16, CraftGUITextureSheet.Controls2);
	static Texture MutationArrow = new StandardTexture(20, 94, 32, 16, CraftGUITextureSheet.Controls2);
	IColorMix value;
	int type;

	protected ControlColorMixSymbol(IWidget parent, int x, int y, int type, IColorMix value) {
		super(parent, x, y, 16 + type * 16, 16);
		this.value = value;
		this.type = type;
		addAttribute(Attribute.MouseOver);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderBackground(int guiWidth, int guiHeight) {
		super.onRenderBackground(guiWidth, guiHeight);
		if (type == 0) {
			CraftGUI.render.texture(ControlColorMixSymbol.MutationPlus, Point.ZERO);
		} else {
			CraftGUI.render.texture(ControlColorMixSymbol.MutationArrow, Point.ZERO);
		}
	}

	public void setValue(IColorMix value) {
		this.value = value;
		setColour(0xffffff);
	}

	@Override
	public void getTooltip(Tooltip tooltip) {
		if (type == 1) {
			float chance = value.getChance();
			tooltip.add(I18N.localise("botany.gui.controls.color_mix_symbol.chance", chance));
		}
	}
}
