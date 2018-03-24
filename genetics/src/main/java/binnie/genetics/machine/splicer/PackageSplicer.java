package binnie.genetics.machine.splicer;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import binnie.core.gui.minecraft.IMachineInformation;
import binnie.core.machines.IMachine;
import binnie.core.machines.Machine;
import binnie.core.machines.MachineUtil;
import binnie.core.machines.TileEntityTESRMachine;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.core.machines.inventory.ComponentInventoryTransfer;
import binnie.core.machines.inventory.InventorySlot;
import binnie.core.machines.inventory.SlotValidator;
import binnie.core.machines.power.ComponentPowerReceptor;
import binnie.core.util.I18N;
import binnie.genetics.api.IItemSerum;
import binnie.genetics.core.GeneticsGUI;
import binnie.genetics.genetics.Engineering;
import binnie.genetics.machine.AdvGeneticMachine;
import binnie.genetics.machine.ComponentGeneticGUI;
import binnie.genetics.machine.ModuleMachine;

public class PackageSplicer extends AdvGeneticMachine.PackageAdvGeneticBase implements IMachineInformation {
	public PackageSplicer() {
		super("splicer", 14819893, true);
	}

	@Override
	public void createMachine(final Machine machine) {
		new ComponentGeneticGUI(machine, GeneticsGUI.SPLICER);
		final ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
		InventorySlot slotSerumVial = inventory.addSlot(Splicer.SLOT_SERUM_VIAL, getSlotRL("serum.active"));
		slotSerumVial.forbidInteraction();
		slotSerumVial.setReadOnly();
		final SlotValidator serumValid = new SerumSlotValidator();
		slotSerumVial.setValidator(serumValid);
		for (InventorySlot slot : inventory.addSlotArray(Splicer.SLOT_SERUM_RESERVE, getSlotRL("serum.input"))) {
			slot.setValidator(serumValid);
			slot.forbidExtraction();
		}
		for (InventorySlot slot : inventory.addSlotArray(Splicer.SLOT_SERUM_EXPENDED, getSlotRL("serum.output"))) {
			slot.setValidator(serumValid);
			slot.setReadOnly();
		}
		for (InventorySlot slot : inventory.addSlotArray(Splicer.SLOT_RESERVE, getSlotRL("input"))) {
			slot.forbidExtraction();
			slot.setValidator(new ValidatorIndividualInoculate());
		}
		InventorySlot slotTarget = inventory.addSlot(Splicer.SLOT_TARGET, getSlotRL("process"));
		slotTarget.setValidator(new ValidatorIndividualInoculate());
		slotTarget.setReadOnly();
		slotTarget.forbidInteraction();
		for (InventorySlot slot : inventory.addSlotArray(Splicer.SLOT_FINISHED, getSlotRL("output"))) {
			slot.setReadOnly();
			slot.forbidInsertion();
			slot.setValidator(new ValidatorIndividualInoculate());
		}
		final ComponentInventoryTransfer transfer = new ComponentInventoryTransfer(machine);
		transfer.addRestock(Splicer.SLOT_RESERVE, Splicer.SLOT_TARGET, 1);
		transfer.addRestock(Splicer.SLOT_SERUM_RESERVE, Splicer.SLOT_SERUM_VIAL);
		transfer.addStorage(Splicer.SLOT_SERUM_VIAL, Splicer.SLOT_SERUM_EXPENDED, (stack) -> Engineering.getCharges(stack) == 0);
		transfer.addStorage(Splicer.SLOT_TARGET, Splicer.SLOT_FINISHED, (stack) -> {
			if (!stack.isEmpty()) {
				IMachine machine1 = transfer.getMachine();
				MachineUtil machineUtil = machine1.getMachineUtil();
				if (!machineUtil.getStack(Splicer.SLOT_SERUM_VIAL).isEmpty() && machine1.getInterface(SplicerLogic.class).isValidSerum() != null) {
					return true;
				}
			}
			return false;
		});
		new ComponentPowerReceptor(machine, 20000);
		new SplicerLogic(machine);
		new SplicerFX(machine);
	}

	@Override
	public TileEntity createTileEntity() {
		return new TileEntityTESRMachine(this);
	}

	private static class SerumSlotValidator extends SlotValidator {
		public SerumSlotValidator() {
			super(ModuleMachine.getSpriteSerum());
		}

		@Override
		public boolean isValid(final ItemStack itemStack) {
			return itemStack.getItem() instanceof IItemSerum;
		}

		@Override
		public String getTooltip() {
			return I18N.localise("genetics.machine.adv_machine.splicer.tooltips.slots.serum");
		}
	}
}
