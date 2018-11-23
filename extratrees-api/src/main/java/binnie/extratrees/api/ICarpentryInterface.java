package binnie.extratrees.api;

import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;

import binnie.design.api.IDesignMaterial;

public interface ICarpentryInterface {
	boolean registerCarpentryWood(int index, IDesignMaterial material);

	int getCarpentryWoodIndex(IDesignMaterial material);

	IDesignMaterial getWoodMaterial(int p0);


	@Nullable
	IDesignMaterial getWoodMaterial(ItemStack stack);


}
