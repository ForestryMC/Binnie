package binnie.core.machines.storage;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.craftgui.Attribute;
import binnie.core.craftgui.ITooltip;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.Tooltip;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.controls.core.IControlValue;
import binnie.core.craftgui.minecraft.EnumColor;
import binnie.core.craftgui.renderer.RenderUtil;

class ControlColourSelector extends Control implements ITooltip, IControlValue<EnumColor> {
	private EnumColor value;

	public ControlColourSelector(final IWidget parent, final int x, final int y, final int w, final int h, final EnumColor value) {
		super(parent, x, y, w, h);
		this.setValue(value);
		this.addAttribute(Attribute.MouseOver);
	}

	@Override
	public void getTooltip(final Tooltip tooltip) {
		tooltip.add(this.value.toString());
	}

	@Override
	public EnumColor getValue() {
		return this.value;
	}

	@Override
	public void setValue(final EnumColor value) {
		this.value = value;
		this.setColour(this.getValue().getColour());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderBackground(int guiWidth, int guiHeight) {
		super.onRenderBackground(guiWidth, guiHeight);
		RenderUtil.drawGradientRect(this.getArea(), -16777216 + this.value.getColour(), -16777216 + this.value.getColour());
	}
}
