package binnie.genetics.machine.incubator;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;

public class IncubatorRecipeLarvae extends IncubatorRecipe {
	public IncubatorRecipeLarvae(ItemStack itemStack, FluidStack input, @Nullable FluidStack output, float lossChance, float chance) {
		super(itemStack, input, output, lossChance, chance);
	}
}
