package binnie.botany.gui.database;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.botany.api.genetics.IFlowerColor;
import binnie.core.gui.Attribute;
import binnie.core.gui.ITooltip;
import binnie.core.api.gui.IWidget;
import binnie.core.gui.Tooltip;
import binnie.core.gui.controls.core.Control;
import binnie.core.gui.controls.core.IControlValue;
import binnie.core.gui.renderer.RenderUtil;

public class ControlColorDisplay extends Control implements IControlValue<IFlowerColor>, ITooltip {
	private IFlowerColor value;

	public ControlColorDisplay(IWidget parent, int x, int y, IFlowerColor value) {
		super(parent, x, y, 16, 16);
		this.value = value;
		addAttribute(Attribute.MOUSE_OVER);
	}

	@Override
	public IFlowerColor getValue() {
		return value;
	}

	@Override
	public void setValue(IFlowerColor value) {
		this.value = value;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderBackground(int guiWidth, int guiHeight) {
		RenderUtil.drawSolidRect(getArea(), -1);
		RenderUtil.drawSolidRect(getArea().inset(1), 0xff000000 + getValue().getColor(false));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getTooltip(Tooltip tooltip, ITooltipFlag tooltipFlag) {
		super.getTooltip(tooltip, tooltipFlag);
		tooltip.add(value.getColorName());
	}
}
