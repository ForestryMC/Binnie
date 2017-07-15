package binnie.botany.api;

public interface IColorMix {
	IFlowerColor getColorFirst();

	IFlowerColor getColorSecond();

	boolean isMutation(IFlowerColor colorFirst, IFlowerColor colorSecond);

	int getChance();

	IFlowerColor getResult();
}
