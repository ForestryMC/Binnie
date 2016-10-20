package binnie.core.machines.inventory;

import binnie.Binnie;
import binnie.core.AbstractMod;
import binnie.core.resource.BinnieIcon;

import java.util.ArrayList;
import java.util.List;

public class ValidatorIcon {
    private List<BinnieIcon> iconsInput;
    private List<BinnieIcon> iconsOutput;

    public ValidatorIcon(final AbstractMod mod, final String pathInput, final String pathOutput) {
        this.iconsInput = new ArrayList<BinnieIcon>();
        this.iconsOutput = new ArrayList<BinnieIcon>();
        this.iconsInput.add(Binnie.Resource.getItemIcon(mod, pathInput));
        this.iconsOutput.add(Binnie.Resource.getItemIcon(mod, pathOutput));
    }

    public BinnieIcon getIcon(final boolean input) {
        return input ? this.iconsInput.get(0) : this.iconsOutput.get(0);
    }
}
