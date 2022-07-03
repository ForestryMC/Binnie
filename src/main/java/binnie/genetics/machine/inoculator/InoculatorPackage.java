package binnie.genetics.machine.inoculator;

import binnie.core.craftgui.minecraft.IMachineInformation;
import binnie.core.machines.Machine;
import binnie.core.machines.TileEntityMachine;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.core.machines.inventory.ComponentInventoryTransfer;
import binnie.core.machines.inventory.ComponentTankContainer;
import binnie.core.machines.inventory.InventorySlot;
import binnie.core.machines.inventory.SlotValidator;
import binnie.core.machines.power.ComponentPowerReceptor;
import binnie.genetics.core.GeneticsGUI;
import binnie.genetics.core.GeneticsTexture;
import binnie.genetics.genetics.Engineering;
import binnie.genetics.machine.ComponentGeneticGUI;
import binnie.genetics.machine.PackageGeneticBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class InoculatorPackage extends PackageGeneticBase implements IMachineInformation {
    public InoculatorPackage() {
        super("inoculator", GeneticsTexture.Inoculator, 0xe22235, true);
    }

    @Override
    public void createMachine(Machine machine) {
        new ComponentGeneticGUI(machine, GeneticsGUI.Inoculator);
        ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
        SlotValidator serumValid = new SerumSlotValidator();

        InventorySlot slotSerumVial = inventory.addSlot(Inoculator.SLOT_SERUM_VIAL, "serum.active");
        slotSerumVial.forbidInteraction();
        slotSerumVial.setReadOnly();
        slotSerumVial.setValidator(serumValid);

        inventory.addSlotArray(Inoculator.SLOT_SERUM_RESERVE, "serum.input");
        for (InventorySlot slot : inventory.getSlots(Inoculator.SLOT_SERUM_RESERVE)) {
            slot.setValidator(serumValid);
            slot.forbidExtraction();
        }

        inventory.addSlotArray(Inoculator.SLOT_SERUM_EXPENDED, "serum.output");
        for (InventorySlot slot : inventory.getSlots(Inoculator.SLOT_SERUM_EXPENDED)) {
            slot.setValidator(serumValid);
            slot.setReadOnly();
        }

        inventory.addSlotArray(Inoculator.SLOT_RESERVE, "input");
        for (InventorySlot slot : inventory.getSlots(Inoculator.SLOT_RESERVE)) {
            slot.forbidExtraction();
            slot.setValidator(new IndividualInoculateValidator());
        }

        inventory.addSlot(Inoculator.SLOT_TARGET, "process");
        inventory.getSlot(Inoculator.SLOT_TARGET).setValidator(new IndividualInoculateValidator());
        inventory.getSlot(Inoculator.SLOT_TARGET).setReadOnly();
        inventory.getSlot(Inoculator.SLOT_TARGET).forbidInteraction();
        inventory.addSlotArray(Inoculator.SLOT_FINISHED, "output");
        for (InventorySlot slot : inventory.getSlots(Inoculator.SLOT_FINISHED)) {
            slot.setReadOnly();
            slot.forbidInsertion();
            slot.setValidator(new IndividualInoculateValidator());
        }

        ComponentInventoryTransfer transfer = new ComponentInventoryTransfer(machine);
        transfer.addRestock(Inoculator.SLOT_RESERVE, Inoculator.SLOT_TARGET, 1);
        transfer.addRestock(Inoculator.SLOT_SERUM_RESERVE, Inoculator.SLOT_SERUM_VIAL);
        transfer.addStorage(
                Inoculator.SLOT_SERUM_VIAL, Inoculator.SLOT_SERUM_EXPENDED, new ComponentInventoryTransfer.Condition() {
                    @Override
                    public boolean fufilled(ItemStack stack) {
                        return Engineering.getCharges(stack) == 0;
                    }
                });

        transfer.addStorage(9, Inoculator.SLOT_FINISHED, new ComponentInventoryTransfer.Condition() {
            @Override
            public boolean fufilled(ItemStack stack) {
                return stack != null
                        && transfer.getMachine().getMachineUtil().getStack(Inoculator.SLOT_SERUM_VIAL) != null
                        && transfer.getMachine()
                                        .getInterface(InoculatorComponentLogic.class)
                                        .isValidSerum()
                                != null;
            }
        });

        new ComponentPowerReceptor(machine, Inoculator.POWER_STORAGE);
        new InoculatorComponentLogic(machine);
        new InoculatorComponentFX(machine);
        new ComponentTankContainer(machine)
                .addTank(Inoculator.TANK_VECTOR, "input", Inoculator.TANK_CAPACITY)
                .setValidator(new BacteriaVectorValidator());
    }

    @Override
    public TileEntity createTileEntity() {
        return new TileEntityMachine(this);
    }
}
