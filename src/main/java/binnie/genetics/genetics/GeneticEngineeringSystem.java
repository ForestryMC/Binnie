package binnie.genetics.genetics;

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

	public GeneticEngineeringSystem(BreedingSystem system) {
		chromosomeMap = new HashMap<>();
		breedingSystem = system;
		for (IIndividual indiv : breedingSystem.getSpeciesRoot().getIndividualTemplates()) {
			for (IChromosomeType chromosome : breedingSystem.getSpeciesRoot().getKaryotype()) {
				if (!chromosomeMap.containsKey(chromosome)) {
					chromosomeMap.put(chromosome, new ArrayList<>());
				}
				IAllele a1 = indiv.getGenome().getActiveAllele(chromosome);
				IAllele a2 = indiv.getGenome().getInactiveAllele(chromosome);
				chromosomeMap.get(chromosome).add(a1);
				chromosomeMap.get(chromosome).add(a2);
			}
		}
		for (IChromosomeType chromosome2 : breedingSystem.getSpeciesRoot().getKaryotype()) {
			List<IAllele> alleles = getAlleles(chromosome2);
			TreeSet<IAllele> set = new TreeSet<>(new ComparatorAllele());
			set.addAll(alleles);
			List<IAllele> list = new ArrayList<>();
			list.addAll(set);
			chromosomeMap.put(chromosome2, list);
		}
	}

	public List<IAllele> getAlleles(IChromosomeType chromosome) {
		return chromosomeMap.get(chromosome);
	}

	public ISpeciesRoot getSpeciesRoot() {
		return breedingSystem.getSpeciesRoot();
	}

	class ComparatorAllele implements Comparator<IAllele> {
		@Override
		public int compare(IAllele o1, IAllele o2) {
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
