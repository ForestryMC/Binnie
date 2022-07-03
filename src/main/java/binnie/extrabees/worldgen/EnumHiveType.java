package binnie.extrabees.worldgen;

import forestry.api.apiculture.IHiveDrop;
import java.util.ArrayList;
import java.util.List;

public enum EnumHiveType {
    WATER,
    ROCK,
    NETHER,
    MARBLE;

    public List<IHiveDrop> drops;

    EnumHiveType() {
        drops = new ArrayList<>();
    }
}
