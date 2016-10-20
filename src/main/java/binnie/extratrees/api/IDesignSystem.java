package binnie.extratrees.api;

import net.minecraft.item.ItemStack;

public interface IDesignSystem {
//	IIcon getPrimaryIcon(final IPattern p0);
//
//	IIcon getSecondaryIcon(final IPattern p0);
//
//	void registerIcons(final IIconRegister p0);

    IDesignMaterial getDefaultMaterial();

    IDesignMaterial getMaterial(final int p0);

    int getMaterialIndex(final IDesignMaterial p0);

    IDesignMaterial getDefaultMaterial2();

    ItemStack getAdhesive();

    IDesignMaterial getMaterial(final ItemStack p0);
}
