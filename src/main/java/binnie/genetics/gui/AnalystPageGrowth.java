package binnie.genetics.gui;

import binnie.Binnie;
import binnie.craftgui.controls.ControlTextCentered;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.IArea;
import forestry.api.arboriculture.EnumTreeChromosome;
import forestry.api.arboriculture.ITree;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IIndividual;

public class AnalystPageGrowth extends ControlAnalystPage {
	public AnalystPageGrowth(final IWidget parent, final IArea area, final IIndividual ind) {
		super(parent, area);
		this.setColour(3355443);
		int y = 4;
		final IAlleleSpecies species = ind.getGenome().getPrimary();
		new ControlTextCentered(this, y, "§nGrowth").setColour(this.getColour());
		y += 12;
		if (ind instanceof ITree) {
			final ITree tree = (ITree) ind;
			final int mat = tree.getGenome().getMaturationTime();
			new ControlTextCentered(this, y, "Saplings mature in").setColour(this.getColour());
			y += 12;
			new ControlTextCentered(this, y, "§l" + this.getTimeString(1373.3999f * mat)).setColour(this.getColour());
			y += 22;
			new ControlTextCentered(this, y, "§oHeight: " + Binnie.Genetics.treeBreedingSystem.getAlleleName(EnumTreeChromosome.HEIGHT, ind.getGenome().getActiveAllele(EnumTreeChromosome.HEIGHT))).setColour(this.getColour());
			y += 12;
			new ControlTextCentered(this, y, "§oGirth: " + Binnie.Genetics.treeBreedingSystem.getAlleleName(EnumTreeChromosome.GIRTH, ind.getGenome().getActiveAllele(EnumTreeChromosome.GIRTH))).setColour(this.getColour());
			y += 20;
			new ControlTextCentered(this, y, "Growth Conditions").setColour(this.getColour());
			y += 12;
			//new ControlTextCentered(this, y, "§o" + tree.getGenome().getGrowthProvider().getDescription()).setColour(this.getColour());
			y += 12;
//			for (final String s : tree.getGenome().getGrowthProvider().getInfo()) {
//				new ControlTextCentered(this, y, "§o" + s).setColour(this.getColour());
//				y += 12;
//			}
		}
	}

	@Override
	public String getTitle() {
		return "Growth";
	}
}
