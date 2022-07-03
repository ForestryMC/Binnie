package binnie.extrabees.apiary.machine.lighting;

import binnie.core.machines.Machine;
import binnie.extrabees.apiary.ComponentBeeModifier;
import forestry.api.apiculture.IBeeListener;
import forestry.api.apiculture.IBeeModifier;

public class LightingComponentModifier extends ComponentBeeModifier implements IBeeModifier, IBeeListener {
    public LightingComponentModifier(Machine machine) {
        super(machine);
    }

    @Override
    public boolean isSelfLighted() {
        return true;
    }
}
