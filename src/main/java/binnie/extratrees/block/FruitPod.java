package binnie.extratrees.block;

import binnie.core.BinnieCore;
import binnie.extratrees.ExtraTrees;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.core.IIconProvider;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public enum FruitPod implements IIconProvider {
    Cocoa,
    Banana,
    Coconut,
    Plantain,
    RedBanana,
    Papayimar;

    protected short[] textures;
    protected IIcon[] icons;

    FruitPod() {
        textures = new short[] {
            BinnieCore.proxy.getUniqueTextureUID(),
            BinnieCore.proxy.getUniqueTextureUID(),
            BinnieCore.proxy.getUniqueTextureUID(),
        };
        icons = new IIcon[3];
    }

    public short[] getTextures() {
        return textures;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(short texUID) {
        int index = textures[0] - texUID;
        if (index >= 0 && index < 3) {
            return icons[index];
        }
        return null;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        icons[0] = ExtraTrees.proxy.getIcon(register, "pods/" + toString().toLowerCase() + ".0");
        icons[1] = ExtraTrees.proxy.getIcon(register, "pods/" + toString().toLowerCase() + ".1");
        icons[2] = ExtraTrees.proxy.getIcon(register, "pods/" + toString().toLowerCase() + ".2");
    }
}
