package binnie.botany.craftgui;

import binnie.botany.api.IFlowerColour;
import binnie.core.craftgui.Attribute;
import binnie.core.craftgui.ITooltip;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.Tooltip;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.controls.core.IControlValue;
import binnie.core.craftgui.renderer.RenderUtil;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ControlColorDisplay extends Control implements IControlValue<IFlowerColour>, ITooltip {
	private IFlowerColour value;

	public ControlColorDisplay(IWidget parent, int x, int y, IFlowerColour value) {
		super(parent, x, y, 16, 16);
		this.value = value;
		addAttribute(Attribute.MouseOver);
	}

	@Override
	public IFlowerColour getValue() {
		return value;
	}

	@Override
	public void setValue(IFlowerColour value) {
		this.value = value;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderBackground(int guiWidth, int guiHeight) {
		RenderUtil.drawSolidRect(getArea(), -1);
		RenderUtil.drawSolidRect(getArea().inset(1), 0xff000000 + getValue().getColor(false));
	}

	@Override
	public void getTooltip(Tooltip tooltip) {
		super.getTooltip(tooltip);
		tooltip.add(value.getColorName());
	}
}
