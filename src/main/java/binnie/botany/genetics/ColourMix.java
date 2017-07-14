package binnie.botany.genetics;

import binnie.botany.api.IColorMix;
import binnie.botany.api.IFlowerColour;

public class ColourMix implements IColorMix {
	IFlowerColour colour1;
	IFlowerColour colour2;
	IFlowerColour result;
	int chance;

	public ColourMix(final IFlowerColour colour1, final IFlowerColour colour2, final IFlowerColour result, final int chance) {
		this.colour1 = colour1;
		this.colour2 = colour2;
		this.result = result;
		this.chance = chance;
	}

	@Override
	public IFlowerColour getColorFirst() {
		return this.colour1;
	}

	@Override
	public IFlowerColour getColorSecond() {
		return this.colour2;
	}

	@Override
	public boolean isMutation(final IFlowerColour colorFirst, final IFlowerColour colorSecond) {
		return (colorFirst == this.colour1 && colorSecond == this.colour2) || (colorFirst == this.colour2 && colorSecond == this.colour1);
	}

	@Override
	public int getChance() {
		return this.chance;
	}

	@Override
	public IFlowerColour getResult() {
		return this.result;
	}
}
