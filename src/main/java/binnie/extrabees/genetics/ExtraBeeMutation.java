package binnie.extrabees.genetics;

import binnie.extrabees.genetics.requirements.*;
import forestry.api.apiculture.BeeManager;
import forestry.api.apiculture.IAlleleBeeSpecies;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.apiculture.IBeeMutationCustom;
import forestry.api.apiculture.IBeeRoot;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IMutation;
import forestry.apiculture.genetics.BeeMutation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExtraBeeMutation extends BeeMutation implements IBeeMutationCustom {
    public static List<IMutation> mutations = new ArrayList<>();

    private List<IMutationRequirement> requirements;

    public ExtraBeeMutation(IAlleleBeeSpecies bee0, IAlleleBeeSpecies bee1, IAllele[] result, int chance) {
        super(bee0, bee1, result, chance);
        requirements = new ArrayList<>();
        mutations.add(this);
    }

    @Override
    public IBeeRoot getRoot() {
        return BeeManager.beeRoot;
    }

    @Override
    public float getChance(
            IBeeHousing housing,
            IAlleleBeeSpecies allele0,
            IAlleleBeeSpecies allele1,
            IBeeGenome genome0,
            IBeeGenome genome1) {
        for (IMutationRequirement requirement : requirements) {
            if (!requirement.fufilled(housing, allele0, allele1, genome0, genome1)) {
                return 0.0f;
            }
        }
        return super.getChance(housing, allele0, allele1, genome0, genome1);
    }

    public void restrictPerson(String... nicknames) {
        restrictPerson(new RequirementPerson(nicknames));
    }

    public void restrictPerson(RequirementPerson requirement) {
        requirements.add(requirement);
        addMutationCondition(new DummyMutationCondition(requirement));
        ArrayList<String> descriptions = (ArrayList<String>) getSpecialConditions();
        int i = descriptions.size() - 1;
        String description = descriptions.get(i);
        if (description.contains("/n")) {
            descriptions.remove(i);
            descriptions.addAll(Arrays.asList(description.split("/n")));
        }
    }
}
