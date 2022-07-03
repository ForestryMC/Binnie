package binnie.extrabees.gui.database;

import binnie.Binnie;
import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.ITooltip;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.Tooltip;
import binnie.core.craftgui.WidgetAttribute;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.geometry.IArea;
import binnie.core.craftgui.resource.minecraft.CraftGUITexture;
import forestry.api.apiculture.IAlleleBeeSpecies;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeRoot;
import forestry.api.core.EnumHumidity;
import forestry.api.core.EnumTemperature;
import forestry.api.genetics.EnumTolerance;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class ControlClimateBar extends Control implements ITooltip {
    protected boolean isHumidity;
    protected List<Integer> tolerated;

    private static final Color[] TEMP_COLORS = new Color[] {
        new Color(0x00fffb),
        new Color(0x78bbff),
        new Color(0x4fff30),
        new Color(0xffff00),
        new Color(0xffa200),
        new Color(0xff0000)
    };
    private static final Color[] HUMID_COLORS =
            new Color[] {new Color(0xffe7a3), new Color(0x1aff00), new Color(0x307cff)};

    public ControlClimateBar(IWidget parent, int x, int y, int width, int height) {
        this(parent, x, y, width, height, false);
    }

    public ControlClimateBar(IWidget parent, int x, int y, int width, int height, boolean humidity) {
        super(parent, x, y, width, height);
        tolerated = new ArrayList<>();
        addAttribute(WidgetAttribute.MOUSE_OVER);
        isHumidity = humidity;
    }

    @Override
    public void getTooltip(Tooltip tooltip) {
        if (tolerated.isEmpty()) {
            return;
        }

        int types = isHumidity ? 3 : 6;
        int type = (int)
                ((int) (getRelativeMousePosition().x() - 1.0f) / ((getSize().x() - 2.0f) / types));
        if (!tolerated.contains(type) || type >= types) {
            return;
        }

        if (isHumidity) {
            tooltip.add(EnumHumidity.values()[type].name);
        } else {
            tooltip.add(EnumTemperature.values()[type + 1].name);
        }
    }

    @Override
    public void onRenderBackground() {
        CraftGUI.render.texture(CraftGUITexture.EnergyBarBack, getArea());
        int types = isHumidity ? 3 : 6;
        int w = (int) ((getSize().x() - 2.0f) / types);
        for (int i = 0; i < types; ++i) {
            int x = i * w;
            if (tolerated.contains(i)) {
                Color color;
                if (isHumidity) {
                    color = HUMID_COLORS[i];
                } else {
                    color = TEMP_COLORS[i];
                }
                CraftGUI.render.solid(new IArea(x + 1, 1.0f, w, getSize().y() - 2.0f), color.getRGB());
            }
        }
        CraftGUI.render.texture(CraftGUITexture.EnergyBarGlass, getArea());
    }

    public void setSpecies(IAlleleBeeSpecies species) {
        tolerated.clear();
        if (species == null) {
            return;
        }

        int main;
        EnumTolerance tolerance;
        IBeeRoot beeRoot = Binnie.Genetics.getBeeRoot();
        IBeeGenome genome = beeRoot.templateAsGenome(beeRoot.getTemplate(species.getUID()));
        if (!isHumidity) {
            main = species.getTemperature().ordinal() - 1;
            tolerance = genome.getToleranceTemp();
        } else {
            main = species.getHumidity().ordinal();
            tolerance = genome.getToleranceHumid();
        }

        tolerated.add(main);
        switch (tolerance) {
            case BOTH_5:
            case UP_5:
                addTolerance(main, 5);
                break;

            case BOTH_4:
            case UP_4:
                addTolerance(main, 4);
                break;

            case BOTH_3:
            case UP_3:
                addTolerance(main, 3);
                break;

            case BOTH_2:
            case UP_2:
                addTolerance(main, 2);
                break;

            case BOTH_1:
            case UP_1:
                addTolerance(main, 1);
                break;
        }

        switch (tolerance) {
            case BOTH_5:
            case DOWN_5:
                addTolerance(main, -5);
                break;

            case BOTH_4:
            case DOWN_4:
                addTolerance(main, -4);
                break;

            case BOTH_3:
            case DOWN_3:
                addTolerance(main, -3);
                break;

            case BOTH_2:
            case DOWN_2:
                addTolerance(main, -2);
                break;

            case BOTH_1:
            case DOWN_1:
                addTolerance(main, -1);
                break;
        }
    }

    private void addTolerance(int main, int offset) {
        offset += main;
        if (main > offset) {
            for (int i = offset; i < main; i++) {
                tolerated.add(i);
            }
        } else {
            for (int i = offset; i > main; i--) {
                tolerated.add(i);
            }
        }
    }
}
