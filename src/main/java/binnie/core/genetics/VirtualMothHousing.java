package binnie.core.genetics;

import forestry.api.genetics.IIndividual;
import forestry.api.lepidopterology.IButterfly;
import forestry.api.lepidopterology.IButterflyNursery;
import net.minecraft.entity.player.EntityPlayer;

// TODO unused class?
public class VirtualMothHousing extends VirtualHousing implements IButterflyNursery {
	public VirtualMothHousing(EntityPlayer player) {
		super(player);
	}

	@Override
	public IButterfly getCaterpillar() {
		return null;
	}

	@Override
	public IIndividual getNanny() {
		return null;
	}

	@Override
	public void setCaterpillar(IButterfly butterfly) {
	}

	@Override
	public boolean canNurse(IButterfly butterfly) {
		return false;
	}
}
