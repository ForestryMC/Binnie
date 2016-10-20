package binnie.botany.api;

import forestry.api.genetics.IAllele;
import forestry.api.genetics.IGenome;
import forestry.api.genetics.IMutation;

public interface IFlowerMutation extends IMutation {
    float getChance(final IAllele p0, final IAllele p1, final IGenome p2, final IGenome p3);
}
