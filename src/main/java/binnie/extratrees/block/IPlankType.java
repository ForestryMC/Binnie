package binnie.extratrees.block;

import binnie.extratrees.api.IDesignMaterial;
import net.minecraft.util.IIcon;

public interface IPlankType extends IDesignMaterial {
    IIcon getIcon();

    String getDescription();
}
