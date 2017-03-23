package binnie.genetics.gui.tree;

import binnie.Binnie;
import binnie.craftgui.controls.ControlTextCentered;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.Area;
import binnie.genetics.Genetics;
import binnie.genetics.gui.ControlAnalystPage;
import forestry.api.arboriculture.EnumTreeChromosome;
import forestry.api.arboriculture.ITree;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IIndividual;
import net.minecraft.util.text.TextFormatting;

public class AnalystPageGrowth extends ControlAnalystPage {
	public AnalystPageGrowth(final IWidget parent, final Area area, final IIndividual ind) {
		super(parent, area);
		this.setColour(3355443);
		int y = 4;
		final IAlleleSpecies species = ind.getGenome().getPrimary();
		new ControlTextCentered(this, y, TextFormatting.UNDERLINE + getTitle()).setColour(this.getColour());
		y += 12;
		if (ind instanceof ITree) {
			final ITree tree = (ITree) ind;
			final int mat = tree.getGenome().getMaturationTime();
			new ControlTextCentered(this, y, Genetics.proxy.localise("gui.analyst.growth.mature")).setColour(this.getColour());
			y += 12;
			new ControlTextCentered(this, y, TextFormatting.BOLD + this.getTimeString(Math.round(1373.3999f * mat))).setColour(this.getColour());
			y += 22;
			new ControlTextCentered(this, y, TextFormatting.ITALIC + Genetics.proxy.localise("gui.analyst.growth.height") + ": " + Binnie.GENETICS.treeBreedingSystem.getAlleleName(EnumTreeChromosome.HEIGHT, ind.getGenome().getActiveAllele(EnumTreeChromosome.HEIGHT))).setColour(this.getColour());
			y += 12;
			new ControlTextCentered(this, y, TextFormatting.ITALIC + Genetics.proxy.localise("gui.analyst.growth.girth") + ": " + Binnie.GENETICS.treeBreedingSystem.getAlleleName(EnumTreeChromosome.GIRTH, ind.getGenome().getActiveAllele(EnumTreeChromosome.GIRTH))).setColour(this.getColour());
		}
	}

	@Override
	public String getTitle() {
		return Genetics.proxy.localise("gui.analyst.growth.title");
	}
}
