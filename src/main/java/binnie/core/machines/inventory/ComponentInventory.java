package binnie.core.machines.inventory;

import binnie.core.machines.IMachine;
import binnie.core.machines.MachineComponent;
import net.minecraft.inventory.IInventory;

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
