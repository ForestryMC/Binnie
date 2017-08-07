package binnie.botany.api.genetics;

import forestry.api.core.EnumTemperature;
import forestry.api.genetics.EnumTolerance;
import forestry.api.genetics.IGenome;

import binnie.botany.api.gardening.EnumAcidity;
import binnie.botany.api.gardening.EnumMoisture;

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

	boolean canTolerate(EnumAcidity acidity);

	boolean canTolerate(EnumMoisture moisture);

	boolean canTolerate(EnumTemperature temperature);
}
