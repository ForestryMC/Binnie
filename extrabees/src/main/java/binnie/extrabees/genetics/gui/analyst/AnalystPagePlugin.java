package binnie.extrabees.genetics.gui.analyst;

import java.util.List;

import binnie.core.api.gui.IArea;
import binnie.core.api.gui.ITitledWidget;
import binnie.core.api.gui.IWidget;
import binnie.core.gui.CraftGUI;
import binnie.core.gui.controls.ControlText;
import binnie.core.gui.controls.ControlTextCentered;
import binnie.core.gui.geometry.Area;
import binnie.core.gui.geometry.TextJustification;
import binnie.core.gui.minecraft.control.ControlIconDisplay;
import binnie.core.util.I18N;
import binnie.core.util.TimeUtil;
import binnie.genetics.api.IAnalystPagePlugin;
import binnie.genetics.gui.analyst.AnalystConstants;
import binnie.genetics.gui.analyst.AnalystPageBehaviour;
import binnie.genetics.gui.analyst.AnalystPageBiology;
import binnie.genetics.gui.analyst.tree.AnalystPageClimate;
import binnie.genetics.item.ModuleItems;
import forestry.api.apiculture.EnumBeeChromosome;
import forestry.api.apiculture.IAlleleBeeEffect;
import forestry.api.apiculture.IBee;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.genetics.EnumTolerance;
import forestry.api.genetics.IAlleleTolerance;
import forestry.api.genetics.IIndividual;
import forestry.apiculture.PluginApiculture;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AnalystPagePlugin implements IAnalystPagePlugin<IBee> {
	@Override
	public boolean handles(IIndividual individual) {
		return individual instanceof IBee;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addAnalystPages(IBee individual, IWidget parent, IArea pageSize, List<ITitledWidget> analystPages) {
		analystPages.add(new AnalystPageClimate<>(parent, pageSize, individual, new ClimatePlugin()));
		analystPages.add(new AnalystPageBeeProducts(parent, pageSize, individual));
		analystPages.add(new AnalystPageBiology<>(parent, pageSize, individual, new BiologyPlugin()));
		analystPages.add(new AnalystPageBehaviour<>(parent, pageSize, individual, new BehaviorPlugin()));
	}

	private static class BiologyPlugin implements AnalystPageBiology.IBiologyPlugin<IBee> {
		@Override
		@SideOnly(Side.CLIENT)
		public int addBiologyPages(IBee bee, IWidget parent, int y) {
			if (bee.getGenome().getNeverSleeps()) {
				new ControlIconDisplay(parent, (parent.getWidth() - 64) / 2, y, ModuleItems.iconAllDay)
						.addTooltip(I18N.localise(AnalystConstants.BIOLOGY_KEY + ".allDay"));
			} else if (bee.getGenome().getPrimary().isNocturnal()) {
				new ControlIconDisplay(parent, (parent.getWidth() - 64) / 2, y, ModuleItems.iconNight)
						.addTooltip(I18N.localise(AnalystConstants.BIOLOGY_KEY + ".night"));
			} else {
				new ControlIconDisplay(parent, (parent.getWidth() - 64) / 2, y, ModuleItems.iconDaytime)
						.addTooltip(I18N.localise(AnalystConstants.BIOLOGY_KEY + ".day"));
			}

			if (!bee.getGenome().getToleratesRain()) {
				new ControlIconDisplay(parent, (parent.getWidth() - 64) / 2 + 24, y, ModuleItems.iconNoRain)
						.addTooltip(I18N.localise(AnalystConstants.BIOLOGY_KEY + ".notRain"));
			} else {
				new ControlIconDisplay(parent, (parent.getWidth() - 64) / 2 + 24, y, ModuleItems.iconRain)
						.addTooltip(I18N.localise(AnalystConstants.BIOLOGY_KEY + ".rain"));
			}

			if (bee.getGenome().getCaveDwelling()) {
				new ControlIconDisplay(parent, (parent.getWidth() - 64) / 2 + 48, y, ModuleItems.iconNoSky)
						.addTooltip(I18N.localise(AnalystConstants.BIOLOGY_KEY + ".underground"));
			} else {
				new ControlIconDisplay(parent, (parent.getWidth() - 64) / 2 + 48, y, ModuleItems.iconSky)
						.addTooltip(I18N.localise(AnalystConstants.BIOLOGY_KEY + ".notUnderground"));
			}
			y += 30;

			int fertility = bee.getGenome().getFertility();
			if (fertility > 1) {
				new ControlTextCentered(parent, y, TextFormatting.BOLD + I18N.localise(AnalystConstants.BIOLOGY_KEY + ".fertility.drones", fertility))
						.setColor(parent.getColor());
			} else {
				new ControlTextCentered(parent, y, TextFormatting.BOLD + I18N.localise(AnalystConstants.BIOLOGY_KEY + ".fertility.drone"))
						.setColor(parent.getColor());
			}

			y += 22;
			new ControlTextCentered(parent, y, I18N.localise(AnalystConstants.BIOLOGY_KEY + ".averageLifespan"))
					.setColor(parent.getColor());

			y += 12;
			int lifespan = bee.getGenome().getLifespan() * PluginApiculture.ticksPerBeeWorkCycle;
			new ControlTextCentered(parent, y, TextFormatting.BOLD + TimeUtil.getMCDayString(lifespan * (bee.getGenome().getNeverSleeps() ? 1.0f : 2.0f)))
					.setColor(parent.getColor());
			y += 22;
			return y;
		}
	}

	private static class BehaviorPlugin implements AnalystPageBehaviour.IBehaviourPlugin<IBee> {
		@Override
		@SideOnly(Side.CLIENT)
		public int addBehaviourPages(IBee bee, IWidget parent, int y) {
			y += 8;
			int fertility = bee.getGenome().getFlowering();
			new ControlTextCentered(parent, y, I18N.localise(AnalystConstants.BEHAVIOUR_KEY + ".pollinatesNearby") + "\n" + bee.getGenome().getFlowerProvider().getDescription())
					.setColor(parent.getColor());
			y += 20;

			new ControlTextCentered(parent, y, I18N.localise(AnalystConstants.BEHAVIOUR_KEY + ".everyTime", TimeUtil.getTimeString(PluginApiculture.ticksPerBeeWorkCycle * 100 / fertility)))
					.setColor(parent.getColor());
			y += 22;

			IAlleleBeeEffect effect = bee.getGenome().getEffect();
			Vec3i t = bee.getGenome().getTerritory();
			if (!effect.getUID().contains("None")) {
				String effectDesc = I18N.localiseOrBlank("binniecore.allele." + effect.getUID() + ".desc");
				String loc = effectDesc.isEmpty()
						? I18N.localise(AnalystConstants.BEHAVIOUR_KEY + ".effect", effect.getAlleleName())
						: effectDesc;

				new ControlText(parent, new Area(4, y, parent.getWidth() - 8, 0), loc, TextJustification.TOP_CENTER)
						.setColor(parent.getColor());
				y += (int) (CraftGUI.RENDER.textHeight(loc, parent.getWidth() - 8) + 1.0f);

				new ControlTextCentered(parent, y, I18N.localise(AnalystConstants.BEHAVIOUR_KEY + ".withinBlocks", (int) (t.getX() / 2.0f)))
						.setColor(parent.getColor());
				y += 22;
			}

			new ControlTextCentered(parent, y, I18N.localise(AnalystConstants.BEHAVIOUR_KEY + ".territory", t.getX(), t.getY(), t.getZ()))
					.setColor(parent.getColor());
			y += 22;
			return y;
		}
	}

	private static class ClimatePlugin implements AnalystPageClimate.IClimatePlugin<IBee> {

		@Override
		public EnumTolerance getTemperatureTolerance(IBee bee) {
			IBeeGenome genome = bee.getGenome();
			IAlleleTolerance tolerance = (IAlleleTolerance) genome.getActiveAllele(EnumBeeChromosome.TEMPERATURE_TOLERANCE);
			return tolerance.getValue();
		}

		@Override
		public EnumTolerance getHumidityTolerance(IBee bee) {
			IBeeGenome genome = bee.getGenome();
			IAlleleTolerance tolerance = (IAlleleTolerance) genome.getActiveAllele(EnumBeeChromosome.HUMIDITY_TOLERANCE);
			return tolerance.getValue();
		}

		@Override
		public boolean showHumiditySection() {
			return true;
		}
	}
}
