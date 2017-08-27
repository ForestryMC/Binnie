package binnie.genetics.gui.punnett;

import java.util.LinkedList;
import java.util.List;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.genetics.IAllele;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.ISpeciesRoot;

import binnie.core.api.gui.IWidget;
import binnie.core.gui.controls.ControlText;
import binnie.core.gui.controls.core.Control;
import binnie.core.gui.geometry.Area;
import binnie.core.gui.geometry.TextJustification;
import binnie.core.gui.renderer.RenderUtil;

public class ControlPunnett extends Control {
	private static final int BOX_WIDTH = 80;
	private static final int BOX_HEIGHT = 28;

	protected ControlPunnett(IWidget parent, int x, int y) {
		super(parent, x, y, ControlPunnett.BOX_WIDTH * 3, ControlPunnett.BOX_WIDTH * 3);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderBackground(int guiWidth, int guiHeight) {
		RenderUtil.drawSolidRect(new Area(0, ControlPunnett.BOX_HEIGHT, ControlPunnett.BOX_WIDTH * 3, 1), 11184810);
		RenderUtil.drawSolidRect(new Area(ControlPunnett.BOX_WIDTH / 2, ControlPunnett.BOX_HEIGHT * 2, Math.round(ControlPunnett.BOX_WIDTH * 2.5f), 1), 11184810);
		RenderUtil.drawSolidRect(new Area(ControlPunnett.BOX_WIDTH, 0, 1, ControlPunnett.BOX_HEIGHT * 3), 11184810);
		RenderUtil.drawSolidRect(new Area(ControlPunnett.BOX_WIDTH * 2, ControlPunnett.BOX_HEIGHT / 2, 1, Math.round(ControlPunnett.BOX_HEIGHT * 2.5f)), 11184810);
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
				new ControlText(this, new Area(x * ControlPunnett.BOX_WIDTH, ControlPunnett.BOX_HEIGHT * y, ControlPunnett.BOX_WIDTH, ControlPunnett.BOX_HEIGHT), text.toString(), TextJustification.TOP_CENTER).setColor(11184810);
				++y;
			}
			++x;
		}
		new ControlText(this, new Area(ControlPunnett.BOX_WIDTH, 0, ControlPunnett.BOX_WIDTH, ControlPunnett.BOX_HEIGHT), "\n" + primary1.getAlleleName(), TextJustification.TOP_CENTER).setColor(11184810);
		new ControlText(this, new Area(ControlPunnett.BOX_WIDTH * 2, 0, ControlPunnett.BOX_WIDTH, ControlPunnett.BOX_HEIGHT), "\n" + secondary1.getAlleleName(), TextJustification.TOP_CENTER).setColor(11184810);
		new ControlText(this, new Area(0, ControlPunnett.BOX_HEIGHT, ControlPunnett.BOX_WIDTH, ControlPunnett.BOX_HEIGHT), primary2.getAlleleName(), TextJustification.TOP_CENTER).setColor(11184810);
		new ControlText(this, new Area(0, ControlPunnett.BOX_HEIGHT * 2, ControlPunnett.BOX_WIDTH, ControlPunnett.BOX_HEIGHT), primary2.getAlleleName(), TextJustification.TOP_CENTER).setColor(11184810);
	}
}
