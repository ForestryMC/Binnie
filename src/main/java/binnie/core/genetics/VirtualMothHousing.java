package binnie.core.genetics;

import forestry.api.genetics.IIndividual;
import forestry.api.lepidopterology.IButterfly;
import forestry.api.lepidopterology.IButterflyNursery;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

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
