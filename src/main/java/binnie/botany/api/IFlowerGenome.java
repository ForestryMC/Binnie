package binnie.botany.api;

import forestry.api.core.EnumTemperature;
import forestry.api.genetics.EnumTolerance;
import forestry.api.genetics.IGenome;

public interface IFlowerGenome extends IGenome {
	@Override
	IAlleleFlowerSpecies getPrimary();

	@Override
	IAlleleFlowerSpecies getSecondary();

	IFlowerColour getPrimaryColor();

	IFlowerColour getSecondaryColor();

	IFlowerColour getStemColor();

	int getFertility();

	int getLifespan();

	IFlowerType getType();

	EnumTolerance getToleranceTemperature();

	EnumTolerance getToleranceMoisture();

	EnumTolerance getTolerancePH();

	float getAgeChance();

	float getSappiness();

	boolean canTolerate(EnumAcidity acidity);

	boolean canTolerate(EnumMoisture moisture);

	boolean canTolerate(EnumTemperature temperature);
}
