package binnie.extratrees.alcohol;

public interface ICocktailIngredient {
	String getName();

	String getIdentifier();

	int getColor();

	int getTransparency();

	String getTooltip(int ratio);

	float getABV();
}
