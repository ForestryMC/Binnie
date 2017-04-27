package binnie.genetics.gui;

import binnie.Binnie;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.ControlTextCentered;
import binnie.core.craftgui.geometry.IArea;
import forestry.api.arboriculture.EnumTreeChromosome;
import forestry.api.arboriculture.ITree;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IIndividual;
import net.minecraft.util.EnumChatFormatting;

public class AnalystPageGrowth extends ControlAnalystPage {
	public AnalystPageGrowth(IWidget parent, IArea area, IIndividual ind) {
		super(parent, area);
		setColor(3355443);
		int y = 4;
		IAlleleSpecies species = ind.getGenome().getPrimary();
		new ControlTextCentered(this, y, EnumChatFormatting.UNDERLINE + "Growth").setColor(getColor());
		y += 12;
		if (ind instanceof ITree) {
			ITree tree = (ITree) ind;
			int mat = tree.getGenome().getMaturationTime();
			new ControlTextCentered(this, y, "Saplings mature in").setColor(getColor());
			y += 12;
			new ControlTextCentered(this, y, EnumChatFormatting.BOLD + getTimeString(1373.3999f * mat)).setColor(getColor());
			y += 22;
			new ControlTextCentered(this, y, EnumChatFormatting.ITALIC + "Height: " + Binnie.Genetics.treeBreedingSystem.getAlleleName(EnumTreeChromosome.HEIGHT, ind.getGenome().getActiveAllele(EnumTreeChromosome.HEIGHT))).setColor(getColor());
			y += 12;
			new ControlTextCentered(this, y, EnumChatFormatting.ITALIC + "Girth: " + Binnie.Genetics.treeBreedingSystem.getAlleleName(EnumTreeChromosome.GIRTH, ind.getGenome().getActiveAllele(EnumTreeChromosome.GIRTH))).setColor(getColor());
			y += 20;
			new ControlTextCentered(this, y, "Growth Conditions").setColor(getColor());
			y += 12;
			new ControlTextCentered(this, y, EnumChatFormatting.ITALIC + tree.getGenome().getGrowthProvider().getDescription()).setColor(getColor());
			y += 12;
			for (String s : tree.getGenome().getGrowthProvider().getInfo()) {
				new ControlTextCentered(this, y, EnumChatFormatting.ITALIC + s).setColor(getColor());
				y += 12;
			}
		}
	}

	@Override
	public String getTitle() {
		return "Growth";
	}
}
