package binnie.botany.genetics;

import binnie.botany.api.IColorMix;
import binnie.botany.api.IFlowerColor;

public class ColorMix implements IColorMix {
	IFlowerColor color1;
	IFlowerColor color2;
	IFlowerColor result;
	int chance;

	public ColorMix(IFlowerColor color1, IFlowerColor color2, IFlowerColor result, int chance) {
		this.color1 = color1;
		this.color2 = color2;
		this.result = result;
		this.chance = chance;
	}

	@Override
	public IFlowerColor getColorFirst() {
		return color1;
	}

	@Override
	public IFlowerColor getColorSecond() {
		return color2;
	}

	@Override
	public boolean isMutation(IFlowerColor colorFirst, IFlowerColor colorSecond) {
		return (colorFirst == color1 && colorSecond == color2)
				|| (colorFirst == color2 && colorSecond == color1);
	}

	@Override
	public int getChance() {
		return chance;
	}

	@Override
	public IFlowerColor getResult() {
		return result;
	}
}
