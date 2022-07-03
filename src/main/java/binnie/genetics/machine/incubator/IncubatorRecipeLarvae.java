package binnie.genetics.machine.incubator;

import binnie.core.machines.MachineUtil;
import binnie.genetics.item.GeneticLiquid;
import forestry.api.apiculture.BeeManager;
import forestry.api.apiculture.EnumBeeType;
import forestry.api.apiculture.IBee;
import net.minecraft.item.ItemStack;

public class IncubatorRecipeLarvae extends IncubatorRecipe {
    public IncubatorRecipeLarvae(ItemStack beeLarvaeWildcard) {
        super(beeLarvaeWildcard, GeneticLiquid.GrowthMedium.get(50), null, 1.0f, 0.05f);
    }

    @Override
    public ItemStack getOutputStack(final MachineUtil machine) {
        final ItemStack larvae = machine.getStack(Incubator.SLOT_INCUBATOR);
        final IBee bee = BeeManager.beeRoot.getMember(larvae);
        if (bee == null) {
            return null;
        }
        return BeeManager.beeRoot.getMemberStack(bee, EnumBeeType.DRONE.ordinal());
    }

    @Override
    public ItemStack getExpectedOutput() {
        return BeeManager.beeRoot.getMemberStack(
                BeeManager.beeRoot.getMember(this.getInputStack()), EnumBeeType.DRONE.ordinal());
    }
}
