package binnie.botany.genetics;

import binnie.botany.api.IAlleleFlowerEffect;
import binnie.botany.api.IFlowerGenome;
import binnie.core.util.I18N;
import forestry.api.genetics.IEffectData;
import net.minecraft.world.World;

public class AlleleEffectNone implements IAlleleFlowerEffect {
    @Override
    public boolean isCombinable() {
        return true;
    }

    @Override
    public IEffectData validateStorage(IEffectData storedData) {
        return storedData;
    }

    @Override
    public String getUID() {
        return "binnie.flowerEffectNone";
    }

    @Override
    public boolean isDominant() {
        return false;
    }

    @Override
    public String getName() {
        return I18N.localise("forestry.allele.effect.none");
    }

    @Override
    public IEffectData doEffect(IFlowerGenome genome, IEffectData storedData, World world, int x, int y, int z) {
        return storedData;
    }

    @Override
    public String getUnlocalizedName() {
        return getUID();
    }
}
