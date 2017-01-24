package binnie.craftgui.genetics.machine;

import binnie.Binnie;
import binnie.core.genetics.BreedingSystem;
import binnie.core.genetics.Gene;
import binnie.craftgui.controls.ControlText;
import binnie.craftgui.controls.core.Control;
import binnie.craftgui.controls.core.IControlValue;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.minecraft.Window;
import binnie.genetics.genetics.GeneTracker;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IChromosomeType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ControlGeneScroll extends Control implements IControlValue<BreedingSystem> {
	private String filter;
	private BreedingSystem system;

	protected ControlGeneScroll(final IWidget parent, final float x, final float y, final float w, final float h) {
		super(parent, x, y, w, h);
		this.filter = "";
		this.system = null;
	}

	public void setFilter(final String filter) {
		this.filter = filter.toLowerCase();
		this.refresh();
	}

	public void setGenes(final BreedingSystem system) {
		this.system = system;
		this.refresh();
	}

	public void refresh() {
		this.deleteAllChildren();
		final GeneTracker tracker = GeneTracker.getTracker(Window.get(this).getWorld(), Window.get(this).getUsername());
		final Map<IChromosomeType, List<IAllele>> genes = Binnie.GENETICS.getChromosomeMap(this.system.getSpeciesRoot());
		int x = 0;
		int y = 0;
		final boolean isNEI = ((WindowGeneBank) Window.get(this)).isNei;
		for (final Map.Entry<IChromosomeType, List<IAllele>> entry : genes.entrySet()) {
			final List<IAllele> discovered = new ArrayList<>();
			for (final IAllele allele : entry.getValue()) {
				final Gene gene = new Gene(allele, entry.getKey(), this.system.getSpeciesRoot());
				if ((isNEI || tracker.isSequenced(new Gene(allele, entry.getKey(), this.system.getSpeciesRoot()))) && gene.getName().toLowerCase().contains(this.filter)) {
					discovered.add(allele);
				}
			}
			if (discovered.size() == 0) {
				continue;
			}
			x = 0;
			new ControlText(this, new IPoint(x, y), this.system.getChromosomeName(entry.getKey()));
			y += 12;
			for (final IAllele allele : discovered) {
				if (x + 18 > this.getSize().x()) {
					y += 20;
					x = 0;
				}
				new ControlGene(this, x, y).setValue(new Gene(allele, entry.getKey(), this.system.getSpeciesRoot()));
				x += 18;
			}
			y += 24;
		}
		this.setSize(new IPoint(this.getSize().x(), y));
	}

	@Override
	public void setValue(final BreedingSystem system) {
		this.setGenes(system);
	}

	@Override
	public BreedingSystem getValue() {
		return this.system;
	}
}
