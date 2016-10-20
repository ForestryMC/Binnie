package binnie.genetics.genetics;

import binnie.core.genetics.BreedingSystem;
import forestry.api.genetics.*;

import java.util.*;

public class GeneticEngineeringSystem {
    BreedingSystem breedingSystem;
    public Map<IChromosomeType, List<IAllele>> chromosomeMap;
    static List<IAllele> speciesList;

    public GeneticEngineeringSystem(final BreedingSystem system) {
        this.chromosomeMap = new HashMap<IChromosomeType, List<IAllele>>();
        this.breedingSystem = system;
        for (final IIndividual indiv : this.breedingSystem.getSpeciesRoot().getIndividualTemplates()) {
            for (final IChromosomeType chromosome : this.breedingSystem.getSpeciesRoot().getKaryotype()) {
                if (!this.chromosomeMap.containsKey(chromosome)) {
                    this.chromosomeMap.put(chromosome, new ArrayList<IAllele>());
                }
                try {
                    final IAllele a1 = indiv.getGenome().getActiveAllele(chromosome);
                    final IAllele a2 = indiv.getGenome().getInactiveAllele(chromosome);
                    if (a1 != null) {
                        this.chromosomeMap.get(chromosome).add(a1);
                    }
                    if (a2 != null) {
                        this.chromosomeMap.get(chromosome).add(a2);
                    }
                } catch (Exception ex) {
                }
            }
        }
        for (final IChromosomeType chromosome2 : this.breedingSystem.getSpeciesRoot().getKaryotype()) {
            final List<IAllele> alleles = this.getAlleles(chromosome2);
            final TreeSet<IAllele> set = new TreeSet<IAllele>(new ComparatorAllele());
            set.addAll(alleles);
            final List<IAllele> list = new ArrayList<IAllele>();
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
                return Float.valueOf(((IAlleleFloat) o1).getValue()).compareTo(Float.valueOf(((IAlleleFloat) o2).getValue()));
            }
            if (o1 instanceof IAlleleInteger && o2 instanceof IAlleleInteger) {
                return Integer.valueOf(((IAlleleInteger) o1).getValue()).compareTo(Integer.valueOf(((IAlleleInteger) o2).getValue()));
            }
            return o1.getUID().compareTo(o2.getUID());
        }
    }
}
