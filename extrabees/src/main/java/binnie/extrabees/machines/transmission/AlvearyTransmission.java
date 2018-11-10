package binnie.extrabees.machines.transmission;


import binnie.core.gui.minecraft.IMachineInformation;
import binnie.core.machines.Machine;
import binnie.core.machines.power.ComponentPowerReceptor;
import binnie.extrabees.machines.ExtraBeeMachines;


public class AlvearyTransmission extends ExtraBeeMachines.AlvearyPackage implements IMachineInformation {
	public AlvearyTransmission() {
		super("transmission");
	}

	@Override
	public void createMachine(final Machine machine) {
		new ComponentPowerReceptor(machine, 1000);
		new ComponentTransmission(machine);
	}

}
