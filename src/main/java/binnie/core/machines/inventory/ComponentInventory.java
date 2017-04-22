package binnie.core.machines.inventory;

import binnie.core.machines.*;
import net.minecraft.inventory.*;

public abstract class ComponentInventory extends MachineComponent implements IInventory {
	public ComponentInventory(IMachine machine) {
		super(machine);
	}

	@Override
	public void markDirty() {
		IMachine machine = getMachine();
		if (machine != null) {
			machine.markDirty();
		}
	}
}
