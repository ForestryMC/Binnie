// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extratrees.alcohol;

public interface ICocktailIngredient
{
	String getName();

	String getIdentifier();

	int getColor();

	int getTransparency();

	String getTooltip(final int p0);

	float getABV();
}
