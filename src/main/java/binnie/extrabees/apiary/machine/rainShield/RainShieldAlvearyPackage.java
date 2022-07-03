package binnie.extrabees.apiary.machine.rainShield;

import binnie.core.craftgui.minecraft.IMachineInformation;
import binnie.core.machines.Machine;
import binnie.extrabees.apiary.machine.AlvearyMachine;
import binnie.extrabees.core.ExtraBeeTexture;

public class RainShieldAlvearyPackage extends AlvearyMachine.AlvearyPackage implements IMachineInformation {
    public RainShieldAlvearyPackage() {
        super("rainShield", ExtraBeeTexture.AlvearyRainShield.getTexture(), false);
    }

    @Override
    public void createMachine(Machine machine) {
        new RainShieldModifierComponent(machine);
    }
}
