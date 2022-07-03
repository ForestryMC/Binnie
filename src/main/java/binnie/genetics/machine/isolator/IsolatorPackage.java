package binnie.genetics.machine.isolator;

import binnie.core.craftgui.minecraft.IMachineInformation;
import binnie.core.machines.Machine;
import binnie.core.machines.TileEntityMachine;
import binnie.core.machines.inventory.ComponentChargedSlots;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.core.machines.inventory.ComponentInventoryTransfer;
import binnie.core.machines.inventory.ComponentTankContainer;
import binnie.core.machines.inventory.InventorySlot;
import binnie.core.machines.inventory.SlotValidator;
import binnie.core.machines.power.ComponentPowerReceptor;
import binnie.genetics.core.GeneticsGUI;
import binnie.genetics.core.GeneticsTexture;
import binnie.genetics.item.GeneticsItems;
import binnie.genetics.machine.ComponentGeneticGUI;
import binnie.genetics.machine.ModuleMachine;
import binnie.genetics.machine.PackageGeneticBase;
import net.minecraft.tileentity.TileEntity;

public class IsolatorPackage extends PackageGeneticBase implements IMachineInformation {
    public IsolatorPackage() {
        super("isolator", GeneticsTexture.Isolator, 0xff6f0f, true);
    }

    @Override
    public void createMachine(Machine machine) {
        new ComponentGeneticGUI(machine, GeneticsGUI.Isolator);
        ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
        InventorySlot enzymeSlot = inventory.addSlot(Isolator.SLOT_ENZYME, "enzyme");
        enzymeSlot.setValidator(new SlotValidator.Item(GeneticsItems.Enzyme.get(1), ModuleMachine.IconEnzyme));
        enzymeSlot.forbidExtraction();

        InventorySlot vialSlot = inventory.addSlot(Isolator.SLOT_SEQUENCER_VIAL, "sequencervial");
        vialSlot.setValidator(new SlotValidator.Item(GeneticsItems.EmptySequencer.get(1), ModuleMachine.IconSequencer));
        vialSlot.forbidExtraction();

        inventory.addSlotArray(Isolator.SLOT_RESERVE, "input");
        for (InventorySlot slot : inventory.getSlots(Isolator.SLOT_RESERVE)) {
            slot.setValidator(new SlotValidator.Individual());
            slot.forbidExtraction();
        }

        InventorySlot targetSlot = inventory.addSlot(Isolator.SLOT_TARGET, "process");
        targetSlot.setValidator(new SlotValidator.Individual());
        targetSlot.setReadOnly();
        targetSlot.forbidInteraction();

        InventorySlot resultSlot = inventory.addSlot(Isolator.SLOT_RESULT, "output");
        resultSlot.setReadOnly();
        resultSlot.forbidInteraction();

        inventory.addSlotArray(Isolator.SLOT_FINISHED, "output");
        for (InventorySlot slot : inventory.getSlots(Isolator.SLOT_FINISHED)) {
            slot.setReadOnly();
            slot.forbidInsertion();
        }

        ComponentTankContainer tanks = new ComponentTankContainer(machine);
        tanks.addTank(Isolator.TANK_ETHANOL, "input", Isolator.ETHANOL_CAPACITY);
        tanks.getTankSlot(Isolator.TANK_ETHANOL).setValidator(new EthanolTankValidator());

        ComponentChargedSlots chargedSlots = new ComponentChargedSlots(machine);
        chargedSlots.addCharge(Isolator.SLOT_ENZYME);

        ComponentInventoryTransfer transfer = new ComponentInventoryTransfer(machine);
        transfer.addRestock(Isolator.SLOT_RESERVE, Isolator.SLOT_TARGET, 1);
        transfer.addStorage(Isolator.SLOT_RESULT, Isolator.SLOT_FINISHED);
        new ComponentPowerReceptor(machine, Isolator.POWER_CAPACITY);
        new IsolatorComponentLogic(machine);
        new IsolatorComponentFX(machine);
    }

    @Override
    public TileEntity createTileEntity() {
        return new TileEntityMachine(this);
    }
}
