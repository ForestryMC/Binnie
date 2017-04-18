package binnie.core.machines.component;

import binnie.core.machines.Machine;
import binnie.core.machines.MachineComponent;

public abstract class ComponentRecipe extends MachineComponent implements IComponentRecipe {
	public ComponentRecipe(final Machine machine) {
		super(machine);
	}
}
