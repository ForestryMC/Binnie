package binnie.extratrees.machines.lumbermill;

import binnie.core.craftgui.minecraft.IMachineInformation;
import binnie.core.machines.Machine;
import binnie.core.machines.TileEntityMachine;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.core.machines.inventory.ComponentTankContainer;
import binnie.core.machines.inventory.InventorySlot;
import binnie.core.machines.inventory.TankSlot;
import binnie.core.machines.inventory.TankValidator;
import binnie.core.machines.power.ComponentPowerReceptor;
import binnie.extratrees.core.ExtraTreeTexture;
import binnie.extratrees.core.ExtraTreesGUID;
import binnie.extratrees.machines.ExtraTreeMachine;
import net.minecraft.tileentity.TileEntity;

public class LumbermillPackage extends ExtraTreeMachine.PackageExtraTreeMachine implements IMachineInformation {
    public LumbermillPackage() {
        super("lumbermill", ExtraTreeTexture.lumbermillTexture, true);
    }

    @Override
    public void createMachine(Machine machine) {
        new ExtraTreeMachine.ComponentExtraTreeGUI(machine, ExtraTreesGUID.Lumbermill);
        ComponentInventorySlots inventory = new ComponentInventorySlots(machine);

        InventorySlot woodSlot = inventory.addSlot(Lumbermill.SLOT_WOOD, "input");
        woodSlot.setValidator(new LogSlotValidator());
        woodSlot.forbidExtraction();

        InventorySlot planksSlot = inventory.addSlot(Lumbermill.SLOT_PLANKS, "output");
        planksSlot.setReadOnly();

        InventorySlot barkSlot = inventory.addSlot(Lumbermill.SLOT_BARK, "byproduct");
        barkSlot.setReadOnly();

        InventorySlot sawdustSlot = inventory.addSlot(Lumbermill.SLOT_SAWDUST, "byproduct");
        sawdustSlot.setReadOnly();

        ComponentTankContainer tanks = new ComponentTankContainer(machine);
        TankSlot tankSlot = tanks.addTank(Lumbermill.TANK_WATER, "input", 10000);
        tankSlot.setValidator(new TankValidator.Basic("water"));

        new ComponentPowerReceptor(machine);
        new LumbermillComponentLogic(machine);
    }

    @Override
    public TileEntity createTileEntity() {
        return new TileEntityMachine(this);
    }
}
