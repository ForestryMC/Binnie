package binnie.extrabees.apiary.machine.mutator;

import binnie.core.craftgui.minecraft.IMachineInformation;
import binnie.core.machines.Machine;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.extrabees.apiary.ComponentExtraBeeGUI;
import binnie.extrabees.apiary.machine.AlvearyMachine;
import binnie.extrabees.core.ExtraBeeGUID;
import binnie.extrabees.core.ExtraBeeTexture;

public class MutatorAlvearyPackage extends AlvearyMachine.AlvearyPackage implements IMachineInformation {
    public MutatorAlvearyPackage() {
        super("mutator", ExtraBeeTexture.AlvearyMutator.getTexture(), false);
    }

    @Override
    public void createMachine(Machine machine) {
        new ComponentExtraBeeGUI(machine, ExtraBeeGUID.AlvearyMutator);
        ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
        inventory.addSlot(AlvearyMutator.SLOT_MUTATOR, "mutator");
        inventory.getSlot(AlvearyMutator.SLOT_MUTATOR).setValidator(new MutatorSlotValidator());
        new MutatorModifierComponent(machine);
    }
}
