package binnie.extratrees.api;

import net.minecraft.util.IIcon;

public interface ILayout {
    IPattern getPattern();

    boolean isInverted();

    ILayout rotateRight();

    ILayout rotateLeft();

    ILayout flipHorizontal();

    ILayout flipVertical();

    ILayout invert();

    IIcon getPrimaryIcon(IDesignSystem system);

    IIcon getSecondaryIcon(IDesignSystem system);
}
