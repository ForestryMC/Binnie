package binnie.extrabees.machines.lighting;

import binnie.core.machines.Machine;
import binnie.extrabees.utils.ComponentBeeModifier;
import forestry.api.apiculture.IBeeListener;
import forestry.api.apiculture.IBeeModifier;

public class ComponentLighting extends ComponentBeeModifier implements IBeeModifier, IBeeListener {
	public ComponentLighting(final Machine machine) {
		super(machine);
	}

	@Override
	public boolean isSelfLighted() {
		return true;
	}
}
