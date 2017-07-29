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
	public AnalystPageGrowth(IWidget parent, Area area, IIndividual ind) {
		super(parent, area);
		setColor(3355443);
		int y = 4;
		IAlleleSpecies species = ind.getGenome().getPrimary();
		new ControlTextCentered(this, y, TextFormatting.UNDERLINE + getTitle()).setColor(getColor());
		y += 12;
		if (ind instanceof ITree) {
			ITree tree = (ITree) ind;
			int mat = tree.getGenome().getMaturationTime();
			new ControlTextCentered(this, y, I18N.localise("genetics.gui.analyst.growth.mature")).setColor(getColor());
			y += 12;
			new ControlTextCentered(this, y, TextFormatting.BOLD + getTimeString(Math.round(1373.3999f * mat))).setColor(getColor());
			y += 22;
			new ControlTextCentered(this, y, TextFormatting.ITALIC + I18N.localise("genetics.gui.analyst.growth.height") + ": " + Binnie.GENETICS.treeBreedingSystem.getAlleleName(EnumTreeChromosome.HEIGHT, ind.getGenome().getActiveAllele(EnumTreeChromosome.HEIGHT))).setColor(getColor());
			y += 12;
			new ControlTextCentered(this, y, TextFormatting.ITALIC + I18N.localise("genetics.gui.analyst.growth.girth") + ": " + Binnie.GENETICS.treeBreedingSystem.getAlleleName(EnumTreeChromosome.GIRTH, ind.getGenome().getActiveAllele(EnumTreeChromosome.GIRTH))).setColor(getColor());
		}
	}

	@Override
	public String getTitle() {
		return I18N.localise("genetics.gui.analyst.growth.title");
	}
}
