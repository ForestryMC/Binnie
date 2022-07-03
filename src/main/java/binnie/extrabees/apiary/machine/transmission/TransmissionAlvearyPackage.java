package binnie.extrabees.apiary.machine.transmission;

import binnie.core.craftgui.minecraft.IMachineInformation;
import binnie.core.machines.Machine;
import binnie.core.machines.power.ComponentPowerReceptor;
import binnie.extrabees.apiary.machine.AlvearyMachine;
import binnie.extrabees.core.ExtraBeeTexture;

public class TransmissionAlvearyPackage extends AlvearyMachine.AlvearyPackage implements IMachineInformation {
    public TransmissionAlvearyPackage() {
        super("transmission", ExtraBeeTexture.AlvearyTransmission.getTexture(), false);
    }

    @Override
    public void createMachine(Machine machine) {
        new ComponentPowerReceptor(machine, 1000);
        new TransmissionModifierComponent(machine);
    }
}
