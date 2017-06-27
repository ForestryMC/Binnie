package binnie.botany.craftgui;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.botany.api.IFlowerColour;
import binnie.core.craftgui.Attribute;
import binnie.core.craftgui.ITooltip;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.Tooltip;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.controls.core.IControlValue;
import binnie.core.craftgui.renderer.RenderUtil;

public class ControlColorDisplay extends Control implements IControlValue<IFlowerColour>, ITooltip {
	private IFlowerColour value;

	public ControlColorDisplay(final IWidget parent, final int x, final int y, final IFlowerColour value) {
		super(parent, x, y, 16, 16);
		this.value = value;
		this.addAttribute(Attribute.MouseOver);
	}

	@Override
	public IFlowerColour getValue() {
		return this.value;
	}

	@Override
	public void setValue(final IFlowerColour value) {
		this.value = value;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderBackground(int guiWidth, int guiHeight) {
		RenderUtil.drawSolidRect(this.getArea(), -1);
		RenderUtil.drawSolidRect(this.getArea().inset(1), -16777216 + this.getValue().getColor(false));
	}

	@Override
	public void getTooltip(final Tooltip tooltip) {
		super.getTooltip(tooltip);
		tooltip.add(this.value.getColourName());
	}
}
