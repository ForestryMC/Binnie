package binnie.extrabees.apiary.machine.frame;

import binnie.core.craftgui.minecraft.IMachineInformation;
import binnie.core.machines.Machine;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.extrabees.apiary.ComponentExtraBeeGUI;
import binnie.extrabees.apiary.machine.AlvearyMachine;
import binnie.extrabees.core.ExtraBeeGUID;
import binnie.extrabees.core.ExtraBeeTexture;

public class FrameAlvearyPackage extends AlvearyMachine.AlvearyPackage implements IMachineInformation {
    public FrameAlvearyPackage() {
        super("frame", ExtraBeeTexture.AlvearyFrame.getTexture(), false);
    }

    @Override
    public void createMachine(Machine machine) {
        new ComponentExtraBeeGUI(machine, ExtraBeeGUID.AlvearyFrame);
        ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
        inventory.addSlot(AlvearyFrame.SLOT_FRAME, "frame");
        inventory.getSlot(AlvearyFrame.SLOT_FRAME).setValidator(new FrameSlotValidator());
        new FrameComponentModifier(machine);
    }
}
