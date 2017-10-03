package binnie.botany.api.genetics;

import java.awt.Color;

import forestry.api.genetics.IAllele;
import forestry.api.genetics.IClassification;

public interface IFlowerFactory {
	IAlleleFlowerSpeciesBuilder createSpecies(String modId, String uid, String unlocalizedName, String authority, String unlocalizedDescription, boolean isDominant, IClassification branch, String binomial, IFlowerType flowerType);

	IFlowerMutationBuilder createMutation(IAlleleFlowerSpecies allele0, IAlleleFlowerSpecies allele1, IAllele[] result, int chance);

	IAlleleFlowerColor createFlowerColorAllele(String uid, int id, Color color, Color colorWilted, String name, String unlocalizedName, boolean isDominant);
}
