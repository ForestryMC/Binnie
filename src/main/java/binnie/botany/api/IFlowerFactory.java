package binnie.botany.api;

import forestry.api.genetics.IAllele;
import forestry.api.genetics.IClassification;

import javax.annotation.Nonnull;

public interface IFlowerFactory {

	@Nonnull
	IAlleleFlowerSpeciesBuilder createSpecies(@Nonnull String uid, @Nonnull String unlocalizedName, @Nonnull String authority, @Nonnull String unlocalizedDescription, boolean isDominant, @Nonnull IClassification branch, @Nonnull String binomial, @Nonnull IFlowerType flowerType);

	@Nonnull
	IFlowerMutationBuilder createMutation(@Nonnull IAlleleFlowerSpecies parentFlower0, @Nonnull IAlleleFlowerSpecies parentFlower1, @Nonnull IAllele[] result, int chance);

}
