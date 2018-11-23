package binnie.genetics.api.acclimatiser;

import forestry.api.genetics.IChromosomeType;

public interface IAcclimatiserManager {
	void addTolerance(IChromosomeType chromosome, IToleranceType type);

	IToleranceType getTemperatureToleranceType();

	IToleranceType getHumidityToleranceType();
}
