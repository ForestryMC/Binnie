package binnie.extrabees.genetics.requirements;

import binnie.core.util.I18N;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IGenome;
import java.util.Arrays;
import java.util.List;

public class RequirementGroupOfPersons implements IMutationRequirement {
    protected List<String> names;

    public RequirementGroupOfPersons(String[] name) {
        this.names = Arrays.asList(name);
    }

    @Override
    public String getDescription() {
        return I18N.localise("extrabees.genetics.requirements.user", names);
    }

    @Override
    public boolean fufilled(IBeeHousing housing, IAllele allele0, IAllele allele1, IGenome genome0, IGenome genome1) {
        String ownerName = housing.getOwner().getName();
        return ownerName != null && names.contains(ownerName); // ownerName.contains(name);
    }
}
