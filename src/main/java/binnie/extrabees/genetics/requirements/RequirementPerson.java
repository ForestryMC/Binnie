package binnie.extrabees.genetics.requirements;


import binnie.extrabees.genetics.ExtraBeeMutation;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IGenome;

public class RequirementPerson extends ExtraBeeMutation.MutationRequirement {
	protected String name;

	public RequirementPerson(String name) {
		this.name = name;
	}

	@Override
	public String[] tooltip() {
		return new String[]{"Can only be bred by " + name};
	}

	@Override
	public boolean fufilled(IBeeHousing housing, IAllele allele0, IAllele allele1, IGenome genome0, IGenome genome1) {
		return housing.getOwner().getName() != null
			&& housing.getOwner().getName().equals(name);
	}
}
