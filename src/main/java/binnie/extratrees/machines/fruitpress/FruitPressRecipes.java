package binnie.extratrees.machines.fruitpress;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Map;

public class FruitPressRecipes {
	// Map<input fruit item, Pair<input fruit, output fluid>>
	private static Multimap<Item, Pair<ItemStack, FluidStack>> pressRecipes = ArrayListMultimap.create();

	public static boolean isInput(@Nullable final ItemStack itemstack) {
		return getOutput(itemstack) != null;
	}

	@Nullable
	public static FluidStack getOutput(@Nullable final ItemStack itemstack) {
		if (itemstack == null) {
			return null;
		}

		Item item = itemstack.getItem();
		for (final Map.Entry<ItemStack, FluidStack> entry : FruitPressRecipes.pressRecipes.get(item)) {
			if (itemstack.isItemEqual(entry.getKey())) {
				return entry.getValue();
			}
		}
		return null;
	}

	public static void addRecipe(final ItemStack stack, final FluidStack fluid) {
		if (getOutput(stack) != null) {
			return;
		}
		Item item = stack.getItem();
		FruitPressRecipes.pressRecipes.put(item, Pair.of(stack, fluid));
	}

	public static Collection<Pair<ItemStack, FluidStack>> getRecipes() {
		return pressRecipes.values();
	}
}
