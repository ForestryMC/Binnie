package binnie.core.machines.inventory;

import binnie.core.machines.IMachine;
import binnie.core.machines.MachineComponent;
import net.minecraft.inventory.IInventory;

public abstract class ComponentInventory extends MachineComponent implements IInventory {
	public ComponentInventory(final IMachine machine) {
		super(machine);
	}

	@Override
	public void markDirty() {
		if (this.getMachine() != null) {
			this.getMachine().markDirty();
		}
	}
}
