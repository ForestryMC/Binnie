package binnie.genetics.gui;

import net.minecraft.util.text.TextFormatting;

import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.ISpeciesRoot;

import binnie.Binnie;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.ControlTextCentered;
import binnie.core.craftgui.geometry.Area;
import binnie.core.genetics.BreedingSystem;
import binnie.core.util.I18N;

public class AnalystPageKaryogram extends ControlAnalystPage {
	public AnalystPageKaryogram(IWidget parent, Area area, IIndividual ind) {
		super(parent, area);
		setColor(10040319);
		int y = 4;
		new ControlTextCentered(this, y, TextFormatting.UNDERLINE + getTitle()).setColor(getColor());
		y += 16;
		y += 8;
		ISpeciesRoot root = AlleleManager.alleleRegistry.getSpeciesRoot(ind);
		BreedingSystem system = Binnie.GENETICS.getSystem(root);
		int maxBiomePerLine = (int) ((width() + 4.0f - 16.0f) / 22.0f);
		int karygramX = (width() - (Math.min(maxBiomePerLine, system.getActiveKaryotype().size()) * 18 - 4)) / 2;
		int dx = 0;
		int dy = 0;
		int rem = system.getActiveKaryotype().size();
		for (IChromosomeType type : system.getActiveKaryotype()) {
			new ControlAnalystChromosome(this, karygramX + dx, y + dy, root, type, ind.getGenome().getActiveAllele(type), ind.getGenome().getInactiveAllele(type));
			dx += 22;
			if (dx >= 22 * maxBiomePerLine - 22) {
				rem -= maxBiomePerLine - 1;
				if (rem < maxBiomePerLine) {
					karygramX += (maxBiomePerLine - 1 - rem) * 22 / 2.0f;
				}
				dx = 0;
				dy += 28;
			}
		}
	}

	@Override
	public String getTitle() {
		return I18N.localise("genetics.gui.analyst.karyogram.title");
	}
}
