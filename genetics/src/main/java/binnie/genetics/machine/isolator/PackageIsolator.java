package binnie.genetics.machine.isolator;

import net.minecraft.tileentity.TileEntity;

import binnie.core.gui.minecraft.IMachineInformation;
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
import binnie.genetics.item.GeneticsItems;
import binnie.genetics.machine.ComponentGeneticGUI;
import binnie.genetics.machine.EthanolTankValidator;
import binnie.genetics.machine.GeneticMachine;
import binnie.genetics.modules.ModuleMachine;

public class PackageIsolator extends GeneticMachine.PackageGeneticBase implements IMachineInformation {
	public PackageIsolator() {
		super("isolator", 16740111);
	}

	@Override
	public void createMachine(Machine machine) {
		// GUI
		new ComponentGeneticGUI(machine, GeneticsGUI.ISOLATOR);
		// Inventory
		ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
		InventorySlot slotEnzyme = inventory.addSlot(Isolator.SLOT_ENZYME, getSlotRL("enzyme"));
		slotEnzyme.setValidator(new SlotValidator.Item(GeneticsItems.Enzyme.get(1), ModuleMachine.getSpriteEnzyme()));
		slotEnzyme.forbidExtraction();

		InventorySlot slotSequencer = inventory.addSlot(Isolator.SLOT_SEQUENCER_VIAL, getSlotRL("sequencervial"));
		slotSequencer.setValidator(new SlotValidator.Item(GeneticsItems.EmptySequencer.get(1), ModuleMachine.getSpriteSequencer()));
		slotSequencer.forbidExtraction();

		for (InventorySlot slot : inventory.addSlotArray(Isolator.SLOT_RESERVE, getSlotRL("input"))) {
			slot.setValidator(new SlotValidator.Individual());
			slot.forbidExtraction();
		}

		InventorySlot slotTarget = inventory.addSlot(Isolator.SLOT_TARGET, getSlotRL("process"));
		slotTarget.setValidator(new SlotValidator.Individual());
		slotTarget.setReadOnly();
		slotTarget.forbidInteraction();

		InventorySlot slotResult = inventory.addSlot(Isolator.SLOT_RESULUT, getSlotRL("output"));
		slotResult.setReadOnly();
		slotResult.forbidInteraction();

		for (InventorySlot slot : inventory.addSlotArray(Isolator.SLOT_FINISHED, getSlotRL("output"))) {
			slot.setReadOnly();
			slot.forbidInsertion();
		}
		// Tanks
		ComponentTankContainer tanks = new ComponentTankContainer(machine);
		tanks.addTank(Isolator.TANK_ETHANOL, "input", 1000).setValidator(new EthanolTankValidator());
		//Charged Slots
		ComponentChargedSlots chargedSlots = new ComponentChargedSlots(machine);
		chargedSlots.addCharge(Isolator.SLOT_ENZYME);
		// Transfer
		ComponentInventoryTransfer transfer = new ComponentInventoryTransfer(machine);
		transfer.addRestock(Isolator.SLOT_RESERVE, Isolator.SLOT_TARGET, 1);
		transfer.addStorage(Isolator.SLOT_RESULUT, Isolator.SLOT_FINISHED);
		// Logic
		new ComponentPowerReceptor(machine, 20000);
		new IsolatorLogic(machine);
		new IsolatorFX(machine);
	}

	@Override
	public TileEntity createTileEntity() {
		return new TileEntityMachine(this);
	}

}
