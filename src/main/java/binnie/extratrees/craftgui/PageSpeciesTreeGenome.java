package binnie.extratrees.craftgui;

import binnie.Binnie;
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
import binnie.core.genetics.BreedingSystem;
import binnie.core.util.I18N;
import binnie.extratrees.FakeWorld;
import forestry.api.arboriculture.EnumTreeChromosome;
import forestry.api.arboriculture.IAlleleTreeSpecies;
import forestry.api.arboriculture.ITree;
import forestry.api.arboriculture.ITreeGenome;
import forestry.api.core.ForestryAPI;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IAlleleSpecies;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.common.util.ForgeDirection;

public class PageSpeciesTreeGenome extends PageSpecies {
    public PageSpeciesTreeGenome(IWidget parent, DatabaseTab tab) {
        super(parent, tab);
    }

    @Override
    public void onValueChanged(IAlleleSpecies species) {
        deleteAllChildren();
        IAllele[] template = Binnie.Genetics.getTreeRoot().getTemplate(species.getUID());
        if (template == null) {
            return;
        }

        ITree tree = Binnie.Genetics.getTreeRoot().templateAsIndividual(template);
        if (tree == null) {
            return;
        }

        ItemStack log = null;
        try {
            FakeWorld world = FakeWorld.instance;
            tree.getGenome()
                    .getPrimary()
                    .getGenerator()
                    .setLogBlock(
                            tree.getGenome(),
                            world,
                            0,
                            0,
                            0,
                            ForgeDirection.UP); // getGenome().getPrimary().getGenerator().getWorldGenerator(tree);
            log = world.getWooLog();

        } catch (Exception e) {
            e.printStackTrace();
        }

        ITreeGenome genome = tree.getGenome();
        IAlleleTreeSpecies treeSpecies = genome.getPrimary();
        int w = 144;
        int h = 176;
        new ControlText(this, new IArea(0.0f, 4.0f, w, 16.0f), getValue().toString(), TextJustification.MIDDLE_CENTER);
        ControlScrollableContent scrollable = new ControlScrollableContent(this, 4.0f, 20.0f, w - 8, h - 8 - 16, 12.0f);
        Control contents = new Control(scrollable, 0.0f, 0.0f, w - 8 - 12, h - 8 - 16);
        int tw = w - 8 - 12;
        int w2 = 65;
        int w3 = tw - 50;
        int y = 0;
        int th = 14;
        int th2 = 18;
        BreedingSystem syst = Binnie.Genetics.treeBreedingSystem;
        new ControlText(
                contents,
                new IArea(0.0f, y, w2, th),
                syst.getChromosomeShortName(EnumTreeChromosome.PLANT) + " : ",
                TextJustification.MIDDLE_RIGHT);
        new ControlText(
                contents,
                new IArea(w2, y, w3, th),
                treeSpecies.getPlantType().toString(),
                TextJustification.MIDDLE_LEFT);
        y += th;
        new ControlText(
                contents,
                new IArea(0.0f, y, w2, th),
                I18N.localise("binniecore.gui.temperature.short") + " : ",
                TextJustification.MIDDLE_RIGHT);
        new ControlText(
                contents,
                new IArea(w2, y, w3, th),
                treeSpecies.getTemperature().getName(),
                TextJustification.MIDDLE_LEFT);
        y += th;
        IIcon leaf = treeSpecies.getLeafIcon(false, false);
        IIcon fruit = null;
        int fruitColour = 0xffffff;

        try {
            fruit = ForestryAPI.textureManager.getIcon(
                    genome.getFruitProvider().getIconIndex(genome, null, 0, 0, 0, 100, false));
            fruitColour = genome.getFruitProvider().getColour(genome, null, 0, 0, 0, 100);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (leaf != null) {
            new ControlText(
                    contents,
                    new IArea(0.0f, y, w2, th2),
                    I18N.localise("extratrees.gui.database.leaves") + " : ",
                    TextJustification.MIDDLE_RIGHT);
            new ControlBlockIconDisplay(contents, w2, y, leaf).setColor(treeSpecies.getLeafColour(false));
            if (fruit != null && !treeSpecies.getUID().equals("forestry.treeOak")) {
                new ControlBlockIconDisplay(contents, w2, y, fruit).setColor(fruitColour);
            }
            y += th2;
        }

        // ItemStack log = (genome.getFruitProvider().getProducts().length
        // > 0) ? genome.getFruitProvider().getProducts()[0] : null;
        if (log != null) {
            new ControlText(
                    contents,
                    new IArea(0.0f, y, w2, th2),
                    I18N.localise("extratrees.gui.database.log") + " : ",
                    TextJustification.MIDDLE_RIGHT);
            ControlItemDisplay display = new ControlItemDisplay(contents, w2, y);
            display.setItemStack(log);
            display.setTooltip();
            y += th2;
        }

        new ControlText(
                contents,
                new IArea(0.0f, y, w2, th),
                syst.getChromosomeShortName(EnumTreeChromosome.GROWTH) + " : ",
                TextJustification.MIDDLE_RIGHT);
        new ControlText(
                contents,
                new IArea(w2, y, w3, th),
                genome.getGrowthProvider().getDescription(),
                TextJustification.MIDDLE_LEFT);
        y += th;
        new ControlText(
                contents,
                new IArea(0.0f, y, w2, th),
                syst.getChromosomeShortName(EnumTreeChromosome.HEIGHT) + " : ",
                TextJustification.MIDDLE_RIGHT);
        new ControlText(contents, new IArea(w2, y, w3, th), genome.getHeight() + "x", TextJustification.MIDDLE_LEFT);
        y += th;
        new ControlText(
                contents,
                new IArea(0.0f, y, w2, th),
                syst.getChromosomeShortName(EnumTreeChromosome.FERTILITY) + " : ",
                TextJustification.MIDDLE_RIGHT);
        new ControlText(contents, new IArea(w2, y, w3, th), genome.getFertility() + "x", TextJustification.MIDDLE_LEFT);
        y += th;
        List<ItemStack> fruits = new ArrayList<>();
        Collections.addAll(fruits, genome.getFruitProvider().getProducts());

        if (!fruits.isEmpty()) {
            new ControlText(
                    contents,
                    new IArea(0.0f, y, w2, th2),
                    syst.getChromosomeShortName(EnumTreeChromosome.FRUITS) + " : ",
                    TextJustification.MIDDLE_RIGHT);
            for (ItemStack fruitw : fruits) {
                ControlItemDisplay display2 = new ControlItemDisplay(contents, w2, y);
                display2.setItemStack(fruitw);
                display2.setTooltip();
                y += th2;
            }
        }

        new ControlText(
                contents,
                new IArea(0.0f, y, w2, th),
                syst.getChromosomeShortName(EnumTreeChromosome.YIELD) + " : ",
                TextJustification.MIDDLE_RIGHT);
        new ControlText(contents, new IArea(w2, y, w3, th), genome.getYield() + "x", TextJustification.MIDDLE_LEFT);
        y += th;
        new ControlText(
                contents,
                new IArea(0.0f, y, w2, th),
                syst.getChromosomeShortName(EnumTreeChromosome.SAPPINESS) + " : ",
                TextJustification.MIDDLE_RIGHT);
        new ControlText(contents, new IArea(w2, y, w3, th), genome.getSappiness() + "x", TextJustification.MIDDLE_LEFT);
        y += th;
        new ControlText(
                contents,
                new IArea(0.0f, y, w2, th),
                syst.getChromosomeShortName(EnumTreeChromosome.MATURATION) + " : ",
                TextJustification.MIDDLE_RIGHT);
        new ControlText(
                contents, new IArea(w2, y, w3, th), genome.getMaturationTime() + "x", TextJustification.MIDDLE_LEFT);
        y += th;
        new ControlText(
                contents,
                new IArea(0.0f, y, w2, th),
                syst.getChromosomeShortName(EnumTreeChromosome.GIRTH) + " : ",
                TextJustification.MIDDLE_RIGHT);
        new ControlText(
                contents,
                new IArea(w2, y, w3, th),
                genome.getGirth() + "x" + genome.getGirth(),
                TextJustification.MIDDLE_LEFT);
        y += th;
        contents.setSize(new IPoint(contents.size().x(), y));
        scrollable.setScrollableContent(contents);
    }
}
