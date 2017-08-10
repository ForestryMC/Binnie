package binnie.genetics.integration.jei.incubator;

import binnie.genetics.integration.jei.RecipeUids;

public class LarvaeIncubatorRecipeCategory extends IncubatorRecipeCategory {
	@Override
	public String getUid() {
		return RecipeUids.INCUBATOR_LARVAE;
	}

	@Override
	public String getTitle() {
		return "Larvae Incubation";
	}
}
