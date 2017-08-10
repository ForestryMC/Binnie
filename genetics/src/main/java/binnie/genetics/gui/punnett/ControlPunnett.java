package binnie.genetics.gui.punnett;

import java.util.LinkedList;
import java.util.List;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.genetics.IAllele;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.ISpeciesRoot;

import binnie.core.gui.IWidget;
import binnie.core.gui.controls.ControlText;
import binnie.core.gui.controls.core.Control;
import binnie.core.gui.geometry.Area;
import binnie.core.gui.geometry.TextJustification;
import binnie.core.gui.renderer.RenderUtil;

public class ControlPunnett extends Control {
	static int boxWidth = 80;
	static int boxHeight = 28;

	protected ControlPunnett(IWidget parent, int x, int y) {
		super(parent, x, y, ControlPunnett.boxWidth * 3, ControlPunnett.boxWidth * 3);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderBackground(int guiWidth, int guiHeight) {
		RenderUtil.drawSolidRect(new Area(0, ControlPunnett.boxHeight, ControlPunnett.boxWidth * 3, 1), 11184810);
		RenderUtil.drawSolidRect(new Area(ControlPunnett.boxWidth / 2, ControlPunnett.boxHeight * 2, Math.round(ControlPunnett.boxWidth * 2.5f), 1), 11184810);
		RenderUtil.drawSolidRect(new Area(ControlPunnett.boxWidth, 0, 1, ControlPunnett.boxHeight * 3), 11184810);
		RenderUtil.drawSolidRect(new Area(ControlPunnett.boxWidth * 2, ControlPunnett.boxHeight / 2, 1, Math.round(ControlPunnett.boxHeight * 2.5f)), 11184810);
	}

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
		int y = 1;
		for (IAllele allele1 : new IAllele[]{primary1, secondary1}) {
			y = 1;
			for (IAllele allele2 : new IAllele[]{primary2, secondary2}) {
				List<IAllele> alleles = new LinkedList<>();
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
				StringBuilder text = new StringBuilder();
				for (IAllele allele3 : alleles) {
					text.append(allele3.getAlleleName()).append(": ").append(25.0f / alleles.size()).append("%\n");
				}
				new ControlText(this, new Area(x * ControlPunnett.boxWidth, ControlPunnett.boxHeight * y, ControlPunnett.boxWidth, ControlPunnett.boxHeight), text.toString(), TextJustification.TOP_CENTER).setColor(11184810);
				++y;
			}
			++x;
		}
		new ControlText(this, new Area(ControlPunnett.boxWidth, 0, ControlPunnett.boxWidth, ControlPunnett.boxHeight), "\n" + primary1.getAlleleName(), TextJustification.TOP_CENTER).setColor(11184810);
		new ControlText(this, new Area(ControlPunnett.boxWidth * 2, 0, ControlPunnett.boxWidth, ControlPunnett.boxHeight), "\n" + secondary1.getAlleleName(), TextJustification.TOP_CENTER).setColor(11184810);
		new ControlText(this, new Area(0, ControlPunnett.boxHeight, ControlPunnett.boxWidth, ControlPunnett.boxHeight), primary2.getAlleleName(), TextJustification.TOP_CENTER).setColor(11184810);
		new ControlText(this, new Area(0, ControlPunnett.boxHeight * 2, ControlPunnett.boxWidth, ControlPunnett.boxHeight), primary2.getAlleleName(), TextJustification.TOP_CENTER).setColor(11184810);
	}
}
