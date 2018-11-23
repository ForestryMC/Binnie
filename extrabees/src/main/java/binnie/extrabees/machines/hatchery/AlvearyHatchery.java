package binnie.extrabees.machines.hatchery;

import binnie.core.gui.minecraft.IMachineInformation;
import binnie.core.machines.Machine;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.core.machines.inventory.InventorySlot;
import binnie.extrabees.gui.ExtraBeesGUID;
import binnie.extrabees.machines.ExtraBeeMachines;
import binnie.extrabees.machines.hatchery.window.SlotValidatorLarvae;

public class AlvearyHatchery extends ExtraBeeMachines.AlvearyPackage implements IMachineInformation {
	public static final int[] SLOT_LARVAE = new int[]{0, 1, 2, 3, 4};

	public AlvearyHatchery() {
		super("hatchery");
	}

	@Override
	public void createMachine(Machine machine) {
		new ExtraBeeMachines.ComponentExtraBeeGUI(machine, ExtraBeesGUID.ALVEARY_HATCHERY);
		ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
		for (InventorySlot slot : inventory.addSlotArray(AlvearyHatchery.SLOT_LARVAE, "hatchery")) {
			slot.setValidator(new SlotValidatorLarvae());
		}
		new ComponentFrameModifier(machine);
	}

}
