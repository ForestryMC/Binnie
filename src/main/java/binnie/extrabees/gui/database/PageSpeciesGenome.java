package binnie.extrabees.gui.database;

import binnie.Binnie;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.ControlText;
import binnie.core.craftgui.controls.ControlTextCentered;
import binnie.core.craftgui.database.DatabaseTab;
import binnie.core.craftgui.database.PageSpecies;
import binnie.core.craftgui.geometry.IArea;
import binnie.core.craftgui.geometry.TextJustification;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IAlleleSpecies;

public class PageSpeciesGenome extends PageSpecies {
	protected ControlText title;
	protected ControlText speedText;
	protected ControlText lifespanText;
	protected ControlText fertilityText;
	protected ControlText floweringText;
	protected ControlText territoryText;
	protected ControlText nocturnalText;
	protected ControlText caveDwellingText;
	protected ControlText tolerantFlyerText;
	protected ControlText flowerText;
	protected ControlText effectText;

	public PageSpeciesGenome(IWidget parent, DatabaseTab tab) {
		super(parent, tab);
		title = new ControlTextCentered(this, 8.0f, "Genome");
		new ControlText(this, new IArea(0.0f, 32.0f, 68.0f, 30.0f), "Speed:", TextJustification.TOP_RIGHT);
		new ControlText(this, new IArea(0.0f, 44.0f, 68.0f, 30.0f), "Lifespan:", TextJustification.TOP_RIGHT);
		new ControlText(this, new IArea(0.0f, 56.0f, 68.0f, 30.0f), "Fertility:", TextJustification.TOP_RIGHT);
		new ControlText(this, new IArea(0.0f, 68.0f, 68.0f, 30.0f), "Flowering:", TextJustification.TOP_RIGHT);
		new ControlText(this, new IArea(0.0f, 80.0f, 68.0f, 30.0f), "Territory:", TextJustification.TOP_RIGHT);
		new ControlText(this, new IArea(0.0f, 97.0f, 68.0f, 30.0f), "Behavior:", TextJustification.TOP_RIGHT);
		new ControlText(this, new IArea(0.0f, 109.0f, 68.0f, 30.0f), "Sunlight:", TextJustification.TOP_RIGHT);
		new ControlText(this, new IArea(0.0f, 121.0f, 68.0f, 30.0f), "Rain:", TextJustification.TOP_RIGHT);
		new ControlText(this, new IArea(0.0f, 138.0f, 68.0f, 30.0f), "Flower:", TextJustification.TOP_RIGHT);
		new ControlText(this, new IArea(0.0f, 155.0f, 68.0f, 30.0f), "Effect:", TextJustification.TOP_RIGHT);
		int x = 72;

		speedText = new ControlText(this, new IArea(x, 32.0f, 72.0f, 30.0f), "", TextJustification.TOP_LEFT);
		lifespanText = new ControlText(this, new IArea(x, 44.0f, 72.0f, 30.0f), "", TextJustification.TOP_LEFT);
		fertilityText = new ControlText(this, new IArea(x, 56.0f, 72.0f, 30.0f), "", TextJustification.TOP_LEFT);
		floweringText = new ControlText(this, new IArea(x, 68.0f, 72.0f, 30.0f), "", TextJustification.TOP_LEFT);
		territoryText = new ControlText(this, new IArea(x, 80.0f, 72.0f, 30.0f), "", TextJustification.TOP_LEFT);
		nocturnalText = new ControlText(this, new IArea(x, 97.0f, 72.0f, 30.0f), "", TextJustification.TOP_LEFT);
		caveDwellingText = new ControlText(this, new IArea(x, 109.0f, 72.0f, 30.0f), "", TextJustification.TOP_LEFT);
		tolerantFlyerText = new ControlText(this, new IArea(x, 121.0f, 72.0f, 30.0f), "", TextJustification.TOP_LEFT);
		flowerText = new ControlText(this, new IArea(x, 138.0f, 72.0f, 30.0f), "", TextJustification.TOP_LEFT);
		effectText = new ControlText(this, new IArea(x, 155.0f, 72.0f, 30.0f), "", TextJustification.TOP_LEFT);
	}

	public static String rateFlowering(int flowering) {
		if (flowering >= 99) {
			return "Maximum";
		}
		if (flowering >= 35) {
			return "Fastest";
		}
		if (flowering >= 30) {
			return "Faster";
		}
		if (flowering >= 25) {
			return "Fast";
		}
		if (flowering >= 20) {
			return "Normal";
		}
		if (flowering >= 15) {
			return "Slow";
		}
		if (flowering >= 10) {
			return "Slower";
		}
		return "Slowest";
	}

	public static String rateSpeed(float speed) {
		if (speed >= 1.7f) {
			return "Fastest";
		}
		if (speed >= 1.4f) {
			return "Faster";
		}
		if (speed >= 1.2f) {
			return "Fast";
		}
		if (speed >= 1.0f) {
			return "Normal";
		}
		if (speed >= 0.8f) {
			return "Slow";
		}
		if (speed >= 0.6f) {
			return "Slower";
		}
		return "Slowest";
	}

	public static String rateLifespan(int life) {
		if (life >= 70) {
			return "Longest";
		}
		if (life >= 60) {
			return "Longer";
		}
		if (life >= 50) {
			return "Long";
		}
		if (life >= 45) {
			return "Elongated";
		}
		if (life >= 40) {
			return "Normal";
		}
		if (life >= 35) {
			return "Shortened";
		}
		if (life >= 30) {
			return "Short";
		}
		if (life >= 20) {
			return "Shorter";
		}
		return "Shortest";
	}

	public static String tolerated(boolean t) {
		if (t) {
			return "Tolerated";
		}
		return "Not Tolerated";
	}

	@Override
	public void onValueChanged(IAlleleSpecies species) {
		IAllele[] template = Binnie.Genetics.getBeeRoot().getTemplate(species.getUID());
		if (template == null) {
			return;
		}

		IBeeGenome genome = Binnie.Genetics.getBeeRoot().templateAsGenome(template);
		speedText.setValue(rateSpeed(genome.getSpeed()));
		lifespanText.setValue(rateLifespan(genome.getLifespan()));
		fertilityText.setValue(genome.getFertility() + " children");
		floweringText.setValue(rateFlowering(genome.getFlowering()));
		int[] area = genome.getTerritory();
		territoryText.setValue(area[0] + "x" + area[1] + "x" + area[2]);
		String behavior = "Daytime";

		if (genome.getPrimary().isNocturnal()) {
			behavior = "Nighttime";
		}
		if (genome.getNocturnal()) {
			behavior = "All Day";
		}

		nocturnalText.setValue(behavior);
		if (genome.getCaveDwelling()) {
			caveDwellingText.setValue("Not Needed");
		} else {
			caveDwellingText.setValue("Required");
		}

		tolerantFlyerText.setValue(tolerated(genome.getTolerantFlyer()));
		if (genome.getFlowerProvider() != null) {
			flowerText.setValue(genome.getFlowerProvider().getDescription());
		} else {
			flowerText.setValue("None");
		}
		effectText.setValue(genome.getEffect().getName());
	}
}
