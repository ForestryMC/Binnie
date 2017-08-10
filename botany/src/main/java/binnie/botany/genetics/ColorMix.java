package binnie.botany.genetics;

import binnie.botany.api.genetics.IColorMix;
import binnie.botany.api.genetics.IFlowerColor;

public class ColorMix implements IColorMix {
	IFlowerColor colorFirst;
	IFlowerColor colorSecond;
	IFlowerColor result;
	int chance;

	public ColorMix(IFlowerColor colorFirst, IFlowerColor colorSecond, IFlowerColor result, int chance) {
		this.colorFirst = colorFirst;
		this.colorSecond = colorSecond;
		this.result = result;
		this.chance = chance;
	}

	@Override
	public IFlowerColor getColorFirst() {
		return colorFirst;
	}

	@Override
	public IFlowerColor getColorSecond() {
		return colorSecond;
	}

	@Override
	public boolean isMutation(IFlowerColor colorFirst, IFlowerColor colorSecond) {
		return (colorFirst == this.colorFirst && colorSecond == this.colorSecond) || (colorFirst == this.colorSecond && colorSecond == this.colorFirst);
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
