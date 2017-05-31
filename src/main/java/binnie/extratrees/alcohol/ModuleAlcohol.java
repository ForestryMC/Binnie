package binnie.extratrees.alcohol;

import binnie.Binnie;
import binnie.core.IInitializable;
import binnie.core.liquid.IFluidType;
import binnie.core.resource.BinnieSprite;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.alcohol.drink.DrinkLiquid;
import binnie.extratrees.alcohol.drink.DrinkManager;
import binnie.extratrees.alcohol.drink.ItemDrink;
import binnie.extratrees.item.ExtraTreeItems;
import binnie.extratrees.item.Food;
import binnie.extratrees.machines.brewery.BrewedGrainRecipe;
import binnie.extratrees.machines.brewery.BreweryRecipes;
import binnie.extratrees.machines.distillery.DistilleryLogic;
import binnie.extratrees.machines.distillery.DistilleryRecipes;
import binnie.extratrees.machines.fruitpress.FruitPressRecipes;
import forestry.api.recipes.ISqueezerRecipe;
import forestry.api.recipes.RecipeManagers;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ModuleAlcohol implements IInitializable {
	public ItemDrink drink;
	public Block blockDrink;
	public int drinkRendererID;
	public BinnieSprite liquid;

	@Override
	public void preInit() {
		this.liquid = Binnie.RESOURCE.getBlockSprite(ExtraTrees.instance, "liquids/liquid");
		//ModuleAlcohol.drinkRendererID = BinnieCore.proxy.getUniqueRenderID();
		this.drink = new ItemDrink();
		ExtraTrees.proxy.registerItem(drink);
		//BinnieCore.proxy.registerCustomItemRenderer(ExtraTrees.drink, new CocktailRenderer());
		Binnie.LIQUID.createLiquids(Juice.values());
		Binnie.LIQUID.createLiquids(Alcohol.values());
		Binnie.LIQUID.createLiquids(Spirit.values());
		Binnie.LIQUID.createLiquids(Liqueur.values());
		for (final Juice juice : Juice.values()) {
			Cocktail.registerIngredient(juice);
		}
		for (final Alcohol juice2 : Alcohol.values()) {
			Cocktail.registerIngredient(juice2);
		}
		for (final Spirit juice3 : Spirit.values()) {
			Cocktail.registerIngredient(juice3);
		}
		for (final Liqueur juice4 : Liqueur.values()) {
			Cocktail.registerIngredient(juice4);
		}
		DrinkManager.registerDrinkLiquid(new DrinkLiquid("Water", 13421823, 0.1f, 0.0f, "water"));
	}

	@Override
	public void init() {
	}

	@Override
	public void postInit() {
		for (final Juice juice : Juice.values()) {
			final String oreDict = juice.squeezing;
			final List<ItemStack> ores = new ArrayList<>();
			ores.addAll(OreDictionary.getOres(oreDict));
			for (final Food food : Food.values()) {
				if (food.getOres().contains(oreDict)) {
					ores.add(food.get(1));
				}
			}
			for (final ItemStack stack : ores) {
				for (final ISqueezerRecipe entry : RecipeManagers.squeezerManager.recipes()) {
					final ItemStack input = entry.getResources().get(0);
					final FluidStack output = entry.getFluidOutput();
					if (!ItemStack.areItemStacksEqual(stack, input) && !OreDictionary.itemMatches(input, stack, true)) {
						continue;
					}
					int amount = output.amount;
					if (Objects.equals(output.getFluid().getName(), "seedoil")) {
						amount *= 2;
					}
					FruitPressRecipes.addRecipe(stack, juice.get(amount));
				}
			}
		}
		for (final Alcohol alcohol : Alcohol.values()) {
			for (final String fermentLiquid : alcohol.fermentationLiquid) {
				final FluidStack fluid = Binnie.LIQUID.getFluidStack(fermentLiquid, Fluid.BUCKET_VOLUME);
				if (fluid != null) {
					BreweryRecipes.addRecipe(fluid, alcohol.get(Fluid.BUCKET_VOLUME));
				}
			}
		}

		BreweryRecipes.addRecipes(
			new BrewedGrainRecipe(Alcohol.Ale, BreweryRecipes.GRAIN_BARLEY, BreweryRecipes.HOPS),
			new BrewedGrainRecipe(Alcohol.Lager, BreweryRecipes.GRAIN_BARLEY, BreweryRecipes.HOPS, ExtraTreeItems.LagerYeast.get(1)),
			new BrewedGrainRecipe(Alcohol.Stout, BreweryRecipes.GRAIN_ROASTED, BreweryRecipes.HOPS),
			new BrewedGrainRecipe(Alcohol.CornBeer, BreweryRecipes.GRAIN_CORN, BreweryRecipes.HOPS),
			new BrewedGrainRecipe(Alcohol.RyeBeer, BreweryRecipes.GRAIN_RYE, BreweryRecipes.HOPS),
			new BrewedGrainRecipe(Alcohol.WheatBeer, BreweryRecipes.GRAIN_WHEAT, BreweryRecipes.HOPS),

			new BrewedGrainRecipe(Alcohol.Barley, BreweryRecipes.GRAIN_BARLEY),
			new BrewedGrainRecipe(Alcohol.Corn, BreweryRecipes.GRAIN_CORN),
			new BrewedGrainRecipe(Alcohol.Rye, BreweryRecipes.GRAIN_RYE),
			new BrewedGrainRecipe(Alcohol.Wheat, BreweryRecipes.GRAIN_WHEAT)
		);

		this.addDistillery(Alcohol.Apple, Spirit.AppleBrandy, Spirit.AppleLiquor, Spirit.NeutralSpirit);
		this.addDistillery(Alcohol.Pear, Spirit.PearBrandy, Spirit.PearLiquor, Spirit.NeutralSpirit);
		this.addDistillery(Alcohol.Apricot, Spirit.ApricotBrandy, Spirit.ApricotLiquor, Spirit.NeutralSpirit);
		this.addDistillery(Alcohol.Banana, Spirit.FruitBrandy, Spirit.FruitLiquor, Spirit.NeutralSpirit);
		this.addDistillery(Alcohol.Cherry, Spirit.CherryBrandy, Spirit.CherryLiquor, Spirit.NeutralSpirit);
		this.addDistillery(Alcohol.Elderberry, Spirit.ElderberryBrandy, Spirit.ElderberryLiquor, Spirit.NeutralSpirit);
		this.addDistillery(Alcohol.Peach, Spirit.FruitBrandy, Spirit.FruitLiquor, Spirit.NeutralSpirit);
		this.addDistillery(Alcohol.Plum, Spirit.PlumBrandy, Spirit.FruitLiquor, Spirit.NeutralSpirit);
		this.addDistillery(Alcohol.Carrot, Spirit.FruitBrandy, Spirit.Vodka, Spirit.NeutralSpirit);
		this.addDistillery(Alcohol.WhiteWine, Spirit.Brandy, Spirit.Brandy, Spirit.NeutralSpirit);
		this.addDistillery(Alcohol.RedWine, Spirit.Brandy, Spirit.Brandy, Spirit.NeutralSpirit);
		this.addDistillery(Alcohol.SparklingWine, Spirit.Brandy, Spirit.Brandy, Spirit.NeutralSpirit);
		this.addDistillery(Alcohol.Agave, Spirit.Tequila, Spirit.Tequila, Spirit.NeutralSpirit);
		this.addDistillery(Alcohol.Potato, Spirit.FruitBrandy, Spirit.Vodka, Spirit.NeutralSpirit);
		this.addDistillery(Alcohol.Citrus, Spirit.CitrusBrandy, Spirit.FruitLiquor, Spirit.NeutralSpirit);
		this.addDistillery(Alcohol.Cranberry, Spirit.FruitBrandy, Spirit.FruitLiquor, Spirit.NeutralSpirit);
		this.addDistillery(Alcohol.Pineapple, Spirit.FruitBrandy, Spirit.FruitLiquor, Spirit.NeutralSpirit);
		this.addDistillery(Alcohol.Tomato, Spirit.FruitBrandy, Spirit.FruitLiquor, Spirit.NeutralSpirit);
		this.addDistillery(Alcohol.Fruit, Spirit.FruitBrandy, Spirit.FruitLiquor, Spirit.NeutralSpirit);
		this.addDistillery(Alcohol.Barley, Spirit.Whiskey, Spirit.Vodka, Spirit.NeutralSpirit);
		this.addDistillery(Alcohol.Wheat, Spirit.WheatWhiskey, Spirit.Vodka, Spirit.NeutralSpirit);
		this.addDistillery(Alcohol.Rye, Spirit.RyeWhiskey, Spirit.Vodka, Spirit.NeutralSpirit);
		this.addDistillery(Alcohol.Corn, Spirit.CornWhiskey, Spirit.Vodka, Spirit.NeutralSpirit);
	}

	private void addDistillery(final IFluidType source, final IFluidType singleDistilled, final IFluidType doubleDistilled, final IFluidType tripleDistilled) {
		final int inAmount = DistilleryLogic.INPUT_FLUID_AMOUNT;
		final int outAmount1 = inAmount * 4 / 5;
		final int outAmount2 = inAmount * 2 / 5;
		final int outAmount3 = inAmount / 5;

		DistilleryRecipes.addRecipe(source.get(inAmount), singleDistilled.get(outAmount1), 0);
		DistilleryRecipes.addRecipe(source.get(inAmount), doubleDistilled.get(outAmount2), 1);
		DistilleryRecipes.addRecipe(source.get(inAmount), tripleDistilled.get(outAmount3), 2);

		DistilleryRecipes.addRecipe(singleDistilled.get(inAmount), doubleDistilled.get(outAmount2), 0);
		DistilleryRecipes.addRecipe(singleDistilled.get(inAmount), tripleDistilled.get(outAmount3), 1);

		DistilleryRecipes.addRecipe(doubleDistilled.get(inAmount), tripleDistilled.get(outAmount2), 0);
	}
}
