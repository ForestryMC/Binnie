package binnie.genetics.machine.analyser;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import binnie.core.genetics.ManagerGenetics;
import binnie.core.gui.minecraft.IMachineInformation;
import binnie.core.machines.Machine;
import binnie.core.machines.TileEntityTESRMachine;
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
import binnie.genetics.modules.ModuleMachine;

public class PackageAnalyser extends GeneticMachine.PackageGeneticBase implements IMachineInformation {
	public PackageAnalyser() {
		super("analyser", 9961727);
	}

	@Override
	public void createMachine(final Machine machine) {
		new ComponentGeneticGUI(machine, GeneticsGUI.ANALYSER);
		ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
		for (InventorySlot slot : inventory.addSlotArray(Analyser.SLOT_RESERVE, getSlotRL("input"))) {
			slot.setValidator(new SlotValidatorUnanalysed());
			slot.forbidExtraction();
		}
		InventorySlot slotTarget = inventory.addSlot(Analyser.SLOT_TARGET, getSlotRL("analyse"));
		slotTarget.setReadOnly();
		slotTarget.forbidInteraction();
		InventorySlot slotDye = inventory.addSlot(Analyser.SLOT_DYE, getSlotRL("dye"));
		slotDye.forbidExtraction();
		slotDye.setValidator(new DyeSlotValidator());
		for (InventorySlot slot : inventory.addSlotArray(Analyser.SLOT_FINISHED, getSlotRL("output"))) {
			slot.forbidInsertion();
			slot.setReadOnly();
		}
		ComponentInventoryTransfer transfer = new ComponentInventoryTransfer(machine);
		transfer.addRestock(Analyser.SLOT_RESERVE, 6, 1);
		transfer.addStorage(6, Analyser.SLOT_FINISHED, ManagerGenetics::isAnalysed);
		new ComponentChargedSlots(machine).addCharge(13);
		new ComponentPowerReceptor(machine, 500);
		new AnalyserLogic(machine);
		new AnalyserFX(machine);
	}

	@Override
	public TileEntity createTileEntity() {
		return new TileEntityTESRMachine(this);
	}

	private static class DyeSlotValidator extends SlotValidator {
		public DyeSlotValidator() {
			super(ModuleMachine.getSpriteDye());
		}

		@Override
		public boolean isValid(final ItemStack itemStack) {
			return itemStack.isItemEqual(GeneticsItems.DNADye.get(1));
		}

		@Override
		public String getTooltip() {
			return GeneticsItems.DNADye.get(1).getDisplayName();
		}
	}
}
