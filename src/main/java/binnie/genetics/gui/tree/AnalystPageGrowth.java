package binnie.genetics.gui.tree;

import net.minecraft.util.text.TextFormatting;

import forestry.api.arboriculture.EnumTreeChromosome;
import forestry.api.arboriculture.ITree;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IIndividual;

import binnie.Binnie;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.ControlTextCentered;
import binnie.core.craftgui.geometry.Area;
import binnie.core.util.I18N;
import binnie.genetics.gui.ControlAnalystPage;

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
			new ControlTextCentered(this, y, I18N.localise("genetics.gui.analyst.growth.mature")).setColour(this.getColour());
			y += 12;
			new ControlTextCentered(this, y, TextFormatting.BOLD + this.getTimeString(Math.round(1373.3999f * mat))).setColour(this.getColour());
			y += 22;
			new ControlTextCentered(this, y, TextFormatting.ITALIC + I18N.localise("genetics.gui.analyst.growth.height") + ": " + Binnie.GENETICS.treeBreedingSystem.getAlleleName(EnumTreeChromosome.HEIGHT, ind.getGenome().getActiveAllele(EnumTreeChromosome.HEIGHT))).setColour(this.getColour());
			y += 12;
			new ControlTextCentered(this, y, TextFormatting.ITALIC + I18N.localise("genetics.gui.analyst.growth.girth") + ": " + Binnie.GENETICS.treeBreedingSystem.getAlleleName(EnumTreeChromosome.GIRTH, ind.getGenome().getActiveAllele(EnumTreeChromosome.GIRTH))).setColour(this.getColour());
		}
	}

	@Override
	public String getTitle() {
		return I18N.localise("genetics.gui.analyst.growth.title");
	}
}
