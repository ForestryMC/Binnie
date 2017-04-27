package binnie.genetics.gui;

import binnie.Binnie;
import binnie.botany.api.IFlower;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.ControlTextCentered;
import binnie.core.craftgui.geometry.IArea;
import binnie.core.craftgui.geometry.IPoint;
import binnie.core.craftgui.minecraft.control.ControlIconDisplay;
import binnie.genetics.item.ModuleItem;
import forestry.api.apiculture.IBee;
import forestry.api.arboriculture.EnumTreeChromosome;
import forestry.api.arboriculture.ITree;
import forestry.api.genetics.IIndividual;
import forestry.api.lepidopterology.IButterfly;
import forestry.plugins.PluginApiculture;
import net.minecraftforge.common.EnumPlantType;

import java.text.DecimalFormat;

public class AnalystPageBiology extends ControlAnalystPage {
	public AnalystPageBiology(IWidget parent, IArea area, IIndividual ind) {
		super(parent, area);
		setColor(26214);
		int y = 4;
		new ControlTextCentered(this, y, "§nBiology").setColor(getColor());
		y += 12;
		if (ind instanceof IBee) {
			IBee bee = (IBee) ind;
			if (bee.getGenome().getNocturnal()) {
				new ControlIconDisplay(this, (w() - 64.0f) / 2.0f, y, ModuleItem.iconAllDay.getIcon()).addTooltip("Active all day and night");
			} else if (bee.getGenome().getPrimary().isNocturnal()) {
				new ControlIconDisplay(this, (w() - 64.0f) / 2.0f, y, ModuleItem.iconNight.getIcon()).addTooltip("Active at night");
			} else {
				new ControlIconDisplay(this, (w() - 64.0f) / 2.0f, y, ModuleItem.iconDaytime.getIcon()).addTooltip("Active during the day");
			}
			if (!bee.getGenome().getTolerantFlyer()) {
				new ControlIconDisplay(this, (w() - 64.0f) / 2.0f + 24.0f, y, ModuleItem.iconNoRain.getIcon()).addTooltip("Cannot work during rain");
			} else {
				new ControlIconDisplay(this, (w() - 64.0f) / 2.0f + 24.0f, y, ModuleItem.iconRain.getIcon()).addTooltip("Can work during rain");
			}
			if (bee.getGenome().getCaveDwelling()) {
				new ControlIconDisplay(this, (w() - 64.0f) / 2.0f + 48.0f, y, ModuleItem.iconNoSky.getIcon()).addTooltip("Can work underground");
			} else {
				new ControlIconDisplay(this, (w() - 64.0f) / 2.0f + 48.0f, y, ModuleItem.iconSky.getIcon()).addTooltip("Cannot work underground");
			}
			y += 30;
		} else if (ind instanceof IButterfly) {
			IButterfly moth = (IButterfly) ind;
			if (moth.getGenome().getNocturnal()) {
				new ControlIconDisplay(this, (w() - 64.0f) / 2.0f, y, ModuleItem.iconAllDay.getIcon()).addTooltip("Active all day and night");
			} else if (moth.getGenome().getPrimary().isNocturnal()) {
				new ControlIconDisplay(this, (w() - 64.0f) / 2.0f, y, ModuleItem.iconNight.getIcon()).addTooltip("Active at night");
			} else {
				new ControlIconDisplay(this, (w() - 64.0f) / 2.0f, y, ModuleItem.iconDaytime.getIcon()).addTooltip("Active during the day");
			}
			if (!moth.getGenome().getTolerantFlyer()) {
				new ControlIconDisplay(this, (w() - 64.0f) / 2.0f + 24.0f, y, ModuleItem.iconNoRain.getIcon()).addTooltip("Cannot work during rain");
			} else {
				new ControlIconDisplay(this, (w() - 64.0f) / 2.0f + 24.0f, y, ModuleItem.iconRain.getIcon()).addTooltip("Can work during rain");
			}
			if (moth.getGenome().getFireResist()) {
				new ControlIconDisplay(this, (w() - 64.0f) / 2.0f + 48.0f, y, ModuleItem.iconNoFire.getIcon()).addTooltip("Nonflammable");
			} else {
				new ControlIconDisplay(this, (w() - 64.0f) / 2.0f + 48.0f, y, ModuleItem.iconFire.getIcon()).addTooltip("Flammable");
			}
			y += 30;
		} else if (ind instanceof ITree) {
			new ControlTextCentered(this, y, "§oSappiness: " + Binnie.Genetics.treeBreedingSystem.getAlleleName(EnumTreeChromosome.SAPPINESS, ind.getGenome().getActiveAllele(EnumTreeChromosome.SAPPINESS))).setColor(getColor());
			y += 20;
		} else {
			y += 10;
		}
		if (ind instanceof IBee) {
			IBee bee = (IBee) ind;
			int fertility = bee.getGenome().getFertility();
			new ControlTextCentered(this, y, "§l" + fertility + "§r drone" + ((fertility > 1) ? "s" : "") + " per hive").setColor(getColor());
			y += 22;
			int lifespan = bee.getGenome().getLifespan() * PluginApiculture.ticksPerBeeWorkCycle;
			new ControlTextCentered(this, y, "Average Lifespan").setColor(getColor());
			y += 12;
			new ControlTextCentered(this, y, "§l" + getMCDayString(lifespan * (bee.getGenome().getNocturnal() ? 1.0f : 2.0f))).setColor(getColor());
			y += 22;
		}
		if (ind instanceof IButterfly) {
			IButterfly bee2 = (IButterfly) ind;
			int fertility = bee2.getGenome().getFertility();
			new ControlTextCentered(this, y, "Lays §l" + fertility + "§r caterpillar" + ((fertility > 1) ? "s" : "") + " before dying").setColor(getColor());
			y += 32;
			float caterpillarMatureTime = 1365.3999f * Math.round(bee2.getGenome().getLifespan() / (bee2.getGenome().getFertility() * 2));
			new ControlTextCentered(this, y, "Caterpillar Gestation").setColor(getColor());
			y += 12;
			new ControlTextCentered(this, y, "§l" + getMCDayString(caterpillarMatureTime)).setColor(getColor());
			y += 22;
			int speed = (int) (20.0f * bee2.getGenome().getSpeed());
			new ControlTextCentered(this, y, "Flight Speed").setColor(getColor());
			y += 12;
			new ControlTextCentered(this, y, "§l" + speed + "§r blocks per second").setColor(getColor());
			y += 22;
		}
		if (ind instanceof ITree) {
			ITree tree = (ITree) ind;
			int fertility = (int) (1.0f / tree.getGenome().getFertility());
			new ControlTextCentered(this, y, "1 Sapling per §l" + fertility + "§r leave" + ((fertility > 1) ? "s" : "")).setColor(getColor());
			y += 22;
			float butterflySpawn = 1365.3999f / (tree.getGenome().getSappiness() * tree.getGenome().getYield() * 0.5f);
			new ControlTextCentered(this, y, "Butterfies spawn every\n" + getTimeString(butterflySpawn) + " per leaf").setColor(getColor());
			y += 34;
			new ControlTextCentered(this, y, "Plant Types").setColor(getColor());
			y += 12;
			for (EnumPlantType type : tree.getGenome().getPlantTypes()) {
				new ControlTextCentered(this, y, "§o" + type.name()).setColor(getColor());
				y += 12;
			}
		}
		if (ind instanceof IFlower) {
			IFlower flower = (IFlower) ind;
			float butterflySpawn2 = 1365.3999f / (flower.getGenome().getSappiness() * 0.2f);
			new ControlTextCentered(this, y, "Butterfies spawn every\n" + getTimeString(butterflySpawn2)).setColor(getColor());
			y += 30;
			float CHANCE_DISPERSAL = 0.8f;
			CHANCE_DISPERSAL += 0.2f * flower.getGenome().getFertility();
			float CHANCE_POLLINATE = 0.6f;
			CHANCE_POLLINATE += 0.25f * flower.getGenome().getFertility();
			float CHANCE_SELFPOLLINATE = 0.2f * CHANCE_POLLINATE;
			if (CHANCE_DISPERSAL > 1.0f) {
				CHANCE_DISPERSAL = 1.0f;
			}
			if (CHANCE_POLLINATE > 1.0f) {
				CHANCE_POLLINATE = 1.0f;
			}
			float dispersalTime = 1365.3999f / CHANCE_DISPERSAL;
			float pollinateTime = 1365.3999f / CHANCE_POLLINATE;
			float lifespan2 = flower.getMaxAge() * 20.0f * 68.27f / flower.getGenome().getAgeChance();
			float floweringLifespan = (flower.getMaxAge() - 1) * 20.0f * 68.27f / flower.getGenome().getAgeChance();
			floweringLifespan -= 1365.3999f;
			new ControlTextCentered(this, y, "Average Lifespan").setColor(getColor());
			y += 12;
			new ControlTextCentered(this, y, "§l" + getMCDayString(lifespan2)).setColor(getColor());
			y += 22;
			new ControlTextCentered(this, y, "Seed Dispersal").setColor(getColor());
			y += 12;
			new ControlTextCentered(this, y, "§o" + (int) (floweringLifespan / dispersalTime) + " per lifetime").setColor(getColor());
			y += 22;
			new ControlTextCentered(this, y, "Pollination").setColor(getColor());
			y += 12;
			new ControlTextCentered(this, y, "§o" + (int) (floweringLifespan / pollinateTime) + " per lifetime").setColor(getColor());
			y += 22;
		}
		setSize(new IPoint(w(), y));
	}

	private String getMCDayString(float time) {
		float seconds = time / 20.0f;
		float minutes = seconds / 60.0f;
		float days = minutes / 20.0f;
		DecimalFormat df = new DecimalFormat("#.0");
		return df.format(days) + " MC days";
	}

	@Override
	public String getTitle() {
		return "Biology";
	}
}
