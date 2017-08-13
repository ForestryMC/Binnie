package binnie.botany.gui.database;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.botany.api.genetics.IColorMix;
import binnie.core.gui.Attribute;
import binnie.core.gui.CraftGUI;
import binnie.core.gui.ITooltip;
import binnie.core.gui.IWidget;
import binnie.core.gui.Tooltip;
import binnie.core.gui.controls.core.Control;
import binnie.core.gui.database.DatabaseConstants;
import binnie.core.gui.geometry.Point;
import binnie.core.gui.resource.textures.Texture;
import binnie.core.gui.resource.textures.CraftGUITextureSheet;
import binnie.core.gui.resource.textures.StandardTexture;
import binnie.core.util.I18N;

public class ControlColorMixSymbol extends Control implements ITooltip {
	static Texture MutationPlus = new StandardTexture(2, 94, 16, 16, CraftGUITextureSheet.CONTROLS);
	static Texture MutationArrow = new StandardTexture(20, 94, 32, 16, CraftGUITextureSheet.CONTROLS);
	IColorMix value;
	int type;

	protected ControlColorMixSymbol(IWidget parent, int x, int y, int type, IColorMix value) {
		super(parent, x, y, 16 + type * 16, 16);
		this.value = value;
		this.type = type;
		addAttribute(Attribute.MOUSE_OVER);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderBackground(int guiWidth, int guiHeight) {
		super.onRenderBackground(guiWidth, guiHeight);
		if (type == 0) {
			CraftGUI.RENDER.texture(ControlColorMixSymbol.MutationPlus, Point.ZERO);
		} else {
			CraftGUI.RENDER.texture(ControlColorMixSymbol.MutationArrow, Point.ZERO);
		}
	}

	@Override
	public void getTooltip(Tooltip tooltip, ITooltipFlag tooltipFlag) {
		if (type == 1) {
			float chance = value.getChance();
			tooltip.add(I18N.localise(DatabaseConstants.BOTANY_CONTROL_KEY + ".color_mix_symbol.chance", chance));
		}
	}
}
