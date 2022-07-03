package binnie.genetics.gui;

import binnie.botany.api.EnumFlowerChromosome;
import binnie.botany.api.IFlower;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.ControlText;
import binnie.core.craftgui.controls.ControlTextCentered;
import binnie.core.craftgui.geometry.IArea;
import binnie.core.craftgui.geometry.IPoint;
import binnie.core.craftgui.geometry.TextJustification;
import binnie.core.genetics.Tolerance;
import binnie.core.util.I18N;
import forestry.api.apiculture.EnumBeeChromosome;
import forestry.api.apiculture.IBee;
import forestry.api.core.EnumHumidity;
import forestry.api.core.EnumTemperature;
import forestry.api.genetics.EnumTolerance;
import forestry.api.genetics.IAlleleTolerance;
import forestry.api.genetics.IIndividual;
import forestry.api.lepidopterology.EnumButterflyChromosome;
import forestry.api.lepidopterology.IButterfly;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;

public class AnalystPageClimate extends ControlAnalystPage {
    public AnalystPageClimate(IWidget parent, IArea area, IIndividual ind) {
        super(parent, area);
        setColor(0x006633);
        EnumTemperature temp = ind.getGenome().getPrimary().getTemperature();
        EnumTolerance tempTol = EnumTolerance.NONE;
        EnumHumidity humid = ind.getGenome().getPrimary().getHumidity();
        EnumTolerance humidTol = EnumTolerance.NONE;

        if (ind instanceof IBee) {
            tempTol = ((IAlleleTolerance) ind.getGenome().getActiveAllele(EnumBeeChromosome.TEMPERATURE_TOLERANCE))
                    .getValue();
            humidTol = ((IAlleleTolerance) ind.getGenome().getActiveAllele(EnumBeeChromosome.HUMIDITY_TOLERANCE))
                    .getValue();
        }

        if (ind instanceof IFlower) {
            tempTol = ((IAlleleTolerance) ind.getGenome().getActiveAllele(EnumFlowerChromosome.TEMPERATURE_TOLERANCE))
                    .getValue();
            humidTol = EnumTolerance.BOTH_5;
        }

        if (ind instanceof IButterfly) {
            tempTol = ((IAlleleTolerance)
                            ind.getGenome().getActiveAllele(EnumButterflyChromosome.TEMPERATURE_TOLERANCE))
                    .getValue();
            humidTol = ((IAlleleTolerance) ind.getGenome().getActiveAllele(EnumButterflyChromosome.HUMIDITY_TOLERANCE))
                    .getValue();
        }

        int y = 4;
        new ControlTextCentered(this, y, EnumChatFormatting.UNDERLINE + getTitle()).setColor(getColor());

        y += 16;
        new ControlText(
                        this,
                        new IArea(4.0f, y, w() - 8.0f, 14.0f),
                        I18N.localise("genetics.gui.analyst.climate.temperature"),
                        TextJustification.MIDDLE_CENTER)
                .setColor(getColor());
        y += 12;
        createTemperatureBar(this, (w() - 100.0f) / 2.0f, y, 100.0f, 10.0f, temp, tempTol);
        y += 16;

        if (!(ind instanceof IFlower)) {
            new ControlText(
                            this,
                            new IArea(4.0f, y, w() - 8.0f, 14.0f),
                            I18N.localise("genetics.gui.analyst.climate.humidity"),
                            TextJustification.MIDDLE_CENTER)
                    .setColor(getColor());
            y += 12;
            createHumidity(this, (w() - 100.0f) / 2.0f, y, 100.0f, 10.0f, humid, humidTol);
            y += 16;
        }
        new ControlText(
                        this,
                        new IArea(4.0f, y, w() - 8.0f, 14.0f),
                        I18N.localise("genetics.gui.analyst.climate.biomes"),
                        TextJustification.MIDDLE_CENTER)
                .setColor(getColor());

        y += 12;
        List<BiomeGenBase> biomes = new ArrayList<>();
        for (BiomeGenBase biome : BiomeGenBase.getBiomeGenArray()) {
            if (biome == null || !BiomeDictionary.isBiomeRegistered(biome) || biome == BiomeGenBase.frozenOcean) {
                continue;
            }

            if (!Tolerance.canTolerate(temp, EnumTemperature.getFromBiome(biome), tempTol)
                    || !Tolerance.canTolerate(humid, EnumHumidity.getFromValue(biome.rainfall), humidTol)) {
                continue;
            }

            boolean match = false;
            for (BiomeGenBase eBiome : biomes) {
                if (biome.biomeName.contains(eBiome.biomeName)
                        && EnumHumidity.getFromValue(eBiome.rainfall) == EnumHumidity.getFromValue(biome.rainfall)
                        && EnumTemperature.getFromBiome(eBiome) == EnumTemperature.getFromBiome(biome)) {
                    match = true;
                }
            }

            if (!match) {
                biomes.add(biome);
            }
        }

        int maxBiomePerLine = (int) ((w() + 2.0f - 16.0f) / 18.0f);
        float biomeListX = (w() - (Math.min(maxBiomePerLine, biomes.size()) * 18 - 2)) / 2.0f;
        int dx = 0;
        int dy = 0;
        for (BiomeGenBase biome2 : biomes) {
            new ControlBiome(this, biomeListX + dx, y + dy, 16.0f, 16.0f, biome2);
            dx += 18;
            if (dx >= 18 * maxBiomePerLine) {
                dx = 0;
                dy += 18;
            }
        }
        setSize(new IPoint(w(), y + dy + 18 + 8));
    }

    protected void createTemperatureBar(
            IWidget parent, float x, float y, float w, float h, EnumTemperature value, EnumTolerance tol) {
        new ControlToleranceBar<EnumTemperature>(parent, x, y, w, h, EnumTemperature.class) {
            @Override
            protected String getName(EnumTemperature value) {
                return value.name;
            }

            @Override
            protected int getColor(EnumTemperature value) {
                return (new int[] {0x00fffb, 0x78bbff, 0x4fff30, 0xffff00, 0xffa200, 0xff0000})[value.ordinal() - 1];
            }
        }.setValues(value, tol);
    }

    protected void createHumidity(
            IWidget parent, float x, float y, float w, float h, EnumHumidity value, EnumTolerance tol) {
        new ControlToleranceBar<EnumHumidity>(parent, x, y, w, h, EnumHumidity.class) {
            @Override
            protected String getName(EnumHumidity value) {
                return value.name;
            }

            @Override
            protected int getColor(EnumHumidity value) {
                return (new int[] {0xffe7a3, 0x1aff00, 0x307cff})[value.ordinal()];
            }
        }.setValues(value, tol);
    }

    @Override
    public String getTitle() {
        return I18N.localise("genetics.gui.analyst.climate");
    }
}
