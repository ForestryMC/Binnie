package binnie.genetics.machine.sequencer;

import net.minecraft.tileentity.TileEntity;

import binnie.core.gui.minecraft.IMachineInformation;
import binnie.core.machines.Machine;
import binnie.core.machines.TileEntityMachine;
import binnie.core.machines.inventory.ComponentChargedSlots;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.core.machines.inventory.ComponentInventoryTransfer;
import binnie.core.machines.inventory.InventorySlot;
import binnie.core.machines.inventory.SlotValidator;
import binnie.core.machines.power.ComponentPowerReceptor;
import binnie.genetics.core.GeneticsGUI;
import binnie.genetics.item.GeneticsItems;
import binnie.genetics.machine.ComponentGeneticGUI;
import binnie.genetics.machine.GeneticMachine;
import binnie.genetics.machine.ModuleMachine;

public class PackageSequencer extends GeneticMachine.PackageGeneticBase implements IMachineInformation {
	public PackageSequencer() {
		super("sequencer", 12058418, true);
	}

	@Override
	public void createMachine(final Machine machine) {
		new ComponentGeneticGUI(machine, GeneticsGUI.SEQUENCER);
		ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
		InventorySlot slotDye = inventory.addSlot(Sequencer.SLOT_DYE, "dye");
		slotDye.setValidator(new SlotValidator.Item(GeneticsItems.FluorescentDye.get(1), ModuleMachine.spriteDye));
		slotDye.forbidExtraction();
		for (InventorySlot slot : inventory.addSlotArray(Sequencer.SLOT_RESERVE, "input")) {
			slot.setValidator(new SlotValidatorUnsequenced());
			slot.forbidExtraction();
		}
		InventorySlot slotTarget = inventory.addSlot(Sequencer.SLOT_TARGET, "process");
		slotTarget.setValidator(new SlotValidatorUnsequenced());
		slotTarget.setReadOnly();
		slotTarget.forbidInteraction();
		InventorySlot slotDone = inventory.addSlot(Sequencer.SLOT_DONE, "output");
		slotDone.setReadOnly();
		ComponentInventoryTransfer transfer = new ComponentInventoryTransfer(machine);
		transfer.addRestock(Sequencer.SLOT_RESERVE, Sequencer.SLOT_TARGET, 1);
		new ComponentChargedSlots(machine).addCharge(0);
		new ComponentPowerReceptor(machine, 10000);
		new SequencerLogic(machine);
		new SequencerFX(machine);
	}

	@Override
	public TileEntity createTileEntity() {
		return new TileEntityMachine(this);
	}

	@Override
	public void register() {
	}
}
