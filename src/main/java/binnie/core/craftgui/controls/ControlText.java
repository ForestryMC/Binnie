// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.craftgui.controls;

import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.controls.core.IControlValue;
import binnie.core.craftgui.geometry.IArea;
import binnie.core.craftgui.geometry.IPoint;
import binnie.core.craftgui.geometry.TextJustification;

public class ControlText extends Control implements IControlValue<String>
{
	private String text;
	private TextJustification align;

	public ControlText(final IWidget parent, final IPoint pos, final String text) {
		this(parent, new IArea(pos, new IPoint(500.0f, 0.0f)), text, TextJustification.TopLeft);
	}

	public ControlText(final IWidget parent, final String text, final TextJustification align) {
		this(parent, parent.getArea(), text, align);
	}

	public ControlText(final IWidget parent, final IArea area, final String text, final TextJustification align) {
		super(parent, area.pos().x(), area.pos().y(), area.size().x(), area.size().y());
		setValue(text);
		this.align = align;
	}

	@Override
	public void onRenderBackground() {
		CraftGUI.Render.text(getArea(), align, text, getColour());
	}

	@Override
	public void setValue(final String text) {
		this.text = text;
	}

	@Override
	public String getValue() {
		return text;
	}
}
