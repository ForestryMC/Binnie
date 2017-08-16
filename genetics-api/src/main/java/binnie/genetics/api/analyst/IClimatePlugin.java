package binnie.genetics.api.analyst;

import forestry.api.genetics.EnumTolerance;
import forestry.api.genetics.IIndividual;

public interface IClimatePlugin<T extends IIndividual> {
	EnumTolerance getTemperatureTolerance(T individual);

	EnumTolerance getHumidityTolerance(T individual);

	boolean showHumiditySection();
}
