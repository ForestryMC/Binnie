package binnie.core.craftgui.controls.core;

import binnie.core.craftgui.ITooltip;
import binnie.core.craftgui.ITooltipHelp;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.Tooltip;
import binnie.core.craftgui.Widget;
import binnie.core.craftgui.WidgetAttribute;
import binnie.core.craftgui.geometry.IArea;
import binnie.core.craftgui.geometry.IPoint;
import binnie.core.craftgui.minecraft.Window;
import java.util.ArrayList;
import java.util.List;

public class Control extends Widget implements ITooltipHelp, ITooltip {
    public int extraLevel;

    protected List<String> helpStrings;
    protected List<String> tooltipStrings;

    public Control(IWidget parent, float x, float y, float w, float h) {
        super(parent);
        helpStrings = new ArrayList<>();
        tooltipStrings = new ArrayList<>();
        extraLevel = 0;
        setPosition(new IPoint(x, y));
        setSize(new IPoint(w, h));
        initialise();
    }

    public Control(IWidget parent, IArea area) {
        this(parent, area.x(), area.y(), area.w(), area.h());
    }

    protected void initialise() {
        // ignored
    }

    @Override
    public void onUpdateClient() {
        // ignored
    }

    public void addHelp(String string) {
        helpStrings.add(string);
    }

    public void addHelp(String[] strings) {
        for (String string : strings) {
            addHelp(string);
        }
    }

    public void addTooltip(String string) {
        addAttribute(WidgetAttribute.MOUSE_OVER);
        tooltipStrings.add(string);
    }

    @Override
    public int getLevel() {
        return extraLevel + super.getLevel();
    }

    @Override
    public void getHelpTooltip(Tooltip tooltip) {
        tooltip.add(helpStrings);
    }

    @Override
    public void getTooltip(Tooltip tooltip) {
        tooltip.add(tooltipStrings);
    }

    public Window getWindow() {
        return (Window) getSuperParent();
    }
}
