package binnie.extratrees.modules;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

import forestry.api.modules.ForestryModule;
import forestry.api.recipes.ISqueezerRecipe;
import forestry.api.recipes.RecipeManagers;
import forestry.core.fluids.Fluids;

import binnie.core.Binnie;
import binnie.core.Constants;
import binnie.core.Mods;
import binnie.core.liquid.DrinkManager;
import binnie.core.liquid.FluidType;
import binnie.core.liquid.IFluidDefinition;
import binnie.core.modules.BlankModule;
import binnie.core.modules.ExtraTreesModuleUIDs;
import binnie.core.modules.ModuleManager;
import binnie.core.util.OreDictionaryUtil;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.alcohol.GlasswareType;
import binnie.extratrees.alcohol.ICocktailIngredientProvider;
import binnie.extratrees.alcohol.drink.DrinkLiquid;
import binnie.extratrees.api.recipes.ExtraTreesRecipeManager;
import binnie.extratrees.api.recipes.IBreweryManager;
import binnie.extratrees.api.recipes.IDistilleryManager;
import binnie.extratrees.api.recipes.IFruitPressManager;
import binnie.extratrees.items.ExtraTreeItems;
import binnie.extratrees.items.Food;
import binnie.extratrees.items.ItemDrink;
import binnie.extratrees.liquid.Alcohol;
import binnie.extratrees.liquid.Cocktail;
import binnie.extratrees.liquid.Juice;
import binnie.extratrees.liquid.Liqueur;
import binnie.extratrees.liquid.Spirit;
import binnie.extratrees.machines.distillery.DistilleryLogic;

@ForestryModule(moduleID = ExtraTreesModuleUIDs.ALCOHOL, containerID = Constants.EXTRA_TREES_MOD_ID, name = "Alcohol", unlocalizedDescription = "extratrees.module.alcohol")
public class ModuleAlcohol extends BlankModule {
	@Nullable
	public static ItemDrink drink;
	@Nullable
	public static Block blockDrink;

	public ModuleAlcohol() {
		super(Constants.EXTRA_TREES_MOD_ID, ExtraTreesModuleUIDs.CORE);
	}

	@Override
	public void registerItemsAndBlocks() {
		drink = new ItemDrink();
		ExtraTrees.proxy.registerItem(drink);
	}

	@Override
	public void preInit() {
		//ModuleAlcohol.drinkRendererID = BinnieCore.proxy.getUniqueRenderID();
		//BinnieCore.proxy.registerCustomItemRenderer(ExtraTrees.drink, new CocktailRenderer());
		registerLiquids(Juice.values());
		registerLiquids(Alcohol.values());
		registerLiquids(Spirit.values());
		registerLiquids(Liqueur.values());
		DrinkManager.registerDrinkLiquid(new DrinkLiquid("Water", 13421823, 0.1f, 0.0f, "water"));
	}

	@Override
	public void doInit() {
		IBreweryManager breweryManager = ExtraTreesRecipeManager.breweryManager;
		IFruitPressManager fruitPressManager = ExtraTreesRecipeManager.fruitPressManager;

		if(ModuleManager.isModuleEnabled("forestry", "apiculture")) {
			ItemStack wax = Mods.Forestry.stack("beeswax");
			ItemStack waxCast = Mods.Forestry.stackWildcard("wax_cast");
			for (GlasswareType glasswareType : GlasswareType.values()) {
				ItemStack result = drink.getStack(glasswareType, null, 8);
				Object[] recipe = glasswareType.getRecipePattern(wax.getItem());
				RecipeManagers.fabricatorManager.addRecipe(waxCast, Fluids.GLASS.getFluid(glasswareType.getRecipeGlassCost()), result, recipe);
			}
		}

		if(fruitPressManager != null) {
			for (Juice juice : Juice.values()) {
				String oreDict = juice.getSqueezing();
				List<ItemStack> ores = new ArrayList<>(OreDictionary.getOres(oreDict));
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
						fruitPressManager.addRecipe(stack, juice.get(amount));
					}
				}
			}
		}
		if(breweryManager != null) {
			for (Alcohol alcohol : Alcohol.values()) {
				FluidType type = alcohol.getType();
				for (String fermentLiquid : alcohol.getFermentationLiquid()) {
					FluidStack fluid = Binnie.LIQUID.getFluidStack(fermentLiquid);
					if (fluid != null) {
						breweryManager.addRecipe(fluid, type.get());
					}
				}
			}

			breweryManager.addGrainRecipe(OreDictionaryUtil.GRAIN_BARLEY, Alcohol.Ale.get(), OreDictionaryUtil.HOPS);

			breweryManager.addGrainRecipe(OreDictionaryUtil.GRAIN_BARLEY, Alcohol.Lager.get(), OreDictionaryUtil.HOPS, ExtraTreeItems.LAGER_YEAST.get(1));
			breweryManager.addGrainRecipe(OreDictionaryUtil.GRAIN_ROASTED, Alcohol.Stout.get(), OreDictionaryUtil.HOPS);
			breweryManager.addGrainRecipe(OreDictionaryUtil.GRAIN_CORN, Alcohol.CornBeer.get(), OreDictionaryUtil.HOPS);
			breweryManager.addGrainRecipe(OreDictionaryUtil.GRAIN_RYE, Alcohol.RyeBeer.get(), OreDictionaryUtil.HOPS);
			breweryManager.addGrainRecipe(OreDictionaryUtil.GRAIN_WHEAT, Alcohol.WheatBeer.get(), OreDictionaryUtil.HOPS);

			breweryManager.addGrainRecipe(OreDictionaryUtil.GRAIN_BARLEY, Alcohol.Barley.get());
			breweryManager.addGrainRecipe(OreDictionaryUtil.GRAIN_CORN, Alcohol.Corn.get());
			breweryManager.addGrainRecipe(OreDictionaryUtil.GRAIN_RYE, Alcohol.Rye.get());
			breweryManager.addGrainRecipe(OreDictionaryUtil.GRAIN_WHEAT, Alcohol.Wheat.get());
		}

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

	private void addDistillery(final IFluidDefinition source, final IFluidDefinition singleDistilled, final IFluidDefinition doubleDistilled, final IFluidDefinition tripleDistilled) {
		final int inAmount = DistilleryLogic.INPUT_FLUID_AMOUNT;
		final int outAmount1 = inAmount * 4 / 5;
		final int outAmount2 = inAmount * 2 / 5;
		final int outAmount3 = inAmount / 5;

		IDistilleryManager distilleryManager = ExtraTreesRecipeManager.distilleryManager;

		distilleryManager.addRecipe(source.get(inAmount), singleDistilled.get(outAmount1), 0);
		distilleryManager.addRecipe(source.get(inAmount), doubleDistilled.get(outAmount2), 1);
		distilleryManager.addRecipe(source.get(inAmount), tripleDistilled.get(outAmount3), 2);

		distilleryManager.addRecipe(singleDistilled.get(inAmount), doubleDistilled.get(outAmount2), 0);
		distilleryManager.addRecipe(singleDistilled.get(inAmount), tripleDistilled.get(outAmount3), 1);
		distilleryManager.addRecipe(doubleDistilled.get(inAmount), tripleDistilled.get(outAmount2), 0);
	}


	private <L extends IFluidDefinition & ICocktailIngredientProvider> void registerLiquids(L[] liquids){
		Binnie.LIQUID.createLiquids(liquids);
		Cocktail.registerIngredients(liquids);
	}
}
