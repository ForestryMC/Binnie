package binnie.core.util;

import java.util.Collections;
import java.util.List;

import net.minecraft.item.ItemStack;

import net.minecraftforge.oredict.OreDictionary;

public class OreDictionaryUtil {

	public static final String GRAIN_BARLEY = "seedBarley";
	public static final String GRAIN_WHEAT = "seedWheat";
	public static final String GRAIN_RYE = "seedRye";
	public static final String GRAIN_CORN = "seedCorn";
	public static final String GRAIN_ROASTED = "seedRoasted";
	public static final String HOPS = "cropHops";

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

	public static List<ItemStack> getOres(String oreName) {
		return OreDictionary.getOres(oreName);
	}

	public static boolean hasOreId(ItemStack itemStack, int oreId) {
		int[] oreIds = OreDictionary.getOreIDs(itemStack);
		return contains(oreIds, oreId);
	}

	public static boolean hasOreName(ItemStack itemStack, String oreName) {
		int[] oreIds = OreDictionary.getOreIDs(itemStack);
		int oreId = OreDictionary.getOreID(oreName);
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
