package binnie.extratrees.genetics;

import binnie.Binnie;
import binnie.core.resource.BinnieIcon;
import binnie.extratrees.ExtraTrees;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.core.IIconProvider;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public enum FruitSprite implements IIconProvider {
    Tiny,
    Small,
    Average,
    Large,
    Larger,
    Pear;

    protected BinnieIcon icon;

    public short getIndex() {
        return (short) (ordinal() + 4200);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(short texUID) {
        int index = texUID - 4200;
        if (index >= 0 && index < values().length) {
            return values()[index].icon.getIcon();
        }
        return null;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        icon = Binnie.Resource.getBlockIcon(
                ExtraTrees.instance, "fruit/" + toString().toLowerCase());
    }
}
