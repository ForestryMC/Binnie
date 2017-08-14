package binnie.genetics.api.acclimatiser;

import forestry.api.genetics.IChromosomeType;

public interface IAcclimatiserManager {
	void addTolerance(final IChromosomeType chromosome, final IToleranceType type);

	IToleranceType getTemperatureToleranceType();

	IToleranceType getHumidityToleranceType();
}
