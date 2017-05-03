package binnie.extratrees.alcohol;

import binnie.Binnie;
import binnie.core.BinnieCore;
import binnie.core.IInitializable;
import binnie.core.liquid.IFluidType;
import binnie.core.liquid.ItemFluidContainer;
import binnie.core.resource.BinnieIcon;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.alcohol.drink.CocktailRenderer;
import binnie.extratrees.alcohol.drink.DrinkLiquid;
import binnie.extratrees.alcohol.drink.DrinkManager;
import binnie.extratrees.alcohol.drink.ItemDrink;
import binnie.extratrees.item.Food;
import binnie.extratrees.machines.Brewery;
import binnie.extratrees.machines.Distillery;
import binnie.extratrees.machines.Press;
import forestry.api.recipes.RecipeManagers;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ModuleAlcohol implements IInitializable {
	public static int drinkRendererID;
	public static BinnieIcon liquid;

	@Override
	public void preInit() {
		ModuleAlcohol.liquid = Binnie.Resource.getBlockIcon(ExtraTrees.instance, "liquids/liquid");
		ModuleAlcohol.drinkRendererID = BinnieCore.proxy.getUniqueRenderID();
		ExtraTrees.drink = new ItemDrink();
		BinnieCore.proxy.registerCustomItemRenderer(ExtraTrees.drink, new CocktailRenderer());
		Binnie.Liquid.createLiquids(Juice.values(), ItemFluidContainer.LiquidJuice);
		Binnie.Liquid.createLiquids(Alcohol.values(), ItemFluidContainer.LiquidAlcohol);
		Binnie.Liquid.createLiquids(Spirit.values(), ItemFluidContainer.LiquidSpirit);
		Binnie.Liquid.createLiquids(Liqueur.values(), ItemFluidContainer.LiquidLiqueuer);

		for (Juice juice : Juice.values()) {
			Cocktail.registerIngredient(juice);
		}
		for (Alcohol juice2 : Alcohol.values()) {
			Cocktail.registerIngredient(juice2);
		}
		for (Spirit juice3 : Spirit.values()) {
			Cocktail.registerIngredient(juice3);
		}
		for (Liqueur juice4 : Liqueur.values()) {
			Cocktail.registerIngredient(juice4);
		}
		DrinkManager.registerDrinkLiquid("water", new DrinkLiquid("Water", 0xccccff, 0.1f, 0.0f));
	}

	@Override
	public void init() {
		// ignored
	}

	@Override
	public void postInit() {
		for (Juice juice : Juice.values()) {
			String oreDict = juice.squeezing;
			List<ItemStack> ores = new ArrayList<ItemStack>();
			ores.addAll(OreDictionary.getOres(oreDict));
			for (Food food : Food.values()) {
				if (food.getOres().contains(oreDict)) {
					ores.add(food.get(1));
				}
			}

			for (ItemStack stack : ores) {
				for (Map.Entry<Object[], Object[]> entry : RecipeManagers.squeezerManager.getRecipes().entrySet()) {
					try {
						ItemStack input = (ItemStack) entry.getKey()[0];
						FluidStack output = (FluidStack) entry.getValue()[1];
						if (!ItemStack.areItemStacksEqual(stack, input) && !OreDictionary.itemMatches(input, stack, true)) {
							continue;
						}
						int amount = output.amount;
						if (output.getFluid().getName() == "seedoil") {
							amount *= 2;
						}
						Press.addRecipe(stack, juice.get(amount));
					} catch (Exception ex) {
						// ignored
					}
				}
			}
		}

		for (Alcohol alcohol : Alcohol.values()) {
			for (String fermentLiquid : alcohol.fermentationLiquid) {
				FluidStack fluid = Binnie.Liquid.getLiquidStack(fermentLiquid, 5);
				if (fluid != null) {
					Brewery.addRecipe(fluid, alcohol.get(5));
				}
			}
		}

		Brewery.addBeerAndMashRecipes();
		addDistillery(Alcohol.Apple, Spirit.AppleBrandy, Spirit.AppleLiquor, Spirit.NeutralSpirit);
		addDistillery(Alcohol.Pear, Spirit.PearBrandy, Spirit.PearLiquor, Spirit.NeutralSpirit);
		addDistillery(Alcohol.Apricot, Spirit.ApricotBrandy, Spirit.ApricotLiquor, Spirit.NeutralSpirit);
		addDistillery(Alcohol.Banana, Spirit.FruitBrandy, Spirit.FruitLiquor, Spirit.NeutralSpirit);
		addDistillery(Alcohol.Cherry, Spirit.CherryBrandy, Spirit.CherryLiquor, Spirit.NeutralSpirit);
		addDistillery(Alcohol.Elderberry, Spirit.ElderberryBrandy, Spirit.ElderberryLiquor, Spirit.NeutralSpirit);
		addDistillery(Alcohol.Peach, Spirit.FruitBrandy, Spirit.FruitLiquor, Spirit.NeutralSpirit);
		addDistillery(Alcohol.Plum, Spirit.PlumBrandy, Spirit.FruitLiquor, Spirit.NeutralSpirit);
		addDistillery(Alcohol.Carrot, Spirit.FruitBrandy, Spirit.Vodka, Spirit.NeutralSpirit);
		addDistillery(Alcohol.WhiteWine, Spirit.Brandy, Spirit.Brandy, Spirit.NeutralSpirit);
		addDistillery(Alcohol.RedWine, Spirit.Brandy, Spirit.Brandy, Spirit.NeutralSpirit);
		addDistillery(Alcohol.SparklingWine, Spirit.Brandy, Spirit.Brandy, Spirit.NeutralSpirit);
		addDistillery(Alcohol.Agave, Spirit.Tequila, Spirit.Tequila, Spirit.NeutralSpirit);
		addDistillery(Alcohol.Potato, Spirit.FruitBrandy, Spirit.Vodka, Spirit.NeutralSpirit);
		addDistillery(Alcohol.Citrus, Spirit.CitrusBrandy, Spirit.FruitLiquor, Spirit.NeutralSpirit);
		addDistillery(Alcohol.Cranberry, Spirit.FruitBrandy, Spirit.FruitLiquor, Spirit.NeutralSpirit);
		addDistillery(Alcohol.Pineapple, Spirit.FruitBrandy, Spirit.FruitLiquor, Spirit.NeutralSpirit);
		addDistillery(Alcohol.Tomato, Spirit.FruitBrandy, Spirit.FruitLiquor, Spirit.NeutralSpirit);
		addDistillery(Alcohol.Fruit, Spirit.FruitBrandy, Spirit.FruitLiquor, Spirit.NeutralSpirit);
		addDistillery(Alcohol.Barley, Spirit.Whiskey, Spirit.Vodka, Spirit.NeutralSpirit);
		addDistillery(Alcohol.Wheat, Spirit.WheatWhiskey, Spirit.Vodka, Spirit.NeutralSpirit);
		addDistillery(Alcohol.Rye, Spirit.RyeWhiskey, Spirit.Vodka, Spirit.NeutralSpirit);
		addDistillery(Alcohol.Corn, Spirit.CornWhiskey, Spirit.Vodka, Spirit.NeutralSpirit);
	}

	private void addDistillery(IFluidType source, IFluidType a, IFluidType b, IFluidType c) {
		Distillery.addRecipe(source.get(5), a.get(4), 0);
		Distillery.addRecipe(source.get(5), b.get(2), 1);
		Distillery.addRecipe(source.get(5), c.get(1), 2);
		Distillery.addRecipe(a.get(5), b.get(2), 0);
		Distillery.addRecipe(a.get(5), b.get(1), 1);
		Distillery.addRecipe(b.get(5), c.get(2), 0);
	}
}
