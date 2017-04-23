// 
// Decompiled by Procyon v0.5.30
// 

package binnie.genetics.gui;

import java.util.List;
import binnie.core.craftgui.geometry.IPoint;
import forestry.api.core.EnumHumidity;
import binnie.core.genetics.Tolerance;
import forestry.api.core.EnumTemperature;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraft.world.biome.BiomeGenBase;
import java.util.ArrayList;
import binnie.core.craftgui.controls.ControlText;
import binnie.core.craftgui.geometry.TextJustification;
import binnie.core.craftgui.controls.ControlTextCentered;
import forestry.api.lepidopterology.EnumButterflyChromosome;
import forestry.api.lepidopterology.IButterfly;
import binnie.botany.api.EnumFlowerChromosome;
import binnie.botany.api.IFlower;
import forestry.api.apiculture.EnumBeeChromosome;
import forestry.api.genetics.IAlleleTolerance;
import forestry.api.apiculture.IBee;
import forestry.api.genetics.EnumTolerance;
import forestry.api.genetics.IIndividual;
import binnie.core.craftgui.geometry.IArea;
import binnie.core.craftgui.IWidget;

public class AnalystPageClimate extends ControlAnalystPage
{
	public AnalystPageClimate(final IWidget parent, final IArea area, final IIndividual ind) {
		super(parent, area);
		this.setColour(26163);
		final EnumTemperature temp = ind.getGenome().getPrimary().getTemperature();
		EnumTolerance tempTol = EnumTolerance.NONE;
		final EnumHumidity humid = ind.getGenome().getPrimary().getHumidity();
		EnumTolerance humidTol = EnumTolerance.NONE;
		if (ind instanceof IBee) {
			tempTol = ((IAlleleTolerance) ind.getGenome().getActiveAllele(EnumBeeChromosome.TEMPERATURE_TOLERANCE)).getValue();
			humidTol = ((IAlleleTolerance) ind.getGenome().getActiveAllele(EnumBeeChromosome.HUMIDITY_TOLERANCE)).getValue();
		}
		if (ind instanceof IFlower) {
			tempTol = ((IAlleleTolerance) ind.getGenome().getActiveAllele(EnumFlowerChromosome.TEMPERATURE_TOLERANCE)).getValue();
			humidTol = EnumTolerance.BOTH_5;
		}
		if (ind instanceof IButterfly) {
			tempTol = ((IAlleleTolerance) ind.getGenome().getActiveAllele(EnumButterflyChromosome.TEMPERATURE_TOLERANCE)).getValue();
			humidTol = ((IAlleleTolerance) ind.getGenome().getActiveAllele(EnumButterflyChromosome.HUMIDITY_TOLERANCE)).getValue();
		}
		int y = 4;
		new ControlTextCentered(this, y, "§nClimate").setColour(this.getColour());
		y += 16;
		new ControlText(this, new IArea(4.0f, y, this.w() - 8.0f, 14.0f), "Temp. Tolerance", TextJustification.MiddleCenter).setColour(this.getColour());
		y += 12;
		this.createTemperatureBar(this, (this.w() - 100.0f) / 2.0f, y, 100.0f, 10.0f, temp, tempTol);
		y += 16;
		if (!(ind instanceof IFlower)) {
			new ControlText(this, new IArea(4.0f, y, this.w() - 8.0f, 14.0f), "Humidity Tolerance", TextJustification.MiddleCenter).setColour(this.getColour());
			y += 12;
			this.createHumidity(this, (this.w() - 100.0f) / 2.0f, y, 100.0f, 10.0f, humid, humidTol);
			y += 16;
		}
		new ControlText(this, new IArea(4.0f, y, this.w() - 8.0f, 14.0f), "Biomes", TextJustification.MiddleCenter).setColour(this.getColour());
		y += 12;
		final List<BiomeGenBase> biomes = new ArrayList<BiomeGenBase>();
		for (final BiomeGenBase biome : BiomeGenBase.getBiomeGenArray()) {
			if (biome != null) {
				if (BiomeDictionary.isBiomeRegistered(biome)) {
					if (biome != BiomeGenBase.frozenOcean) {
						if (Tolerance.canTolerate(temp, EnumTemperature.getFromBiome(biome), tempTol)) {
							if (Tolerance.canTolerate(humid, EnumHumidity.getFromValue(biome.rainfall), humidTol)) {
								boolean match = false;
								for (final BiomeGenBase eBiome : biomes) {
									if (biome.biomeName.contains(eBiome.biomeName) && EnumHumidity.getFromValue(eBiome.rainfall) == EnumHumidity.getFromValue(biome.rainfall) && EnumTemperature.getFromBiome(eBiome) == EnumTemperature.getFromBiome(biome)) {
										match = true;
									}
								}
								if (!match) {
									biomes.add(biome);
								}
							}
						}
					}
				}
			}
		}
		final int maxBiomePerLine = (int) ((this.w() + 2.0f - 16.0f) / 18.0f);
		final float biomeListX = (this.w() - (Math.min(maxBiomePerLine, biomes.size()) * 18 - 2)) / 2.0f;
		int dx = 0;
		int dy = 0;
		for (final BiomeGenBase biome2 : biomes) {
			new ControlBiome(this, biomeListX + dx, y + dy, 16.0f, 16.0f, biome2);
			dx += 18;
			if (dx >= 18 * maxBiomePerLine) {
				dx = 0;
				dy += 18;
			}
		}
		this.setSize(new IPoint(this.w(), y + dy + 18 + 8));
	}

	protected void createTemperatureBar(final IWidget parent, final float x, final float y, final float w, final float h, final EnumTemperature value, final EnumTolerance tol) {
		new ControlToleranceBar<EnumTemperature>(parent, x, y, w, h, EnumTemperature.class) {
			@Override
			protected String getName(final EnumTemperature value) {
				return value.name;
			}

			@Override
			protected int getColour(final EnumTemperature value) {
				return (new int[] { 65531, 7912447, 5242672, 16776960, 16753152, 16711680 })[value.ordinal() - 1];
			}
		}.setValues(value, tol);
	}

	protected void createHumidity(final IWidget parent, final float x, final float y, final float w, final float h, final EnumHumidity value, final EnumTolerance tol) {
		new ControlToleranceBar<EnumHumidity>(parent, x, y, w, h, EnumHumidity.class) {
			@Override
			protected String getName(final EnumHumidity value) {
				return value.name;
			}

			@Override
			protected int getColour(final EnumHumidity value) {
				return (new int[] { 16770979, 1769216, 3177727 })[value.ordinal()];
			}
		}.setValues(value, tol);
	}

	@Override
	public String getTitle() {
		return "Climate";
	}
}
