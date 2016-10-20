// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extratrees.genetics;

import binnie.extratrees.ExtraTrees;
import binnie.Binnie;
import net.minecraft.client.renderer.texture.IIconRegister;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;
import binnie.core.resource.BinnieIcon;
import forestry.api.core.IIconProvider;

public enum FruitSprite implements IIconProvider
{
	Tiny,
	Small,
	Average,
	Large,
	Larger,
	Pear;

	BinnieIcon icon;

	public short getIndex() {
		return (short) (this.ordinal() + 4200);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(final short texUID) {
		final int index = texUID - 4200;
		if (index >= 0 && index < values().length) {
			return values()[index].icon.getIcon();
		}
		return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(final IIconRegister register) {
		this.icon = Binnie.Resource.getBlockIcon(ExtraTrees.instance, "fruit/" + this.toString().toLowerCase());
	}
}
