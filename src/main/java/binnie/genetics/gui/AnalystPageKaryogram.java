// 
// Decompiled by Procyon v0.5.30
// 

package binnie.genetics.gui;

import binnie.core.genetics.BreedingSystem;
import forestry.api.genetics.ISpeciesRoot;
import forestry.api.genetics.IChromosomeType;
import binnie.Binnie;
import forestry.api.genetics.AlleleManager;
import binnie.core.craftgui.controls.ControlTextCentered;
import forestry.api.genetics.IIndividual;
import binnie.core.craftgui.geometry.IArea;
import binnie.core.craftgui.IWidget;
import net.minecraft.util.EnumChatFormatting;

public class AnalystPageKaryogram extends ControlAnalystPage
{
	public AnalystPageKaryogram(final IWidget parent, final IArea area, final IIndividual ind) {
		super(parent, area);
		this.setColor(10040319);
		int y = 4;
		new ControlTextCentered(this, y, EnumChatFormatting.UNDERLINE + "Karyogram").setColor(this.getColor());
		y += 16;
		y += 8;
		final ISpeciesRoot root = AlleleManager.alleleRegistry.getSpeciesRoot(ind.getClass());
		final BreedingSystem system = Binnie.Genetics.getSystem(root);
		final int maxBiomePerLine = (int) ((this.w() + 4.0f - 16.0f) / 22.0f);
		float karygramX = (this.w() - (Math.min(maxBiomePerLine, system.getActiveKaryotype().size()) * 18 - 4)) / 2.0f;
		int dx = 0;
		int dy = 0;
		int rem = system.getActiveKaryotype().size();
		for (final IChromosomeType type : system.getActiveKaryotype()) {
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
		return "Karyogram";
	}
}
