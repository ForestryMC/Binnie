package binnie.botany.api;

import forestry.api.genetics.IAllele;
import forestry.api.genetics.IClassification;

public interface IFlowerFactory {

	IAlleleFlowerSpeciesBuilder createSpecies(String uid, String unlocalizedName, String authority, String unlocalizedDescription, boolean isDominant, IClassification branch, String binomial, IFlowerType flowerType);

	IFlowerMutationBuilder createMutation(IAlleleFlowerSpecies parentFlower0, IAlleleFlowerSpecies parentFlower1, IAllele[] result, int chance);
}
