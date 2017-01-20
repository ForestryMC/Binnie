package binnie.genetics.integration.jei;

import binnie.core.integration.jei.Drawables;
import binnie.core.integration.jei.SimpleRecipeHandler;
import binnie.genetics.Genetics;
import binnie.genetics.api.IItemChargeable;
import binnie.genetics.integration.jei.genepool.GenepoolRecipeCategory;
import binnie.genetics.integration.jei.genepool.GenepoolRecipeMaker;
import binnie.genetics.integration.jei.genepool.GenepoolRecipeWrapper;
import binnie.genetics.integration.jei.incubator.IncubatorRecipeCategory;
import binnie.genetics.integration.jei.incubator.IncubatorRecipeHandler;
import binnie.genetics.integration.jei.incubator.LarvaeIncubatorRecipeCategory;
import binnie.genetics.integration.jei.incubator.LarvaeIncubatorRecipeMaker;
import binnie.genetics.integration.jei.incubator.LarvaeIncubatorRecipeWrapper;
import binnie.genetics.integration.jei.inoculator.InoculatorRecipeCategory;
import binnie.genetics.integration.jei.inoculator.InoculatorRecipeMaker;
import binnie.genetics.integration.jei.inoculator.InoculatorRecipeWrapper;
import binnie.genetics.integration.jei.inoculator.SplicerRecipeCategory;
import binnie.genetics.integration.jei.inoculator.SplicerRecipeWrapper;
import binnie.genetics.integration.jei.isolator.IsolatorRecipeCategory;
import binnie.genetics.integration.jei.isolator.IsolatorRecipeMaker;
import binnie.genetics.integration.jei.isolator.IsolatorRecipeWrapper;
import binnie.genetics.integration.jei.polymeriser.PolymeriserRecipeCategory;
import binnie.genetics.integration.jei.polymeriser.PolymeriserRecipeMaker;
import binnie.genetics.integration.jei.polymeriser.PolymeriserRecipeWrapper;
import binnie.genetics.integration.jei.sequencer.SequencerRecipeCategory;
import binnie.genetics.integration.jei.sequencer.SequencerRecipeMaker;
import binnie.genetics.integration.jei.sequencer.SequencerRecipeWrapper;
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
		List<Item> chargeables = Arrays.asList(Genetics.itemSequencer, Genetics.itemSerum, Genetics.itemSerumArray);
		ChargeableSubtypeInterpreter chargeableSubtypeInterpreter = new ChargeableSubtypeInterpreter();
		for (Item chargeable : chargeables) {
			subtypeRegistry.registerNbtInterpreter(chargeable, chargeableSubtypeInterpreter);
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
				new GenepoolRecipeCategory()
		);

		registry.addRecipeHandlers(
				new IncubatorRecipeHandler(),
				new SimpleRecipeHandler<>(LarvaeIncubatorRecipeWrapper.class, RecipeUids.INCUBATOR_LARVAE),
				new SimpleRecipeHandler<>(IsolatorRecipeWrapper.class, RecipeUids.ISOLATOR),
				new SimpleRecipeHandler<>(PolymeriserRecipeWrapper.class, RecipeUids.POLYMERISER),
				new SimpleRecipeHandler<>(SequencerRecipeWrapper.class, RecipeUids.SEQUENCER),
				new SimpleRecipeHandler<>(InoculatorRecipeWrapper.class, RecipeUids.INOCULATOR),
				new SimpleRecipeHandler<>(SplicerRecipeWrapper.class, RecipeUids.SPLICER),
				new SimpleRecipeHandler<>(GenepoolRecipeWrapper.class, RecipeUids.GENEPOOL)
		);

		registry.addRecipeCategoryCraftingItem(LaboratoryMachine.Incubator.get(1), RecipeUids.INCUBATOR, RecipeUids.INCUBATOR_LARVAE);
		registry.addRecipeCategoryCraftingItem(GeneticMachine.Isolator.get(1), RecipeUids.ISOLATOR);
		registry.addRecipeCategoryCraftingItem(GeneticMachine.Polymeriser.get(1), RecipeUids.POLYMERISER);
		registry.addRecipeCategoryCraftingItem(GeneticMachine.Sequencer.get(1), RecipeUids.SEQUENCER);
		registry.addRecipeCategoryCraftingItem(GeneticMachine.Inoculator.get(1), RecipeUids.INOCULATOR);
		registry.addRecipeCategoryCraftingItem(AdvGeneticMachine.Splicer.get(1), RecipeUids.SPLICER);
		registry.addRecipeCategoryCraftingItem(LaboratoryMachine.Genepool.get(1), RecipeUids.GENEPOOL);

		registry.addRecipes(Incubator.getRecipes());
		registry.addRecipes(LarvaeIncubatorRecipeMaker.create(Incubator.getLarvaeRecipe()));
		registry.addRecipes(IsolatorRecipeMaker.create());
		registry.addRecipes(PolymeriserRecipeMaker.create());
		registry.addRecipes(SequencerRecipeMaker.create());
		registry.addRecipes(InoculatorRecipeMaker.create());
		registry.addRecipes(GenepoolRecipeMaker.create());
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
				return info + ":" + root.getUID();
			}

			return null;
		}
	}
}
