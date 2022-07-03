package binnie.genetics.gui;

import binnie.Binnie;
import binnie.botany.api.IFlower;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.ControlTextCentered;
import binnie.core.craftgui.geometry.IArea;
import binnie.core.craftgui.geometry.IPoint;
import binnie.core.craftgui.minecraft.control.ControlIconDisplay;
import binnie.core.util.I18N;
import binnie.genetics.item.ModuleItem;
import forestry.api.apiculture.IBee;
import forestry.api.arboriculture.EnumTreeChromosome;
import forestry.api.arboriculture.ITree;
import forestry.api.genetics.IIndividual;
import forestry.api.lepidopterology.IButterfly;
import forestry.plugins.PluginApiculture;
import java.text.DecimalFormat;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.EnumPlantType;

public class AnalystPageBiology extends ControlAnalystPage {
    private static final float SPAWN_KOEF = 1365.3999f;

    public AnalystPageBiology(IWidget parent, IArea area, IIndividual ind) {
        super(parent, area);
        setColor(0x006666);
        int y = 4;
        new ControlTextCentered(this, y, EnumChatFormatting.UNDERLINE + getTitle()).setColor(getColor());
        y += 12;

        if (ind instanceof IBee) {
            IBee bee = (IBee) ind;
            if (bee.getGenome().getNocturnal()) {
                new ControlIconDisplay(this, (w() - 64.0f) / 2.0f, y, ModuleItem.iconAllDay.getIcon())
                        .addTooltip(I18N.localise("genetics.gui.analyst.biology.allDay"));
            } else if (bee.getGenome().getPrimary().isNocturnal()) {
                new ControlIconDisplay(this, (w() - 64.0f) / 2.0f, y, ModuleItem.iconNight.getIcon())
                        .addTooltip(I18N.localise("genetics.gui.analyst.biology.night"));
            } else {
                new ControlIconDisplay(this, (w() - 64.0f) / 2.0f, y, ModuleItem.iconDaytime.getIcon())
                        .addTooltip(I18N.localise("genetics.gui.analyst.biology.day"));
            }

            if (!bee.getGenome().getTolerantFlyer()) {
                new ControlIconDisplay(this, (w() - 64.0f) / 2.0f + 24.0f, y, ModuleItem.iconNoRain.getIcon())
                        .addTooltip(I18N.localise("genetics.gui.analyst.biology.notRain"));
            } else {
                new ControlIconDisplay(this, (w() - 64.0f) / 2.0f + 24.0f, y, ModuleItem.iconRain.getIcon())
                        .addTooltip(I18N.localise("genetics.gui.analyst.biology.rain"));
            }

            if (bee.getGenome().getCaveDwelling()) {
                new ControlIconDisplay(this, (w() - 64.0f) / 2.0f + 48.0f, y, ModuleItem.iconNoSky.getIcon())
                        .addTooltip(I18N.localise("genetics.gui.analyst.biology.underground"));
            } else {
                new ControlIconDisplay(this, (w() - 64.0f) / 2.0f + 48.0f, y, ModuleItem.iconSky.getIcon())
                        .addTooltip(I18N.localise("genetics.gui.analyst.biology.notUnderground"));
            }
            y += 30;
        } else if (ind instanceof IButterfly) {
            IButterfly moth = (IButterfly) ind;
            if (moth.getGenome().getNocturnal()) {
                new ControlIconDisplay(this, (w() - 64.0f) / 2.0f, y, ModuleItem.iconAllDay.getIcon())
                        .addTooltip(I18N.localise("genetics.gui.analyst.biology.allDay"));
            } else if (moth.getGenome().getPrimary().isNocturnal()) {
                new ControlIconDisplay(this, (w() - 64.0f) / 2.0f, y, ModuleItem.iconNight.getIcon())
                        .addTooltip(I18N.localise("genetics.gui.analyst.biology.night"));
            } else {
                new ControlIconDisplay(this, (w() - 64.0f) / 2.0f, y, ModuleItem.iconDaytime.getIcon())
                        .addTooltip(I18N.localise("genetics.gui.analyst.biology.day"));
            }

            if (!moth.getGenome().getTolerantFlyer()) {
                new ControlIconDisplay(this, (w() - 64.0f) / 2.0f + 24.0f, y, ModuleItem.iconNoRain.getIcon())
                        .addTooltip(I18N.localise("genetics.gui.analyst.biology.notRain"));
            } else {
                new ControlIconDisplay(this, (w() - 64.0f) / 2.0f + 24.0f, y, ModuleItem.iconRain.getIcon())
                        .addTooltip(I18N.localise("genetics.gui.analyst.biology.rain"));
            }

            if (moth.getGenome().getFireResist()) {
                new ControlIconDisplay(this, (w() - 64.0f) / 2.0f + 48.0f, y, ModuleItem.iconNoFire.getIcon())
                        .addTooltip(I18N.localise("genetics.gui.analyst.biology.nonflammable"));
            } else {
                new ControlIconDisplay(this, (w() - 64.0f) / 2.0f + 48.0f, y, ModuleItem.iconFire.getIcon())
                        .addTooltip(I18N.localise("genetics.gui.analyst.biology.flammable"));
            }
            y += 30;
        } else if (ind instanceof ITree) {
            String alleleName = Binnie.Genetics.treeBreedingSystem.getAlleleName(
                    EnumTreeChromosome.SAPPINESS, ind.getGenome().getActiveAllele(EnumTreeChromosome.SAPPINESS));
            new ControlTextCentered(
                            this,
                            y,
                            EnumChatFormatting.ITALIC
                                    + I18N.localise("genetics.gui.analyst.biology.sappiness", alleleName))
                    .setColor(getColor());
            y += 20;
        } else {
            y += 10;
        }

        if (ind instanceof IBee) {
            IBee bee = (IBee) ind;
            int fertility = bee.getGenome().getFertility();
            if (fertility > 1) {
                new ControlTextCentered(
                                this,
                                y,
                                EnumChatFormatting.BOLD
                                        + I18N.localise("genetics.gui.analyst.biology.fertility.drones", fertility))
                        .setColor(getColor());
            } else {
                new ControlTextCentered(
                                this,
                                y,
                                EnumChatFormatting.BOLD + I18N.localise("genetics.gui.analyst.biology.fertility.drone"))
                        .setColor(getColor());
            }

            y += 22;
            new ControlTextCentered(this, y, I18N.localise("genetics.gui.analyst.biology.averageLifespan"))
                    .setColor(getColor());

            y += 12;
            int lifespan = bee.getGenome().getLifespan() * PluginApiculture.ticksPerBeeWorkCycle;
            new ControlTextCentered(
                            this,
                            y,
                            EnumChatFormatting.BOLD
                                    + getMCDayString(lifespan * (bee.getGenome().getNocturnal() ? 1.0f : 2.0f)))
                    .setColor(getColor());
            y += 22;
        }

        if (ind instanceof IButterfly) {
            IButterfly bee2 = (IButterfly) ind;
            int fertility = bee2.getGenome().getFertility();
            if (fertility > 1) {
                new ControlTextCentered(
                                this, y, I18N.localise("genetics.gui.analyst.biology.fertility.moths", fertility))
                        .setColor(getColor());
            } else {
                new ControlTextCentered(this, y, I18N.localise("genetics.gui.analyst.biology.fertility.moth"))
                        .setColor(getColor());
            }

            y += 32;
            float caterpillarMatureTime = SPAWN_KOEF
                    * Math.round(
                            bee2.getGenome().getLifespan() / (bee2.getGenome().getFertility() * 2));
            new ControlTextCentered(this, y, I18N.localise("genetics.gui.analyst.biology.caterpillarGestation"))
                    .setColor(getColor());

            y += 12;
            new ControlTextCentered(this, y, EnumChatFormatting.BOLD + getMCDayString(caterpillarMatureTime))
                    .setColor(getColor());

            y += 22;
            int speed = (int) (20.0f * bee2.getGenome().getSpeed());
            new ControlTextCentered(this, y, I18N.localise("genetics.gui.analyst.biology.flightSpeed"))
                    .setColor(getColor());

            y += 12;
            new ControlTextCentered(
                            this,
                            y,
                            EnumChatFormatting.BOLD + I18N.localise("genetics.gui.analyst.biology.blocksPerSec", speed))
                    .setColor(getColor());
            y += 22;
        }

        if (ind instanceof ITree) {
            ITree tree = (ITree) ind;
            int fertility = (int) (1.0f / tree.getGenome().getFertility());
            if (fertility > 1) {
                new ControlTextCentered(
                                this, y, I18N.localise("genetics.gui.analyst.biology.fertility.leaves", fertility))
                        .setColor(getColor());
            } else {
                new ControlTextCentered(this, y, I18N.localise("genetics.gui.analyst.biology.fertility.leaf"))
                        .setColor(getColor());
            }

            y += 22;
            float butterflySpawn = SPAWN_KOEF
                    / (tree.getGenome().getSappiness() * tree.getGenome().getYield() * 0.5f);
            new ControlTextCentered(
                            this,
                            y,
                            I18N.localise(
                                    "genetics.gui.analyst.biology.mothSpawn.perLeaf", getTimeString(butterflySpawn)))
                    .setColor(getColor());

            y += 34;
            new ControlTextCentered(this, y, I18N.localise("genetics.gui.analyst.biology.planTypes"))
                    .setColor(getColor());

            y += 12;
            for (EnumPlantType type : tree.getGenome().getPlantTypes()) {
                new ControlTextCentered(this, y, EnumChatFormatting.ITALIC + type.name()).setColor(getColor());
                y += 12;
            }
        }
        if (ind instanceof IFlower) {
            IFlower flower = (IFlower) ind;
            float butterflySpawn2 = SPAWN_KOEF / (flower.getGenome().getSappiness() * 0.2f);
            new ControlTextCentered(
                            this,
                            y,
                            I18N.localise("genetics.gui.analyst.biology.mothSpawn", getTimeString(butterflySpawn2)))
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

            new ControlTextCentered(this, y, I18N.localise("genetics.gui.analyst.biology.averageLifespan"))
                    .setColor(getColor());
            y += 12;

            new ControlTextCentered(this, y, EnumChatFormatting.BOLD + getMCDayString(lifespan2)).setColor(getColor());
            y += 22;

            new ControlTextCentered(this, y, I18N.localise("genetics.gui.analyst.biology.seedDispersal"))
                    .setColor(getColor());
            y += 12;

            new ControlTextCentered(
                            this,
                            y,
                            EnumChatFormatting.ITALIC
                                    + I18N.localise("genetics.gui.analyst.biology.perLifetime", (int)
                                            (floweringLifespan / dispersalTime)))
                    .setColor(getColor());
            y += 22;

            new ControlTextCentered(this, y, I18N.localise("genetics.gui.analyst.biology.pollination"))
                    .setColor(getColor());
            y += 12;

            new ControlTextCentered(
                            this,
                            y,
                            EnumChatFormatting.ITALIC
                                    + I18N.localise("genetics.gui.analyst.biology.perLifetime", (int)
                                            (floweringLifespan / pollinateTime)))
                    .setColor(getColor());
            y += 22;
        }
        setSize(new IPoint(w(), y));
    }

    private String getMCDayString(float time) {
        float seconds = time / 20.0f;
        float minutes = seconds / 60.0f;
        float days = minutes / 20.0f;
        DecimalFormat df = new DecimalFormat("#.#");
        return I18N.localise("genetics.gui.analyst.biology.mcDays", df.format(days));
    }

    @Override
    public String getTitle() {
        return I18N.localise("genetics.gui.analyst.biology");
    }
}
