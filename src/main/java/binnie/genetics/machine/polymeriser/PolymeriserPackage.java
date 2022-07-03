package binnie.genetics.machine.polymeriser;

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
import binnie.genetics.machine.ComponentGeneticGUI;
import binnie.genetics.machine.ModuleMachine;
import binnie.genetics.machine.PackageGeneticBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class PolymeriserPackage extends PackageGeneticBase implements IMachineInformation {
    public PolymeriserPackage() {
        super("polymeriser", GeneticsTexture.Polymeriser, 0x00e5c3, true);
    }

    @Override
    public void createMachine(Machine machine) {
        new ComponentGeneticGUI(machine, GeneticsGUI.Replicator);
        ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
        InventorySlot goldSlot = inventory.addSlot(Polymeriser.SLOT_GOLD, "catalyst");
        goldSlot.setValidator(new SlotValidator.Item(new ItemStack(Items.gold_nugget, 1), ModuleMachine.IconNugget));
        goldSlot.forbidExtraction();

        InventorySlot serumSlot = inventory.addSlot(Polymeriser.SLOT_SERUM, "process");
        serumSlot.setValidator(new UnfilledSerumSlotValidator());
        serumSlot.forbidInteraction();
        serumSlot.setReadOnly();

        for (InventorySlot slot : inventory.addSlotArray(Polymeriser.SLOT_SERUM_RESERVE, "input")) {
            slot.setValidator(new UnfilledSerumSlotValidator());
            slot.forbidExtraction();
        }

        for (InventorySlot slot : inventory.addSlotArray(Polymeriser.SLOT_SERUM_FINISHED, "output")) {
            slot.setReadOnly();
        }

        ComponentInventoryTransfer transfer = new ComponentInventoryTransfer(machine);
        transfer.addRestock(Polymeriser.SLOT_SERUM_RESERVE, Polymeriser.SLOT_SERUM, 1);
        transfer.addStorage(
                Polymeriser.SLOT_SERUM, Polymeriser.SLOT_SERUM_FINISHED, new ComponentInventoryTransfer.Condition() {
                    @Override
                    public boolean fufilled(ItemStack stack) {
                        return !stack.isItemDamaged();
                    }
                });

        ComponentTankContainer tank = new ComponentTankContainer(machine);
        tank.addTank(Polymeriser.TANK_BACTERIA, "input", Polymeriser.BACTERIA_TANK_CAPACITY);
        tank.getTankSlot(Polymeriser.TANK_BACTERIA).setValidator(new PolymerisingBacteriaValidator());

        tank.addTank(Polymeriser.TANK_DNA, "input", Polymeriser.DNA_TANK_CAPACITY);
        tank.getTankSlot(Polymeriser.TANK_DNA).setValidator(new DnaValidator());

        new ComponentChargedSlots(machine).addCharge(Polymeriser.SLOT_GOLD);
        new ComponentPowerReceptor(machine, Polymeriser.POWER_CAPACITY);
        new PolymeriserComponentLogic(machine);
        new PolymeriserComponentFX(machine);
    }

    @Override
    public TileEntity createTileEntity() {
        return new TileEntityMachine(this);
    }
}
