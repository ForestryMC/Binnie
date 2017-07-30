package binnie.genetics.gui.analyst;

import java.text.DecimalFormat;

import net.minecraft.util.text.TextFormatting;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.apiculture.IBee;
import forestry.api.arboriculture.EnumTreeChromosome;
import forestry.api.arboriculture.ITree;
import forestry.api.genetics.IIndividual;
import forestry.api.lepidopterology.IButterfly;
import forestry.apiculture.PluginApiculture;

import binnie.Binnie;
import binnie.botany.api.IFlower;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.ControlTextCentered;
import binnie.core.craftgui.geometry.Area;
import binnie.core.craftgui.geometry.Point;
import binnie.core.craftgui.minecraft.control.ControlIconDisplay;
import binnie.core.util.I18N;
import binnie.genetics.item.ModuleItems;

@SideOnly(Side.CLIENT)
public class AnalystPageBiology extends ControlAnalystPage {
	private static final float SPAWN_KOEF = 1365.3999f;

	public AnalystPageBiology(IWidget parent, Area area, IIndividual ind) {
		super(parent, area);
		setColor(0x006666);
		int y = 4;
		new ControlTextCentered(this, y, TextFormatting.UNDERLINE + getTitle())
			.setColor(getColor());
		y += 12;

		if (ind instanceof IBee) {
			IBee bee = (IBee) ind;
			if (bee.getGenome().getNeverSleeps()) {
				new ControlIconDisplay(this, (width() - 64) / 2, y, ModuleItems.iconAllDay)
					.addTooltip(I18N.localise(AnalystConstants.BIOLOGY_KEY + ".allDay"));
			} else if (bee.getGenome().getPrimary().isNocturnal()) {
				new ControlIconDisplay(this, (width() - 64) / 2, y, ModuleItems.iconNight)
					.addTooltip(I18N.localise(AnalystConstants.BIOLOGY_KEY + ".night"));
			} else {
				new ControlIconDisplay(this, (width() - 64) / 2, y, ModuleItems.iconDaytime)
					.addTooltip(I18N.localise(AnalystConstants.BIOLOGY_KEY + ".day"));
			}

			if (!bee.getGenome().getToleratesRain()) {
				new ControlIconDisplay(this, (width() - 64) / 2 + 24, y, ModuleItems.iconNoRain)
					.addTooltip(I18N.localise(AnalystConstants.BIOLOGY_KEY + ".notRain"));
			} else {
				new ControlIconDisplay(this, (width() - 64) / 2 + 24, y, ModuleItems.iconRain)
					.addTooltip(I18N.localise(AnalystConstants.BIOLOGY_KEY + ".rain"));
			}

			if (bee.getGenome().getCaveDwelling()) {
				new ControlIconDisplay(this, (width() - 64) / 2 + 48, y, ModuleItems.iconNoSky)
					.addTooltip(I18N.localise(AnalystConstants.BIOLOGY_KEY + ".underground"));
			} else {
				new ControlIconDisplay(this, (width() - 64) / 2 + 48, y, ModuleItems.iconSky)
					.addTooltip(I18N.localise(AnalystConstants.BIOLOGY_KEY + ".notUnderground"));
			}
			y += 30;
		} else if (ind instanceof IButterfly) {
			IButterfly moth = (IButterfly) ind;
			if (moth.getGenome().getNocturnal()) {
				new ControlIconDisplay(this, (width() - 64) / 2, y, ModuleItems.iconAllDay)
					.addTooltip(I18N.localise(AnalystConstants.BIOLOGY_KEY + ".allDay"));
			} else if (moth.getGenome().getPrimary().isNocturnal()) {
				new ControlIconDisplay(this, (width() - 64) / 2, y, ModuleItems.iconNight)
					.addTooltip(I18N.localise(AnalystConstants.BIOLOGY_KEY + ".night"));
			} else {
				new ControlIconDisplay(this, (width() - 64) / 2, y, ModuleItems.iconDaytime)
					.addTooltip(I18N.localise(AnalystConstants.BIOLOGY_KEY + ".day"));
			}

			if (!moth.getGenome().getTolerantFlyer()) {
				new ControlIconDisplay(this, (width() - 64) / 2 + 24, y, ModuleItems.iconNoRain)
					.addTooltip(I18N.localise(AnalystConstants.BIOLOGY_KEY + ".notRain"));
			} else {
				new ControlIconDisplay(this, (width() - 64) / 2 + 24, y, ModuleItems.iconRain)
					.addTooltip(I18N.localise(AnalystConstants.BIOLOGY_KEY + ".rain"));
			}

			if (moth.getGenome().getFireResist()) {
				new ControlIconDisplay(this, (width() - 64) / 2 + 48, y, ModuleItems.iconNoFire)
					.addTooltip(I18N.localise(AnalystConstants.BIOLOGY_KEY + ".nonflammable"));
			} else {
				new ControlIconDisplay(this, (width() - 64) / 2 + 48, y, ModuleItems.iconFire)
					.addTooltip(I18N.localise(AnalystConstants.BIOLOGY_KEY + ".flammable"));
			}
			y += 30;
		} else if (ind instanceof ITree) {
			String alleleName = Binnie.GENETICS.treeBreedingSystem.getAlleleName(EnumTreeChromosome.SAPPINESS, ind.getGenome().getActiveAllele(EnumTreeChromosome.SAPPINESS));
			new ControlTextCentered(this, y, TextFormatting.ITALIC + I18N.localise(AnalystConstants.BIOLOGY_KEY + ".sappiness", alleleName))
				.setColor(getColor());
			y += 20;
		} else {
			y += 10;
		}

		if (ind instanceof IBee) {
			IBee bee = (IBee) ind;
			int fertility = bee.getGenome().getFertility();
			if (fertility > 1) {
				new ControlTextCentered(this, y, TextFormatting.BOLD + I18N.localise(AnalystConstants.BIOLOGY_KEY + ".fertility.drones", fertility))
					.setColor(getColor());
			} else {
				new ControlTextCentered(this, y, TextFormatting.BOLD + I18N.localise(AnalystConstants.BIOLOGY_KEY + ".fertility.drone"))
					.setColor(getColor());
			}

			y += 22;
			new ControlTextCentered(this, y, I18N.localise(AnalystConstants.BIOLOGY_KEY + ".averageLifespan"))
				.setColor(getColor());

			y += 12;
			int lifespan = bee.getGenome().getLifespan() * PluginApiculture.ticksPerBeeWorkCycle;
			new ControlTextCentered(this, y, TextFormatting.BOLD + getMCDayString(lifespan * (bee.getGenome().getNeverSleeps() ? 1.0f : 2.0f)))
				.setColor(getColor());
			y += 22;
		}

		if (ind instanceof IButterfly) {
			IButterfly bee2 = (IButterfly) ind;
			int fertility = bee2.getGenome().getFertility();
			if (fertility > 1) {
				new ControlTextCentered(this, y, I18N.localise(AnalystConstants.BIOLOGY_KEY + ".fertility.moths", fertility))
					.setColor(getColor());
			} else {
				new ControlTextCentered(this, y, I18N.localise(AnalystConstants.BIOLOGY_KEY + ".fertility.moth"))
					.setColor(getColor());
			}

			y += 32;
			float caterpillarMatureTime = SPAWN_KOEF * Math.round(bee2.getGenome().getLifespan() / (bee2.getGenome().getFertility() * 2));
			new ControlTextCentered(this, y, I18N.localise(AnalystConstants.BIOLOGY_KEY + ".caterpillarGestation"))
				.setColor(getColor());

			y += 12;
			new ControlTextCentered(this, y, TextFormatting.BOLD + getMCDayString(caterpillarMatureTime))
				.setColor(getColor());

			y += 22;
			int speed = (int) (20.0f * bee2.getGenome().getSpeed());
			new ControlTextCentered(this, y, I18N.localise(AnalystConstants.BIOLOGY_KEY + ".flightSpeed"))
				.setColor(getColor());

			y += 12;
			new ControlTextCentered(this, y, TextFormatting.BOLD + I18N.localise(AnalystConstants.BIOLOGY_KEY + ".blocksPerSec", speed))
				.setColor(getColor());
			y += 22;
		}

		if (ind instanceof ITree) {
			ITree tree = (ITree) ind;
			int fertility = (int) (1.0f / tree.getGenome().getFertility());
			if (fertility > 1) {
				new ControlTextCentered(this, y, I18N.localise(AnalystConstants.BIOLOGY_KEY + ".fertility.leaves", fertility))
					.setColor(getColor());
			} else {
				new ControlTextCentered(this, y, I18N.localise(AnalystConstants.BIOLOGY_KEY + ".fertility.leaf"))
					.setColor(getColor());
			}

			y += 22;
			int butterflySpawn = Math.round(SPAWN_KOEF / (tree.getGenome().getSappiness() * tree.getGenome().getYield() * 0.5f));
			new ControlTextCentered(this, y, I18N.localise(AnalystConstants.BIOLOGY_KEY + ".mothSpawn.perLeaf", getTimeString(butterflySpawn)))
				.setColor(getColor());

			y += 34;
			new ControlTextCentered(this, y, I18N.localise(AnalystConstants.BIOLOGY_KEY + ".plantTypes")).setColor(getColor());

			y += 12;
			new ControlTextCentered(this, y, TextFormatting.ITALIC + tree.getGenome().getPrimary().getPlantType().toString())
				.setColor(getColor());
		}
		if (ind instanceof IFlower) {
			IFlower flower = (IFlower) ind;
			int butterflySpawn2 = Math.round(SPAWN_KOEF / (flower.getGenome().getSappiness() * 0.2f));
			new ControlTextCentered(this, y, I18N.localise(AnalystConstants.BIOLOGY_KEY + ".mothSpawn", getTimeString(butterflySpawn2)))
				.setColor(getColor());
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
			float dispersalTime = SPAWN_KOEF / chanceDispersal;
			float pollinateTime = SPAWN_KOEF / chancePollinate;
			float lifespan2 = maxAge * 20.0f * 68.27f / ageChance;
			float floweringLifespan = (maxAge - 1) * 20.0f * 68.27f / ageChance - SPAWN_KOEF;

			new ControlTextCentered(this, y, I18N.localise(AnalystConstants.BIOLOGY_KEY + ".averageLifespan"))
				.setColor(getColor());
			y += 12;

			new ControlTextCentered(this, y, TextFormatting.BOLD + getMCDayString(lifespan2))
				.setColor(getColor());
			y += 22;

			new ControlTextCentered(this, y, I18N.localise(AnalystConstants.BIOLOGY_KEY + ".seedDispersal"))
				.setColor(getColor());
			y += 12;

			new ControlTextCentered(this, y, TextFormatting.ITALIC + I18N.localise(AnalystConstants.BIOLOGY_KEY + ".perLifetime", (int) (floweringLifespan / dispersalTime)))
				.setColor(getColor());
			y += 22;

			new ControlTextCentered(this, y, I18N.localise(AnalystConstants.BIOLOGY_KEY + ".pollination"))
				.setColor(getColor());
			y += 12;

			new ControlTextCentered(this, y, TextFormatting.ITALIC + I18N.localise(AnalystConstants.BIOLOGY_KEY + ".perLifetime", (int) (floweringLifespan / pollinateTime)))
				.setColor(getColor());
			y += 22;
		}
		setSize(new Point(width(), y));
	}

	private String getMCDayString(float time) {
		float seconds = time / 20.0f;
		float minutes = seconds / 60.0f;
		float days = minutes / 20.0f;
		DecimalFormat df = new DecimalFormat("#.#");
		return I18N.localise(AnalystConstants.BIOLOGY_KEY + ".mcDays", df.format(days));
	}

	@Override
	public String getTitle() {
		return I18N.localise(AnalystConstants.BIOLOGY_KEY + "");
	}
}
