package binnie.genetics.machine.incubator;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

import binnie.core.BinnieCore;
import binnie.core.Mods;
import binnie.genetics.item.GeneticLiquid;
import binnie.genetics.item.GeneticsItems;

public class Incubator {
	public static final int[] SLOT_QUEUE = new int[]{0, 1, 2};
	public static final int SLOT_INCUBATOR = 3;
	public static final int[] SLOT_OUTPUT = new int[]{4, 5, 6};
	public static final int TANK_INPUT = 0;
	public static final int TANK_OUTPUT = 1;
	private static final List<IIncubatorRecipe> RECIPES = new ArrayList<>();
	@Nullable
	private static IncubatorRecipeLarvae LARVAE_RECIPE;

	public static void addRecipes() {
		Incubator.RECIPES.add(new IncubatorRecipe(GeneticsItems.GrowthMedium.stack(1), new FluidStack(FluidRegistry.WATER, 25), GeneticLiquid.GrowthMedium.stack(25), 0.2f));
		Incubator.RECIPES.add(new IncubatorRecipe(new ItemStack(Items.WHEAT), GeneticLiquid.GrowthMedium.stack(25), GeneticLiquid.Bacteria.stack(5), 0.2f));
		Incubator.RECIPES.add(new IncubatorRecipe(GeneticsItems.GrowthMedium.stack(1), GeneticLiquid.Bacteria.stack(0), GeneticLiquid.Bacteria.stack(5), 0.05f));
		Incubator.RECIPES.add(new IncubatorRecipe(new ItemStack(Items.SUGAR), GeneticLiquid.Bacteria.stack(2), null, 0.5f, 0.2f)
			.setOutputStack(GeneticsItems.Enzyme.stack(1)));
		Incubator.RECIPES.add(new IncubatorRecipe(GeneticsItems.GrowthMedium.stack(1), GeneticLiquid.BacteriaPoly.stack(0), GeneticLiquid.BacteriaPoly.stack(5), 0.05f));
		Incubator.RECIPES.add(new IncubatorRecipe(GeneticsItems.GrowthMedium.stack(1), GeneticLiquid.BacteriaVector.stack(0), GeneticLiquid.BacteriaVector.stack(5), 0.05f));
		Incubator.RECIPES.add(new IncubatorRecipe(new ItemStack(Items.DYE, 1, 15), GeneticLiquid.Bacteria.stack(10), GeneticLiquid.BacteriaPoly.stack(10), 0.1f));
		Incubator.RECIPES.add(new IncubatorRecipe(new ItemStack(Items.BLAZE_POWDER), GeneticLiquid.Bacteria.stack(10), GeneticLiquid.BacteriaVector.stack(10), 0.05f));
		if (BinnieCore.isApicultureActive()) {
			ItemStack beeLarvaeWildcard = Mods.Forestry.stack("bee_larvae_ge", 1, OreDictionary.WILDCARD_VALUE);
			LARVAE_RECIPE = new IncubatorRecipeLarvae(beeLarvaeWildcard);
			Incubator.RECIPES.add(LARVAE_RECIPE);
		}
	}

	public static List<IIncubatorRecipe> getRecipes() {
		return Collections.unmodifiableList(RECIPES);
	}

	@Nullable
	public static IncubatorRecipeLarvae getLarvaeRecipe() {
		return LARVAE_RECIPE;
	}
}
