package binnie.botany.genetics;

import binnie.Binnie;
import binnie.botany.api.IBotanistTracker;
import forestry.api.genetics.IBreedingTracker;
import forestry.api.genetics.IIndividual;
import forestry.core.genetics.BreedingTracker;
import net.minecraft.entity.player.EntityPlayer;

public class BotanistTracker extends BreedingTracker implements IBotanistTracker {
    public BotanistTracker(String s) {
        super(s);
    }

    @Override
    public void registerPickup(IIndividual individual) {
        // ignored
    }

    @Override
    protected IBreedingTracker getBreedingTracker(EntityPlayer player) {
        return Binnie.Genetics.getFlowerRoot().getBreedingTracker(player.worldObj, player.getGameProfile());
    }

    @Override
    protected String speciesRootUID() {
        return "rootFlowers";
    }
}
