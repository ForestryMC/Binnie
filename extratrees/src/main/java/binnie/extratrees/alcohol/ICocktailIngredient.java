package binnie.extratrees.alcohol;

public interface ICocktailIngredient {
	String getDisplayName();

	String getIdentifier();

	/**
	 * @return The color of the ingredient.
	 */
	int getColor();

	/**
	 * @return The transparency of the ingredient.
	 */
	int getTransparency();

	String getTooltip(int ratio);

	float getABV();
}
