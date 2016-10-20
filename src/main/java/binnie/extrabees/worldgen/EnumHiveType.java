package binnie.extrabees.worldgen;

import forestry.api.apiculture.IHiveDrop;
import net.minecraft.util.IStringSerializable;

import java.util.ArrayList;
import java.util.List;

public enum EnumHiveType implements IStringSerializable {
    Water,
    Rock,
    Nether,
    Marble;

    public List<IHiveDrop> drops;

    private EnumHiveType() {
        this.drops = new ArrayList<IHiveDrop>();
    }

    @Override
    public String getName() {
        return this.name().toLowerCase();
    }
}
