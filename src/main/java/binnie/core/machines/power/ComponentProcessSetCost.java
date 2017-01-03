package binnie.core.machines.power;

import binnie.core.machines.IMachine;

public class ComponentProcessSetCost extends ComponentProcess {
	private int processLength;
	private int processEnergy;

	public ComponentProcessSetCost(final IMachine machine, final int processEnergy, final int processLength) {
		super(machine);
		this.processLength = processLength;
		this.processEnergy = processEnergy;
	}

	@Override
	public int getProcessLength() {
		return this.processLength;
	}

	@Override
	public int getProcessEnergy() {
		return this.processEnergy;
	}
}
