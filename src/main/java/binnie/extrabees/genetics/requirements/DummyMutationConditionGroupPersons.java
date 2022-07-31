package binnie.extrabees.genetics.requirements;

import forestry.api.core.IClimateProvider;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IGenome;
import forestry.api.genetics.IMutationCondition;
import net.minecraft.world.World;

public class DummyMutationConditionGroupPersons implements IMutationCondition {
    private final RequirementGroupOfPersons requirement;

    public DummyMutationConditionGroupPersons(RequirementGroupOfPersons requirement) {
        this.requirement = requirement;
    }

    @Override
    public float getChance(
            World world,
            int x,
            int y,
            int z,
            IAllele allele0,
            IAllele allele1,
            IGenome genome0,
            IGenome genome1,
            IClimateProvider climate) {
        return 1.0F;
    }

    @Override
    public String getDescription() {
        return requirement.getDescription();
    }
}
