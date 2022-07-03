package binnie.extrabees.apiary.machine.hatchery;

import binnie.core.craftgui.minecraft.IMachineInformation;
import binnie.core.machines.Machine;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.core.machines.inventory.InventorySlot;
import binnie.extrabees.apiary.ComponentExtraBeeGUI;
import binnie.extrabees.apiary.machine.AlvearyMachine;
import binnie.extrabees.core.ExtraBeeGUID;
import binnie.extrabees.core.ExtraBeeTexture;

public class HatcheryAlvearyPackage extends AlvearyMachine.AlvearyPackage implements IMachineInformation {
    public HatcheryAlvearyPackage() {
        super("hatchery", ExtraBeeTexture.AlvearyHatchery.getTexture(), false);
    }

    @Override
    public void createMachine(Machine machine) {
        new ComponentExtraBeeGUI(machine, ExtraBeeGUID.AlvearyHatchery);
        ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
        for (InventorySlot slot : inventory.addSlotArray(AlvearyHatchery.SLOT_LARVAE, "hatchery")) {
            slot.setValidator(new LarvaeSlotValidator());
        }
        new HatcheryComponentModifier(machine);
    }
}
