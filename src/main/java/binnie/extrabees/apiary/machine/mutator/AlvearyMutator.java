package binnie.extrabees.apiary.machine.mutator;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.item.ItemStack;

public class AlvearyMutator {
    public static final int SLOT_MUTATOR = 0;

    protected static Map<ItemStack, Float> mutations = new HashMap<>();

    public static boolean isMutationItem(ItemStack item) {
        return getMutationMult(item) > 1.0f;
    }

    public static float getMutationMult(ItemStack item) {
        if (item == null) {
            return 1.0f;
        }

        for (ItemStack comp : AlvearyMutator.mutations.keySet()) {
            if (ItemStack.areItemStackTagsEqual(item, comp) && item.isItemEqual(comp)) {
                return AlvearyMutator.mutations.get(comp);
            }
        }
        return 1.0f;
    }

    public static void addMutationItem(ItemStack item, float chance) {
        if (item == null) {
            return;
        }
        AlvearyMutator.mutations.put(item, chance);
    }

    public static Collection<ItemStack> getMutagens() {
        return AlvearyMutator.mutations.keySet();
    }
}
