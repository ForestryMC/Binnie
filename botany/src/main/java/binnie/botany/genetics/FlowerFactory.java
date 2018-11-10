package binnie.botany.genetics;

import java.awt.Color;

import forestry.api.genetics.IAllele;
import forestry.api.genetics.IClassification;

import binnie.botany.api.BotanyAPI;
import binnie.botany.api.genetics.IAlleleFlowerColor;
import binnie.botany.api.genetics.IAlleleFlowerSpecies;
import binnie.botany.api.genetics.IAlleleFlowerSpeciesBuilder;
import binnie.botany.api.genetics.IFlowerFactory;
import binnie.botany.api.genetics.IFlowerMutationBuilder;
import binnie.botany.api.genetics.IFlowerType;
import binnie.botany.genetics.allele.AlleleFlowerSpecies;
import binnie.botany.models.FlowerSpriteManager;

public class FlowerFactory implements IFlowerFactory {
	@Override
	public IAlleleFlowerSpeciesBuilder createSpecies(String modId, String uid, String unlocalizedName, String authority, String unlocalizedDescription, boolean isDominant, IClassification branch, String binomial, IFlowerType flowerType) {
		FlowerSpriteManager.initSprites(flowerType);
		return new AlleleFlowerSpecies(modId, uid, unlocalizedName, authority, unlocalizedDescription, isDominant, branch, binomial, flowerType);
	}

	@Override
	public IFlowerMutationBuilder createMutation(IAlleleFlowerSpecies allele0, IAlleleFlowerSpecies allele1, IAllele[] result, int chance) {
		FlowerMutation mutation = new FlowerMutation(allele0, allele1, result, chance);
		BotanyAPI.flowerRoot.registerMutation(mutation);
		return mutation;
	}

	@Override
	public IAlleleFlowerColor createFlowerColorAllele(String uid, int id, Color color, Color colorWilted, String name, String unlocalizedName, boolean isDominant) {
		return new AlleleFlowerColor(uid, id, color, colorWilted, name, unlocalizedName, isDominant);
	}
}
