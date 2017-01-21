package binnie.extratrees.machines.fruitpress;

import binnie.core.machines.Machine;
import binnie.core.machines.TileEntityMachine;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.core.machines.inventory.ComponentInventoryTransfer;
import binnie.core.machines.inventory.ComponentTankContainer;
import binnie.core.machines.power.ComponentPowerReceptor;
import binnie.craftgui.minecraft.IMachineInformation;
import binnie.extratrees.core.ExtraTreeTexture;
import binnie.extratrees.core.ExtraTreesGUID;
import binnie.extratrees.machines.ExtraTreeMachine;
import net.minecraft.tileentity.TileEntity;

public class FruitPressMachine extends ExtraTreeMachine.PackageExtraTreeMachine implements IMachineInformation {
	public static final int SLOT_FRUIT = 0;
	public static final int SLOT_CURRENT = 1;
	public static final int TANK_OUTPUT = 0;
	public static final int TANK_OUTPUT_CAPACITY = 5000;

	public FruitPressMachine() {
		super("press", ExtraTreeTexture.pressTexture, true);
	}

	@Override
	public void createMachine(final Machine machine) {
		new ExtraTreeMachine.ComponentExtraTreeGUI(machine, ExtraTreesGUID.Press);
		final ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
		inventory.addSlot(SLOT_FRUIT, "input");
		inventory.getSlot(SLOT_FRUIT).setValidator(new SlotValidatorSqueezable());
		inventory.getSlot(SLOT_FRUIT).forbidExtraction();
		inventory.addSlot(SLOT_CURRENT, "process");
		inventory.getSlot(SLOT_CURRENT).setValidator(new SlotValidatorSqueezable());
		inventory.getSlot(SLOT_CURRENT).forbidInteraction();
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
