package binnie.genetics.machine.acclimatiser;

import binnie.core.craftgui.minecraft.IMachineInformation;
import binnie.core.machines.Machine;
import binnie.core.machines.TileEntityMachine;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.core.machines.inventory.ComponentInventoryTransfer;
import binnie.core.machines.inventory.InventorySlot;
import binnie.core.machines.inventory.SlotValidator;
import binnie.core.machines.power.ComponentPowerReceptor;
import binnie.genetics.core.GeneticsGUI;
import binnie.genetics.core.GeneticsTexture;
import binnie.genetics.machine.ComponentGeneticGUI;
import binnie.genetics.machine.PackageGeneticBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class AcclimatiserPackage extends PackageGeneticBase implements IMachineInformation {
    public AcclimatiserPackage() {
        super("acclimatiser", GeneticsTexture.Acclimatiser, 0x966a49, true);
    }

    @Override
    public void createMachine(Machine machine) {
        new ComponentGeneticGUI(machine, GeneticsGUI.Acclimatiser);
        ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
        InventorySlot processSlot = inventory.addSlot(Acclimatiser.SLOT_TARGET, "process");
        processSlot.setValidator(new SlotValidator.Individual());
        processSlot.setReadOnly();
        processSlot.forbidExtraction();

        for (InventorySlot slot : inventory.addSlotArray(Acclimatiser.SLOT_RESERVE, "input")) {
            slot.forbidExtraction();
            slot.setValidator(new SlotValidator.Individual());
        }

        for (InventorySlot slot : inventory.addSlotArray(Acclimatiser.SLOT_DONE, "output")) {
            slot.setReadOnly();
            slot.setValidator(new SlotValidator.Individual());
        }

        for (InventorySlot slot : inventory.addSlotArray(Acclimatiser.SLOT_ACCLIMATISER, "acclimatiser")) {
            slot.setValidator(new AcclimatiserItemSlotValidator());
        }

        ComponentInventoryTransfer transfer = new ComponentInventoryTransfer(machine);
        transfer.addRestock(Acclimatiser.SLOT_RESERVE, Acclimatiser.SLOT_TARGET, 1);
        transfer.addStorage(
                Acclimatiser.SLOT_TARGET, Acclimatiser.SLOT_DONE, new ComponentInventoryTransfer.Condition() {
                    @Override
                    public boolean fufilled(ItemStack stack) {
                        return !Acclimatiser.canAcclimatise(
                                stack, machine.getMachineUtil().getNonNullStacks(Acclimatiser.SLOT_ACCLIMATISER));
                    }
                });

        new ComponentPowerReceptor(machine, 5000);
        new AcclimatiserComponentLogic(machine);
    }

    @Override
    public TileEntity createTileEntity() {
        return new TileEntityMachine(this);
    }
}
