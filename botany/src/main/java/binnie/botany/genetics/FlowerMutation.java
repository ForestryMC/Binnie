package binnie.botany.genetics;

import forestry.api.climate.ClimateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import forestry.api.core.ForestryAPI;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IMutation;
import forestry.core.genetics.mutations.Mutation;

import binnie.botany.api.genetics.IAlleleFlowerSpecies;
import binnie.botany.api.genetics.IFlowerGenome;
import binnie.botany.api.genetics.IFlowerMutation;
import binnie.botany.api.genetics.IFlowerMutationBuilder;
import binnie.botany.api.genetics.IFlowerRoot;
import binnie.botany.core.BotanyCore;

public class FlowerMutation extends Mutation implements IFlowerMutation, IFlowerMutationBuilder {
	public FlowerMutation(IAlleleFlowerSpecies allele0, IAlleleFlowerSpecies allele1, IAllele[] template, int chance) {
		super(allele0, allele1, template, chance);
	}

	@Override
	public IFlowerRoot getRoot() {
		return BotanyCore.getFlowerRoot();
	}

	@Override
	public IMutation build() {
		return this;
	}

	@Override
	public float getChance(World world, BlockPos pos, IAlleleFlowerSpecies allele0, IAlleleFlowerSpecies allele1, IFlowerGenome genome0, IFlowerGenome genome1) {
		float processedChance = getChance(world, pos, allele0, allele1, genome0, genome1, ClimateManager.climateRoot.getDefaultClimate(world, pos));
		if (processedChance <= 0) {
			return 0;
		}
		return processedChance;
	}
}
