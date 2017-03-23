package binnie.craftgui.controls;

import binnie.craftgui.controls.core.Control;
import binnie.craftgui.controls.core.IControlValue;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.Area;
import binnie.craftgui.core.geometry.Point;
import binnie.craftgui.core.geometry.TextJustification;
import binnie.craftgui.core.renderer.RenderUtil;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ControlText extends Control implements IControlValue<String> {
	private String text;
	private TextJustification align;

	public ControlText(final IWidget parent, final Point pos, final String text) {
		this(parent, new Area(pos, new Point(500, 0)), text, TextJustification.TopLeft);
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
