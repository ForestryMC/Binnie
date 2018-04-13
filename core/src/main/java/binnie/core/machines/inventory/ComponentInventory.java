package binnie.core.machines.inventory;

import net.minecraft.inventory.IInventory;

import binnie.core.machines.IMachine;
import binnie.core.machines.MachineComponent;

public abstract class ComponentInventory extends MachineComponent implements IInventory {
	public ComponentInventory(final IMachine machine) {
		super(machine);
	}

	@Override
	public void markDirty() {
		IMachine machine = this.getMachine();
		machine.markDirty();
	}
}
