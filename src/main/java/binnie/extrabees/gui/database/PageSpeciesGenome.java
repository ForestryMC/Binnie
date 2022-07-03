package binnie.extrabees.gui.database;

import binnie.Binnie;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.ControlText;
import binnie.core.craftgui.controls.ControlTextCentered;
import binnie.core.craftgui.database.DatabaseTab;
import binnie.core.craftgui.database.PageSpecies;
import binnie.core.craftgui.geometry.IArea;
import binnie.core.craftgui.geometry.TextJustification;
import binnie.core.util.I18N;
import binnie.genetics.genetics.AlleleHelper;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.genetics.EnumTolerance;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IAlleleSpecies;
import forestry.core.genetics.alleles.EnumAllele;

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
        title = new ControlTextCentered(this, 8.0f, I18N.localise("extrabees.gui.database.tab.species.genome"));
        new ControlText(
                this,
                new IArea(0.0f, 32.0f, 68.0f, 30.0f),
                I18N.localise("extrabees.gui.database.tab.species.genome.speed"),
                TextJustification.TOP_RIGHT);
        new ControlText(
                this,
                new IArea(0.0f, 44.0f, 68.0f, 30.0f),
                I18N.localise("extrabees.gui.database.tab.species.genome.lifespan"),
                TextJustification.TOP_RIGHT);
        new ControlText(
                this,
                new IArea(0.0f, 56.0f, 68.0f, 30.0f),
                I18N.localise("extrabees.gui.database.tab.species.genome.fertility"),
                TextJustification.TOP_RIGHT);
        new ControlText(
                this,
                new IArea(0.0f, 68.0f, 68.0f, 30.0f),
                I18N.localise("extrabees.gui.database.tab.species.genome.flowering"),
                TextJustification.TOP_RIGHT);
        new ControlText(
                this,
                new IArea(0.0f, 80.0f, 68.0f, 30.0f),
                I18N.localise("extrabees.gui.database.tab.species.genome.territory"),
                TextJustification.TOP_RIGHT);
        new ControlText(
                this,
                new IArea(0.0f, 97.0f, 68.0f, 30.0f),
                I18N.localise("extrabees.gui.database.tab.species.genome.behavior"),
                TextJustification.TOP_RIGHT);
        new ControlText(
                this,
                new IArea(0.0f, 109.0f, 68.0f, 30.0f),
                I18N.localise("extrabees.gui.database.tab.species.genome.sunlight"),
                TextJustification.TOP_RIGHT);
        new ControlText(
                this,
                new IArea(0.0f, 121.0f, 68.0f, 30.0f),
                I18N.localise("extrabees.gui.database.tab.species.genome.rain"),
                TextJustification.TOP_RIGHT);
        new ControlText(
                this,
                new IArea(0.0f, 138.0f, 68.0f, 30.0f),
                I18N.localise("extrabees.gui.database.tab.species.genome.flower"),
                TextJustification.TOP_RIGHT);
        new ControlText(
                this,
                new IArea(0.0f, 155.0f, 68.0f, 30.0f),
                I18N.localise("extrabees.gui.database.tab.species.genome.effect"),
                TextJustification.TOP_RIGHT);
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
            return I18N.localise("extrabees.gui.database.tab.species.genome.tolerated");
        }
        return AlleleHelper.toDisplay(EnumTolerance.NONE);
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
        fertilityText.setValue(
                I18N.localise("extrabees.gui.database.tab.species.genome.children", genome.getFertility()));
        floweringText.setValue(rateFlowering(genome.getFlowering()));
        int[] area = genome.getTerritory();
        territoryText.setValue(area[0] + "x" + area[1] + "x" + area[2]);

        String behavior;
        if (genome.getNocturnal()) {
            behavior = I18N.localise("extrabees.gui.database.tab.species.genome.allDay");
        } else if (genome.getPrimary().isNocturnal()) {
            behavior = I18N.localise("extrabees.gui.database.tab.species.genome.nighttime");
        } else {
            behavior = I18N.localise("extrabees.gui.database.tab.species.genome.daytime");
        }

        nocturnalText.setValue(behavior);
        if (genome.getCaveDwelling()) {
            caveDwellingText.setValue(I18N.localise("extrabees.gui.database.tab.species.genome.notNeeded"));
        } else {
            caveDwellingText.setValue(I18N.localise("extrabees.gui.database.tab.species.genome.required"));
        }

        tolerantFlyerText.setValue(tolerated(genome.getTolerantFlyer()));
        if (genome.getFlowerProvider() != null) {
            flowerText.setValue(genome.getFlowerProvider().getDescription());
        } else {
            flowerText.setValue(AlleleHelper.toDisplay(EnumTolerance.NONE));
        }
        effectText.setValue(genome.getEffect().getName());
    }
}
