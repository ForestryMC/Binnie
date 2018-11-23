package binnie.core.machines.storage;

import net.minecraft.client.util.ITooltipFlag;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.api.gui.IWidget;
import binnie.core.gui.Attribute;
import binnie.core.gui.ITooltip;
import binnie.core.gui.Tooltip;
import binnie.core.gui.controls.core.Control;
import binnie.core.gui.controls.core.IControlValue;
import binnie.core.gui.minecraft.EnumColor;
import binnie.core.gui.renderer.RenderUtil;

class ControlColourSelector extends Control implements ITooltip, IControlValue<EnumColor> {
	private EnumColor value;

	public ControlColourSelector(IWidget parent, int x, int y, int w, int h, EnumColor value) {
		super(parent, x, y, w, h);
		this.setValue(value);
		this.addAttribute(Attribute.MOUSE_OVER);
	}

	@Override
	public void getTooltip(Tooltip tooltip, ITooltipFlag tooltipFlag) {
		tooltip.add(this.value.toString());
	}

	@Override
	public EnumColor getValue() {
		return this.value;
	}

	@Override
	public void setValue(EnumColor value) {
		this.value = value;
		this.setColor(this.getValue().getColor());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderBackground(int guiWidth, int guiHeight) {
		super.onRenderBackground(guiWidth, guiHeight);
		RenderUtil.drawGradientRect(this.getArea(), -16777216 + this.value.getColor(), -16777216 + this.value.getColor());
	}
}
