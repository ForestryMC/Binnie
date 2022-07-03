package binnie.botany.api;

import forestry.api.genetics.IAlleleEffect;
import forestry.api.genetics.IEffectData;
import net.minecraft.world.World;

public interface IAlleleFlowerEffect extends IAlleleEffect {
    IEffectData doEffect(IFlowerGenome genome, IEffectData storedData, World world, int x, int y, int z);
}
