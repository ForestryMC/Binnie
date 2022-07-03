package binnie.genetics.machine.acclimatiser;

import binnie.core.genetics.Gene;
import binnie.core.genetics.Tolerance;
import binnie.genetics.machine.inoculator.Inoculator;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAlleleTolerance;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.IGenome;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.ISpeciesRoot;
import java.util.Random;
import net.minecraft.item.ItemStack;

public class ToleranceSystem {
    public String uid;
    public IChromosomeType chromosomeOrdinal;
    public ToleranceType type;

    ToleranceSystem(String uid, IChromosomeType chromosomeOrdinal, ToleranceType type) {
        this.uid = uid;
        this.chromosomeOrdinal = chromosomeOrdinal;
        this.type = type;
    }

    public boolean canAlter(ItemStack stack, ItemStack acclim) {
        IIndividual member = AlleleManager.alleleRegistry.getIndividual(stack);
        IGenome genome = member.getGenome();
        IAlleleTolerance tolAllele = (IAlleleTolerance) genome.getActiveAllele(chromosomeOrdinal);
        Tolerance tol = Tolerance.get(tolAllele.getValue());
        float effect = type.getEffect(acclim);
        return (effect > 0.0f && tol.getBounds()[1] < 5) || (effect < 0.0f && tol.getBounds()[0] > -5);
    }

    public ItemStack alter(ItemStack stack, ItemStack acc) {
        Random rand = new Random();
        float effect = type.getEffect(acc);
        if (rand.nextFloat() > Math.abs(effect)) {
            return stack;
        }

        IIndividual member = AlleleManager.alleleRegistry.getIndividual(stack);
        IGenome genome = member.getGenome();
        IAlleleTolerance tolAllele = (IAlleleTolerance) genome.getActiveAllele(chromosomeOrdinal);
        Tolerance tol = Tolerance.get(tolAllele.getValue());
        Tolerance newTol = Acclimatiser.alterTolerance(tol, effect);
        if (rand.nextFloat() > 1.0f / (-newTol.getBounds()[0] + newTol.getBounds()[1])) {
            return stack;
        }

        ISpeciesRoot root = AlleleManager.alleleRegistry.getSpeciesRoot(stack);
        Inoculator.setGene(new Gene(newTol.getAllele(), chromosomeOrdinal, root), stack, rand.nextInt(2));
        return stack;
    }
}
