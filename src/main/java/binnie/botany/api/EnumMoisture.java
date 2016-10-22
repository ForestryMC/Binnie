package binnie.botany.api;

import net.minecraft.util.IStringSerializable;

public enum EnumMoisture implements IStringSerializable {
    Dry,
    Normal,
    Damp;

    @Override
    public String getName() {
        return name().toLowerCase();
    }
}
