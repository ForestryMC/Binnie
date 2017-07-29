package binnie.core.craftgui.controls;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.controls.core.IControlValue;
import binnie.core.craftgui.geometry.Area;
import binnie.core.craftgui.geometry.Point;
import binnie.core.craftgui.geometry.TextJustification;
import binnie.core.craftgui.renderer.RenderUtil;

public class ControlText extends Control implements IControlValue<String> {
	private String text;
	private TextJustification align;

	public ControlText(final IWidget parent, final Point pos, final String text) {
		this(parent, new Area(pos, new Point(500, 0)), text, TextJustification.TOP_LEFT);
	}

	public ControlText(final IWidget parent, final String text, final TextJustification align) {
		this(parent, parent.getArea(), text, align);
	}

	public ControlText(final IWidget parent, final Area area, final String text, final TextJustification align) {
		super(parent, area.pos().x(), area.pos().y(), area.size().x(), area.size().y());
		this.text = text;
		this.align = align;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderBackground(int guiWidth, int guiHeight) {
		RenderUtil.drawText(this.getArea(), this.align, this.text, this.getColor());
	}

	@Override
	public String getValue() {
		return this.text;
	}

	@Override
	public void setValue(final String text) {
		this.text = text;
	}
}
