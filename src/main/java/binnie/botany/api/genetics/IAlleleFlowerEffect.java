package binnie.botany.api.genetics;

import net.minecraft.world.World;

import forestry.api.genetics.IAlleleEffect;
import forestry.api.genetics.IEffectData;

public interface IAlleleFlowerEffect extends IAlleleEffect {
	IEffectData doEffect(IFlowerGenome genome, IEffectData effect, World world, int x, int y, int z);
}
