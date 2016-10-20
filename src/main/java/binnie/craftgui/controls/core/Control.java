package binnie.craftgui.controls.core;

import binnie.craftgui.core.*;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.minecraft.Window;

import java.util.ArrayList;
import java.util.List;

public class Control extends Widget implements ITooltipHelp, ITooltip {
    List<String> helpStrings;
    List<String> tooltipStrings;
    public int extraLevel;

    public Control(final IWidget parent, final float x, final float y, final float w, final float h) {
        super(parent);
        this.helpStrings = new ArrayList<String>();
        this.tooltipStrings = new ArrayList<String>();
        this.extraLevel = 0;
        this.setPosition(new IPoint(x, y));
        this.setSize(new IPoint(w, h));
        this.initialise();
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
        this.helpStrings.add(string);
    }

    public void addHelp(final String[] strings) {
        for (final String string : strings) {
            this.addHelp(string);
        }
    }

    public void addTooltip(final String string) {
        this.addAttribute(Attribute.MouseOver);
        this.tooltipStrings.add(string);
    }

    public void addTooltip(final String[] strings) {
        for (final String string : strings) {
            this.addTooltip(string);
        }
    }

    @Override
    public int getLevel() {
        return this.extraLevel + super.getLevel();
    }

    @Override
    public void getHelpTooltip(final Tooltip tooltip) {
        tooltip.add(this.helpStrings);
    }

    @Override
    public void getTooltip(final Tooltip tooltip) {
        tooltip.add(this.tooltipStrings);
    }

    public Window getWindow() {
        return (Window) this.getSuperParent();
    }
}
