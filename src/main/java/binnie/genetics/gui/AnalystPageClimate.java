package binnie.genetics.gui;

import binnie.botany.api.EnumFlowerChromosome;
import binnie.botany.api.IFlower;
import binnie.core.genetics.Tolerance;
import binnie.craftgui.controls.ControlText;
import binnie.craftgui.controls.ControlTextCentered;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.core.geometry.TextJustification;
import forestry.api.apiculture.EnumBeeChromosome;
import forestry.api.apiculture.IBee;
import forestry.api.core.EnumHumidity;
import forestry.api.core.EnumTemperature;
import forestry.api.genetics.EnumTolerance;
import forestry.api.genetics.IAlleleTolerance;
import forestry.api.genetics.IIndividual;
import forestry.api.lepidopterology.EnumButterflyChromosome;
import forestry.api.lepidopterology.IButterfly;
import net.minecraft.init.Biomes;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

import java.util.ArrayList;
import java.util.List;

public class AnalystPageClimate extends ControlAnalystPage {
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
		new ControlText(this, new IArea(4, y, this.w() - 8, 14), "Temp. Tolerance", TextJustification.MiddleCenter).setColour(this.getColour());
		y += 12;
		this.createTemperatureBar(this, (this.w() - 100) / 2, y, 100, 10, temp, tempTol);
		y += 16;
		if (!(ind instanceof IFlower)) {
			new ControlText(this, new IArea(4, y, this.w() - 8, 14), "Humidity Tolerance", TextJustification.MiddleCenter).setColour(this.getColour());
			y += 12;
			this.createHumidity(this, (this.w() - 100) / 2, y, 100, 10, humid, humidTol);
			y += 16;
		}
		new ControlText(this, new IArea(4, y, this.w() - 8, 14), "Biomes", TextJustification.MiddleCenter).setColour(this.getColour());
		y += 12;
		final List<Biome> biomes = new ArrayList<>();
		for (final Biome biome : Biome.EXPLORATION_BIOMES_LIST) { //TODO check
			if (biome != null) {
				if (BiomeDictionary.isBiomeRegistered(biome)) {
					if (biome != Biomes.FROZEN_OCEAN) {
						if (Tolerance.canTolerate(temp, EnumTemperature.getFromBiome(biome), tempTol)) {
							if (Tolerance.canTolerate(humid, EnumHumidity.getFromValue(biome.getRainfall()), humidTol)) {
								boolean match = false;
								for (final Biome eBiome : biomes) {
									if (biome.getBiomeName().contains(eBiome.getBiomeName()) && EnumHumidity.getFromValue(eBiome.getRainfall()) == EnumHumidity.getFromValue(biome.getRainfall()) && EnumTemperature.getFromBiome(eBiome) == EnumTemperature.getFromBiome(biome)) {
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
		final int maxBiomePerLine = (this.w() + 2 - 16) / 18;
		final int biomeListX = (this.w() - (Math.min(maxBiomePerLine, biomes.size()) * 18 - 2)) / 2;
		int dx = 0;
		int dy = 0;
		for (final Biome biome2 : biomes) {
			new ControlBiome(this, biomeListX + dx, y + dy, 16, 16, biome2);
			dx += 18;
			if (dx >= 18 * maxBiomePerLine) {
				dx = 0;
				dy += 18;
			}
		}
		this.setSize(new IPoint(this.w(), y + dy + 18 + 8));
	}

	protected void createTemperatureBar(final IWidget parent, final int x, final int y, final int w, final int h, final EnumTemperature value, final EnumTolerance tol) {
		new ControlToleranceBar<EnumTemperature>(parent, x, y, w, h, EnumTemperature.class) {
			@Override
			protected String getName(final EnumTemperature value) {
				return value.name;
			}

			@Override
			protected int getColour(final EnumTemperature value) {
				return (new int[]{65531, 7912447, 5242672, 16776960, 16753152, 16711680})[value.ordinal() - 1];
			}
		}.setValues(value, tol);
	}

	protected void createHumidity(final IWidget parent, final int x, final int y, final int w, final int h, final EnumHumidity value, final EnumTolerance tol) {
		new ControlToleranceBar<EnumHumidity>(parent, x, y, w, h, EnumHumidity.class) {
			@Override
			protected String getName(final EnumHumidity value) {
				return value.name;
			}

			@Override
			protected int getColour(final EnumHumidity value) {
				return (new int[]{16770979, 1769216, 3177727})[value.ordinal()];
			}
		}.setValues(value, tol);
	}

	@Override
	public String getTitle() {
		return "Climate";
	}
}
