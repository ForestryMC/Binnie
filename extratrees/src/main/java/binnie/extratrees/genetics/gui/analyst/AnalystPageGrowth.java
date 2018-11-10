package binnie.extratrees.genetics.gui.analyst;

import net.minecraft.util.text.TextFormatting;

import forestry.api.arboriculture.EnumTreeChromosome;
import forestry.api.arboriculture.ITree;
import forestry.api.arboriculture.TreeManager;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IIndividual;

import binnie.core.Binnie;
import binnie.core.api.genetics.IBreedingSystem;
import binnie.core.api.gui.IArea;
import binnie.core.api.gui.ITitledWidget;
import binnie.core.api.gui.IWidget;
import binnie.core.gui.controls.ControlTextCentered;
import binnie.core.gui.controls.core.Control;
import binnie.core.util.I18N;
import binnie.core.util.TimeUtil;
import binnie.genetics.api.analyst.AnalystConstants;

public class AnalystPageGrowth extends Control implements ITitledWidget {
	public AnalystPageGrowth(IWidget parent, IArea area, IIndividual ind) {
		super(parent, area);
		setColor(3355443);
		int y = 4;
		IAlleleSpecies species = ind.getGenome().getPrimary();
		new ControlTextCentered(this, y, TextFormatting.UNDERLINE + getTitle()).setColor(getColor());
		y += 12;
		if (ind instanceof ITree) {
			ITree tree = (ITree) ind;
			int mat = tree.getGenome().getMaturationTime();
			new ControlTextCentered(this, y, I18N.localise(AnalystConstants.GROWTH_KEY + ".mature")).setColor(getColor());
			y += 12;
			new ControlTextCentered(this, y, TextFormatting.BOLD + TimeUtil.getTimeString(Math.round(1373.3999f * mat))).setColor(getColor());
			y += 22;
			IBreedingSystem treeSystem = Binnie.GENETICS.getSystem(TreeManager.treeRoot);
			new ControlTextCentered(this, y, TextFormatting.ITALIC + I18N.localise(AnalystConstants.GROWTH_KEY + ".height") + ": " + treeSystem.getAlleleName(EnumTreeChromosome.HEIGHT, ind.getGenome().getActiveAllele(EnumTreeChromosome.HEIGHT))).setColor(getColor());
			y += 12;
			new ControlTextCentered(this, y, TextFormatting.ITALIC + I18N.localise(AnalystConstants.GROWTH_KEY + ".girth") + ": " + treeSystem.getAlleleName(EnumTreeChromosome.GIRTH, ind.getGenome().getActiveAllele(EnumTreeChromosome.GIRTH))).setColor(getColor());
		}
	}

	@Override
	public String getTitle() {
		return I18N.localise(AnalystConstants.GROWTH_KEY + ".title");
	}
}
