package binnie.botany.api;

public interface IColorMix {
	IFlowerColour getColorFirst();

	IFlowerColour getColorSecond();

	boolean isMutation(IFlowerColour colorFirst, IFlowerColour colorSecond);

	int getChance();

	IFlowerColour getResult();
}
