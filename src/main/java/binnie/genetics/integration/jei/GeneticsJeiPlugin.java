package binnie.genetics.integration.jei;

import binnie.core.integration.jei.Drawables;
import binnie.genetics.Genetics;
import binnie.genetics.api.IIncubatorRecipe;
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
import forestry.api.genetics.ISpeciesRoot;
import mezz.jei.api.BlankModPlugin;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.ISubtypeRegistry;
import mezz.jei.api.JEIPlugin;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

@JEIPlugin
public class GeneticsJeiPlugin extends BlankModPlugin {
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
	public void register(IModRegistry registry) {
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

		registry.addRecipeCategoryCraftingItem(LaboratoryMachine.Incubator.get(1), RecipeUids.INCUBATOR, RecipeUids.INCUBATOR_LARVAE);
		registry.addRecipeCategoryCraftingItem(GeneticMachine.Isolator.get(1), RecipeUids.ISOLATOR);
		registry.addRecipeCategoryCraftingItem(GeneticMachine.Polymeriser.get(1), RecipeUids.POLYMERISER);
		registry.addRecipeCategoryCraftingItem(GeneticMachine.Sequencer.get(1), RecipeUids.SEQUENCER);
		registry.addRecipeCategoryCraftingItem(GeneticMachine.Inoculator.get(1), RecipeUids.INOCULATOR);
		registry.addRecipeCategoryCraftingItem(AdvGeneticMachine.Splicer.get(1), RecipeUids.SPLICER);
		registry.addRecipeCategoryCraftingItem(LaboratoryMachine.Genepool.get(1), RecipeUids.GENEPOOL);
		registry.addRecipeCategoryCraftingItem(new ItemStack(Genetics.items().database), RecipeUids.DATABASE);
		registry.addRecipeCategoryCraftingItem(new ItemStack(Genetics.items().database, 1, 1), RecipeUids.DATABASE);

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
		@Nullable
		@Override
		public String getSubtypeInfo(ItemStack itemStack) {
			Item item = itemStack.getItem();
			if (item instanceof IItemChargeable) {
				IItemChargeable itemChargeable = (IItemChargeable) item;
				boolean fullyCharged = (itemChargeable.getCharges(itemStack) == itemChargeable.getMaxCharges(itemStack));
				String info = fullyCharged ? "charged" : "uncharged";
				ISpeciesRoot root = itemChargeable.getSpeciesRoot(itemStack);
				if (root != null) {
					return info + ":" + root.getUID();
				}
			}

			return null;
		}
	}
}
