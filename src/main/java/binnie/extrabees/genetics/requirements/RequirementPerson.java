package binnie.extrabees.genetics.requirements;

import binnie.core.util.I18N;
import binnie.extrabees.ExtraBees;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IGenome;
import net.minecraft.client.resources.I18n;

public class RequirementPerson implements IMutationRequirement {
	protected String name;

	public RequirementPerson(String name) {
		this.name = name;
	}

	@Override
	public String getDescription() {
		return I18N.localise(ExtraBees.instance, "genetics.requirements.user", name);
	}

	@Override
	public boolean fufilled(IBeeHousing housing, IAllele allele0, IAllele allele1, IGenome genome0, IGenome genome1) {
		String ownerName = housing.getOwner().getName();
		return ownerName != null && ownerName.equals(name);
	}
}
