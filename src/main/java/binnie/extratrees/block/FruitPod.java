package binnie.extratrees.block;

import binnie.core.BinnieCore;

public enum FruitPod {
    Cocoa,
    Banana,
    Coconut,
    Plantain,
    RedBanana,
    Papayimar;

    short[] textures;
//	IIcon[] icons;

    FruitPod() {
        this.textures = new short[]{BinnieCore.proxy.getUniqueTextureUID(), BinnieCore.proxy.getUniqueTextureUID(), BinnieCore.proxy.getUniqueTextureUID()};
//		this.icons = new IIcon[3];
    }

    public short[] getTextures() {
        return this.textures;
    }

//	@Override
//	@SideOnly(Side.CLIENT)
//	public IIcon getIcon(final short texUID) {
//		final int index = this.textures[0] - texUID;
//		if (index >= 0 && index < 3) {
//			return this.icons[index];
//		}
//		return null;
//	}
//
//	@Override
//	@SideOnly(Side.CLIENT)
//	public void registerIcons(final IIconRegister register) {
//		this.icons[0] = ExtraTrees.proxy.getIcon(register, "pods/" + this.toString().toLowerCase() + ".0");
//		this.icons[1] = ExtraTrees.proxy.getIcon(register, "pods/" + this.toString().toLowerCase() + ".1");
//		this.icons[2] = ExtraTrees.proxy.getIcon(register, "pods/" + this.toString().toLowerCase() + ".2");
//	}
}
