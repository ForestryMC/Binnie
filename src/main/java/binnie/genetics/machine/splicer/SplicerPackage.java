package binnie.genetics.machine.splicer;

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
import binnie.genetics.genetics.Engineering;
import binnie.genetics.machine.AdvGeneticMachine;
import binnie.genetics.machine.ComponentGeneticGUI;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class SplicerPackage extends AdvGeneticMachine.PackageAdvGeneticBase implements IMachineInformation {
    public SplicerPackage() {
        super("splicer", GeneticsTexture.Splicer, 0xe22235, true);
    }

    @Override
    public void createMachine(Machine machine) {
        new ComponentGeneticGUI(machine, GeneticsGUI.Splicer);
        ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
        SlotValidator serumValid = new SerumSlotValidator();

        InventorySlot serumSlot = inventory.addSlot(Splicer.SLOT_SERUM_VIAL, "serum.active");
        serumSlot.forbidInteraction();
        serumSlot.setReadOnly();
        serumSlot.setValidator(serumValid);

        inventory.addSlotArray(Splicer.SLOT_SERUM_RESERVE, "serum.input");
        for (InventorySlot slot : inventory.getSlots(Splicer.SLOT_SERUM_RESERVE)) {
            slot.setValidator(serumValid);
            slot.forbidExtraction();
        }

        inventory.addSlotArray(Splicer.SLOT_SERUM_EXPENDED, "serum.output");
        for (InventorySlot slot : inventory.getSlots(Splicer.SLOT_SERUM_EXPENDED)) {
            slot.setValidator(serumValid);
            slot.setReadOnly();
        }

        inventory.addSlotArray(Splicer.SLOT_RESERVE, "input");
        for (InventorySlot slot : inventory.getSlots(Splicer.SLOT_RESERVE)) {
            slot.forbidExtraction();
            slot.setValidator(new IndividualInoculateValidator());
        }

        InventorySlot targetSlot = inventory.addSlot(Splicer.SLOT_TARGET, "process");
        targetSlot.setValidator(new IndividualInoculateValidator());
        targetSlot.setReadOnly();
        targetSlot.forbidInteraction();

        inventory.addSlotArray(Splicer.SLOT_FINISHED, "output");
        for (InventorySlot slot : inventory.getSlots(Splicer.SLOT_FINISHED)) {
            slot.setReadOnly();
            slot.forbidInsertion();
            slot.setValidator(new IndividualInoculateValidator());
        }

        ComponentInventoryTransfer transfer = new ComponentInventoryTransfer(machine);
        transfer.addRestock(Splicer.SLOT_RESERVE, Splicer.SLOT_TARGET, 1);
        transfer.addRestock(Splicer.SLOT_SERUM_RESERVE, Splicer.SLOT_SERUM_VIAL);
        transfer.addStorage(
                Splicer.SLOT_SERUM_VIAL, Splicer.SLOT_SERUM_EXPENDED, new ComponentInventoryTransfer.Condition() {
                    @Override
                    public boolean fufilled(ItemStack stack) {
                        return Engineering.getCharges(stack) == 0;
                    }
                });
        transfer.addStorage(Splicer.SLOT_TARGET, Splicer.SLOT_FINISHED, new ComponentInventoryTransfer.Condition() {
            @Override
            public boolean fufilled(ItemStack stack) {
                return stack != null
                        && transfer.getMachine().getMachineUtil().getStack(Splicer.SLOT_SERUM_VIAL) != null
                        && transfer.getMachine()
                                        .getInterface(SplicerComponentLogic.class)
                                        .isValidSerum()
                                != null;
            }
        });

        new ComponentPowerReceptor(machine, Splicer.POWER_CAPACITY);
        new SplicerComponentLogic(machine);
        new SplicerComponentFX(machine);
    }

    @Override
    public TileEntity createTileEntity() {
        return new TileEntityMachine(this);
    }
}
