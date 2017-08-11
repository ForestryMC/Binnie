package binnie.genetics.machine.craftgui;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import binnie.core.api.genetics.IBreedingSystem;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IChromosomeType;

import binnie.core.Binnie;
import binnie.core.genetics.Gene;
import binnie.core.gui.IWidget;
import binnie.core.gui.controls.ControlText;
import binnie.core.gui.controls.core.Control;
import binnie.core.gui.controls.core.IControlValue;
import binnie.core.gui.geometry.Point;
import binnie.core.gui.minecraft.Window;
import binnie.genetics.genetics.GeneTracker;

public class ControlGeneScroll extends Control implements IControlValue<IBreedingSystem> {
	private String filter;
	@Nullable
	private IBreedingSystem system;

	protected ControlGeneScroll(final IWidget parent, final int x, final int y, final int w, final int h) {
		super(parent, x, y, w, h);
		this.filter = "";
		this.system = null;
	}

	public void setFilter(final String filter) {
		this.filter = filter.toLowerCase();
		this.refresh();
	}

	public void setGenes(final IBreedingSystem system) {
		this.system = system;
		this.refresh();
	}

	public void refresh() {
		if (system == null) {
			return;
		}
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
			new ControlText(this, new Point(x, y), this.system.getChromosomeName(entry.getKey()));
			y += 12;
			for (final IAllele allele : discovered) {
				if (x + 18 > this.getSize().xPos()) {
					y += 20;
					x = 0;
				}
				new ControlGene(this, x, y, new Gene(allele, entry.getKey(), this.system.getSpeciesRoot()));
				x += 18;
			}
			y += 24;
		}
		this.setSize(new Point(this.getSize().xPos(), y));
	}

	@Override
	public IBreedingSystem getValue() {
		return this.system;
	}

	@Override
	public void setValue(final IBreedingSystem system) {
		this.setGenes(system);
	}
}
