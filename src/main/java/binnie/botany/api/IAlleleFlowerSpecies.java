package binnie.botany.api;

import forestry.api.genetics.IAlleleSpecies;

public interface IAlleleFlowerSpecies extends IAlleleSpecies {
    IFlowerType getType();

    EnumAcidity getPH();

    EnumMoisture getMoisture();
}
