// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.craftgui.controls.core;

import binnie.core.craftgui.Attribute;
import binnie.core.craftgui.ITooltip;
import binnie.core.craftgui.ITooltipHelp;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.Tooltip;
import binnie.core.craftgui.Widget;
import binnie.core.craftgui.geometry.IArea;
import binnie.core.craftgui.geometry.IPoint;
import binnie.core.craftgui.minecraft.Window;

import java.util.ArrayList;
import java.util.List;

public class Control extends Widget implements ITooltipHelp, ITooltip
{
	List<String> helpStrings;
	List<String> tooltipStrings;
	public int extraLevel;

	public Control(final IWidget parent, final float x, final float y, final float w, final float h) {
		super(parent);
		helpStrings = new ArrayList<String>();
		tooltipStrings = new ArrayList<String>();
		extraLevel = 0;
		setPosition(new IPoint(x, y));
		setSize(new IPoint(w, h));
		initialise();
	}

	public Control(final IWidget parent, final IArea area) {
		this(parent, area.x(), area.y(), area.w(), area.h());
	}

	protected void initialise() {
	}

	@Override
	public void onUpdateClient() {
	}

	public void addHelp(final String string) {
		helpStrings.add(string);
	}

	public void addHelp(final String[] strings) {
		for (final String string : strings) {
			addHelp(string);
		}
	}

	public void addTooltip(final String string) {
		addAttribute(Attribute.MouseOver);
		tooltipStrings.add(string);
	}

	public void addTooltip(final String[] strings) {
		for (final String string : strings) {
			addTooltip(string);
		}
	}

	@Override
	public int getLevel() {
		return extraLevel + super.getLevel();
	}

	@Override
	public void getHelpTooltip(final Tooltip tooltip) {
		tooltip.add(helpStrings);
	}

	@Override
	public void getTooltip(final Tooltip tooltip) {
		tooltip.add(tooltipStrings);
	}

	public Window getWindow() {
		return (Window) getSuperParent();
	}
}
