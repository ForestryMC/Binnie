package binnie.core.machines.component;

import binnie.core.machines.*;

public abstract class ComponentRecipe extends MachineComponent implements IComponentRecipe {
	public ComponentRecipe(Machine machine) {
		super(machine);
	}
}
