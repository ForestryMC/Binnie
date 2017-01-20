package binnie.genetics.integration.jei.inoculator;

import binnie.genetics.integration.jei.RecipeUids;

public class SplicerRecipeCategory extends InoculatorRecipeCategory {
	public SplicerRecipeCategory() {
		super(true);
	}

	@Override
	public String getUid() {
		return RecipeUids.SPLICER;
	}

	@Override
	public String getTitle() {
		return "Splicing";
	}
}
