package binnie.genetics.integration.jei;

import java.util.Arrays;
import java.util.List;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import forestry.api.genetics.ISpeciesRoot;

import binnie.core.integration.jei.Drawables;
import binnie.genetics.Genetics;
import binnie.genetics.machine.incubator.IIncubatorRecipe;
import binnie.genetics.api.IItemChargeable;
import binnie.genetics.integration.jei.database.DatabaseRecipeCategory;
import binnie.genetics.integration.jei.database.DatabaseRecipeMaker;
import binnie.genetics.integration.jei.genepool.GenepoolRecipeCategory;
import binnie.genetics.integration.jei.genepool.GenepoolRecipeMaker;
import binnie.genetics.integration.jei.incubator.IncubatorRecipeCategory;
import binnie.genetics.integration.jei.incubator.IncubatorRecipeWrapper;
import binnie.genetics.integration.jei.incubator.LarvaeIncubatorRecipeCategory;
import binnie.genetics.integration.jei.incubator.LarvaeIncubatorRecipeMaker;
import binnie.genetics.integration.jei.inoculator.InoculatorRecipeCategory;
import binnie.genetics.integration.jei.inoculator.InoculatorRecipeMaker;
import binnie.genetics.integration.jei.inoculator.SplicerRecipeCategory;
import binnie.genetics.integration.jei.isolator.IsolatorRecipeCategory;
import binnie.genetics.integration.jei.isolator.IsolatorRecipeMaker;
import binnie.genetics.integration.jei.polymeriser.PolymeriserRecipeCategory;
import binnie.genetics.integration.jei.polymeriser.PolymeriserRecipeMaker;
import binnie.genetics.integration.jei.sequencer.SequencerRecipeCategory;
import binnie.genetics.integration.jei.sequencer.SequencerRecipeMaker;
import binnie.genetics.item.ModuleItems;
import binnie.genetics.machine.AdvGeneticMachine;
import binnie.genetics.machine.GeneticMachine;
import binnie.genetics.machine.LaboratoryMachine;
import binnie.genetics.machine.incubator.Incubator;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.ISubtypeRegistry;
import mezz.jei.api.JEIPlugin;

@JEIPlugin
public class GeneticsJeiPlugin implements IModPlugin {
	public static IJeiHelpers jeiHelpers;
	public static IGuiHelper guiHelper;
	public static Drawables drawables;

	@Override
	public void registerItemSubtypes(ISubtypeRegistry subtypeRegistry) {
		ModuleItems items = Genetics.items();
		List<Item> chargeables = Arrays.asList(items.itemSequencer, items.itemSerum, items.itemSerumArray);
		ChargeableSubtypeInterpreter chargeableSubtypeInterpreter = new ChargeableSubtypeInterpreter();
		for (Item chargeable : chargeables) {
			subtypeRegistry.registerSubtypeInterpreter(chargeable, chargeableSubtypeInterpreter);
		}
	}

	@Override
	public void registerCategories(IRecipeCategoryRegistration registry) {
		GeneticsJeiPlugin.jeiHelpers = registry.getJeiHelpers();
		GeneticsJeiPlugin.guiHelper = jeiHelpers.getGuiHelper();
		GeneticsJeiPlugin.drawables = Drawables.getDrawables(guiHelper);
		
		registry.addRecipeCategories(
				new IncubatorRecipeCategory(),
				new LarvaeIncubatorRecipeCategory(),
				new IsolatorRecipeCategory(),
				new PolymeriserRecipeCategory(),
				new SequencerRecipeCategory(),
				new InoculatorRecipeCategory(),
				new SplicerRecipeCategory(),
				new GenepoolRecipeCategory(),
				new DatabaseRecipeCategory()
		);
	}

	@Override
	public void register(IModRegistry registry) {
		registry.addRecipeCatalyst(LaboratoryMachine.Incubator.get(1), RecipeUids.INCUBATOR, RecipeUids.INCUBATOR_LARVAE);
		registry.addRecipeCatalyst(GeneticMachine.Isolator.get(1), RecipeUids.ISOLATOR);
		registry.addRecipeCatalyst(GeneticMachine.Polymeriser.get(1), RecipeUids.POLYMERISER);
		registry.addRecipeCatalyst(GeneticMachine.Sequencer.get(1), RecipeUids.SEQUENCER);
		registry.addRecipeCatalyst(GeneticMachine.Inoculator.get(1), RecipeUids.INOCULATOR);
		registry.addRecipeCatalyst(AdvGeneticMachine.Splicer.get(1), RecipeUids.SPLICER);
		registry.addRecipeCatalyst(LaboratoryMachine.Genepool.get(1), RecipeUids.GENEPOOL);
		registry.addRecipeCatalyst(new ItemStack(Genetics.items().database), RecipeUids.DATABASE);
		registry.addRecipeCatalyst(new ItemStack(Genetics.items().database, 1, 1), RecipeUids.DATABASE);

		registry.addRecipes(Incubator.getRecipes(), RecipeUids.INCUBATOR);
		registry.addRecipes(LarvaeIncubatorRecipeMaker.create(Incubator.getLarvaeRecipe()), RecipeUids.INCUBATOR);
		registry.addRecipes(IsolatorRecipeMaker.create(), RecipeUids.ISOLATOR);
		registry.addRecipes(PolymeriserRecipeMaker.create(), RecipeUids.POLYMERISER);
		registry.addRecipes(SequencerRecipeMaker.create(), RecipeUids.SEQUENCER);
		registry.addRecipes(InoculatorRecipeMaker.create(), RecipeUids.INOCULATOR);
		registry.addRecipes(GenepoolRecipeMaker.create(), RecipeUids.GENEPOOL);
		registry.addRecipes(DatabaseRecipeMaker.create(), RecipeUids.DATABASE);

		registry.handleRecipes(IIncubatorRecipe.class, IncubatorRecipeWrapper::new, RecipeUids.INCUBATOR);
	}

	private static class ChargeableSubtypeInterpreter implements ISubtypeRegistry.ISubtypeInterpreter {
		@Override
		public String apply(ItemStack itemStack) {
			Item item = itemStack.getItem();
			if (item instanceof IItemChargeable) {
				IItemChargeable itemChargeable = (IItemChargeable) item;
				boolean fullyCharged = (itemChargeable.getCharges(itemStack) == itemChargeable.getMaxCharges(itemStack));
				String info = fullyCharged ? "charged" : "uncharged";
				ISpeciesRoot root = itemChargeable.getSpeciesRoot(itemStack);
				if (root != null) {
					return info + ':' + root.getUID();
				}
			}

			return ISubtypeRegistry.ISubtypeInterpreter.NONE;
		}
	}
}
