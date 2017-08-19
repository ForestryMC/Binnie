package binnie.extratrees.api;

import javax.annotation.Nullable;

import binnie.design.api.IDesignMaterial;
import net.minecraft.item.ItemStack;

public interface ICarpentryInterface {
	boolean registerCarpentryWood(final int index, final IDesignMaterial material);

	int getCarpentryWoodIndex(final IDesignMaterial material);

	IDesignMaterial getWoodMaterial(final int p0);


	@Nullable
	IDesignMaterial getWoodMaterial(final ItemStack stack);


}
