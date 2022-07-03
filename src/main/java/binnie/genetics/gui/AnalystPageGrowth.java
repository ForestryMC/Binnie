package binnie.genetics.gui;

import binnie.Binnie;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.ControlTextCentered;
import binnie.core.craftgui.geometry.IArea;
import binnie.core.util.I18N;
import forestry.api.arboriculture.EnumTreeChromosome;
import forestry.api.arboriculture.ITree;
import forestry.api.genetics.IIndividual;
import net.minecraft.util.EnumChatFormatting;

public class AnalystPageGrowth extends ControlAnalystPage {
    public AnalystPageGrowth(IWidget parent, IArea area, IIndividual ind) {
        super(parent, area);
        setColor(0x333333);
        int y = 4;
        new ControlTextCentered(this, y, EnumChatFormatting.UNDERLINE + getTitle()).setColor(getColor());

        y += 12;
        if (ind instanceof ITree) {
            ITree tree = (ITree) ind;
            int mat = tree.getGenome().getMaturationTime();
            new ControlTextCentered(this, y, I18N.localise("genetics.gui.analyst.growth.mature")).setColor(getColor());

            y += 12;
            new ControlTextCentered(this, y, EnumChatFormatting.BOLD + getTimeString(1373.3999f * mat))
                    .setColor(getColor());

            y += 22;
            String alleleName = Binnie.Genetics.treeBreedingSystem.getAlleleName(
                    EnumTreeChromosome.HEIGHT, ind.getGenome().getActiveAllele(EnumTreeChromosome.HEIGHT));
            new ControlTextCentered(
                            this,
                            y,
                            EnumChatFormatting.ITALIC + I18N.localise("genetics.gui.analyst.growth.height", alleleName))
                    .setColor(getColor());

            y += 12;
            alleleName = Binnie.Genetics.treeBreedingSystem.getAlleleName(
                    EnumTreeChromosome.GIRTH, ind.getGenome().getActiveAllele(EnumTreeChromosome.GIRTH));
            new ControlTextCentered(
                            this,
                            y,
                            EnumChatFormatting.ITALIC + I18N.localise("genetics.gui.analyst.growth.girth", alleleName))
                    .setColor(getColor());

            y += 20;
            new ControlTextCentered(this, y, I18N.localise("genetics.gui.analyst.growth.conditions"))
                    .setColor(getColor());

            y += 12;
            new ControlTextCentered(
                            this,
                            y,
                            EnumChatFormatting.ITALIC
                                    + tree.getGenome().getGrowthProvider().getDescription())
                    .setColor(getColor());

            y += 12;
            for (String s : tree.getGenome().getGrowthProvider().getInfo()) {
                new ControlTextCentered(this, y, EnumChatFormatting.ITALIC + s).setColor(getColor());
                y += 12;
            }
        }
    }

    @Override
    public String getTitle() {
        return I18N.localise("genetics.gui.analyst.growth");
    }
}
