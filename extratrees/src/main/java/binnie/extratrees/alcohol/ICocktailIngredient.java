package binnie.extratrees.alcohol;

public interface ICocktailIngredient {
	String getDisplayName();

	String getIdentifier();

	int getColor();

	int getTransparency();

	String getTooltip(final int p0);

	float getABV();
}
