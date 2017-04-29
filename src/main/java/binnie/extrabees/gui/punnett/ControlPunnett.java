package binnie.extrabees.gui.punnett;

import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.ControlText;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.geometry.IArea;
import binnie.core.craftgui.geometry.TextJustification;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.ISpeciesRoot;

import java.util.LinkedList;
import java.util.List;

public class ControlPunnett extends Control {
	protected static int boxWidth = 80;
	protected static int boxHeight = 28;

	protected ControlPunnett(IWidget parent, float x, float y) {
		super(parent, x, y, ControlPunnett.boxWidth * 3, ControlPunnett.boxWidth * 3);
	}

	@Override
	public void onRenderBackground() {
		CraftGUI.Render.solid(new IArea(0.0f, ControlPunnett.boxHeight, ControlPunnett.boxWidth * 3, 1.0f), 11184810);
		CraftGUI.Render.solid(new IArea(ControlPunnett.boxWidth / 2.0f, ControlPunnett.boxHeight * 2, ControlPunnett.boxWidth * 2.5f, 1.0f), 11184810);
		CraftGUI.Render.solid(new IArea(ControlPunnett.boxWidth, 0.0f, 1.0f, ControlPunnett.boxHeight * 3), 11184810);
		CraftGUI.Render.solid(new IArea(ControlPunnett.boxWidth * 2, ControlPunnett.boxHeight / 2.0f, 1.0f, ControlPunnett.boxHeight * 2.5f), 11184810);
	}

	// TODO unused method?
	public void setup(IChromosomeType chromosome, IIndividual ind1, IIndividual ind2, ISpeciesRoot root) {
		deleteAllChildren();
		if (chromosome == null || ind1 == null || ind2 == null || root == null) {
			return;
		}

		IAllele primary1 = ind1.getGenome().getActiveAllele(chromosome);
		IAllele primary2 = ind2.getGenome().getActiveAllele(chromosome);
		IAllele secondary1 = ind1.getGenome().getInactiveAllele(chromosome);
		IAllele secondary2 = ind2.getGenome().getInactiveAllele(chromosome);
		int x = 1;
		int y;

		for (IAllele allele1 : new IAllele[]{primary1, secondary1}) {
			y = 1;
			for (IAllele allele2 : new IAllele[]{primary2, secondary2}) {
				List<IAllele> alleles = new LinkedList<IAllele>();
				if (allele1.isDominant() && !allele2.isDominant()) {
					alleles.add(allele1);
				} else if (allele2.isDominant() && !allele1.isDominant()) {
					alleles.add(allele2);
				} else {
					alleles.add(allele1);
					if (allele1 != allele2) {
						alleles.add(allele2);
					}
				}

				String text = "";
				for (IAllele allele3 : alleles) {
					text = text + allele3.getName() + ": " + 25.0f / alleles.size() + "%\n";
				}
				new ControlText(this, new IArea(x * ControlPunnett.boxWidth, ControlPunnett.boxHeight * y, ControlPunnett.boxWidth, ControlPunnett.boxHeight), text, TextJustification.TopCenter).setColor(11184810);
				y++;
			}
			x++;
		}
		new ControlText(this, new IArea(ControlPunnett.boxWidth, 0.0f, ControlPunnett.boxWidth, ControlPunnett.boxHeight), "\n" + primary1.getName(), TextJustification.TopCenter).setColor(11184810);
		new ControlText(this, new IArea(ControlPunnett.boxWidth * 2, 0.0f, ControlPunnett.boxWidth, ControlPunnett.boxHeight), "\n" + secondary1.getName(), TextJustification.TopCenter).setColor(11184810);
		new ControlText(this, new IArea(0.0f, ControlPunnett.boxHeight, ControlPunnett.boxWidth, ControlPunnett.boxHeight), primary2.getName(), TextJustification.TopCenter).setColor(11184810);
		new ControlText(this, new IArea(0.0f, ControlPunnett.boxHeight * 2, ControlPunnett.boxWidth, ControlPunnett.boxHeight), primary2.getName(), TextJustification.TopCenter).setColor(11184810);
	}
}
