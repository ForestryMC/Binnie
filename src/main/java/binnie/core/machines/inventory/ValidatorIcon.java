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
		this.iconsInput = new ArrayList<>();
		this.iconsOutput = new ArrayList<>();
		this.iconsInput.add(Binnie.RESOURCE.getItemIcon(mod, pathInput));
		this.iconsOutput.add(Binnie.RESOURCE.getItemIcon(mod, pathOutput));
	}

	public BinnieIcon getIcon(final boolean input) {
		return input ? this.iconsInput.get(0) : this.iconsOutput.get(0);
	}
}
