package binnie.genetics.machine.acclimatiser;

import java.util.Random;

import binnie.genetics.api.acclimatiser.IToleranceType;
import binnie.genetics.machine.splicer.Splicer;
import net.minecraft.item.ItemStack;

import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAlleleTolerance;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.IGenome;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.ISpeciesRoot;

import binnie.core.genetics.Gene;
import binnie.core.genetics.Tolerance;

class ToleranceSystem {
	private final String speciesRootUid;
	private final IChromosomeType chromosomeType;
	private final IToleranceType type;

	ToleranceSystem(final IChromosomeType chromosomeType, final IToleranceType type) {
		this.speciesRootUid = chromosomeType.getSpeciesRoot().getUID();
		this.chromosomeType = chromosomeType;
		this.type = type;
	}

	public boolean canAlter(final ItemStack stack, final ItemStack acclim) {
		final IIndividual member = AlleleManager.alleleRegistry.getIndividual(stack);
		final IGenome genome = member.getGenome();
		final IAlleleTolerance tolAllele = (IAlleleTolerance) genome.getActiveAllele(this.chromosomeType);
		final Tolerance tol = Tolerance.get(tolAllele.getValue());
		final float effect = this.type.getEffect(acclim);
		return (effect > 0.0f && tol.getBounds()[1] < 5) || (effect < 0.0f && tol.getBounds()[0] > -5);
	}

	public ItemStack alter(final ItemStack stack, final ItemStack acc) {
		final Random rand = new Random();
		final float effect = this.type.getEffect(acc);
		if (rand.nextFloat() > Math.abs(effect)) {
			return stack;
		}
		final IIndividual member = AlleleManager.alleleRegistry.getIndividual(stack);
		final IGenome genome = member.getGenome();
		final IAlleleTolerance tolAllele = (IAlleleTolerance) genome.getActiveAllele(this.chromosomeType);
		final Tolerance tol = Tolerance.get(tolAllele.getValue());
		final Tolerance newTol = Acclimatiser.alterTolerance(tol, effect);
		if (rand.nextFloat() > 1.0f / (-newTol.getBounds()[0] + newTol.getBounds()[1])) {
			return stack;
		}
		final ISpeciesRoot root = AlleleManager.alleleRegistry.getSpeciesRoot(stack);
		boolean setPrimary = rand.nextBoolean();
		boolean setSecondary = !setPrimary;
		Gene gene = new Gene(newTol.getAllele(), this.chromosomeType, root);
		Splicer.setGene(gene, stack, setPrimary, setSecondary);
		return stack;
	}

	public String getSpeciesRootUid() {
		return speciesRootUid;
	}

	public IToleranceType getType() {
		return type;
	}
}
