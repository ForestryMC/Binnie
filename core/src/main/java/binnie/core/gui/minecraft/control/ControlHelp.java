package binnie.core.gui.minecraft.control;

import binnie.core.ModId;
import binnie.core.gui.KeyBindings;
import binnie.core.util.I18N;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.gui.Attribute;
import binnie.core.gui.CraftGUI;
import binnie.core.gui.ITooltip;
import binnie.core.api.gui.IWidget;
import binnie.core.gui.Tooltip;
import binnie.core.gui.controls.core.Control;
import binnie.core.gui.resource.textures.CraftGUITexture;

@SideOnly(Side.CLIENT)
public class ControlHelp extends Control implements ITooltip {
	public ControlHelp(final IWidget parent, final int x, final int y) {
		super(parent, x, y, 16, 16);
		this.addAttribute(Attribute.MOUSE_OVER);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderBackground(int guiWidth, int guiHeight) {
		CraftGUI.RENDER.texture(CraftGUITexture.HELP_BUTTON, this.getArea());
	}

	@Override
	public void getTooltip(final Tooltip tooltip, ITooltipFlag tooltipFlag) {
		this.getTooltip(tooltip);
	}

	@Override
	public void getHelpTooltip(final Tooltip tooltip, ITooltipFlag tooltipFlag) {
		this.getTooltip(tooltip);
	}

	private void getTooltip(final Tooltip tooltip) {
		tooltip.setMaxWidth(140);
		tooltip.setType(Tooltip.Type.HELP);
		tooltip.add(I18N.localise(ModId.CORE, "gui.help.title"));
		tooltip.add(I18N.localise(ModId.CORE, "gui.help.desc", KeyBindings.holdForHelpTooltips.getDisplayName()));
	}
}
