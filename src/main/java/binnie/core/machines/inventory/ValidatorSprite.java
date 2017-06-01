package binnie.core.machines.inventory;

import binnie.Binnie;
import binnie.core.AbstractMod;
import binnie.core.resource.BinnieSprite;

import java.util.ArrayList;
import java.util.List;

public class ValidatorSprite {

	private List<BinnieSprite> spritesInput;
	private List<BinnieSprite> spritesOutput;

	public ValidatorSprite(final AbstractMod mod, final String pathInput, final String pathOutput) {
		this.spritesInput = new ArrayList<>();
		this.spritesOutput = new ArrayList<>();
		this.spritesInput.add(Binnie.RESOURCE.getItemSprite(mod, pathInput));
		this.spritesOutput.add(Binnie.RESOURCE.getItemSprite(mod, pathOutput));
	}

	public BinnieSprite getSprite(final boolean input) {
		return input ? this.spritesInput.get(0) : this.spritesOutput.get(0);
	}
}
