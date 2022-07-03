package binnie.core.machines.inventory;

import binnie.Binnie;
import binnie.core.AbstractMod;
import binnie.core.resource.BinnieIcon;
import java.util.ArrayList;
import java.util.List;

public class ValidatorIcon {
    private List<BinnieIcon> iconsInput;
    private List<BinnieIcon> iconsOutput;

    public ValidatorIcon(AbstractMod mod, String pathInput, String pathOutput) {
        iconsInput = new ArrayList<>();
        iconsOutput = new ArrayList<>();
        iconsInput.add(Binnie.Resource.getItemIcon(mod, pathInput));
        iconsOutput.add(Binnie.Resource.getItemIcon(mod, pathOutput));
    }

    public BinnieIcon getIcon(boolean input) {
        return input ? iconsInput.get(0) : iconsOutput.get(0);
    }
}
