package binnie.core.machines.power;

import binnie.core.machines.errors.IErrorStateSource;

public interface IProcess extends IErrorStateSource {
	float getEnergyPerTick();

	String getTooltip();

	boolean isInProgress();

	ProcessInfo getInfo();
}
