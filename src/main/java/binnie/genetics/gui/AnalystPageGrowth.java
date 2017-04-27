// 
// Decompiled by Procyon v0.5.30
// 

package binnie.genetics.gui;

import forestry.api.genetics.IAlleleSpecies;
import forestry.api.arboriculture.EnumTreeChromosome;
import binnie.Binnie;
import forestry.api.arboriculture.ITree;
import binnie.core.craftgui.controls.ControlTextCentered;
import forestry.api.genetics.IIndividual;
import binnie.core.craftgui.geometry.IArea;
import binnie.core.craftgui.IWidget;
import net.minecraft.util.EnumChatFormatting;

public class AnalystPageGrowth extends ControlAnalystPage
{
	public AnalystPageGrowth(final IWidget parent, final IArea area, final IIndividual ind) {
		super(parent, area);
		this.setColor(3355443);
		int y = 4;
		final IAlleleSpecies species = ind.getGenome().getPrimary();
		new ControlTextCentered(this, y, EnumChatFormatting.UNDERLINE + "Growth").setColor(this.getColor());
		y += 12;
		if (ind instanceof ITree) {
			final ITree tree = (ITree) ind;
			final int mat = tree.getGenome().getMaturationTime();
			new ControlTextCentered(this, y, "Saplings mature in").setColor(this.getColor());
			y += 12;
			new ControlTextCentered(this, y, EnumChatFormatting.BOLD + this.getTimeString(1373.3999f * mat)).setColor(this.getColor());
			y += 22;
			new ControlTextCentered(this, y, EnumChatFormatting.ITALIC + "Height: " + Binnie.Genetics.treeBreedingSystem.getAlleleName(EnumTreeChromosome.HEIGHT, ind.getGenome().getActiveAllele(EnumTreeChromosome.HEIGHT))).setColor(this.getColor());
			y += 12;
			new ControlTextCentered(this, y, EnumChatFormatting.ITALIC + "Girth: " + Binnie.Genetics.treeBreedingSystem.getAlleleName(EnumTreeChromosome.GIRTH, ind.getGenome().getActiveAllele(EnumTreeChromosome.GIRTH))).setColor(this.getColor());
			y += 20;
			new ControlTextCentered(this, y, "Growth Conditions").setColor(this.getColor());
			y += 12;
			new ControlTextCentered(this, y, EnumChatFormatting.ITALIC + tree.getGenome().getGrowthProvider().getDescription()).setColor(this.getColor());
			y += 12;
			for (final String s : tree.getGenome().getGrowthProvider().getInfo()) {
				new ControlTextCentered(this, y, EnumChatFormatting.ITALIC + s).setColor(this.getColor());
				y += 12;
			}
		}
	}

	@Override
	public String getTitle() {
		return "Growth";
	}
}
