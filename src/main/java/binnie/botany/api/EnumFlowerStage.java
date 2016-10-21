package binnie.botany.api;

import forestry.api.genetics.ISpeciesType;
import forestry.arboriculture.genetics.TreeRoot;

public enum EnumFlowerStage implements ISpeciesType {
    FLOWER("Flower"),
    SEED("Seed"),
    POLLEN("Pollen");

    public static final EnumFlowerStage[] VALUES;
    String name;

    EnumFlowerStage(final String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    static {
        VALUES = values();
    }
}
