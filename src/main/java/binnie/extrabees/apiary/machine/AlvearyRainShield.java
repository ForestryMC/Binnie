package binnie.extrabees.apiary.machine;

import binnie.core.machines.Machine;
import binnie.craftgui.minecraft.IMachineInformation;
import binnie.extrabees.apiary.ComponentBeeModifier;
import binnie.extrabees.core.ExtraBeeTexture;
import forestry.api.apiculture.IBeeListener;
import forestry.api.apiculture.IBeeModifier;

public class AlvearyRainShield {
    public static class PackageAlvearyRainShield extends AlvearyMachine.AlvearyPackage implements IMachineInformation {
        public PackageAlvearyRainShield() {
            super("rainShield", ExtraBeeTexture.AlvearyRainShield.getTexture(), false);
        }

        @Override
        public void createMachine(final Machine machine) {
            new ComponentRainShield(machine);
        }
    }

    public static class ComponentRainShield extends ComponentBeeModifier implements IBeeModifier, IBeeListener {
        public ComponentRainShield(final Machine machine) {
            super(machine);
        }

        @Override
        public boolean isSealed() {
            return true;
        }
    }
}
