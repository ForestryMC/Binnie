package binnie.craftgui.controls;

import binnie.craftgui.controls.core.Control;
import binnie.craftgui.controls.core.IControlValue;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.core.geometry.TextJustification;
import binnie.craftgui.core.renderer.RenderUtil;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ControlText extends Control implements IControlValue<String> {
	private String text;
	private TextJustification align;

	public ControlText(final IWidget parent, final IPoint pos, final String text) {
		this(parent, new IArea(pos, new IPoint(500, 0)), text, TextJustification.TopLeft);
	}

	public ControlText(final IWidget parent, final String text, final TextJustification align) {
		this(parent, parent.getArea(), text, align);
	}

	public ControlText(final IWidget parent, final IArea area, final String text, final TextJustification align) {
		super(parent, area.pos().x(), area.pos().y(), area.size().x(), area.size().y());
		this.text = text;
		this.align = align;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderBackground(int guiWidth, int guiHeight) {
		RenderUtil.drawText(this.getArea(), this.align, this.text, this.getColour());
	}

	@Override
	public void setValue(final String text) {
		this.text = text;
	}

	@Override
	public String getValue() {
		return this.text;
	}
}
