package binnie.botany.api;

import forestry.api.genetics.IAllele;
import forestry.api.genetics.IGenome;
import forestry.api.genetics.IMutation;

public interface IFlowerMutation extends IMutation {
    float getChance(IAllele allele0, IAllele allele1, IGenome genome0, IGenome genome1);
}
