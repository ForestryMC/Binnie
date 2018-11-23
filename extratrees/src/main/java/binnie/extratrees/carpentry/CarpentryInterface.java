package binnie.extratrees.carpentry;

import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.Map;

import net.minecraft.item.ItemStack;

import binnie.design.api.IDesignMaterial;
import binnie.extratrees.api.ICarpentryInterface;

public class CarpentryInterface implements ICarpentryInterface {
	private static final Map<Integer, IDesignMaterial> woodMap = new LinkedHashMap<>();

	@Override
	public boolean registerCarpentryWood(int index, IDesignMaterial wood) {
		return CarpentryInterface.woodMap.put(index, wood) == null;
	}

	@Override
	public int getCarpentryWoodIndex(IDesignMaterial wood) {
		for (Integer integer : CarpentryInterface.woodMap.keySet()) {
			if (CarpentryInterface.woodMap.get(integer).equals(wood)) {
				return integer;
			}
		}
		return -1;
	}

	@Override
	public IDesignMaterial getWoodMaterial(int index) {
		return CarpentryInterface.woodMap.get(index);
	}


	@Override
	@Nullable
	public IDesignMaterial getWoodMaterial(ItemStack stack) {
		for (Map.Entry<Integer, IDesignMaterial> entry : CarpentryInterface.woodMap.entrySet()) {
			for (boolean fireproof : new boolean[]{true, false}) {
				ItemStack key = entry.getValue().getStack(fireproof);
				if (key.isItemEqual(stack)) {
					return entry.getValue();
				}
			}
		}
		return null;
	}
}
