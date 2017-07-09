package binnie.genetics.gui.bee.database;

import net.minecraft.util.math.Vec3i;

import forestry.api.apiculture.IBee;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.genetics.EnumTolerance;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IAlleleSpecies;
import forestry.core.genetics.alleles.EnumAllele;

import binnie.Binnie;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.ControlText;
import binnie.core.craftgui.controls.ControlTextCentered;
import binnie.core.craftgui.database.DatabaseTab;
import binnie.core.craftgui.database.PageSpecies;
import binnie.core.craftgui.geometry.Area;
import binnie.core.craftgui.geometry.TextJustification;
import binnie.core.util.I18N;
import binnie.genetics.genetics.AlleleHelper;

public class PageSpeciesGenome extends PageSpecies {
	ControlText title;
	ControlText speedText;
	ControlText lifespanText;
	ControlText fertilityText;
	ControlText floweringText;
	ControlText territoryText;
	ControlText nocturnalText;
	ControlText caveDwellingText;
	ControlText tolerantFlyerText;
	ControlText flowerText;
	ControlText effectText;

	public PageSpeciesGenome(final IWidget parent, final DatabaseTab tab) {
		super(parent, tab);
		title = new ControlTextCentered(this, 8, I18N.localise("extrabees.gui.database.tab.species.genome"));
		new ControlText(this, new Area(0, 32, 68, 30), I18N.localise("extrabees.gui.database.tab.species.genome.speed"), TextJustification.TOP_RIGHT);
		new ControlText(this, new Area(0, 44, 68, 30), I18N.localise("extrabees.gui.database.tab.species.genome.lifespan"), TextJustification.TOP_RIGHT);
		new ControlText(this, new Area(0, 56, 68, 30), I18N.localise("extrabees.gui.database.tab.species.genome.fertility"), TextJustification.TOP_RIGHT);
		new ControlText(this, new Area(0, 68, 68, 30), I18N.localise("extrabees.gui.database.tab.species.genome.flowering"), TextJustification.TOP_RIGHT);
		new ControlText(this, new Area(0, 80, 68, 30), I18N.localise("extrabees.gui.database.tab.species.genome.territory"), TextJustification.TOP_RIGHT);
		new ControlText(this, new Area(0, 97, 68, 30), I18N.localise("extrabees.gui.database.tab.species.genome.behavior"), TextJustification.TOP_RIGHT);
		new ControlText(this, new Area(0, 109, 68, 30), I18N.localise("extrabees.gui.database.tab.species.genome.sunlight"), TextJustification.TOP_RIGHT);
		new ControlText(this, new Area(0, 121, 68, 30), I18N.localise("extrabees.gui.database.tab.species.genome.rain"), TextJustification.TOP_RIGHT);
		new ControlText(this, new Area(0, 138, 68, 30), I18N.localise("extrabees.gui.database.tab.species.genome.flower"), TextJustification.TOP_RIGHT);
		new ControlText(this, new Area(0, 155, 68, 30), I18N.localise("extrabees.gui.database.tab.species.genome.effect"), TextJustification.TOP_RIGHT);
		final int x = 72;
		this.speedText = new ControlText(this, new Area(x, 32, 72, 30), "", TextJustification.TOP_LEFT);
		this.lifespanText = new ControlText(this, new Area(x, 44, 72, 30), "", TextJustification.TOP_LEFT);
		this.fertilityText = new ControlText(this, new Area(x, 56, 72, 30), "", TextJustification.TOP_LEFT);
		this.floweringText = new ControlText(this, new Area(x, 68, 72, 30), "", TextJustification.TOP_LEFT);
		this.territoryText = new ControlText(this, new Area(x, 80, 72, 30), "", TextJustification.TOP_LEFT);
		this.nocturnalText = new ControlText(this, new Area(x, 97, 72, 30), "", TextJustification.TOP_LEFT);
		this.caveDwellingText = new ControlText(this, new Area(x, 109, 72, 30), "", TextJustification.TOP_LEFT);
		this.tolerantFlyerText = new ControlText(this, new Area(x, 121, 72, 30), "", TextJustification.TOP_LEFT);
		this.flowerText = new ControlText(this, new Area(x, 138, 72, 30), "", TextJustification.TOP_LEFT);
		this.effectText = new ControlText(this, new Area(x, 155, 72, 30), "", TextJustification.TOP_LEFT);
	}

	public static String rateFlowering(final int flowering) {
		if (flowering >= 99) {
			return AlleleHelper.toDisplay(EnumAllele.Flowering.MAXIMUM);
		}
		if (flowering >= 35) {
			return AlleleHelper.toDisplay(EnumAllele.Flowering.FASTEST);
		}
		if (flowering >= 30) {
			return AlleleHelper.toDisplay(EnumAllele.Flowering.FASTER);
		}
		if (flowering >= 25) {
			return AlleleHelper.toDisplay(EnumAllele.Flowering.FAST);
		}
		if (flowering >= 20) {
			return AlleleHelper.toDisplay(EnumAllele.Flowering.AVERAGE);
		}
		if (flowering >= 15) {
			return AlleleHelper.toDisplay(EnumAllele.Flowering.SLOW);
		}
		if (flowering >= 10) {
			return AlleleHelper.toDisplay(EnumAllele.Flowering.SLOWER);
		}
		return AlleleHelper.toDisplay(EnumAllele.Flowering.SLOWEST);
	}

	public static String rateSpeed(final float speed) {
		if (speed >= 1.7f) {
			return AlleleHelper.toDisplay(EnumAllele.Speed.FASTEST);
		}
		if (speed >= 1.4f) {
			return AlleleHelper.toDisplay(EnumAllele.Speed.FASTER);
		}
		if (speed >= 1.2f) {
			return AlleleHelper.toDisplay(EnumAllele.Speed.FAST);
		}
		if (speed >= 1.0f) {
			return AlleleHelper.toDisplay(EnumAllele.Speed.NORMAL);
		}
		if (speed >= 0.8f) {
			return AlleleHelper.toDisplay(EnumAllele.Speed.SLOW);
		}
		if (speed >= 0.6f) {
			return AlleleHelper.toDisplay(EnumAllele.Speed.SLOWER);
		}
		return AlleleHelper.toDisplay(EnumAllele.Speed.SLOWEST);
	}

	public static String rateLifespan(final int life) {
		if (life >= 70) {
			return AlleleHelper.toDisplay(EnumAllele.Lifespan.LONGEST);
		}
		if (life >= 60) {
			return AlleleHelper.toDisplay(EnumAllele.Lifespan.LONGER);
		}
		if (life >= 50) {
			return AlleleHelper.toDisplay(EnumAllele.Lifespan.LONG);
		}
		if (life >= 45) {
			return AlleleHelper.toDisplay(EnumAllele.Lifespan.ELONGATED);
		}
		if (life >= 40) {
			return AlleleHelper.toDisplay(EnumAllele.Lifespan.NORMAL);
		}
		if (life >= 35) {
			return AlleleHelper.toDisplay(EnumAllele.Lifespan.SHORTENED);
		}
		if (life >= 30) {
			return AlleleHelper.toDisplay(EnumAllele.Lifespan.SHORT);
		}
		if (life >= 20) {
			return AlleleHelper.toDisplay(EnumAllele.Lifespan.SHORTER);
		}
		return AlleleHelper.toDisplay(EnumAllele.Lifespan.SHORTEST);
	}

	public static String tolerated(final boolean t) {
		if (t) {
			return I18N.localise("extrabees.gui.database.tab.species.genome.tolerated");
		}
		return AlleleHelper.toDisplay(EnumTolerance.NONE);
	}

	@Override
	public void onValueChanged(final IAlleleSpecies species) {
		final IAllele[] template = Binnie.GENETICS.getBeeRoot().getTemplate(species.getUID());
		if (template != null) {
			final IBeeGenome genome = Binnie.GENETICS.getBeeRoot().templateAsGenome(template);
			final IBee bee = Binnie.GENETICS.getBeeRoot().getBee(genome);
			this.speedText.setValue(rateSpeed(genome.getSpeed()));
			this.lifespanText.setValue(rateLifespan(genome.getLifespan()));
			this.fertilityText.setValue(I18N.localise("extrabees.gui.database.tab.species.genome.children", genome.getFertility()));
			this.floweringText.setValue(rateFlowering(genome.getFlowering()));
			final Vec3i area = genome.getTerritory();
			this.territoryText.setValue(area.getX() + "x" + area.getY() + "x" + area.getZ());
			String behavior = I18N.localise("extrabees.gui.database.tab.species.genome.daytime");
			if (genome.getPrimary().isNocturnal()) {
				behavior = I18N.localise("extrabees.gui.database.tab.species.genome.nighttime");
			}
			if (genome.getNeverSleeps()) {
				behavior = I18N.localise("extrabees.gui.database.tab.species.genome.allDay");
			}
			this.nocturnalText.setValue(behavior);
			if (genome.getCaveDwelling()) {
				this.caveDwellingText.setValue(I18N.localise("extrabees.gui.database.tab.species.genome.notNeeded"));
			} else {
				this.caveDwellingText.setValue(I18N.localise("extrabees.gui.database.tab.species.genome.required"));
			}
			this.tolerantFlyerText.setValue(tolerated(genome.getToleratesRain()));
			if (genome.getFlowerProvider() != null) {
				this.flowerText.setValue(genome.getFlowerProvider().getDescription());
			} else {
				this.flowerText.setValue(AlleleHelper.toDisplay(EnumTolerance.NONE));
			}
			this.effectText.setValue(genome.getEffect().getName());
		}
	}
}
