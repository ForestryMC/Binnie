package binnie.botany.api;

import forestry.api.core.EnumTemperature;
import forestry.api.genetics.EnumTolerance;
import forestry.api.genetics.IGenome;

public interface IFlowerGenome extends IGenome {
    @Override
    IAlleleFlowerSpecies getPrimary();

    @Override
    IAlleleFlowerSpecies getSecondary();

    IFlowerColor getPrimaryColor();

    IFlowerColor getSecondaryColor();

    IFlowerColor getStemColor();

    int getFertility();

    int getLifespan();

    IFlowerType getType();

    EnumTolerance getToleranceTemperature();

    EnumTolerance getToleranceMoisture();

    EnumTolerance getTolerancePH();

    float getAgeChance();

    float getSappiness();

    boolean canTolerate(EnumAcidity pH);

    boolean canTolerate(EnumMoisture moisture);

    boolean canTolerate(EnumTemperature temp);
}
