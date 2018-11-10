package binnie.botany.genetics;

import net.minecraft.entity.player.EntityPlayer;

import forestry.api.genetics.IBreedingTracker;
import forestry.api.genetics.IIndividual;
import forestry.core.genetics.BreedingTracker;

import binnie.botany.api.BotanyAPI;
import binnie.botany.api.genetics.IBotanistTracker;

public class BotanistTracker extends BreedingTracker implements IBotanistTracker {
	public BotanistTracker(String s) {
		super(s, "NORMAL");
	}

	@Override
	public void registerPickup(IIndividual individual) {
	}

	@Override
	protected IBreedingTracker getBreedingTracker(EntityPlayer player) {
		return BotanyAPI.flowerRoot.getBreedingTracker(player.world, player.getGameProfile());
	}

	@Override
	protected String speciesRootUID() {
		return "rootFlowers";
	}
}
