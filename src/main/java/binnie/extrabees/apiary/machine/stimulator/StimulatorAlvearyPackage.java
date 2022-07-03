package binnie.extrabees.apiary.machine.stimulator;

import binnie.core.craftgui.minecraft.IMachineInformation;
import binnie.core.machines.Machine;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.core.machines.power.ComponentPowerReceptor;
import binnie.extrabees.apiary.ComponentExtraBeeGUI;
import binnie.extrabees.apiary.machine.AlvearyMachine;
import binnie.extrabees.core.ExtraBeeGUID;
import binnie.extrabees.core.ExtraBeeTexture;

public class StimulatorAlvearyPackage extends AlvearyMachine.AlvearyPackage implements IMachineInformation {
    public StimulatorAlvearyPackage() {
        super("stimulator", ExtraBeeTexture.AlvearyStimulator.getTexture(), true);
    }

    @Override
    public void createMachine(Machine machine) {
        new ComponentExtraBeeGUI(machine, ExtraBeeGUID.AlvearyStimulator);
        ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
        inventory.addSlot(AlvearyStimulator.SLOT_CIRCUIT, "circuit");
        inventory.getSlot(AlvearyStimulator.SLOT_CIRCUIT).setValidator(new CircuitSlotValidator());
        new ComponentPowerReceptor(machine);
        new StimulatorModifierComponent(machine);
    }
}
