package binnie.botany.api.genetics;

public final class ColorMix implements IColorMix {
	private final IFlowerColor colorFirst;
	private final IFlowerColor colorSecond;
	private final IFlowerColor result;
	private final int chance;

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
