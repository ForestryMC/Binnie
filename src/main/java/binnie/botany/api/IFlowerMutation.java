package binnie.botany.api;

import forestry.api.genetics.IMutation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IFlowerMutation extends IMutation {
	@Override
	IFlowerRoot getRoot();

	float getChance(World world, BlockPos pos, IAlleleFlowerSpecies allele0, IAlleleFlowerSpecies allele1, IFlowerGenome genome0, IFlowerGenome genome1);
}
