package binnie.genetics.integration.jei;

import binnie.core.integration.jei.Drawables;
import binnie.genetics.integration.jei.incubator.IncubatorRecipeCategory;
import binnie.genetics.integration.jei.incubator.IncubatorRecipeHandler;
import binnie.genetics.integration.jei.incubator.LarvaeIncubatorRecipeCategory;
import binnie.genetics.integration.jei.incubator.LarvaeIncubatorRecipeHandler;
import binnie.genetics.integration.jei.incubator.LarvaeIncubatorRecipeMaker;
import binnie.genetics.integration.jei.isolator.IsolatorRecipeCategory;
import binnie.genetics.integration.jei.isolator.IsolatorRecipeHandler;
import binnie.genetics.integration.jei.isolator.IsolatorRecipeMaker;
import binnie.genetics.machine.GeneticMachine;
import binnie.genetics.machine.LaboratoryMachine;
import binnie.genetics.machine.incubator.Incubator;
import mezz.jei.api.BlankModPlugin;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;

@JEIPlugin
public class GeneticsJeiPlugin extends BlankModPlugin {
	public static IJeiHelpers jeiHelpers;
	public static IGuiHelper guiHelper;
	public static Drawables drawables;

	@Override
	public void register(IModRegistry registry) {
		GeneticsJeiPlugin.jeiHelpers = registry.getJeiHelpers();
		GeneticsJeiPlugin.guiHelper = jeiHelpers.getGuiHelper();
		GeneticsJeiPlugin.drawables = Drawables.getDrawables(guiHelper);

		registry.addRecipeCategories(
				new IncubatorRecipeCategory(),
				new LarvaeIncubatorRecipeCategory(),
				new IsolatorRecipeCategory()
		);

		registry.addRecipeHandlers(
				new IncubatorRecipeHandler(),
				new LarvaeIncubatorRecipeHandler(),
				new IsolatorRecipeHandler()
		);

		registry.addRecipeCategoryCraftingItem(LaboratoryMachine.Incubator.get(1), RecipeUids.INCUBATOR, RecipeUids.INCUBATOR_LARVAE);
		registry.addRecipeCategoryCraftingItem(GeneticMachine.Isolator.get(1), RecipeUids.ISOLATOR);

		registry.addRecipes(Incubator.getRecipes());
		registry.addRecipes(LarvaeIncubatorRecipeMaker.create(Incubator.getLarvaeRecipe()));
		registry.addRecipes(IsolatorRecipeMaker.create());
	}
}
