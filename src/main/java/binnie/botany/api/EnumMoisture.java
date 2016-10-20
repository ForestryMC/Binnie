package binnie.botany.api;

import net.minecraft.util.IStringSerializable;

public enum EnumMoisture implements IStringSerializable {
    Dry,
    Normal,
    Damp;

    public String getID() {
        return this.name().toLowerCase();
    }

    @Override
    public String getName() {
        return name().toLowerCase();
    }
}
