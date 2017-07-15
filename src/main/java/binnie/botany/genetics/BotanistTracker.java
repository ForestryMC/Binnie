package binnie.botany.genetics;

import net.minecraft.entity.player.EntityPlayer;

import forestry.api.genetics.IBreedingTracker;
import forestry.api.genetics.IIndividual;
import forestry.core.genetics.BreedingTracker;

import binnie.Binnie;
import binnie.botany.api.IBotanistTracker;

public class BotanistTracker extends BreedingTracker implements IBotanistTracker {
	public BotanistTracker(String s) {
		super(s, "NORMAL");
	}

	@Override
	public void registerPickup(IIndividual individual) {
	}

	@Override
	protected IBreedingTracker getBreedingTracker(EntityPlayer player) {
		return Binnie.GENETICS.getFlowerRoot().getBreedingTracker(player.world, player.getGameProfile());
	}

	@Override
	protected String speciesRootUID() {
		return "rootFlowers";
	}
}
