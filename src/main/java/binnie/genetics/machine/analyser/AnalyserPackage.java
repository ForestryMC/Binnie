package binnie.genetics.machine.analyser;

import binnie.core.craftgui.minecraft.IMachineInformation;
import binnie.core.machines.Machine;
import binnie.core.machines.TileEntityMachine;
import binnie.core.machines.inventory.ComponentChargedSlots;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.core.machines.inventory.ComponentInventoryTransfer;
import binnie.core.machines.inventory.InventorySlot;
import binnie.core.machines.power.ComponentPowerReceptor;
import binnie.genetics.core.GeneticsGUI;
import binnie.genetics.core.GeneticsTexture;
import binnie.genetics.machine.ComponentGeneticGUI;
import binnie.genetics.machine.PackageGeneticBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class AnalyserPackage extends PackageGeneticBase implements IMachineInformation {
    public AnalyserPackage() {
        super("analyser", GeneticsTexture.Analyser, 0x9800ff, true);
    }

    @Override
    public void createMachine(Machine machine) {
        new ComponentGeneticGUI(machine, GeneticsGUI.Analyser);
        ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
        inventory.addSlotArray(Analyser.SLOT_RESERVE, "input");
        for (InventorySlot slot : inventory.getSlots(Analyser.SLOT_RESERVE)) {
            slot.setValidator(new UnanalysedSlotValidator());
            slot.forbidExtraction();
        }

        InventorySlot targetSlot = inventory.addSlot(Analyser.SLOT_TARGET, "analyse");
        targetSlot.setValidator(new UnanalysedSlotValidator());
        targetSlot.setReadOnly();
        targetSlot.forbidInteraction();

        InventorySlot dyeSlot = inventory.addSlot(Analyser.SLOT_DYE, "dye");
        dyeSlot.forbidExtraction();
        dyeSlot.setValidator(new DyeSlotValidator());

        inventory.addSlotArray(Analyser.SLOT_FINISHED, "output");
        for (InventorySlot slot : inventory.getSlots(Analyser.SLOT_FINISHED)) {
            slot.forbidInsertion();
            slot.setReadOnly();
        }

        ComponentInventoryTransfer transfer = new ComponentInventoryTransfer(machine);
        transfer.addRestock(Analyser.SLOT_RESERVE, Analyser.SLOT_TARGET, 1);
        transfer.addStorage(Analyser.SLOT_TARGET, Analyser.SLOT_FINISHED, new ComponentInventoryTransfer.Condition() {
            @Override
            public boolean fufilled(ItemStack stack) {
                return Analyser.isAnalysed(stack);
            }
        });

        new ComponentChargedSlots(machine).addCharge(Analyser.SLOT_DYE);
        new ComponentPowerReceptor(machine, 500);
        new AnalyserComponentLogic(machine);
        new AnalyserComponentFX(machine);
    }

    @Override
    public TileEntity createTileEntity() {
        return new TileEntityMachine(this);
    }
}
