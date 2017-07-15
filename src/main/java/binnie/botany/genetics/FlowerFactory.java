package binnie.botany.genetics;

import forestry.api.genetics.IAllele;
import forestry.api.genetics.IClassification;

import binnie.botany.api.FlowerManager;
import binnie.botany.api.IAlleleFlowerSpecies;
import binnie.botany.api.IAlleleFlowerSpeciesBuilder;
import binnie.botany.api.IFlowerFactory;
import binnie.botany.api.IFlowerMutationBuilder;
import binnie.botany.api.IFlowerType;
import binnie.botany.flower.FlowerSpriteManager;

public class FlowerFactory implements IFlowerFactory {
	@Override
	public IAlleleFlowerSpeciesBuilder createSpecies(String uid, String unlocalizedName, String authority, String unlocalizedDescription, boolean isDominant, IClassification branch, String binomial, IFlowerType flowerType) {
		FlowerSpriteManager.initSprites(flowerType);
		return new AlleleFlowerSpecies(uid, unlocalizedName, authority, unlocalizedDescription, isDominant, branch, binomial, flowerType);
	}

	@Override
	public IFlowerMutationBuilder createMutation(IAlleleFlowerSpecies allele0, IAlleleFlowerSpecies allele1, IAllele[] result, int chance) {
		FlowerMutation mutation = new FlowerMutation(allele0, allele1, result, chance);
		FlowerManager.flowerRoot.registerMutation(mutation);
		return mutation;
	}
}
