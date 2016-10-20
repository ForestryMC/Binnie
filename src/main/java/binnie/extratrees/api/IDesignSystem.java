// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extratrees.api;

import net.minecraft.item.ItemStack;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public interface IDesignSystem
{
	IIcon getPrimaryIcon(final IPattern p0);

	IIcon getSecondaryIcon(final IPattern p0);

	void registerIcons(final IIconRegister p0);

	IDesignMaterial getDefaultMaterial();

	IDesignMaterial getMaterial(final int p0);

	int getMaterialIndex(final IDesignMaterial p0);

	IDesignMaterial getDefaultMaterial2();

	ItemStack getAdhesive();

	IDesignMaterial getMaterial(final ItemStack p0);
}
