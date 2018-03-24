package binnie.genetics.machine.acclimatiser;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;

import binnie.core.gui.minecraft.IMachineInformation;
import binnie.core.machines.Machine;
import binnie.core.machines.TileEntityMachine;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.core.machines.inventory.ComponentInventoryTransfer;
import binnie.core.machines.inventory.InventorySlot;
import binnie.core.machines.inventory.SlotValidator;
import binnie.core.machines.power.ComponentPowerReceptor;
import binnie.genetics.core.GeneticsGUI;
import binnie.genetics.machine.ComponentGeneticGUI;
import binnie.genetics.machine.GeneticMachine;

public class PackageAcclimatiser extends GeneticMachine.PackageGeneticBase implements IMachineInformation {
	public PackageAcclimatiser() {
		super("acclimatiser", 9857609);
	}

	@Override
	public void createMachine(final Machine machine) {
		new ComponentGeneticGUI(machine, GeneticsGUI.ACCLIMATISER);
		final ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
		InventorySlot slotTarget = inventory.addSlot(Acclimatiser.SLOT_TARGET, getSlotRL("process"));
		slotTarget.setValidator(new SlotValidator.Individual());
		slotTarget.setReadOnly();
		slotTarget.forbidExtraction();
		for (final InventorySlot slot : inventory.addSlotArray(Acclimatiser.SLOT_RESERVE, getSlotRL("input"))) {
			slot.forbidExtraction();
			slot.setValidator(new SlotValidator.Individual());
		}
		for (final InventorySlot slot : inventory.addSlotArray(Acclimatiser.SLOT_DRONE, getSlotRL("output"))) {
			slot.setReadOnly();
			slot.setValidator(new SlotValidator.Individual());
		}
		for (final InventorySlot slot : inventory.addSlotArray(Acclimatiser.SLOT_ACCLIMATISER, getSlotRL("acclimatiser"))) {
			slot.setValidator(new ValidatorAcclimatiserItem());
		}
		final ComponentInventoryTransfer transfer = new ComponentInventoryTransfer(machine);
		transfer.addRestock(Acclimatiser.SLOT_RESERVE, Acclimatiser.SLOT_TARGET, 1);
		transfer.addStorage(Acclimatiser.SLOT_TARGET, Acclimatiser.SLOT_DRONE, (stack) -> {
			NonNullList<ItemStack> stacks = machine.getMachineUtil().getNonEmptyStacks(Acclimatiser.SLOT_ACCLIMATISER);
			return !Acclimatiser.canAcclimatise(stack, stacks);
		});
		new ComponentPowerReceptor(machine, 5000);
		new AcclimatiserLogic(machine);
	}

	@Override
	public TileEntity createTileEntity() {
		return new TileEntityMachine(this);
	}

}
