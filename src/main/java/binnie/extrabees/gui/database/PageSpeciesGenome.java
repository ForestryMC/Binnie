package binnie.extrabees.gui.database;

import binnie.Binnie;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.ControlText;
import binnie.core.craftgui.controls.ControlTextCentered;
import binnie.core.craftgui.database.DatabaseTab;
import binnie.core.craftgui.database.PageSpecies;
import binnie.core.craftgui.geometry.Area;
import binnie.core.craftgui.geometry.TextJustification;
import forestry.api.apiculture.IBee;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IAlleleSpecies;
import net.minecraft.util.math.Vec3i;

public class PageSpeciesGenome extends PageSpecies {
	ControlText pageSpeciesGenome_Title;
	ControlText pageSpeciesGenome_SpeedText;
	ControlText pageSpeciesGenome_LifespanText;
	ControlText pageSpeciesGenome_FertilityText;
	ControlText pageSpeciesGenome_FloweringText;
	ControlText pageSpeciesGenome_TerritoryText;
	ControlText pageSpeciesGenome_NocturnalText;
	ControlText pageSpeciesGenome_CaveDwellingText;
	ControlText pageSpeciesGenome_TolerantFlyerText;
	ControlText pageSpeciesGenome_FlowerText;
	ControlText pageSpeciesGenome_EffectText;

	public PageSpeciesGenome(final IWidget parent, final DatabaseTab tab) {
		super(parent, tab);
		this.pageSpeciesGenome_Title = new ControlTextCentered(this, 8, "Genome");
		new ControlText(this, new Area(0, 32, 68, 30), "Speed:", TextJustification.TopRight);
		new ControlText(this, new Area(0, 44, 68, 30), "Lifespan:", TextJustification.TopRight);
		new ControlText(this, new Area(0, 56, 68, 30), "Fertility:", TextJustification.TopRight);
		new ControlText(this, new Area(0, 68, 68, 30), "Flowering:", TextJustification.TopRight);
		new ControlText(this, new Area(0, 80, 68, 30), "Territory:", TextJustification.TopRight);
		new ControlText(this, new Area(0, 97, 68, 30), "Behavior:", TextJustification.TopRight);
		new ControlText(this, new Area(0, 109, 68, 30), "Sunlight:", TextJustification.TopRight);
		new ControlText(this, new Area(0, 121, 68, 30), "Rain:", TextJustification.TopRight);
		new ControlText(this, new Area(0, 138, 68, 30), "Flower:", TextJustification.TopRight);
		new ControlText(this, new Area(0, 155, 68, 30), "Effect:", TextJustification.TopRight);
		final int x = 72;
		this.pageSpeciesGenome_SpeedText = new ControlText(this, new Area(x, 32, 72, 30), "", TextJustification.TopLeft);
		this.pageSpeciesGenome_LifespanText = new ControlText(this, new Area(x, 44, 72, 30), "", TextJustification.TopLeft);
		this.pageSpeciesGenome_FertilityText = new ControlText(this, new Area(x, 56, 72, 30), "", TextJustification.TopLeft);
		this.pageSpeciesGenome_FloweringText = new ControlText(this, new Area(x, 68, 72, 30), "", TextJustification.TopLeft);
		this.pageSpeciesGenome_TerritoryText = new ControlText(this, new Area(x, 80, 72, 30), "", TextJustification.TopLeft);
		this.pageSpeciesGenome_NocturnalText = new ControlText(this, new Area(x, 97, 72, 30), "", TextJustification.TopLeft);
		this.pageSpeciesGenome_CaveDwellingText = new ControlText(this, new Area(x, 109, 72, 30), "", TextJustification.TopLeft);
		this.pageSpeciesGenome_TolerantFlyerText = new ControlText(this, new Area(x, 121, 72, 30), "", TextJustification.TopLeft);
		this.pageSpeciesGenome_FlowerText = new ControlText(this, new Area(x, 138, 72, 30), "", TextJustification.TopLeft);
		this.pageSpeciesGenome_EffectText = new ControlText(this, new Area(x, 155, 72, 30), "", TextJustification.TopLeft);
	}

	@Override
	public void onValueChanged(final IAlleleSpecies species) {
		final IAllele[] template = Binnie.GENETICS.getBeeRoot().getTemplate(species.getUID());
		if (template != null) {
			final IBeeGenome genome = Binnie.GENETICS.getBeeRoot().templateAsGenome(template);
			final IBee bee = Binnie.GENETICS.getBeeRoot().getBee(genome);
			this.pageSpeciesGenome_SpeedText.setValue(rateSpeed(genome.getSpeed()));
			this.pageSpeciesGenome_LifespanText.setValue(rateLifespan(genome.getLifespan()));
			this.pageSpeciesGenome_FertilityText.setValue(genome.getFertility() + " children");
			this.pageSpeciesGenome_FloweringText.setValue(rateFlowering(genome.getFlowering()));
			final Vec3i area = genome.getTerritory();
			this.pageSpeciesGenome_TerritoryText.setValue(area.getX() + "x" + area.getY() + "x" + area.getZ());
			String behavior = "Daytime";
			if (genome.getPrimary().isNocturnal()) {
				behavior = "Nighttime";
			}
			if (genome.getNeverSleeps()) {
				behavior = "All Day";
			}
			this.pageSpeciesGenome_NocturnalText.setValue(behavior);
			if (genome.getCaveDwelling()) {
				this.pageSpeciesGenome_CaveDwellingText.setValue("Not Needed");
			} else {
				this.pageSpeciesGenome_CaveDwellingText.setValue("Required");
			}
			this.pageSpeciesGenome_TolerantFlyerText.setValue(tolerated(genome.getToleratesRain()));
			if (genome.getFlowerProvider() != null) {
				this.pageSpeciesGenome_FlowerText.setValue(genome.getFlowerProvider().getDescription());
			} else {
				this.pageSpeciesGenome_FlowerText.setValue("None");
			}
			this.pageSpeciesGenome_EffectText.setValue(genome.getEffect().getName());
		}
	}

	public static String rateFlowering(final int flowering) {
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

	public static String rateSpeed(final float speed) {
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

	public static String rateLifespan(final int life) {
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

	public static String tolerated(final boolean t) {
		if (t) {
			return "Tolerated";
		}
		return "Not Tolerated";
	}
}
