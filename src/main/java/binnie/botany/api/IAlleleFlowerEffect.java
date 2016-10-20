package binnie.botany.api;

import forestry.api.genetics.IAlleleEffect;
import forestry.api.genetics.IEffectData;
import net.minecraft.world.World;

public interface IAlleleFlowerEffect extends IAlleleEffect {
    IEffectData doEffect(final IFlowerGenome p0, final IEffectData p1, final World p2, final int p3, final int p4, final int p5);
}
