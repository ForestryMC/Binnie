package binnie.genetics.gui;

import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.ITooltip;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.Tooltip;
import binnie.core.craftgui.WidgetAttribute;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.geometry.IArea;
import binnie.core.craftgui.geometry.IBorder;
import binnie.core.genetics.Tolerance;
import forestry.api.core.EnumTemperature;
import forestry.api.genetics.EnumTolerance;
import java.util.EnumSet;
import net.minecraft.util.EnumChatFormatting;

public abstract class ControlToleranceBar<T extends Enum<T>> extends Control implements ITooltip {
    EnumSet<T> tolerated;
    EnumSet<T> fullSet;
    private Class<T> enumClass;

    public ControlToleranceBar(IWidget parent, float x, float y, float width, float height, Class<T> clss) {
        super(parent, x, y, width, height);
        addAttribute(WidgetAttribute.MOUSE_OVER);
        enumClass = clss;
        tolerated = EnumSet.noneOf(enumClass);
        fullSet = EnumSet.allOf(enumClass);
        if (enumClass == EnumTemperature.class) {
            fullSet.remove(EnumTemperature.NONE);
        }
    }

    @Override
    public void getTooltip(Tooltip tooltip) {
        int types = fullSet.size();
        int type = (int) ((int) getRelativeMousePosition().x() / (getSize().x() / types));
        for (T tol : fullSet) {
            if (tol.ordinal() - ((enumClass == EnumTemperature.class) ? 1 : 0) == type) {
                tooltip.add((tolerated.contains(tol) ? "" : EnumChatFormatting.DARK_GRAY) + getName(tol));
            }
        }
    }

    protected abstract String getName(T p0);

    protected abstract int getColor(T p0);

    @Override
    public void onRenderBackground() {
        CraftGUI.render.gradientRect(getArea(), 0xaaaaaaaa, 0xaaaaaaaa);
        float w = getArea().w() / fullSet.size();
        int t = 0;
        for (T value : fullSet) {
            int col = (tolerated.contains(value) ? 0xff000000 : 0x33000000) + getColor(value);
            IBorder inset = new IBorder(tolerated.contains(value) ? 1.0f : 3.0f);
            CraftGUI.render.gradientRect(new IArea(w * t, 0.0f, w, h()).inset(inset), col, col);
            ++t;
        }
    }

    public void setValues(T value, EnumTolerance enumTol) {
        tolerated.clear();
        Tolerance tol = Tolerance.get(enumTol);
        for (T full : fullSet) {
            if (full.ordinal() > value.ordinal() + tol.getBounds()[1]) {
                continue;
            }
            if (full.ordinal() < value.ordinal() + tol.getBounds()[0]) {
                continue;
            }
            tolerated.add(full);
        }
    }
}
