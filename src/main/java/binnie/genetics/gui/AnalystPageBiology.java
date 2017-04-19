package binnie.genetics.gui;

import binnie.Binnie;
import binnie.botany.api.IFlower;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.ControlTextCentered;
import binnie.core.craftgui.geometry.Area;
import binnie.core.craftgui.geometry.Point;
import binnie.core.craftgui.minecraft.control.ControlIconDisplay;
import binnie.genetics.Genetics;
import binnie.genetics.item.ModuleItems;
import forestry.api.apiculture.IBee;
import forestry.api.arboriculture.EnumTreeChromosome;
import forestry.api.arboriculture.ITree;
import forestry.api.genetics.IIndividual;
import forestry.api.lepidopterology.IButterfly;
import forestry.apiculture.PluginApiculture;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.text.DecimalFormat;
import java.util.Locale;

//TODO:localise
@SideOnly(Side.CLIENT)
public class AnalystPageBiology extends ControlAnalystPage {
	public AnalystPageBiology(final IWidget parent, final Area area, final IIndividual ind) {
		super(parent, area);
		this.setColour(26214);
		int y = 4;
		new ControlTextCentered(this, y, TextFormatting.UNDERLINE + getTitle()).setColour(this.getColour());
		y += 12;
		if (ind instanceof IBee) {
			final IBee bee = (IBee) ind;
			if (bee.getGenome().getNeverSleeps()) {
				new ControlIconDisplay(this, (width() - 64) / 2, y, ModuleItems.iconAllDay).addTooltip(Genetics.proxy.localise("gui.analyst.biology.bee.allday"));
			} else if (bee.getGenome().getPrimary().isNocturnal()) {
				new ControlIconDisplay(this, (width() - 64) / 2, y, ModuleItems.iconNight).addTooltip(Genetics.proxy.localise("gui.analyst.biology.bee.night"));
			} else {
				new ControlIconDisplay(this, (width() - 64) / 2, y, ModuleItems.iconDaytime).addTooltip(Genetics.proxy.localise("gui.analyst.biology.bee.day"));
			}
			if (!bee.getGenome().getToleratesRain()) {
				new ControlIconDisplay(this, (width() - 64) / 2 + 24, y, ModuleItems.iconNoRain).addTooltip(Genetics.proxy.localise("gui.analyst.biology.bee.norain"));
			} else {
				new ControlIconDisplay(this, (width() - 64) / 2 + 24, y, ModuleItems.iconRain).addTooltip(Genetics.proxy.localise("gui.analyst.biology.bee.rain"));
			}
			if (bee.getGenome().getCaveDwelling()) {
				new ControlIconDisplay(this, (width() - 64) / 2 + 48, y, ModuleItems.iconNoSky).addTooltip(Genetics.proxy.localise("gui.analyst.biology.bee.nosky"));
			} else {
				new ControlIconDisplay(this, (width() - 64) / 2 + 48, y, ModuleItems.iconSky).addTooltip(Genetics.proxy.localise("gui.analyst.biology.bee.sky"));
			}
			y += 30;
		} else if (ind instanceof IButterfly) {
			final IButterfly moth = (IButterfly) ind;
			if (moth.getGenome().getNocturnal()) {
				new ControlIconDisplay(this, (width() - 64) / 2, y, ModuleItems.iconAllDay).addTooltip("Active all day and night");
			} else if (moth.getGenome().getPrimary().isNocturnal()) {
				new ControlIconDisplay(this, (width() - 64) / 2, y, ModuleItems.iconNight).addTooltip("Active at night");
			} else {
				new ControlIconDisplay(this, (width() - 64) / 2, y, ModuleItems.iconDaytime).addTooltip("Active during the day");
			}
			if (!moth.getGenome().getTolerantFlyer()) {
				new ControlIconDisplay(this, (width() - 64) / 2 + 24, y, ModuleItems.iconNoRain).addTooltip("Cannot work during rain");
			} else {
				new ControlIconDisplay(this, (width() - 64) / 2 + 24, y, ModuleItems.iconRain).addTooltip("Can work during rain");
			}
			if (moth.getGenome().getFireResist()) {
				new ControlIconDisplay(this, (width() - 64) / 2 + 48, y, ModuleItems.iconNoFire).addTooltip("Nonflammable");
			} else {
				new ControlIconDisplay(this, (width() - 64) / 2 + 48, y, ModuleItems.iconFire).addTooltip("Flammable");
			}
			y += 30;
		} else if (ind instanceof ITree) {
			new ControlTextCentered(this, y, "§oSappiness: " + Binnie.GENETICS.treeBreedingSystem.getAlleleName(EnumTreeChromosome.SAPPINESS, ind.getGenome().getActiveAllele(EnumTreeChromosome.SAPPINESS))).setColour(this.getColour());
			y += 20;
		} else {
			y += 10;
		}
		if (ind instanceof IBee) {
			final IBee bee = (IBee) ind;
			final int fertility = bee.getGenome().getFertility();
			new ControlTextCentered(this, y, "§l" + fertility + "§r drone" + ((fertility > 1) ? "s" : "") + " per hive").setColour(this.getColour());
			y += 22;
			final int lifespan = bee.getGenome().getLifespan() * PluginApiculture.ticksPerBeeWorkCycle;
			new ControlTextCentered(this, y, "Average Lifespan").setColour(this.getColour());
			y += 12;
			new ControlTextCentered(this, y, "§l" + this.getMCDayString(lifespan * (bee.getGenome().getNeverSleeps() ? 1.0f : 2.0f))).setColour(this.getColour());
			y += 22;
		}
		if (ind instanceof IButterfly) {
			final IButterfly bee2 = (IButterfly) ind;
			final int fertility = bee2.getGenome().getFertility();
			new ControlTextCentered(this, y, "Lays §l" + fertility + "§r caterpillar" + ((fertility > 1) ? "s" : "") + " before dying").setColour(this.getColour());
			y += 32;
			final float caterpillarMatureTime = 1365.3999f * Math.round(bee2.getGenome().getLifespan() / (bee2.getGenome().getFertility() * 2));
			new ControlTextCentered(this, y, "Caterpillar Gestation").setColour(this.getColour());
			y += 12;
			new ControlTextCentered(this, y, "§l" + this.getMCDayString(caterpillarMatureTime)).setColour(this.getColour());
			y += 22;
			final int speed = (int) (20.0f * bee2.getGenome().getSpeed());
			new ControlTextCentered(this, y, "Flight Speed").setColour(this.getColour());
			y += 12;
			new ControlTextCentered(this, y, "§l" + speed + "§r blocks per second").setColour(this.getColour());
			y += 22;
		}
		if (ind instanceof ITree) {
			final ITree tree = (ITree) ind;
			final int fertility = (int) (1.0f / tree.getGenome().getFertility());
			new ControlTextCentered(this, y, "1 Sapling per §l" + fertility + "§r leave" + ((fertility > 1) ? "s" : "")).setColour(this.getColour());
			y += 22;
			final int butterflySpawn = Math.round(1365.3999f / (tree.getGenome().getSappiness() * tree.getGenome().getYield() * 0.5f));
			new ControlTextCentered(this, y, "Butterfies spawn every\n" + this.getTimeString(butterflySpawn) + " per leaf").setColour(this.getColour());
			y += 34;
			new ControlTextCentered(this, y, "Plant Type").setColour(this.getColour());
			y += 12;
			new ControlTextCentered(this, y, "§o" + tree.getGenome().getPrimary().getPlantType().toString().toLowerCase(Locale.ENGLISH)).setColour(this.getColour());
		}
		if (ind instanceof IFlower) {
			final IFlower flower = (IFlower) ind;
			final int butterflySpawn2 = Math.round(1365.3999f / (flower.getGenome().getSappiness() * 0.2f));
			new ControlTextCentered(this, y, "Butterfies spawn every\n" + this.getTimeString(butterflySpawn2)).setColour(this.getColour());
			y += 30;
			float CHANCE_DISPERSAL = 0.8f;
			CHANCE_DISPERSAL += 0.2f * flower.getGenome().getFertility();
			float CHANCE_POLLINATE = 0.6f;
			CHANCE_POLLINATE += 0.25f * flower.getGenome().getFertility();
			final float CHANCE_SELFPOLLINATE = 0.2f * CHANCE_POLLINATE;
			if (CHANCE_DISPERSAL > 1.0f) {
				CHANCE_DISPERSAL = 1.0f;
			}
			if (CHANCE_POLLINATE > 1.0f) {
				CHANCE_POLLINATE = 1.0f;
			}
			final float dispersalTime = 1365.3999f / CHANCE_DISPERSAL;
			final float pollinateTime = 1365.3999f / CHANCE_POLLINATE;
			final float lifespan2 = flower.getMaxAge() * 20.0f * 68.27f / flower.getGenome().getAgeChance();
			float floweringLifespan = (flower.getMaxAge() - 1) * 20.0f * 68.27f / flower.getGenome().getAgeChance();
			floweringLifespan -= 1365.3999f;
			new ControlTextCentered(this, y, "Average Lifespan").setColour(this.getColour());
			y += 12;
			new ControlTextCentered(this, y, "§l" + this.getMCDayString(lifespan2)).setColour(this.getColour());
			y += 22;
			new ControlTextCentered(this, y, "Seed Dispersal").setColour(this.getColour());
			y += 12;
			new ControlTextCentered(this, y, "§o" + (int) (floweringLifespan / dispersalTime) + " per lifetime").setColour(this.getColour());
			y += 22;
			new ControlTextCentered(this, y, "Pollination").setColour(this.getColour());
			y += 12;
			new ControlTextCentered(this, y, "§o" + (int) (floweringLifespan / pollinateTime) + " per lifetime").setColour(this.getColour());
			y += 22;
		}
		this.setSize(new Point(this.width(), y));
	}

	private String getMCDayString(final float time) {
		final float seconds = time / 20.0f;
		final float minutes = seconds / 60.0f;
		final float days = minutes / 20.0f;
		final DecimalFormat df = new DecimalFormat("#.0");
		return df.format(days) + " MC days";
	}

	@Override
	public String getTitle() {
		return Genetics.proxy.localise("gui.analyst.biology.title");
	}
}
