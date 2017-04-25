// 
// Decompiled by Procyon v0.5.30
// 

package binnie.genetics.gui;

import java.text.DecimalFormat;
import binnie.core.craftgui.geometry.IPoint;
import binnie.botany.api.IFlower;
import net.minecraftforge.common.EnumPlantType;
import forestry.plugins.PluginApiculture;
import forestry.api.arboriculture.EnumTreeChromosome;
import binnie.Binnie;
import forestry.api.arboriculture.ITree;
import forestry.api.lepidopterology.IButterfly;
import binnie.core.craftgui.minecraft.control.ControlIconDisplay;
import binnie.genetics.item.ModuleItem;
import forestry.api.apiculture.IBee;
import binnie.core.craftgui.controls.ControlTextCentered;
import forestry.api.genetics.IIndividual;
import binnie.core.craftgui.geometry.IArea;
import binnie.core.craftgui.IWidget;

public class AnalystPageBiology extends ControlAnalystPage
{
	public AnalystPageBiology(final IWidget parent, final IArea area, final IIndividual ind) {
		super(parent, area);
		this.setColor(26214);
		int y = 4;
		new ControlTextCentered(this, y, "§nBiology").setColor(this.getColor());
		y += 12;
		if (ind instanceof IBee) {
			final IBee bee = (IBee) ind;
			if (bee.getGenome().getNocturnal()) {
				new ControlIconDisplay(this, (this.w() - 64.0f) / 2.0f, y, ModuleItem.iconAllDay.getIcon()).addTooltip("Active all day and night");
			}
			else if (bee.getGenome().getPrimary().isNocturnal()) {
				new ControlIconDisplay(this, (this.w() - 64.0f) / 2.0f, y, ModuleItem.iconNight.getIcon()).addTooltip("Active at night");
			}
			else {
				new ControlIconDisplay(this, (this.w() - 64.0f) / 2.0f, y, ModuleItem.iconDaytime.getIcon()).addTooltip("Active during the day");
			}
			if (!bee.getGenome().getTolerantFlyer()) {
				new ControlIconDisplay(this, (this.w() - 64.0f) / 2.0f + 24.0f, y, ModuleItem.iconNoRain.getIcon()).addTooltip("Cannot work during rain");
			}
			else {
				new ControlIconDisplay(this, (this.w() - 64.0f) / 2.0f + 24.0f, y, ModuleItem.iconRain.getIcon()).addTooltip("Can work during rain");
			}
			if (bee.getGenome().getCaveDwelling()) {
				new ControlIconDisplay(this, (this.w() - 64.0f) / 2.0f + 48.0f, y, ModuleItem.iconNoSky.getIcon()).addTooltip("Can work underground");
			}
			else {
				new ControlIconDisplay(this, (this.w() - 64.0f) / 2.0f + 48.0f, y, ModuleItem.iconSky.getIcon()).addTooltip("Cannot work underground");
			}
			y += 30;
		}
		else if (ind instanceof IButterfly) {
			final IButterfly moth = (IButterfly) ind;
			if (moth.getGenome().getNocturnal()) {
				new ControlIconDisplay(this, (this.w() - 64.0f) / 2.0f, y, ModuleItem.iconAllDay.getIcon()).addTooltip("Active all day and night");
			}
			else if (moth.getGenome().getPrimary().isNocturnal()) {
				new ControlIconDisplay(this, (this.w() - 64.0f) / 2.0f, y, ModuleItem.iconNight.getIcon()).addTooltip("Active at night");
			}
			else {
				new ControlIconDisplay(this, (this.w() - 64.0f) / 2.0f, y, ModuleItem.iconDaytime.getIcon()).addTooltip("Active during the day");
			}
			if (!moth.getGenome().getTolerantFlyer()) {
				new ControlIconDisplay(this, (this.w() - 64.0f) / 2.0f + 24.0f, y, ModuleItem.iconNoRain.getIcon()).addTooltip("Cannot work during rain");
			}
			else {
				new ControlIconDisplay(this, (this.w() - 64.0f) / 2.0f + 24.0f, y, ModuleItem.iconRain.getIcon()).addTooltip("Can work during rain");
			}
			if (moth.getGenome().getFireResist()) {
				new ControlIconDisplay(this, (this.w() - 64.0f) / 2.0f + 48.0f, y, ModuleItem.iconNoFire.getIcon()).addTooltip("Nonflammable");
			}
			else {
				new ControlIconDisplay(this, (this.w() - 64.0f) / 2.0f + 48.0f, y, ModuleItem.iconFire.getIcon()).addTooltip("Flammable");
			}
			y += 30;
		}
		else if (ind instanceof ITree) {
			new ControlTextCentered(this, y, "§oSappiness: " + Binnie.Genetics.treeBreedingSystem.getAlleleName(EnumTreeChromosome.SAPPINESS, ind.getGenome().getActiveAllele(EnumTreeChromosome.SAPPINESS))).setColor(this.getColor());
			y += 20;
		}
		else {
			y += 10;
		}
		if (ind instanceof IBee) {
			final IBee bee = (IBee) ind;
			final int fertility = bee.getGenome().getFertility();
			new ControlTextCentered(this, y, "§l" + fertility + "§r drone" + ((fertility > 1) ? "s" : "") + " per hive").setColor(this.getColor());
			y += 22;
			final int lifespan = bee.getGenome().getLifespan() * PluginApiculture.ticksPerBeeWorkCycle;
			new ControlTextCentered(this, y, "Average Lifespan").setColor(this.getColor());
			y += 12;
			new ControlTextCentered(this, y, "§l" + this.getMCDayString(lifespan * (bee.getGenome().getNocturnal() ? 1.0f : 2.0f))).setColor(this.getColor());
			y += 22;
		}
		if (ind instanceof IButterfly) {
			final IButterfly bee2 = (IButterfly) ind;
			final int fertility = bee2.getGenome().getFertility();
			new ControlTextCentered(this, y, "Lays §l" + fertility + "§r caterpillar" + ((fertility > 1) ? "s" : "") + " before dying").setColor(this.getColor());
			y += 32;
			final float caterpillarMatureTime = 1365.3999f * Math.round(bee2.getGenome().getLifespan() / (bee2.getGenome().getFertility() * 2));
			new ControlTextCentered(this, y, "Caterpillar Gestation").setColor(this.getColor());
			y += 12;
			new ControlTextCentered(this, y, "§l" + this.getMCDayString(caterpillarMatureTime)).setColor(this.getColor());
			y += 22;
			final int speed = (int) (20.0f * bee2.getGenome().getSpeed());
			new ControlTextCentered(this, y, "Flight Speed").setColor(this.getColor());
			y += 12;
			new ControlTextCentered(this, y, "§l" + speed + "§r blocks per second").setColor(this.getColor());
			y += 22;
		}
		if (ind instanceof ITree) {
			final ITree tree = (ITree) ind;
			final int fertility = (int) (1.0f / tree.getGenome().getFertility());
			new ControlTextCentered(this, y, "1 Sapling per §l" + fertility + "§r leave" + ((fertility > 1) ? "s" : "")).setColor(this.getColor());
			y += 22;
			final float butterflySpawn = 1365.3999f / (tree.getGenome().getSappiness() * tree.getGenome().getYield() * 0.5f);
			new ControlTextCentered(this, y, "Butterfies spawn every\n" + this.getTimeString(butterflySpawn) + " per leaf").setColor(this.getColor());
			y += 34;
			new ControlTextCentered(this, y, "Plant Types").setColor(this.getColor());
			y += 12;
			for (final EnumPlantType type : tree.getGenome().getPlantTypes()) {
				new ControlTextCentered(this, y, "§o" + type.name()).setColor(this.getColor());
				y += 12;
			}
		}
		if (ind instanceof IFlower) {
			final IFlower flower = (IFlower) ind;
			final float butterflySpawn2 = 1365.3999f / (flower.getGenome().getSappiness() * 0.2f);
			new ControlTextCentered(this, y, "Butterfies spawn every\n" + this.getTimeString(butterflySpawn2)).setColor(this.getColor());
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
			new ControlTextCentered(this, y, "Average Lifespan").setColor(this.getColor());
			y += 12;
			new ControlTextCentered(this, y, "§l" + this.getMCDayString(lifespan2)).setColor(this.getColor());
			y += 22;
			new ControlTextCentered(this, y, "Seed Dispersal").setColor(this.getColor());
			y += 12;
			new ControlTextCentered(this, y, "§o" + (int) (floweringLifespan / dispersalTime) + " per lifetime").setColor(this.getColor());
			y += 22;
			new ControlTextCentered(this, y, "Pollination").setColor(this.getColor());
			y += 12;
			new ControlTextCentered(this, y, "§o" + (int) (floweringLifespan / pollinateTime) + " per lifetime").setColor(this.getColor());
			y += 22;
		}
		this.setSize(new IPoint(this.w(), y));
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
		return "Biology";
	}
}
