package binnie.genetics.machine.craftgui;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import forestry.api.genetics.IAllele;
import forestry.api.genetics.IChromosomeType;

import binnie.core.Binnie;
import binnie.core.api.genetics.IBreedingSystem;
import binnie.core.api.gui.IWidget;
import binnie.core.genetics.Gene;
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

	protected ControlGeneScroll(IWidget parent, int x, int y, int w, int h) {
		super(parent, x, y, w, h);
		this.filter = "";
		this.system = null;
	}

	public void setFilter(String filter) {
		this.filter = filter.toLowerCase();
		this.refresh();
	}

	public void setGenes(IBreedingSystem system) {
		this.system = system;
		this.refresh();
	}

	public void refresh() {
		if (system == null) {
			return;
		}
		this.deleteAllChildren();
		GeneTracker tracker = GeneTracker.getTracker(Window.get(this).getWorld(), Window.get(this).getUsername());
		Map<IChromosomeType, List<IAllele>> genes = Binnie.GENETICS.getChromosomeMap(this.system.getSpeciesRoot());
		int x = 0;
		int y = 0;
		boolean master = ((WindowGeneBank) Window.get(this)).isMaster();
		for (Map.Entry<IChromosomeType, List<IAllele>> entry : genes.entrySet()) {
			List<IAllele> discovered = new ArrayList<>();
			for (IAllele allele : entry.getValue()) {
				Gene gene = new Gene(allele, entry.getKey(), this.system.getSpeciesRoot());
				if ((master || tracker.isSequenced(new Gene(allele, entry.getKey(), this.system.getSpeciesRoot()))) && gene.getName().toLowerCase().contains(this.filter)) {
					discovered.add(allele);
				}
			}
			if (discovered.size() == 0) {
				continue;
			}
			x = 0;
			new ControlText(this, new Point(x, y), this.system.getChromosomeName(entry.getKey()));
			y += 12;
			for (IAllele allele : discovered) {
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
	public void setValue(IBreedingSystem system) {
		this.setGenes(system);
	}
}
