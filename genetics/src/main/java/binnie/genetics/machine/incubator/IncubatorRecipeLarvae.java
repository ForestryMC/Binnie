package binnie.genetics.machine.incubator;

import binnie.core.Mods;
import binnie.core.machines.MachineUtil;
import binnie.genetics.item.GeneticLiquid;
import forestry.api.apiculture.BeeManager;
import forestry.api.apiculture.EnumBeeType;
import forestry.api.apiculture.IBee;
import net.minecraft.item.ItemStack;

import net.minecraftforge.oredict.OreDictionary;

public class IncubatorRecipeLarvae extends IncubatorRecipe {
	public IncubatorRecipeLarvae(ItemStack beeLarvaeWildcard) {
		super(beeLarvaeWildcard, GeneticLiquid.GrowthMedium.get(50), null, 1.0f, 0.05f);
	}

	@Override
	public ItemStack getOutputStack(final MachineUtil machine) {
		final ItemStack larvae = machine.getStack(3);
		final IBee bee = BeeManager.beeRoot.getMember(larvae);
		if (bee == null) {
			return ItemStack.EMPTY;
		}
		return BeeManager.beeRoot.getMemberStack(bee, EnumBeeType.DRONE);
	}

	@Override
	public ItemStack getExpectedOutput() {
		return Mods.Forestry.stack("bee_drone_ge", 1, OreDictionary.WILDCARD_VALUE);
	}
}
