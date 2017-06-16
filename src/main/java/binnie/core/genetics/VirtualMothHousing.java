package binnie.core.genetics;

import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import forestry.api.genetics.IIndividual;
import forestry.api.lepidopterology.IButterfly;
import forestry.api.lepidopterology.IButterflyNursery;

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
	public void setCaterpillar(@Nullable final IButterfly butterfly) {
	}

	@Override
	@Nullable
	public IIndividual getNanny() {
		return null;
	}

	@Override
	public boolean canNurse(final IButterfly butterfly) {
		return false;
	}

	@Override
	public World getWorldObj() {
		return getWorld();
	}
}
