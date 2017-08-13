package binnie.extratrees.machines.fruitpress;

import net.minecraft.tileentity.TileEntity;

import binnie.core.gui.minecraft.IMachineInformation;
import binnie.core.machines.Machine;
import binnie.core.machines.TileEntityMachine;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.core.machines.inventory.ComponentInventoryTransfer;
import binnie.core.machines.inventory.ComponentTankContainer;
import binnie.core.machines.inventory.InventorySlot;
import binnie.core.machines.power.ComponentPowerReceptor;
import binnie.extratrees.gui.ExtraTreesGUID;
import binnie.extratrees.machines.ExtraTreeMachine;
import binnie.extratrees.machines.fruitpress.window.SlotValidatorSqueezable;

public class FruitPressMachine extends ExtraTreeMachine.PackageExtraTreeMachine implements IMachineInformation {
	public static final int SLOT_FRUIT = 0;
	public static final int SLOT_CURRENT = 1;
	public static final int TANK_OUTPUT = 0;
	public static final int TANK_OUTPUT_CAPACITY = 5000;

	public FruitPressMachine() {
		super("press", true);
	}

	@Override
	public void createMachine(final Machine machine) {
		new ExtraTreeMachine.ComponentExtraTreeGUI(machine, ExtraTreesGUID.PRESS);
		final ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
		final InventorySlot input = inventory.addSlot(SLOT_FRUIT, "input");
		input.setValidator(new SlotValidatorSqueezable());
		input.forbidExtraction();

		final InventorySlot process = inventory.addSlot(SLOT_CURRENT, "process");
		process.setValidator(new SlotValidatorSqueezable());
		process.forbidInteraction();

		final ComponentTankContainer tanks = new ComponentTankContainer(machine);
		tanks.addTank(TANK_OUTPUT, "output", TANK_OUTPUT_CAPACITY);
		new ComponentPowerReceptor(machine);
		new ComponentInventoryTransfer(machine).addRestock(new int[]{SLOT_FRUIT}, SLOT_CURRENT, 1);
		new FruitPressLogic(machine);
	}

	@Override
	public TileEntity createTileEntity() {
		return new TileEntityMachine(this);
	}

	@Override
	public void register() {
	}
}
