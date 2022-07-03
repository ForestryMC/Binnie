package binnie.extratrees.api;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public interface IPattern {
    IIcon getPrimaryIcon(IDesignSystem system);

    IIcon getSecondaryIcon(IDesignSystem system);

    ILayout getRotation();

    ILayout getHorizontalFlip();

    void registerIcons(IIconRegister register);
}
