// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extratrees.block;

import binnie.extratrees.ExtraTrees;
import net.minecraft.client.renderer.texture.IIconRegister;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import binnie.core.BinnieCore;
import net.minecraft.util.IIcon;
import forestry.api.core.IIconProvider;

public enum FruitPod implements IIconProvider
{
	Cocoa,
	Banana,
	Coconut,
	Plantain,
	RedBanana,
	Papayimar;

	short[] textures;
	IIcon[] icons;

	private FruitPod() {
		this.textures = new short[] { BinnieCore.proxy.getUniqueTextureUID(), BinnieCore.proxy.getUniqueTextureUID(), BinnieCore.proxy.getUniqueTextureUID() };
		this.icons = new IIcon[3];
	}

	public short[] getTextures() {
		return this.textures;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(final short texUID) {
		final int index = this.textures[0] - texUID;
		if (index >= 0 && index < 3) {
			return this.icons[index];
		}
		return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(final IIconRegister register) {
		this.icons[0] = ExtraTrees.proxy.getIcon(register, "pods/" + this.toString().toLowerCase() + ".0");
		this.icons[1] = ExtraTrees.proxy.getIcon(register, "pods/" + this.toString().toLowerCase() + ".1");
		this.icons[2] = ExtraTrees.proxy.getIcon(register, "pods/" + this.toString().toLowerCase() + ".2");
	}
}
