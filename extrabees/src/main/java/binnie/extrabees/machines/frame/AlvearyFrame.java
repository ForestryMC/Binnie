package binnie.extrabees.machines.frame;

import binnie.core.gui.minecraft.IMachineInformation;
import binnie.core.machines.Machine;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.extrabees.gui.ExtraBeesGUID;
import binnie.extrabees.machines.ExtraBeeMachines;
import binnie.extrabees.machines.frame.window.SlotValidatorFrame;

public class AlvearyFrame extends ExtraBeeMachines.AlvearyPackage implements IMachineInformation {
	public static final int SLOT_FRAME = 0;

	public AlvearyFrame() {
		super("frame");
	}

	@Override
	public void createMachine(Machine machine) {
		new ExtraBeeMachines.ComponentExtraBeeGUI(machine, ExtraBeesGUID.ALVEARY_FRAME);
		ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
		inventory.addSlot(AlvearyFrame.SLOT_FRAME, getSlotRL("frame")).setValidator(new SlotValidatorFrame());
		new ComponentFrameModifier(machine);
	}

}
