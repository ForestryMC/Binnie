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
	static final String PRE_FIX = "genetics.gui.database.tab.species.genome.extrabees.";
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

	public PageSpeciesGenome(IWidget parent, DatabaseTab tab) {
		super(parent, tab);
		title = new ControlTextCentered(this, 8, I18N.localise(PRE_FIX + "title"));
		new ControlText(this, new Area(0, 32, 68, 30), I18N.localise(PRE_FIX + "speed"), TextJustification.TOP_RIGHT);
		new ControlText(this, new Area(0, 44, 68, 30), I18N.localise(PRE_FIX + "lifespan"), TextJustification.TOP_RIGHT);
		new ControlText(this, new Area(0, 56, 68, 30), I18N.localise(PRE_FIX + "fertility"), TextJustification.TOP_RIGHT);
		new ControlText(this, new Area(0, 68, 68, 30), I18N.localise(PRE_FIX + "flowering"), TextJustification.TOP_RIGHT);
		new ControlText(this, new Area(0, 80, 68, 30), I18N.localise(PRE_FIX + "territory"), TextJustification.TOP_RIGHT);
		new ControlText(this, new Area(0, 97, 68, 30), I18N.localise(PRE_FIX + "behavior"), TextJustification.TOP_RIGHT);
		new ControlText(this, new Area(0, 109, 68, 30), I18N.localise(PRE_FIX + "sunlight"), TextJustification.TOP_RIGHT);
		new ControlText(this, new Area(0, 121, 68, 30), I18N.localise(PRE_FIX + "rain"), TextJustification.TOP_RIGHT);
		new ControlText(this, new Area(0, 138, 68, 30), I18N.localise(PRE_FIX + "flower"), TextJustification.TOP_RIGHT);
		new ControlText(this, new Area(0, 155, 68, 30), I18N.localise(PRE_FIX + "effect"), TextJustification.TOP_RIGHT);
		int x = 72;
		speedText = new ControlText(this, new Area(x, 32, 72, 30), "", TextJustification.TOP_LEFT);
		lifespanText = new ControlText(this, new Area(x, 44, 72, 30), "", TextJustification.TOP_LEFT);
		fertilityText = new ControlText(this, new Area(x, 56, 72, 30), "", TextJustification.TOP_LEFT);
		floweringText = new ControlText(this, new Area(x, 68, 72, 30), "", TextJustification.TOP_LEFT);
		territoryText = new ControlText(this, new Area(x, 80, 72, 30), "", TextJustification.TOP_LEFT);
		nocturnalText = new ControlText(this, new Area(x, 97, 72, 30), "", TextJustification.TOP_LEFT);
		caveDwellingText = new ControlText(this, new Area(x, 109, 72, 30), "", TextJustification.TOP_LEFT);
		tolerantFlyerText = new ControlText(this, new Area(x, 121, 72, 30), "", TextJustification.TOP_LEFT);
		flowerText = new ControlText(this, new Area(x, 138, 72, 30), "", TextJustification.TOP_LEFT);
		effectText = new ControlText(this, new Area(x, 155, 72, 30), "", TextJustification.TOP_LEFT);
	}

	public static String rateFlowering(int flowering) {
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

	public static String rateSpeed(float speed) {
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

	public static String rateLifespan(int life) {
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

	public static String tolerated(boolean t) {
		if (t) {
			return I18N.localise(PRE_FIX + "tolerated");
		}
		return AlleleHelper.toDisplay(EnumTolerance.NONE);
	}

	@Override
	public void onValueChanged(IAlleleSpecies species) {
		IAllele[] template = Binnie.GENETICS.getBeeRoot().getTemplate(species.getUID());
		if (template == null) {
			return;
		}

		IBeeGenome genome = Binnie.GENETICS.getBeeRoot().templateAsGenome(template);
		IBee bee = Binnie.GENETICS.getBeeRoot().getBee(genome);
		speedText.setValue(rateSpeed(genome.getSpeed()));
		lifespanText.setValue(rateLifespan(genome.getLifespan()));
		fertilityText.setValue(I18N.localise(PRE_FIX + "children", genome.getFertility()));
		floweringText.setValue(rateFlowering(genome.getFlowering()));
		Vec3i area = genome.getTerritory();
		territoryText.setValue(area.getX() + "x" + area.getY() + "x" + area.getZ());
		String behavior = I18N.localise(PRE_FIX + "daytime");
		if (genome.getPrimary().isNocturnal()) {
			behavior = I18N.localise(PRE_FIX + "nighttime");
		}
		if (genome.getNeverSleeps()) {
			behavior = I18N.localise(PRE_FIX + "allDay");
		}

		nocturnalText.setValue(behavior);
		if (genome.getCaveDwelling()) {
			caveDwellingText.setValue(I18N.localise(PRE_FIX + "notNeeded"));
		} else {
			caveDwellingText.setValue(I18N.localise(PRE_FIX + "required"));
		}

		tolerantFlyerText.setValue(tolerated(genome.getToleratesRain()));
		if (genome.getFlowerProvider() != null) {
			flowerText.setValue(genome.getFlowerProvider().getDescription());
		} else {
			flowerText.setValue(AlleleHelper.toDisplay(EnumTolerance.NONE));
		}
		effectText.setValue(genome.getEffect().getAlleleName());
	}
}
