package binnie.genetics.integration.jei;

import binnie.core.integration.jei.Drawables;
import binnie.genetics.integration.jei.incubator.IncubatorRecipeCategory;
import binnie.genetics.integration.jei.incubator.LarvaeIncubatorRecipeCategory;
import binnie.genetics.integration.jei.incubator.IncubatorRecipeHandler;
import binnie.genetics.integration.jei.incubator.LarvaeIncubatorRecipeHandler;
import binnie.genetics.integration.jei.incubator.LarvaeIncubatorRecipeMaker;
import binnie.genetics.machine.Incubator;
import binnie.genetics.machine.LaboratoryMachine;
import mezz.jei.api.*;

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
                new LarvaeIncubatorRecipeCategory()
        );

        registry.addRecipeHandlers(
                new IncubatorRecipeHandler(),
                new LarvaeIncubatorRecipeHandler()
        );

        registry.addRecipeCategoryCraftingItem(LaboratoryMachine.Incubator.get(1), RecipeUids.INCUBATOR, RecipeUids.INCUBATOR_LARVAE);

        registry.addRecipes(Incubator.getRecipes());
        registry.addRecipes(LarvaeIncubatorRecipeMaker.create(Incubator.getLarvaeRecipe()));
    }
}
