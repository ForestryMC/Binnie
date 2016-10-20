// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.item;

import java.util.List;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.item.ItemStack;

public interface IItemMisc extends IItemEnum
{
	IIcon getIcon(final ItemStack p0);

	@SideOnly(Side.CLIENT)
	void registerIcons(final IIconRegister p0);

	void addInformation(final List p0);
}
