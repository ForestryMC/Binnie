package binnie.core.machines.inventory;

import java.util.ArrayList;
import java.util.List;

import binnie.core.Binnie;
import binnie.core.AbstractMod;
import binnie.core.resource.BinnieSprite;

public class ValidatorSprite {

	private List<BinnieSprite> spritesInput;
	private List<BinnieSprite> spritesOutput;

	public ValidatorSprite(final AbstractMod mod, final String pathInput, final String pathOutput) {
		this(mod.getModId(), pathInput, pathOutput);
	}

	public ValidatorSprite(final String modId, final String pathInput, final String pathOutput) {
		this.spritesInput = new ArrayList<>();
		this.spritesOutput = new ArrayList<>();
		this.spritesInput.add(Binnie.RESOURCE.getItemSprite(modId, pathInput));
		this.spritesOutput.add(Binnie.RESOURCE.getItemSprite(modId, pathOutput));
	}

	public BinnieSprite getSprite(final boolean input) {
		return input ? this.spritesInput.get(0) : this.spritesOutput.get(0);
	}
}
