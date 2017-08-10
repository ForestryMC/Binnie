package binnie.core.machines.power;

import binnie.core.machines.errors.IErrorStateSource;

interface IProcessTimed extends IProcess, IErrorStateSource {
	int getProcessLength();

	int getProcessEnergy();

	float getProgress();

	float getProgressPerTick();
}
