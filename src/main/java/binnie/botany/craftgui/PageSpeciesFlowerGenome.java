package binnie.botany.craftgui;

import binnie.Binnie;
import binnie.botany.api.EnumFlowerChromosome;
import binnie.botany.api.EnumFlowerStage;
import binnie.botany.api.IAlleleFlowerSpecies;
import binnie.botany.api.IFlower;
import binnie.botany.api.IFlowerGenome;
import binnie.botany.core.BotanyCore;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.ControlText;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.controls.scroll.ControlScrollableContent;
import binnie.core.craftgui.database.DatabaseTab;
import binnie.core.craftgui.database.PageSpecies;
import binnie.core.craftgui.geometry.IArea;
import binnie.core.craftgui.geometry.IPoint;
import binnie.core.craftgui.geometry.TextJustification;
import binnie.core.craftgui.minecraft.control.ControlItemDisplay;
import binnie.core.util.I18N;
import binnie.genetics.genetics.AlleleHelper;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IIndividual;
import net.minecraft.item.ItemStack;

public class PageSpeciesFlowerGenome extends PageSpecies {
    public PageSpeciesFlowerGenome(IWidget parent, DatabaseTab tab) {
        super(parent, tab);
    }

    @Override
    public void onValueChanged(IAlleleSpecies species) {
        deleteAllChildren();
        IAllele[] template = Binnie.Genetics.getFlowerRoot().getTemplate(species.getUID());
        if (template == null) {
            return;
        }

        IFlower tree = Binnie.Genetics.getFlowerRoot().templateAsIndividual(template);
        if (tree == null) {
            return;
        }

        IFlowerGenome genome = tree.getGenome();
        IAlleleFlowerSpecies treeSpecies = genome.getPrimary();
        int w = 144;
        int h = 176;

        new ControlText(
                this,
                new IArea(0.0f, 4.0f, w, 16.0f),
                I18N.localise("botany.gui.database.tab.genome"),
                TextJustification.MIDDLE_CENTER);
        ControlScrollableContent scrollable = new ControlScrollableContent(this, 4.0f, 20.0f, w - 8, h - 8 - 16, 12.0f);
        Control contents = new Control(scrollable, 0.0f, 0.0f, w - 8 - 12, h - 8 - 16);

        int tw = w - 8 - 12;
        int w2 = 55;
        int w3 = tw - 50;
        int y = 0;
        int th = 14;
        int valueX = w2 + 4;
        new ControlText(
                contents,
                new IArea(0.0f, y, w2, th),
                I18N.localise("botany.gui.database.tab.genome.temp"),
                TextJustification.MIDDLE_RIGHT);
        new ControlText(
                contents,
                new IArea(valueX, y, w3, th),
                AlleleHelper.toDisplay(treeSpecies.getTemperature()),
                TextJustification.MIDDLE_LEFT);
        y += th;

        new ControlText(
                contents,
                new IArea(0.0f, y, w2, th),
                I18N.localise("botany.gui.database.tab.genome.moist"),
                TextJustification.MIDDLE_RIGHT);
        new ControlText(
                contents,
                new IArea(valueX, y, w3, th),
                AlleleHelper.toDisplay(treeSpecies.getMoisture()),
                TextJustification.MIDDLE_LEFT);
        y += th;

        new ControlText(
                contents,
                new IArea(0.0f, y, w2, th),
                I18N.localise("botany.gui.database.tab.genome.ph"),
                TextJustification.MIDDLE_RIGHT);
        new ControlText(
                contents,
                new IArea(valueX, y, w3, th),
                AlleleHelper.toDisplay(treeSpecies.getPH()),
                TextJustification.MIDDLE_LEFT);
        y += th;

        new ControlText(
                contents,
                new IArea(0.0f, y, w2, th),
                I18N.localise("botany.gui.database.tab.genome.fertility"),
                TextJustification.MIDDLE_RIGHT);
        new ControlText(
                contents, new IArea(valueX, y, w3, th), genome.getFertility() + "x", TextJustification.MIDDLE_LEFT);
        y += th;

        float lifespan = genome.getLifespan() * 68.27f / genome.getAgeChance() / 24000.0f;
        String lifespanValue = String.format("%.2f", lifespan);
        new ControlText(
                contents,
                new IArea(0.0f, y, w2, th),
                I18N.localise("botany.gui.database.tab.genome.lifespan"),
                TextJustification.MIDDLE_RIGHT);
        new ControlText(
                contents,
                new IArea(valueX, y, w3, th),
                I18N.localise("botany.gui.database.tab.genome.lifespan.value", lifespanValue),
                TextJustification.MIDDLE_LEFT);
        y += th;

        new ControlText(
                contents,
                new IArea(0.0f, y, w2, th),
                I18N.localise("botany.gui.database.tab.genome.nectar"),
                TextJustification.MIDDLE_RIGHT);
        new ControlText(
                contents,
                new IArea(valueX, y, w3, th),
                genome.getActiveAllele(EnumFlowerChromosome.SAPPINESS).getName(),
                TextJustification.MIDDLE_LEFT);
        y += th;

        int x = w2;
        int tot = 0;
        for (IIndividual vid : BotanyCore.getFlowerRoot().getIndividualTemplates()) {
            if (vid.getGenome().getPrimary() == treeSpecies) {
                if (tot > 0 && tot % 3 == 0) {
                    x -= 54;
                    y += 18;
                }
                ItemStack stack = BotanyCore.getFlowerRoot().getMemberStack(vid, EnumFlowerStage.FLOWER.ordinal());
                ControlItemDisplay display = new ControlItemDisplay(contents, x, y);
                display.setItemStack(stack);
                tot++;
                x += 18;
            }
        }

        int numOfLines = 1 + (tot - 1) / 3;
        new ControlText(
                contents,
                new IArea(0.0f, y - (numOfLines - 1) * 18, w2, 4 + 18 * numOfLines),
                I18N.localise("botany.gui.database.tab.genome.varieties"),
                TextJustification.MIDDLE_RIGHT);
        y += th;
        contents.setSize(new IPoint(contents.size().x(), y));
        scrollable.setScrollableContent(contents);
    }
}
