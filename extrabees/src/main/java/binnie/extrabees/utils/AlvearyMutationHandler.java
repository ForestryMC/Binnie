package binnie.extrabees.utils;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import static org.apache.commons.lang3.ObjectUtils.firstNonNull;

public class AlvearyMutationHandler {
	private static final int MUTATIONS_CAPACITY = 10;

	private static final List<Pair<ItemStack, Float>> MUTATIONS = new ArrayList<>(MUTATIONS_CAPACITY);

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

	public static void addMutationItem(@Nullable Item item, float chance) {
		addMutationItem(new ItemStack(firstNonNull(item, Item.getItemFromBlock(Blocks.AIR))), chance);
	}

	public static void addMutationItem(final ItemStack item, final float chance) {
		if (item.isEmpty()) {
			return;
		}
		MUTATIONS.add(Pair.of(item, chance));
		MUTATIONS.sort(Comparator.comparing(Pair::getValue));
	}

	public static void registerMutationItems() {
		AlvearyMutationHandler.addMutationItem(new ItemStack(Blocks.SOUL_SAND), 1.5f);
		AlvearyMutationHandler.addMutationItem(Utils.getIC2Item("UranFuel"), 4.0f);
		AlvearyMutationHandler.addMutationItem(Utils.getIC2Item("MOXFuel"), 10.0f);
		AlvearyMutationHandler.addMutationItem(Utils.getIC2Item("Plutonium"), 8.0f);
		AlvearyMutationHandler.addMutationItem(Utils.getIC2Item("smallPlutonium"), 5.0f);
		AlvearyMutationHandler.addMutationItem(Utils.getIC2Item("Uran235"), 4.0f);
		AlvearyMutationHandler.addMutationItem(Utils.getIC2Item("smallUran235"), 2.5f);
		AlvearyMutationHandler.addMutationItem(Utils.getIC2Item("Uran238"), 2.0f);
		AlvearyMutationHandler.addMutationItem(new ItemStack(Items.ENDER_PEARL), 2.0f);
		AlvearyMutationHandler.addMutationItem(new ItemStack(Items.ENDER_EYE), 4.0f);
	}

	public static List<Pair<ItemStack, Float>> getMutagens() {
		return Collections.unmodifiableList(MUTATIONS);
	}
}
