package binnie.extrabees.machines.lighting;

import binnie.core.gui.minecraft.IMachineInformation;
import binnie.core.machines.Machine;
import binnie.extrabees.machines.ExtraBeeMachines;

public class AlvearyLighting extends ExtraBeeMachines.AlvearyPackage implements IMachineInformation {
	public AlvearyLighting() {
		super("lighting");
	}

	@Override
	public void createMachine(final Machine machine) {
		new ComponentLighting(machine);
	}
}

