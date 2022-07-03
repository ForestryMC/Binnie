package binnie.genetics.machine.sequencer;

import binnie.Binnie;
import binnie.core.craftgui.minecraft.IMachineInformation;
import binnie.core.machines.Machine;
import binnie.core.machines.TileEntityMachine;
import binnie.core.machines.inventory.ComponentChargedSlots;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.core.machines.inventory.ComponentInventoryTransfer;
import binnie.core.machines.inventory.InventorySlot;
import binnie.core.machines.inventory.SlotValidator;
import binnie.core.machines.power.ComponentPowerReceptor;
import binnie.genetics.Genetics;
import binnie.genetics.core.GeneticsGUI;
import binnie.genetics.core.GeneticsTexture;
import binnie.genetics.item.GeneticsItems;
import binnie.genetics.machine.ComponentGeneticGUI;
import binnie.genetics.machine.ModuleMachine;
import binnie.genetics.machine.PackageGeneticBase;
import net.minecraft.tileentity.TileEntity;

public class SequencerPackage extends PackageGeneticBase implements IMachineInformation {
    public SequencerPackage() {
        super("sequencer", GeneticsTexture.Sequencer, 0xb7ff32, true);
        Sequencer.fxSeqA = Binnie.Resource.getBlockIcon(Genetics.instance, "fx/sequencer.a");
        Sequencer.fxSeqG = Binnie.Resource.getBlockIcon(Genetics.instance, "fx/sequencer.g");
        Sequencer.fxSeqT = Binnie.Resource.getBlockIcon(Genetics.instance, "fx/sequencer.t");
        Sequencer.fxSeqC = Binnie.Resource.getBlockIcon(Genetics.instance, "fx/sequencer.c");
    }

    @Override
    public void createMachine(Machine machine) {
        new ComponentGeneticGUI(machine, GeneticsGUI.Sequencer);
        ComponentInventorySlots inventory = new ComponentInventorySlots(machine);

        InventorySlot slotDye = inventory.addSlot(Sequencer.SLOT_DYE_INDEX, "dye");
        slotDye.setValidator(new SlotValidator.Item(GeneticsItems.FluorescentDye.get(1), ModuleMachine.IconDye));
        slotDye.forbidExtraction();

        inventory.addSlotArray(Sequencer.SLOT_RESERVE, "input");
        for (InventorySlot slot : inventory.getSlots(Sequencer.SLOT_RESERVE)) {
            slot.setValidator(new UnsequencedSlotValidator());
            slot.forbidExtraction();
        }

        InventorySlot slotTarget = inventory.addSlot(Sequencer.SLOT_TARGET_INDEX, "process");
        slotTarget.setValidator(new UnsequencedSlotValidator());
        slotTarget.setReadOnly();
        slotTarget.forbidInteraction();

        InventorySlot slotDone = inventory.addSlot(Sequencer.SLOT_DONE_INDEX, "output");
        slotDone.setReadOnly();

        ComponentInventoryTransfer transfer = new ComponentInventoryTransfer(machine);
        transfer.addRestock(Sequencer.SLOT_RESERVE, Sequencer.SLOT_TARGET_INDEX, 1);

        new ComponentChargedSlots(machine).addCharge(Sequencer.SLOT_DYE_INDEX);
        new ComponentPowerReceptor(machine, Sequencer.POWER_CAPACITY);
        new SequencerComponentFX(machine);

        SequencerComponentLogic logic = new SequencerComponentLogic(machine);
        transfer.setTransferListener(logic);
    }

    @Override
    public TileEntity createTileEntity() {
        return new TileEntityMachine(this);
    }
}
