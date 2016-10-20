// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extratrees.api;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public interface IPattern
{
	IIcon getPrimaryIcon(final IDesignSystem p0);

	IIcon getSecondaryIcon(final IDesignSystem p0);

	ILayout getRotation();

	ILayout getHorizontalFlip();

	void registerIcons(final IIconRegister p0);
}
