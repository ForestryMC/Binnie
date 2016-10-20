package binnie.extrabees.gui.database;

import binnie.Binnie;
import binnie.craftgui.controls.ControlText;
import binnie.craftgui.controls.ControlTextCentered;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.core.geometry.TextJustification;
import binnie.craftgui.mod.database.DatabaseTab;
import binnie.craftgui.mod.database.PageSpecies;
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
        this.pageSpeciesGenome_Title = new ControlTextCentered(this, 8.0f, "Genome");
        new ControlText(this, new IArea(0.0f, 32.0f, 68.0f, 30.0f), "Speed:", TextJustification.TopRight);
        new ControlText(this, new IArea(0.0f, 44.0f, 68.0f, 30.0f), "Lifespan:", TextJustification.TopRight);
        new ControlText(this, new IArea(0.0f, 56.0f, 68.0f, 30.0f), "Fertility:", TextJustification.TopRight);
        new ControlText(this, new IArea(0.0f, 68.0f, 68.0f, 30.0f), "Flowering:", TextJustification.TopRight);
        new ControlText(this, new IArea(0.0f, 80.0f, 68.0f, 30.0f), "Territory:", TextJustification.TopRight);
        new ControlText(this, new IArea(0.0f, 97.0f, 68.0f, 30.0f), "Behavior:", TextJustification.TopRight);
        new ControlText(this, new IArea(0.0f, 109.0f, 68.0f, 30.0f), "Sunlight:", TextJustification.TopRight);
        new ControlText(this, new IArea(0.0f, 121.0f, 68.0f, 30.0f), "Rain:", TextJustification.TopRight);
        new ControlText(this, new IArea(0.0f, 138.0f, 68.0f, 30.0f), "Flower:", TextJustification.TopRight);
        new ControlText(this, new IArea(0.0f, 155.0f, 68.0f, 30.0f), "Effect:", TextJustification.TopRight);
        final int x = 72;
        this.pageSpeciesGenome_SpeedText = new ControlText(this, new IArea(x, 32.0f, 72.0f, 30.0f), "", TextJustification.TopLeft);
        this.pageSpeciesGenome_LifespanText = new ControlText(this, new IArea(x, 44.0f, 72.0f, 30.0f), "", TextJustification.TopLeft);
        this.pageSpeciesGenome_FertilityText = new ControlText(this, new IArea(x, 56.0f, 72.0f, 30.0f), "", TextJustification.TopLeft);
        this.pageSpeciesGenome_FloweringText = new ControlText(this, new IArea(x, 68.0f, 72.0f, 30.0f), "", TextJustification.TopLeft);
        this.pageSpeciesGenome_TerritoryText = new ControlText(this, new IArea(x, 80.0f, 72.0f, 30.0f), "", TextJustification.TopLeft);
        this.pageSpeciesGenome_NocturnalText = new ControlText(this, new IArea(x, 97.0f, 72.0f, 30.0f), "", TextJustification.TopLeft);
        this.pageSpeciesGenome_CaveDwellingText = new ControlText(this, new IArea(x, 109.0f, 72.0f, 30.0f), "", TextJustification.TopLeft);
        this.pageSpeciesGenome_TolerantFlyerText = new ControlText(this, new IArea(x, 121.0f, 72.0f, 30.0f), "", TextJustification.TopLeft);
        this.pageSpeciesGenome_FlowerText = new ControlText(this, new IArea(x, 138.0f, 72.0f, 30.0f), "", TextJustification.TopLeft);
        this.pageSpeciesGenome_EffectText = new ControlText(this, new IArea(x, 155.0f, 72.0f, 30.0f), "", TextJustification.TopLeft);
    }

    @Override
    public void onValueChanged(final IAlleleSpecies species) {
        final IAllele[] template = Binnie.Genetics.getBeeRoot().getTemplate(species.getUID());
        if (template != null) {
            final IBeeGenome genome = Binnie.Genetics.getBeeRoot().templateAsGenome(template);
            final IBee bee = Binnie.Genetics.getBeeRoot().getBee(genome);
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
