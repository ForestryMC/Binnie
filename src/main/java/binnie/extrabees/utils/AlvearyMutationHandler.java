package binnie.extrabees.utils;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.apache.commons.lang3.ObjectUtils.firstNonNull;

/**
 * Created by Elec332 on 13-5-2017.
 */
public class AlvearyMutationHandler {

	private static final List<Pair<ItemStack, Float>> MUTATIONS = new ArrayList<>();

	public static boolean isMutationItem(final ItemStack item) {
		return getMutationMult(item) > 1.0f;
	}

	public static float getMutationMult(final ItemStack item) {
		if (!item.isEmpty()) {
			for (final Pair<ItemStack, Float> comp : MUTATIONS) {
				ItemStack key = comp.getKey();
				if (item.isItemEqual(key) && ItemStack.areItemStackTagsEqual(item, key)) {
					return comp.getValue();
				}
			}
		}
		return 1.0f;
	}

	public static void addMutationItem(@Nullable Item item, float chance){
		addMutationItem(new ItemStack(firstNonNull(item, Item.getItemFromBlock(Blocks.AIR))), chance);
	}

	public static void addMutationItem(final ItemStack item, final float chance) {
		if (item.isEmpty()) {
			return;
		}
		MUTATIONS.add(Pair.of(item, chance));
		MUTATIONS.sort(Comparator.comparing(Pair::getValue));
	}

	public static List<Pair<ItemStack, Float>> getMutagens() {
		return Collections.unmodifiableList(MUTATIONS);
	}

}
