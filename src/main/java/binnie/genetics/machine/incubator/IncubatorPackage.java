package binnie.genetics.machine.incubator;

import binnie.core.craftgui.minecraft.IMachineInformation;
import binnie.core.machines.Machine;
import binnie.core.machines.TileEntityMachine;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.core.machines.inventory.ComponentTankContainer;
import binnie.core.machines.inventory.InventorySlot;
import binnie.core.machines.power.ComponentPowerReceptor;
import binnie.genetics.core.GeneticsGUI;
import binnie.genetics.core.GeneticsTexture;
import binnie.genetics.machine.ComponentGeneticGUI;
import binnie.genetics.machine.PackageGeneticBase;
import net.minecraft.tileentity.TileEntity;

public class IncubatorPackage extends PackageGeneticBase implements IMachineInformation {
    public IncubatorPackage() {
        super("incubator", GeneticsTexture.Incubator, 0xffd951, true);
    }

    @Override
    public void createMachine(Machine machine) {
        new ComponentGeneticGUI(machine, GeneticsGUI.Incubator);
        ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
        inventory.addSlotArray(Incubator.SLOT_QUEUE, "input");
        for (InventorySlot slot : inventory.getSlots(Incubator.SLOT_QUEUE)) {
            slot.forbidExtraction();
        }

        InventorySlot incubatorSlot = inventory.addSlot(Incubator.SLOT_INCUBATOR, "incubator");
        incubatorSlot.forbidInteraction();
        incubatorSlot.setReadOnly();

        inventory.addSlotArray(Incubator.SLOT_OUTPUT, "output");
        for (InventorySlot slot : inventory.getSlots(Incubator.SLOT_OUTPUT)) {
            slot.forbidInsertion();
            slot.setReadOnly();
        }

        new ComponentPowerReceptor(machine, Incubator.POWER_CAPACITY);
        ComponentTankContainer tanks = new ComponentTankContainer(machine);
        tanks.addTank(Incubator.TANK_INPUT, "input", Incubator.INPUT_TANK_CAPACITY)
                .forbidExtraction();
        tanks.addTank(Incubator.TANK_OUTPUT, "output", Incubator.OUTPU_TANK_CAPACITY)
                .setReadOnly();
        new IncubatorComponentLogic(machine);
    }

    @Override
    public TileEntity createTileEntity() {
        return new TileEntityMachine(this);
    }
}
