package binnie.genetics.machine.acclimatiser;

import java.util.Random;

import net.minecraft.item.ItemStack;

import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAlleleTolerance;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.IGenome;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.ISpeciesRoot;

import binnie.core.genetics.Gene;
import binnie.core.genetics.Tolerance;
import binnie.genetics.api.acclimatiser.IToleranceType;
import binnie.genetics.machine.splicer.Splicer;

class ToleranceSystem {
	private final String speciesRootUid;
	private final IChromosomeType chromosomeType;
	private final IToleranceType type;

	ToleranceSystem(IChromosomeType chromosomeType, IToleranceType type) {
		this.speciesRootUid = chromosomeType.getSpeciesRoot().getUID();
		this.chromosomeType = chromosomeType;
		this.type = type;
	}

	public boolean canAlter(ItemStack stack, ItemStack acclim) {
		IIndividual member = AlleleManager.alleleRegistry.getIndividual(stack);
		IGenome genome = member.getGenome();
		IAlleleTolerance tolAllele = (IAlleleTolerance) genome.getActiveAllele(this.chromosomeType);
		Tolerance tol = Tolerance.get(tolAllele.getValue());
		float effect = this.type.getEffect(acclim);
		return (effect > 0.0f && tol.getBounds()[1] < 5) || (effect < 0.0f && tol.getBounds()[0] > -5);
	}

	public ItemStack alter(ItemStack stack, ItemStack acc) {
		Random rand = new Random();
		float effect = this.type.getEffect(acc);
		if (rand.nextFloat() > Math.abs(effect)) {
			return stack;
		}
		IIndividual member = AlleleManager.alleleRegistry.getIndividual(stack);
		IGenome genome = member.getGenome();
		IAlleleTolerance tolAllele = (IAlleleTolerance) genome.getActiveAllele(this.chromosomeType);
		Tolerance tol = Tolerance.get(tolAllele.getValue());
		Tolerance newTol = Acclimatiser.alterTolerance(tol, effect);
		if (rand.nextFloat() > 1.0f / (-newTol.getBounds()[0] + newTol.getBounds()[1])) {
			return stack;
		}
		ISpeciesRoot root = AlleleManager.alleleRegistry.getSpeciesRoot(stack);
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
