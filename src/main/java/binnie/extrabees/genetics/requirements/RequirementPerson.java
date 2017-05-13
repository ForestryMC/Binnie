package binnie.extrabees.genetics.requirements;

import forestry.api.genetics.IAllele;
import forestry.api.genetics.IGenome;
import forestry.api.genetics.IMutationCondition;
import net.minecraft.world.World;

public class RequirementPerson implements IMutationCondition {
	protected String name;

	public RequirementPerson(String name) {
		this.name = name;
	}

	@Override
	public float getChance(World world, int x, int y, int z, IAllele allele0, IAllele allele1, IGenome genome0, IGenome genome1) {
		String ownerName = housing.getOwner().getName();
		if (ownerName != null && ownerName.equals(name)) {
			return 1.0f;
		}
		return 0.0f;
	}

	@Override
	public String getDescription() {
		return "Can only be bred by " + name;
	}
}
