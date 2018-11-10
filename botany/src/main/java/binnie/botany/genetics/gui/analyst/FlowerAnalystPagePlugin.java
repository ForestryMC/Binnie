package binnie.botany.genetics.gui.analyst;

import java.util.List;

import net.minecraft.util.text.TextFormatting;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.genetics.EnumTolerance;
import forestry.api.genetics.IAlleleTolerance;
import forestry.api.genetics.IIndividual;

import binnie.botany.api.genetics.EnumFlowerChromosome;
import binnie.botany.api.genetics.IFlower;
import binnie.botany.api.genetics.IFlowerGenome;
import binnie.core.Constants;
import binnie.core.api.gui.IArea;
import binnie.core.api.gui.ITitledWidget;
import binnie.core.api.gui.IWidget;
import binnie.core.gui.controls.ControlTextCentered;
import binnie.core.util.I18N;
import binnie.core.util.TimeUtil;
import binnie.genetics.api.analyst.AnalystConstants;
import binnie.genetics.api.analyst.IAnalystManager;
import binnie.genetics.api.analyst.IAnalystPagePlugin;
import binnie.genetics.api.analyst.IBiologyPlugin;
import binnie.genetics.api.analyst.IClimatePlugin;

public class FlowerAnalystPagePlugin implements IAnalystPagePlugin<IFlower> {
	@Override
	public boolean handles(IIndividual individual) {
		return individual instanceof IFlower;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addAnalystPages(IFlower individual, IWidget parent, IArea pageSize, List<ITitledWidget> analystPages, IAnalystManager analystManager) {
		analystPages.add(analystManager.createClimatePage(parent, pageSize, individual, new ClimatePlugin()));
		analystPages.add(new AnalystPageSoil(parent, pageSize, individual));
		analystPages.add(analystManager.createBiologyPage(parent, pageSize, individual, new BiologyPlugin()));
		analystPages.add(new AnalystPageAppearance(parent, pageSize, individual));
	}

	private static class BiologyPlugin implements IBiologyPlugin<IFlower> {
		@Override
		@SideOnly(Side.CLIENT)
		public int addBiologyPages(IFlower flower, IWidget parent, int y, IAnalystManager analystManager) {
			y += 10;
			int butterflySpawn2 = Math.round(Constants.SPAWN_KOEF / (flower.getGenome().getSappiness() * 0.2f));
			new ControlTextCentered(parent, y, I18N.localise(AnalystConstants.BIOLOGY_KEY + ".mothSpawn", TimeUtil.getTimeString(butterflySpawn2)))
				.setColor(parent.getColor());
			y += 30;

			int fertility = flower.getGenome().getFertility();
			float chanceDispersal = 0.8f;
			chanceDispersal += 0.2f * fertility;
			if (chanceDispersal > 1.0f) {
				chanceDispersal = 1.0f;
			}

			float chancePollinate = 0.6f;
			chancePollinate += 0.25f * fertility;
			if (chancePollinate > 1.0f) {
				chancePollinate = 1.0f;
			}

			float maxAge = flower.getMaxAge();
			float ageChance = flower.getGenome().getAgeChance();
			float dispersalTime = Constants.SPAWN_KOEF / chanceDispersal;
			float pollinateTime = Constants.SPAWN_KOEF / chancePollinate;
			float lifespan2 = maxAge * 20.0f * 68.27f / ageChance;
			float floweringLifespan = (maxAge - 1) * 20.0f * 68.27f / ageChance - Constants.SPAWN_KOEF;

			new ControlTextCentered(parent, y, I18N.localise(AnalystConstants.BIOLOGY_KEY + ".averageLifespan"))
				.setColor(parent.getColor());
			y += 12;

			new ControlTextCentered(parent, y, TextFormatting.BOLD + TimeUtil.getMCDayString(lifespan2))
				.setColor(parent.getColor());
			y += 22;

			new ControlTextCentered(parent, y, I18N.localise(AnalystConstants.BIOLOGY_KEY + ".seedDispersal"))
				.setColor(parent.getColor());
			y += 12;

			new ControlTextCentered(parent, y, TextFormatting.ITALIC + I18N.localise(AnalystConstants.BIOLOGY_KEY + ".perLifetime", (int) (floweringLifespan / dispersalTime)))
				.setColor(parent.getColor());
			y += 22;

			new ControlTextCentered(parent, y, I18N.localise(AnalystConstants.BIOLOGY_KEY + ".pollination"))
				.setColor(parent.getColor());
			y += 12;

			new ControlTextCentered(parent, y, TextFormatting.ITALIC + I18N.localise(AnalystConstants.BIOLOGY_KEY + ".perLifetime", (int) (floweringLifespan / pollinateTime)))
				.setColor(parent.getColor());
			y += 22;
			return y;
		}
	}

	private static class ClimatePlugin implements IClimatePlugin<IFlower> {

		@Override
		public EnumTolerance getTemperatureTolerance(IFlower flower) {
			IFlowerGenome genome = flower.getGenome();
			IAlleleTolerance tolerance = (IAlleleTolerance) genome.getActiveAllele(EnumFlowerChromosome.TEMPERATURE_TOLERANCE);
			return tolerance.getValue();
		}

		@Override
		public EnumTolerance getHumidityTolerance(IFlower flower) {
			return EnumTolerance.BOTH_5;
		}

		@Override
		public boolean showHumiditySection() {
			return false;
		}
	}
}
