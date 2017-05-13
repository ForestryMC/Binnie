package binnie.extrabees.genetics.requirements;


import forestry.api.genetics.IAllele;
import forestry.api.genetics.IGenome;
import forestry.api.genetics.IMutationCondition;
import net.minecraft.world.World;

public class DummyMutationCondition implements IMutationCondition {
	private final RequirementPerson requirement;

	public DummyMutationCondition(RequirementPerson requirement) {
		this.requirement = requirement;
	}

	@Override
	public float getChance(World world, int i, int i1, int i2, IAllele iAllele, IAllele iAllele1, IGenome iGenome, IGenome iGenome1) {
		return 1.0F;
	}

	@Override
	public String getDescription() {
		return requirement.getDescription();
	}
}
