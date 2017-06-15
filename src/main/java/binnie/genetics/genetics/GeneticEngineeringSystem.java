package binnie.genetics.genetics;

import com.google.common.base.Throwables;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import forestry.api.genetics.IAllele;
import forestry.api.genetics.IAlleleFloat;
import forestry.api.genetics.IAlleleInteger;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.ISpeciesRoot;

import binnie.core.genetics.BreedingSystem;

public class GeneticEngineeringSystem {
	static List<IAllele> speciesList;
	public Map<IChromosomeType, List<IAllele>> chromosomeMap;
	BreedingSystem breedingSystem;

	public GeneticEngineeringSystem(final BreedingSystem system) {
		this.chromosomeMap = new HashMap<>();
		this.breedingSystem = system;
		for (final IIndividual indiv : this.breedingSystem.getSpeciesRoot().getIndividualTemplates()) {
			for (final IChromosomeType chromosome : this.breedingSystem.getSpeciesRoot().getKaryotype()) {
				if (!this.chromosomeMap.containsKey(chromosome)) {
					this.chromosomeMap.put(chromosome, new ArrayList<>());
				}
				try {
					final IAllele a1 = indiv.getGenome().getActiveAllele(chromosome);
					final IAllele a2 = indiv.getGenome().getInactiveAllele(chromosome);
					this.chromosomeMap.get(chromosome).add(a1);
					this.chromosomeMap.get(chromosome).add(a2);
				} catch (Exception ex) {
					throw Throwables.propagate(ex);
				}
			}
		}
		for (final IChromosomeType chromosome2 : this.breedingSystem.getSpeciesRoot().getKaryotype()) {
			final List<IAllele> alleles = this.getAlleles(chromosome2);
			final TreeSet<IAllele> set = new TreeSet<>(new ComparatorAllele());
			set.addAll(alleles);
			final List<IAllele> list = new ArrayList<>();
			list.addAll(set);
			this.chromosomeMap.put(chromosome2, list);
		}
	}

	public List<IAllele> getAlleles(final IChromosomeType chromosome) {
		return this.chromosomeMap.get(chromosome);
	}

	public ISpeciesRoot getSpeciesRoot() {
		return this.breedingSystem.getSpeciesRoot();
	}

	class ComparatorAllele implements Comparator<IAllele> {
		@Override
		public int compare(final IAllele o1, final IAllele o2) {
			if (o1 instanceof IAlleleFloat && o2 instanceof IAlleleFloat) {
				return Float.valueOf(((IAlleleFloat) o1).getValue()).compareTo(((IAlleleFloat) o2).getValue());
			}
			if (o1 instanceof IAlleleInteger && o2 instanceof IAlleleInteger) {
				return Integer.valueOf(((IAlleleInteger) o1).getValue()).compareTo(((IAlleleInteger) o2).getValue());
			}
			return o1.getUID().compareTo(o2.getUID());
		}
	}
}
