package binnie.botany.genetics;

import net.minecraft.world.World;

import forestry.api.genetics.IEffectData;

import binnie.botany.api.IAlleleFlowerEffect;
import binnie.botany.api.IFlowerGenome;

public class AlleleEffectNone implements IAlleleFlowerEffect {
	@Override
	public boolean isCombinable() {
		return true;
	}

	@Override
	public IEffectData validateStorage(final IEffectData storedData) {
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
	public IEffectData doEffect(final IFlowerGenome genome, final IEffectData storedData, final World world, final int x, final int y, final int z) {
		return storedData;
	}

	@Override
	public String getUnlocalizedName() {
		return this.getUID();
	}
}
