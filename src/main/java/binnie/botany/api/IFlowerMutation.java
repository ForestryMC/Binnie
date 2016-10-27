package binnie.botany.api;

import forestry.api.genetics.IMutation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IFlowerMutation extends IMutation {
	@Override
	IFlowerRoot getRoot();

	float getChance(final World world, final BlockPos pos, final IAlleleFlowerSpecies p0, final IAlleleFlowerSpecies p1, final IFlowerGenome p2, final IFlowerGenome p3);
}
