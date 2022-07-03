package binnie.extratrees.api;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public interface IDesignSystem {
    IIcon getPrimaryIcon(IPattern pattern);

    IIcon getSecondaryIcon(IPattern pattern);

    void registerIcons(IIconRegister register);

    IDesignMaterial getDefaultMaterial();

    IDesignMaterial getMaterial(int id);

    int getMaterialIndex(IDesignMaterial wood);

    IDesignMaterial getDefaultMaterial2();

    ItemStack getAdhesive();

    IDesignMaterial getMaterial(ItemStack stack);
}
