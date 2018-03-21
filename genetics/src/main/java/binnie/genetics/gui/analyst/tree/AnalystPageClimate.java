package binnie.genetics.gui.analyst.tree;

import java.util.ArrayList;
import java.util.List;

import binnie.core.api.gui.IArea;
import binnie.core.api.gui.ITitledWidget;
import binnie.core.api.gui.IWidget;
import binnie.core.genetics.Tolerance;
import binnie.core.gui.controls.ControlText;
import binnie.core.gui.controls.ControlTextCentered;
import binnie.core.gui.controls.core.Control;
import binnie.core.gui.geometry.Area;
import binnie.core.gui.geometry.Point;
import binnie.core.gui.geometry.TextJustification;
import binnie.core.util.I18N;
import binnie.genetics.api.analyst.IClimatePlugin;
import binnie.genetics.api.analyst.AnalystConstants;
import binnie.genetics.gui.analyst.ControlBiome;
import binnie.core.gui.controls.ControlToleranceBar;
import forestry.api.core.EnumHumidity;
import forestry.api.core.EnumTemperature;
import forestry.api.genetics.EnumTolerance;
import forestry.api.genetics.IIndividual;
import net.minecraft.init.Biomes;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class AnalystPageClimate<T extends IIndividual> extends Control implements ITitledWidget {

	public AnalystPageClimate(IWidget parent, IArea area, T ind, IClimatePlugin<T> plugin) {
		super(parent, area);
		setColor(26163);
		EnumTemperature temp = ind.getGenome().getPrimary().getTemperature();
		EnumTolerance tempTol = plugin.getTemperatureTolerance(ind);
		EnumHumidity humid = ind.getGenome().getPrimary().getHumidity();
		EnumTolerance humidTol = plugin.getHumidityTolerance(ind);
		int y = 4;
		new ControlTextCentered(this, y, TextFormatting.UNDERLINE + getTitle()).setColor(getColor());
		y += 16;
		new ControlText(this, new Area(4, y, getWidth() - 8, 14), I18N.localise(AnalystConstants.CLIMATE_KEY + ".temp"), TextJustification.MIDDLE_CENTER).setColor(getColor());
		y += 12;
		createTemperatureBar(this, (getWidth() - 100) / 2, y, 100, 10, temp, tempTol);
		y += 16;
		if (plugin.showHumiditySection()) {
			new ControlText(this, new Area(4, y, getWidth() - 8, 14), I18N.localise(AnalystConstants.CLIMATE_KEY + ".hum"), TextJustification.MIDDLE_CENTER).setColor(getColor());
			y += 12;
			createHumidity(this, (getWidth() - 100) / 2, y, 100, 10, humid, humidTol);
			y += 16;
		}
		new ControlText(this, new Area(4, y, getWidth() - 8, 14), I18N.localise(AnalystConstants.CLIMATE_KEY + ".biomes"), TextJustification.MIDDLE_CENTER).setColor(getColor());
		y += 12;
		List<Biome> biomes = new ArrayList<>();
		for (Biome biome : ForgeRegistries.BIOMES.getValuesCollection()) { //TODO check
			if (biome != null &&
				biome != Biomes.FROZEN_OCEAN &&
				Tolerance.canTolerate(temp, EnumTemperature.getFromBiome(biome), tempTol) &&
				Tolerance.canTolerate(humid, EnumHumidity.getFromValue(biome.getRainfall()), humidTol)) {
				boolean match = false;
				for (Biome eBiome : biomes) {
					if (biome.getBiomeName().contains(eBiome.getBiomeName()) && EnumHumidity.getFromValue(eBiome.getRainfall()) == EnumHumidity.getFromValue(biome.getRainfall()) && EnumTemperature.getFromBiome(eBiome) == EnumTemperature.getFromBiome(biome)) {
						match = true;
					}
				}
				if (!match) {
					biomes.add(biome);
				}
			}
		}
		int maxBiomePerLine = (getWidth() + 2 - 16) / 18;
		int biomeListX = (getWidth() - (Math.min(maxBiomePerLine, biomes.size()) * 18 - 2)) / 2;
		int dx = 0;
		int dy = 0;
		for (Biome biome2 : biomes) {
			new ControlBiome(this, biomeListX + dx, y + dy, 16, 16, biome2);
			dx += 18;
			if (dx >= 18 * maxBiomePerLine) {
				dx = 0;
				dy += 18;
			}
		}
		setSize(new Point(getWidth(), y + dy + 18 + 8));
	}

	protected void createTemperatureBar(IWidget parent, int x, int y, int w, int h, EnumTemperature value, forestry.api.genetics.EnumTolerance tol) {
		new TemperatureBar(parent, x, y, w, h).setValues(value, tol);
	}

	protected void createHumidity(IWidget parent, int x, int y, int w, int h, EnumHumidity value, forestry.api.genetics.EnumTolerance tol) {
		new HumidityBar(parent, x, y, w, h).setValues(value, tol);
	}

	@Override
	public String getTitle() {
		return I18N.localise(AnalystConstants.CLIMATE_KEY + ".title");
	}

	private static class TemperatureBar extends ControlToleranceBar<EnumTemperature> {
		public TemperatureBar(IWidget parent, int x, int y, int w, int h) {
			super(parent, x, y, w, h, EnumTemperature.class);
		}

		@Override
		protected String getName(EnumTemperature value) {
			return value.name;
		}

		@Override
		protected int getColour(EnumTemperature value) {
			return (new int[]{65531, 7912447, 5242672, 16776960, 16753152, 16711680})[value.ordinal() - 1];
		}
	}

	private static class HumidityBar extends ControlToleranceBar<EnumHumidity> {
		public HumidityBar(IWidget parent, int x, int y, int w, int h) {
			super(parent, x, y, w, h, EnumHumidity.class);
		}

		@Override
		protected String getName(EnumHumidity value) {
			return value.name;
		}

		@Override
		protected int getColour(EnumHumidity value) {
			return (new int[]{16770979, 1769216, 3177727})[value.ordinal()];
		}
	}
}
