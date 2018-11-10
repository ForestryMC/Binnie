package binnie.extrabees.machines.rainshield;

import binnie.core.gui.minecraft.IMachineInformation;
import binnie.core.machines.Machine;
import binnie.extrabees.machines.ExtraBeeMachines;


public class AlvearyRainShield extends ExtraBeeMachines.AlvearyPackage implements IMachineInformation {
	public AlvearyRainShield() {
		super("rainShield");
	}

	@Override
	public void createMachine(final Machine machine) {
		new ComponentRainShield(machine);
	}
}
