// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.genetics;

import forestry.api.genetics.IIndividual;
import forestry.api.lepidopterology.IButterfly;
import net.minecraft.entity.player.EntityPlayer;
import forestry.api.lepidopterology.IButterflyNursery;

public class VirtualMothHousing extends VirtualHousing implements IButterflyNursery
{
	public VirtualMothHousing(final EntityPlayer player) {
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
	public void setCaterpillar(final IButterfly butterfly) {
	}

	@Override
	public boolean canNurse(final IButterfly butterfly) {
		return false;
	}
}
