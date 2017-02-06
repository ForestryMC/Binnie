package binnie.core.util;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

public class OreDictionaryUtil {

	private OreDictionaryUtil() {

	}

	public static List<ItemStack> getOres(int oreId) {
		String oreName = OreDictionary.getOreName(oreId);
		if (OreDictionary.doesOreNameExist(oreName)) {
			return OreDictionary.getOres(oreName);
		} else {
			return Collections.emptyList();
		}
	}

	public static boolean hasOreId(ItemStack itemStack, int oreId) {
		int[] oreIds = OreDictionary.getOreIDs(itemStack);
		return contains(oreIds, oreId);
	}

	private static boolean contains(int[] array, int value) {
		for (int arrayValue : array) {
			if (arrayValue == value) {
				return true;
			}
		}
		return false;
	}
}
