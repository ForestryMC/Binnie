package binnie.core.machines.power;

import binnie.core.machines.IMachine;

public class ComponentProcessSetCost extends ComponentProcess {
    private int processLength;
    private int processEnergy;

    public ComponentProcessSetCost(final IMachine machine, final int rfCost, final int timePeriod) {
        super(machine);
        this.processLength = timePeriod;
        this.processEnergy = rfCost;
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
