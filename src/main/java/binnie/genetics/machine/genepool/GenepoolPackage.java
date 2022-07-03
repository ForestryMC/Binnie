package binnie.genetics.machine.genepool;

import binnie.core.craftgui.minecraft.IMachineInformation;
import binnie.core.machines.Machine;
import binnie.core.machines.TileEntityMachine;
import binnie.core.machines.inventory.ComponentChargedSlots;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.core.machines.inventory.ComponentInventoryTransfer;
import binnie.core.machines.inventory.ComponentTankContainer;
import binnie.core.machines.inventory.InventorySlot;
import binnie.core.machines.inventory.SlotValidator;
import binnie.core.machines.inventory.TankSlot;
import binnie.core.machines.power.ComponentPowerReceptor;
import binnie.genetics.core.GeneticsGUI;
import binnie.genetics.core.GeneticsTexture;
import binnie.genetics.item.GeneticsItems;
import binnie.genetics.machine.ComponentGeneticGUI;
import binnie.genetics.machine.ModuleMachine;
import binnie.genetics.machine.PackageGeneticBase;
import net.minecraft.tileentity.TileEntity;

public class GenepoolPackage extends PackageGeneticBase implements IMachineInformation {
    public GenepoolPackage() {
        super("genepool", GeneticsTexture.Genepool, 0xc134b6, true);
    }

    @Override
    public void createMachine(Machine machine) {
        new ComponentGeneticGUI(machine, GeneticsGUI.Genepool);
        ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
        InventorySlot enzymeSlot = inventory.addSlot(Genepool.SLOT_ENZYME, "enzyme");
        enzymeSlot.setValidator(new SlotValidator.Item(GeneticsItems.Enzyme.get(1), ModuleMachine.IconEnzyme));
        enzymeSlot.forbidExtraction();

        InventorySlot slotBee = inventory.addSlot(Genepool.SLOT_BEE, "process");
        slotBee.setValidator(new SlotValidator.Individual());
        slotBee.setReadOnly();
        slotBee.forbidExtraction();
        inventory.addSlotArray(Genepool.SLOT_RESERVE, "input");

        for (InventorySlot slot : inventory.getSlots(Genepool.SLOT_RESERVE)) {
            slot.setValidator(new SlotValidator.Individual());
            slot.forbidExtraction();
        }

        ComponentTankContainer tanks = new ComponentTankContainer(machine);
        TankSlot dnaSlot = tanks.addTank(Genepool.TANK_DNA, "output", Genepool.TANK_DNA_CAPACITY);
        dnaSlot.setReadOnly();

        TankSlot ethanolSlot = tanks.addTank(Genepool.TANK_ETHANOL, "input", Genepool.TANK_ETHANOL_CAPACITY);
        ethanolSlot.forbidExtraction();
        ethanolSlot.setValidator(new EthanolTankValidator());

        ComponentInventoryTransfer transfer = new ComponentInventoryTransfer(machine);
        transfer.addRestock(Genepool.SLOT_RESERVE, Genepool.SLOT_BEE, 1);
        new ComponentPowerReceptor(machine, Genepool.POWER_CAPACITY);
        new GenepoolComponentLogic(machine);
        ComponentChargedSlots chargedSlots = new ComponentChargedSlots(machine);
        chargedSlots.addCharge(Genepool.SLOT_ENZYME);
        new GenepoolComponentFX(machine);
    }

    @Override
    public TileEntity createTileEntity() {
        return new TileEntityMachine(this);
    }
}
