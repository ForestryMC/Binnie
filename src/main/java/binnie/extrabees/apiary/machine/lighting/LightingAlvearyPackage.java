package binnie.extrabees.apiary.machine.lighting;

import binnie.core.craftgui.minecraft.IMachineInformation;
import binnie.core.machines.Machine;
import binnie.extrabees.apiary.machine.AlvearyMachine;
import binnie.extrabees.core.ExtraBeeTexture;

public class LightingAlvearyPackage extends AlvearyMachine.AlvearyPackage implements IMachineInformation {
    public LightingAlvearyPackage() {
        super("lighting", ExtraBeeTexture.AlvearyLighting.getTexture(), false);
    }

    @Override
    public void createMachine(Machine machine) {
        new LightingComponentModifier(machine);
    }
}
