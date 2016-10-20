// 
// Decompiled by Procyon v0.5.30
// 

package binnie.botany.genetics;

import binnie.Binnie;
import forestry.api.genetics.IBreedingTracker;

import net.minecraft.entity.player.EntityPlayer;
import forestry.api.genetics.IIndividual;

import binnie.botany.api.IBotanistTracker;
import forestry.core.genetics.BreedingTracker;

public class BotanistTracker extends BreedingTracker implements IBotanistTracker
{
	public BotanistTracker(final String s) {
		super(s);
	}

	@Override
	public void registerPickup(final IIndividual individual) {
	}

	@Override
	protected IBreedingTracker getBreedingTracker(final EntityPlayer player) {
		return Binnie.Genetics.getFlowerRoot().getBreedingTracker(player.worldObj, player.getGameProfile());
	}

	@Override
	protected String speciesRootUID() {
		return "rootFlowers";
	}
}
