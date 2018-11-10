package binnie.extrabees.machines.stimulator;


import binnie.core.gui.minecraft.IMachineInformation;
import binnie.core.machines.Machine;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.core.machines.power.ComponentPowerReceptor;
import binnie.extrabees.gui.ExtraBeesGUID;
import binnie.extrabees.machines.ExtraBeeMachines;
import binnie.extrabees.machines.stimulator.window.SlotValidatorCircuit;

public class AlvearyStimulator extends ExtraBeeMachines.AlvearyPackage implements IMachineInformation {
	public static final int SLOT_CIRCUIT = 0;

	public AlvearyStimulator() {
		super("stimulator");
	}

	@Override
	public void createMachine(final Machine machine) {
		new ExtraBeeMachines.ComponentExtraBeeGUI(machine, ExtraBeesGUID.ALVEARY_STIMULATOR);
		final ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
		inventory.addSlot(AlvearyStimulator.SLOT_CIRCUIT, getSlotRL("circuit")).setValidator(new SlotValidatorCircuit());
		final ComponentPowerReceptor power = new ComponentPowerReceptor(machine);
		new ComponentStimulatorModifier(machine);
	}
}
