package binnie.botany.api;

import net.minecraft.util.IStringSerializable;

public enum EnumAcidity implements IStringSerializable {
    Acid,
    Neutral,
    Alkaline;

    public String getID() {
        return this.name().toLowerCase();
    }

    @Override
    public String getName() {
        return this.name().toLowerCase();
    }
}
