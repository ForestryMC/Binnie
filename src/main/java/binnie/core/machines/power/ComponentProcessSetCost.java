package binnie.core.machines.power;

import binnie.core.machines.IMachine;

public class ComponentProcessSetCost extends ComponentProcess {
    private int processLength;
    private int processEnergy;

    public ComponentProcessSetCost(IMachine machine, int rfCost, int timePeriod) {
        super(machine);
        processLength = timePeriod;
        processEnergy = rfCost;
    }

    @Override
    public int getProcessLength() {
        return processLength;
    }

    @Override
    public int getProcessEnergy() {
        return processEnergy;
    }
}
