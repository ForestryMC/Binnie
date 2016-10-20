package binnie.extrabees.gui.database;

import binnie.Binnie;
import binnie.craftgui.controls.core.Control;
import binnie.craftgui.core.*;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.resource.minecraft.CraftGUITexture;
import forestry.api.apiculture.IAlleleBeeSpecies;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.core.EnumHumidity;
import forestry.api.core.EnumTemperature;
import forestry.api.genetics.EnumTolerance;

import java.util.ArrayList;
import java.util.List;

public class ControlClimateBar extends Control implements ITooltip {
    boolean isHumidity;
    List<Integer> tolerated;
    int[] tempColours;
    int[] humidColours;

    public ControlClimateBar(final IWidget parent, final int x, final int y, final int width, final int height) {
        super(parent, x, y, width, height);
        this.isHumidity = false;
        this.tolerated = new ArrayList<Integer>();
        this.tempColours = new int[]{65531, 7912447, 5242672, 16776960, 16753152, 16711680};
        this.humidColours = new int[]{16770979, 1769216, 3177727};
        this.addAttribute(Attribute.MouseOver);
    }

    public ControlClimateBar(final IWidget parent, final int x, final int y, final int width, final int height, final boolean humidity) {
        super(parent, x, y, width, height);
        this.isHumidity = false;
        this.tolerated = new ArrayList<Integer>();
        this.tempColours = new int[]{65531, 7912447, 5242672, 16776960, 16753152, 16711680};
        this.humidColours = new int[]{16770979, 1769216, 3177727};
        this.addAttribute(Attribute.MouseOver);
        this.isHumidity = true;
    }

    @Override
    public void getTooltip(final Tooltip list) {
        if (this.tolerated.isEmpty()) {
            return;
        }
        final int types = this.isHumidity ? 3 : 6;
        final int type = (int) ((int) (this.getRelativeMousePosition().x() - 1.0f) / ((this.getSize().x() - 2.0f) / types));
        if (!this.tolerated.contains(type)) {
            return;
        }
        if (type < types) {
            if (this.isHumidity) {
                list.add(EnumHumidity.values()[type].name);
            } else {
                list.add(EnumTemperature.values()[type + 1].name);
            }
        }
    }

    @Override
    public void onRenderBackground() {
        CraftGUI.Render.texture(CraftGUITexture.EnergyBarBack, this.getArea());
        final int types = this.isHumidity ? 3 : 6;
        final int w = (int) ((this.getSize().x() - 2.0f) / types);
        for (int i = 0; i < types; ++i) {
            final int x = i * w;
            if (this.tolerated.contains(i)) {
                int colour = 0;
                if (this.isHumidity) {
                    colour = this.humidColours[i];
                } else {
                    colour = this.tempColours[i];
                }
                CraftGUI.Render.solid(new IArea(x + 1, 1.0f, w, this.getSize().y() - 2.0f), colour);
            }
        }
        CraftGUI.Render.texture(CraftGUITexture.EnergyBarGlass, this.getArea());
    }

    public void setSpecies(final IAlleleBeeSpecies species) {
        this.tolerated.clear();
        if (species == null) {
            return;
        }
        int main;
        EnumTolerance tolerance;
        if (!this.isHumidity) {
            main = species.getTemperature().ordinal() - 1;
            final IBeeGenome genome = Binnie.Genetics.getBeeRoot().templateAsGenome(Binnie.Genetics.getBeeRoot().getTemplate(species.getUID()));
            tolerance = genome.getToleranceTemp();
        } else {
            main = species.getHumidity().ordinal();
            final IBeeGenome genome = Binnie.Genetics.getBeeRoot().templateAsGenome(Binnie.Genetics.getBeeRoot().getTemplate(species.getUID()));
            tolerance = genome.getToleranceHumid();
        }
        this.tolerated.add(main);
        switch (tolerance) {
            case BOTH_5:
            case UP_5: {
                this.tolerated.add(main + 5);
            }
            case BOTH_4:
            case UP_4: {
                this.tolerated.add(main + 4);
            }
            case BOTH_3:
            case UP_3: {
                this.tolerated.add(main + 3);
            }
            case BOTH_2:
            case UP_2: {
                this.tolerated.add(main + 2);
            }
            case BOTH_1:
            case UP_1: {
                this.tolerated.add(main + 1);
                break;
            }
        }
        switch (tolerance) {
            case BOTH_5:
            case DOWN_5: {
                this.tolerated.add(main - 5);
            }
            case BOTH_4:
            case DOWN_4: {
                this.tolerated.add(main - 4);
            }
            case BOTH_3:
            case DOWN_3: {
                this.tolerated.add(main - 3);
            }
            case BOTH_2:
            case DOWN_2: {
                this.tolerated.add(main - 2);
            }
            case BOTH_1:
            case DOWN_1: {
                this.tolerated.add(main - 1);
                break;
            }
        }
    }
}
