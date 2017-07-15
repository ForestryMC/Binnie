package binnie.botany.genetics;

import binnie.botany.api.IAlleleFlowerEffect;
import binnie.botany.api.IFlowerGenome;
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
		return "None";
	}

	@Override
	public IEffectData doEffect(IFlowerGenome genome, IEffectData effect, World world, int x, int y, int z) {
		return effect;
	}

	@Override
	public String getUnlocalizedName() {
		return getUID();
	}
}
