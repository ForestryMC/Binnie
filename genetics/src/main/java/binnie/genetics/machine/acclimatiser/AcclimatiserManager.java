package binnie.genetics.machine.acclimatiser;

import forestry.api.genetics.IChromosomeType;

import binnie.genetics.api.acclimatiser.IAcclimatiserManager;
import binnie.genetics.api.acclimatiser.IToleranceType;

public class AcclimatiserManager implements IAcclimatiserManager {
	@Override
	public void addTolerance(IChromosomeType chromosome, IToleranceType type) {
		Acclimatiser.addTolerance(chromosome, type);
	}

	@Override
	public IToleranceType getTemperatureToleranceType() {
		return ToleranceType.Temperature;
	}

	@Override
	public IToleranceType getHumidityToleranceType() {
		return ToleranceType.Humidity;
	}
}
