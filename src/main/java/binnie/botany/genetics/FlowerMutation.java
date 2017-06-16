package binnie.botany.genetics;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import forestry.api.core.ForestryAPI;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IMutation;
import forestry.core.genetics.mutations.Mutation;

import binnie.botany.api.IAlleleFlowerSpecies;
import binnie.botany.api.IFlowerGenome;
import binnie.botany.api.IFlowerMutation;
import binnie.botany.api.IFlowerMutationBuilder;
import binnie.botany.api.IFlowerRoot;
import binnie.botany.core.BotanyCore;

public class FlowerMutation extends Mutation implements IFlowerMutation, IFlowerMutationBuilder {
	public FlowerMutation(final IAlleleFlowerSpecies allele0, final IAlleleFlowerSpecies allele1, final IAllele[] template, final int chance) {
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
	public float getChance(World world, BlockPos pos, IAlleleFlowerSpecies a0, IAlleleFlowerSpecies a1, IFlowerGenome g0, IFlowerGenome g1) {
		float processedChance = super.getChance(world, pos, a0, a1, g0, g1, ForestryAPI.climateManager.getDefaultClimate(world, pos));
		if (processedChance <= 0) {
			return 0;
		}
		return processedChance;
	}
}
