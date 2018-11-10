package binnie.extratrees.api;

import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;

import binnie.design.api.IDesignMaterial;

public interface ICarpentryInterface {
	boolean registerCarpentryWood(final int index, final IDesignMaterial material);

	int getCarpentryWoodIndex(final IDesignMaterial material);

	IDesignMaterial getWoodMaterial(final int p0);


	@Nullable
	IDesignMaterial getWoodMaterial(final ItemStack stack);


}
