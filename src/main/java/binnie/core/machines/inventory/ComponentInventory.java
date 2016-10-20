// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.machines.inventory;

import binnie.core.machines.IMachine;
import net.minecraft.inventory.IInventory;
import binnie.core.machines.MachineComponent;

public abstract class ComponentInventory extends MachineComponent implements IInventory
{
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
