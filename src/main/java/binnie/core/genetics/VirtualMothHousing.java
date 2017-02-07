package binnie.core.genetics;

import forestry.api.genetics.IIndividual;
import forestry.api.lepidopterology.IButterfly;
import forestry.api.lepidopterology.IButterflyNursery;
import net.minecraft.entity.player.EntityPlayer;

import javax.annotation.Nullable;

public class VirtualMothHousing extends VirtualHousing implements IButterflyNursery {
	public VirtualMothHousing(final EntityPlayer player) {
		super(player);
	}

	@Override
	@Nullable
	public IButterfly getCaterpillar() {
		return null;
	}

	@Override
	@Nullable
	public IIndividual getNanny() {
		return null;
	}

	@Override
	public void setCaterpillar(final IButterfly butterfly) {
	}

	@Override
	public boolean canNurse(final IButterfly butterfly) {
		return false;
	}
}
