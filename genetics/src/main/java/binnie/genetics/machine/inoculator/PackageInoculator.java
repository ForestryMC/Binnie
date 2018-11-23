package binnie.genetics.machine.inoculator;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import net.minecraftforge.fluids.FluidStack;

import binnie.core.gui.minecraft.IMachineInformation;
import binnie.core.machines.IMachine;
import binnie.core.machines.Machine;
import binnie.core.machines.MachineUtil;
import binnie.core.machines.TileEntityTESRMachine;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.core.machines.inventory.ComponentInventoryTransfer;
import binnie.core.machines.inventory.ComponentTankContainer;
import binnie.core.machines.inventory.InventorySlot;
import binnie.core.machines.inventory.SlotValidator;
import binnie.core.machines.inventory.TankValidator;
import binnie.core.machines.power.ComponentPowerReceptor;
import binnie.genetics.api.IItemSerum;
import binnie.genetics.core.GeneticsGUI;
import binnie.genetics.genetics.Engineering;
import binnie.genetics.item.GeneticLiquid;
import binnie.genetics.machine.ComponentGeneticGUI;
import binnie.genetics.machine.GeneticMachine;
import binnie.genetics.modules.ModuleMachine;

public class PackageInoculator extends GeneticMachine.PackageGeneticBase implements IMachineInformation {
	public PackageInoculator() {
		super("inoculator", 14819893);
	}

	@Override
	public void createMachine(Machine machine) {
		new ComponentGeneticGUI(machine, GeneticsGUI.INOCULATOR);
		ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
		InventorySlot slotSerumVial = inventory.addSlot(Inoculator.SLOT_SERUM_VIAL, getSlotRL("serum.active"));
		slotSerumVial.forbidInteraction();
		slotSerumVial.setReadOnly();
		SlotValidator serumValid = new SerumSlotValidator();
		slotSerumVial.setValidator(serumValid);
		for (InventorySlot slot : inventory.addSlotArray(Inoculator.SLOT_SERUM_RESERVE, getSlotRL("serum.input"))) {
			slot.setValidator(serumValid);
			slot.forbidExtraction();
		}
		for (InventorySlot slot : inventory.addSlotArray(Inoculator.SLOT_SERUM_EXPENDED, getSlotRL("serum.output"))) {
			slot.setValidator(serumValid);
			slot.setReadOnly();
		}
		for (InventorySlot slot : inventory.addSlotArray(Inoculator.SLOT_RESERVE, getSlotRL("input"))) {
			slot.forbidExtraction();
			slot.setValidator(new ValidatorIndividualInoculate());
		}
		InventorySlot slotTarget = inventory.addSlot(Inoculator.SLOT_TARGET, getSlotRL("process"));
		slotTarget.setValidator(new ValidatorIndividualInoculate());
		slotTarget.setReadOnly();
		slotTarget.forbidInteraction();
		for (InventorySlot slot : inventory.addSlotArray(Inoculator.SLOT_FINISHED, getSlotRL("output"))) {
			slot.setReadOnly();
			slot.forbidInsertion();
			slot.setValidator(new ValidatorIndividualInoculate());
		}
		ComponentInventoryTransfer transfer = new ComponentInventoryTransfer(machine);
		transfer.addRestock(Inoculator.SLOT_RESERVE, 9, 1);
		transfer.addRestock(Inoculator.SLOT_SERUM_RESERVE, 0);
		transfer.addStorage(Inoculator.SLOT_SERUM_VIAL, Inoculator.SLOT_SERUM_EXPENDED, (stack) -> Engineering.getCharges(stack) == 0);
		transfer.addStorage(Inoculator.SLOT_TARGET, Inoculator.SLOT_FINISHED, (stack) -> {
			if (!stack.isEmpty()) {
				IMachine machine1 = transfer.getMachine();
				MachineUtil machineUtil = machine1.getMachineUtil();
				return !machineUtil.getStack(Inoculator.SLOT_SERUM_VIAL).isEmpty() && machine1.getInterface(InoculatorLogic.class).isValidSerum() != null;
			}
			return false;
		});
		new ComponentPowerReceptor(machine, 15000);
		new InoculatorLogic(machine);
		new InoculatorFX(machine);
		new ComponentTankContainer(machine).addTank(Inoculator.TANK_VEKTOR, "input", 1000).setValidator(new BacteriaVectorTankValidator());
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
		public boolean isValid(ItemStack itemStack) {
			return itemStack.getItem() instanceof IItemSerum;
		}

		@Override
		public String getTooltip() {
			return "Serum Vials & Arrays";
		}
	}

	private static class BacteriaVectorTankValidator extends TankValidator {
		@Override
		public boolean isValid(FluidStack object) {
			return GeneticLiquid.BacteriaVector.get(1).isFluidEqual(object);
		}

		@Override
		public String getTooltip() {
			return GeneticLiquid.BacteriaVector.toString();
		}
	}
}
