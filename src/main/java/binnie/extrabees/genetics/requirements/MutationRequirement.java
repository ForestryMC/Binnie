package binnie.extrabees.genetics.requirements;

import forestry.api.apiculture.IBeeHousing;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IGenome;

public abstract class MutationRequirement {
	public abstract String[] tooltip();

	public abstract boolean fufilled(IBeeHousing housing, IAllele allele0, IAllele allele1, IGenome genome0, IGenome genome1);
}
