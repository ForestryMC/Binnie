package binnie.extrabees.genetics.requirements;


import binnie.extrabees.genetics.ExtraBeeMutation;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IGenome;
import net.minecraftforge.common.BiomeDictionary;

public class RequirementBiomeType extends ExtraBeeMutation.MutationRequirement {
	protected BiomeDictionary.Type type;

	public RequirementBiomeType(BiomeDictionary.Type type) {
		this.type = type;
	}

	@Override
	public String[] tooltip() {
		return new String[]{"Is restricted to " + type + "-like biomes."};
	}

	@Override
	public boolean fufilled(IBeeHousing housing, IAllele allele0, IAllele allele1, IGenome genome0, IGenome genome1) {
		return BiomeDictionary.isBiomeOfType(housing.getBiome(), type);
	}
}
