package binnie.extrabees.machines.rainshield;

import binnie.core.machines.Machine;
import binnie.extrabees.utils.ComponentBeeModifier;
import forestry.api.apiculture.IBeeListener;
import forestry.api.apiculture.IBeeModifier;

public class ComponentRainShield extends ComponentBeeModifier implements IBeeModifier, IBeeListener {
	public ComponentRainShield(final Machine machine) {
		super(machine);
	}

	@Override
	public boolean isSealed() {
		return true;
	}
}
