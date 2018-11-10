package binnie.extrabees.machines.lighting;

import forestry.api.apiculture.IBeeListener;
import forestry.api.apiculture.IBeeModifier;

import binnie.core.machines.Machine;
import binnie.extrabees.utils.ComponentBeeModifier;

public class ComponentLighting extends ComponentBeeModifier implements IBeeModifier, IBeeListener {
	public ComponentLighting(final Machine machine) {
		super(machine);
	}

	@Override
	public boolean isSelfLighted() {
		return true;
	}
}
