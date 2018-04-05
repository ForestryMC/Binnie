package binnie.extratrees.genetics.gui.analyst;

import java.util.List;

import binnie.core.Constants;
import binnie.core.api.gui.IArea;
import binnie.core.api.gui.ITitledWidget;
import binnie.core.api.gui.IWidget;
import binnie.core.gui.controls.ControlText;
import binnie.core.gui.controls.ControlTextCentered;
import binnie.core.gui.geometry.Area;
import binnie.core.gui.geometry.TextJustification;
import binnie.core.gui.minecraft.control.ControlIconDisplay;
import binnie.core.util.I18N;
import binnie.core.util.TimeUtil;
import binnie.extratrees.ExtraTrees;
import binnie.genetics.api.analyst.IAnalystIcons;
import binnie.genetics.api.analyst.IAnalystManager;
import binnie.genetics.api.analyst.IAnalystPagePlugin;
import binnie.genetics.api.analyst.IBehaviourPlugin;
import binnie.genetics.api.analyst.IBiologyPlugin;
import binnie.genetics.api.analyst.IClimatePlugin;
import binnie.genetics.api.analyst.AnalystConstants;
import forestry.api.genetics.EnumTolerance;
import forestry.api.genetics.IAlleleTolerance;
import forestry.api.genetics.IIndividual;
import forestry.api.lepidopterology.EnumButterflyChromosome;
import forestry.api.lepidopterology.IAlleleButterflyEffect;
import forestry.api.lepidopterology.IButterfly;
import forestry.api.lepidopterology.IButterflyGenome;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ButterflyAnalystPagePlugin implements IAnalystPagePlugin<IButterfly> {
	@Override
	public boolean handles(IIndividual individual) {
		return individual instanceof IButterfly;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addAnalystPages(IButterfly individual, IWidget parent, IArea pageSize, List<ITitledWidget> analystPages, IAnalystManager analystManager) {
		analystPages.add(analystManager.createClimatePage(parent, pageSize, individual, new ClimatePlugin()));
		analystPages.add(new AnalystPageSpecimen(parent, pageSize, individual));
		analystPages.add(analystManager.createBiologyPage(parent, pageSize, individual, new BiologyPlugin()));
		analystPages.add(analystManager.createBehaviorPage(parent, pageSize, individual, new BehaviorPlugin()));
	}

	private static class BiologyPlugin implements IBiologyPlugin<IButterfly> {
		@Override
		@SideOnly(Side.CLIENT)
		public int addBiologyPages(IButterfly butterfly, IWidget parent, int y, IAnalystManager analystManager) {
			IAnalystIcons icons = analystManager.getIcons();
			if (butterfly.getGenome().getNocturnal()) {
				new ControlIconDisplay(parent, (parent.getWidth() - 64) / 2, y, icons.getIconAllDay())
						.addTooltip(I18N.localise(AnalystConstants.BIOLOGY_KEY + ".allDay"));
			} else if (butterfly.getGenome().getPrimary().isNocturnal()) {
				new ControlIconDisplay(parent, (parent.getWidth() - 64) / 2, y, icons.getIconNight())
						.addTooltip(I18N.localise(AnalystConstants.BIOLOGY_KEY + ".night"));
			} else {
				new ControlIconDisplay(parent, (parent.getWidth() - 64) / 2, y, icons.getIconDaytime())
						.addTooltip(I18N.localise(AnalystConstants.BIOLOGY_KEY + ".day"));
			}

			if (!butterfly.getGenome().getTolerantFlyer()) {
				new ControlIconDisplay(parent, (parent.getWidth() - 64) / 2 + 24, y, icons.getIconNoRain())
						.addTooltip(I18N.localise(AnalystConstants.BIOLOGY_KEY + ".notRain"));
			} else {
				new ControlIconDisplay(parent, (parent.getWidth() - 64) / 2 + 24, y, icons.getIconRain())
						.addTooltip(I18N.localise(AnalystConstants.BIOLOGY_KEY + ".rain"));
			}

			if (butterfly.getGenome().getFireResist()) {
				new ControlIconDisplay(parent, (parent.getWidth() - 64) / 2 + 48, y, icons.getIconNoFire())
						.addTooltip(I18N.localise(AnalystConstants.BIOLOGY_KEY + ".nonflammable"));
			} else {
				new ControlIconDisplay(parent, (parent.getWidth() - 64) / 2 + 48, y, icons.getIconFire())
						.addTooltip(I18N.localise(AnalystConstants.BIOLOGY_KEY + ".flammable"));
			}
			y += 30;

			int fertility = butterfly.getGenome().getFertility();
			if (fertility > 1) {
				new ControlTextCentered(parent, y, I18N.localise(AnalystConstants.BIOLOGY_KEY + ".fertility.moths", fertility))
						.setColor(parent.getColor());
			} else {
				new ControlTextCentered(parent, y, I18N.localise(AnalystConstants.BIOLOGY_KEY + ".fertility.moth"))
						.setColor(parent.getColor());
			}

			y += 32;
			float caterpillarMatureTime = Constants.SPAWN_KOEF * Math.round(butterfly.getGenome().getLifespan() / (butterfly.getGenome().getFertility() * 2));
			new ControlTextCentered(parent, y, I18N.localise(AnalystConstants.BIOLOGY_KEY + ".caterpillarGestation"))
					.setColor(parent.getColor());

			y += 12;
			new ControlTextCentered(parent, y, TextFormatting.BOLD + TimeUtil.getMCDayString(caterpillarMatureTime))
					.setColor(parent.getColor());

			y += 22;
			int speed = (int) (20.0f * butterfly.getGenome().getSpeed());
			new ControlTextCentered(parent, y, I18N.localise(AnalystConstants.BIOLOGY_KEY + ".flightSpeed"))
					.setColor(parent.getColor());

			y += 12;
			new ControlTextCentered(parent, y, TextFormatting.BOLD + I18N.localise(AnalystConstants.BIOLOGY_KEY + ".blocksPerSec", speed))
					.setColor(parent.getColor());
			y += 22;
			return y;
		}
	}

	private static class BehaviorPlugin implements IBehaviourPlugin<IButterfly> {

		@Override
		public int addBehaviourPages(IButterfly individual, IWidget parent, int y) {
			IButterflyGenome genome = individual.getGenome();
			String metabolismAlleleName = ExtraTrees.mothBreedingSystem.getAlleleName(EnumButterflyChromosome.METABOLISM, genome.getActiveAllele(EnumButterflyChromosome.METABOLISM));
			new ControlTextCentered(parent, y, I18N.localise(AnalystConstants.BEHAVIOUR_KEY + ".metabolism", metabolismAlleleName))
					.setColor(parent.getColor());
			y += 20;

			new ControlTextCentered(parent, y, I18N.localise(AnalystConstants.BEHAVIOUR_KEY + ".pollinatesNearby") + '\n' + genome.getFlowerProvider().getDescription())
					.setColor(parent.getColor());
			y += 20;

			new ControlTextCentered(parent, y, I18N.localise(AnalystConstants.BEHAVIOUR_KEY + ".everyTime", TimeUtil.getTimeString(1500)))
					.setColor(parent.getColor());
			y += 22;

			IAlleleButterflyEffect effect2 = genome.getEffect();
			if (!effect2.getUID().contains("None")) {
				String effectDesc2 = I18N.localiseOrBlank("binniecore.allele." + effect2.getUID() + ".desc");
				String loc2 = effectDesc2.isEmpty()
						? I18N.localise(AnalystConstants.BEHAVIOUR_KEY + ".effect", effect2.getAlleleName())
						: effectDesc2;

				new ControlText(parent, new Area(4, y, parent.getWidth() - 8, 0), loc2, TextJustification.TOP_CENTER)
						.setColor(parent.getColor());
			}
			return y;
		}
	}

	private static class ClimatePlugin implements IClimatePlugin<IButterfly> {

		@Override
		public EnumTolerance getTemperatureTolerance(IButterfly butterfly) {
			IButterflyGenome genome = butterfly.getGenome();
			IAlleleTolerance tolerance = (IAlleleTolerance) genome.getActiveAllele(EnumButterflyChromosome.TEMPERATURE_TOLERANCE);
			return tolerance.getValue();
		}

		@Override
		public EnumTolerance getHumidityTolerance(IButterfly butterfly) {
			IButterflyGenome genome = butterfly.getGenome();
			IAlleleTolerance tolerance = (IAlleleTolerance) genome.getActiveAllele(EnumButterflyChromosome.HUMIDITY_TOLERANCE);
			return tolerance.getValue();
		}

		@Override
		public boolean showHumiditySection() {
			return true;
		}
	}
}
