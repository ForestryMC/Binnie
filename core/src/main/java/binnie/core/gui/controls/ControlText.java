package binnie.core.gui.controls;

import binnie.core.api.gui.IArea;
import binnie.core.api.gui.IPoint;
import binnie.core.gui.geometry.Point;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.api.gui.IWidget;
import binnie.core.gui.controls.core.Control;
import binnie.core.gui.controls.core.IControlValue;
import binnie.core.gui.geometry.Area;
import binnie.core.gui.geometry.TextJustification;
import binnie.core.gui.renderer.RenderUtil;

public class ControlText extends Control implements IControlValue<String> {
	private String text;
	private TextJustification align;

	public ControlText(final IWidget parent, final IPoint pos, final String text) {
		this(parent, new Area(pos, new Point(500, 0)), text, TextJustification.TOP_LEFT);
	}

	public ControlText(final IWidget parent, final IArea area, final String text, final TextJustification align) {
		super(parent, area.pos().xPos(), area.pos().yPos(), area.size().xPos(), area.size().yPos());
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
