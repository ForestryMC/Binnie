package binnie.extrabees.apiary.machine.rainShield;

import binnie.core.machines.Machine;
import binnie.extrabees.apiary.ComponentBeeModifier;
import forestry.api.apiculture.IBeeListener;
import forestry.api.apiculture.IBeeModifier;

public class RainShieldModifierComponent extends ComponentBeeModifier implements IBeeModifier, IBeeListener {
    public RainShieldModifierComponent(Machine machine) {
        super(machine);
    }

    @Override
    public boolean isSealed() {
        return true;
    }
}
