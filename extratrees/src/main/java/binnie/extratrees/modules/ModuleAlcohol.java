package binnie.extratrees.modules;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import forestry.api.modules.ForestryModule;
import forestry.api.recipes.ISqueezerRecipe;
import forestry.api.recipes.RecipeManagers;
import forestry.core.fluids.Fluids;

import binnie.core.Binnie;
import binnie.core.Constants;
import binnie.core.Mods;
import binnie.core.features.FeatureCreationEvent;
import binnie.core.features.FeatureType;
import binnie.core.liquid.DrinkManager;
import binnie.core.liquid.FluidType;
import binnie.core.liquid.IFluidDefinition;
import binnie.core.modules.BinnieModule;
import binnie.core.modules.ExtraTreesModuleUIDs;
import binnie.core.util.ModuleManager;
import binnie.core.util.OreDictUtils;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.alcohol.GlasswareType;
import binnie.extratrees.alcohol.ICocktailIngredientProvider;
import binnie.extratrees.alcohol.drink.DrinkLiquid;
import binnie.extratrees.api.recipes.ExtraTreesRecipeManager;
import binnie.extratrees.api.recipes.IBreweryManager;
import binnie.extratrees.api.recipes.IDistilleryManager;
import binnie.extratrees.api.recipes.IFruitPressManager;
import binnie.extratrees.items.ExtraTreeMiscItems;
import binnie.extratrees.items.Food;
import binnie.extratrees.items.ItemDrink;
import binnie.extratrees.liquid.Alcohol;
import binnie.extratrees.liquid.Cocktail;
import binnie.extratrees.liquid.Juice;
import binnie.extratrees.liquid.Liqueur;
import binnie.extratrees.liquid.Spirit;
import binnie.extratrees.machines.distillery.DistilleryLogic;

@ForestryModule(moduleID = ExtraTreesModuleUIDs.ALCOHOL, containerID = Constants.EXTRA_TREES_MOD_ID, name = "Alcohol", unlocalizedDescription = "extratrees.module.alcohol")
public class ModuleAlcohol extends BinnieModule {
	@Nullable
	public static ItemDrink drink;
	@Nullable
	public static Block blockDrink;

	public ModuleAlcohol() {
		super(Constants.EXTRA_TREES_MOD_ID, ExtraTreesModuleUIDs.CORE);
		MinecraftForge.EVENT_BUS.register(this);
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
		DrinkManager.registerDrinkLiquid(new DrinkLiquid("Water", 13421823, 0.1f, 0.0f, "water"));
	}

	@Override
	public void doInit() {
		IBreweryManager breweryManager = ExtraTreesRecipeManager.breweryManager;
		IFruitPressManager fruitPressManager = ExtraTreesRecipeManager.fruitPressManager;
		if (ModuleManager.isModuleEnabled("forestry", "apiculture")) {
			ItemStack wax = Mods.Forestry.stack("beeswax");
			ItemStack waxCast = Mods.Forestry.stackWildcard("wax_cast");
			for (GlasswareType glasswareType : GlasswareType.values()) {
				ItemStack result = drink.getStack(glasswareType, null, 8);
				Object[] recipe = glasswareType.getRecipePattern(wax.getItem());
				RecipeManagers.fabricatorManager.addRecipe(waxCast, Fluids.GLASS.getFluid(glasswareType.getRecipeGlassCost()), result, recipe);
			}
		}

		if (fruitPressManager != null) {
			for (Juice juice : Juice.values()) {
				String oreDict = juice.getSqueezing();
				List<ItemStack> ores = new ArrayList<>(OreDictionary.getOres(oreDict));
				for (Food food : Food.values()) {
					if (food.getOres().contains(oreDict)) {
						ores.add(food.stack(1));
					}
				}
				for (ItemStack stack : ores) {
					for (ISqueezerRecipe entry : RecipeManagers.squeezerManager.recipes()) {
						ItemStack input = entry.getResources().get(0);
						FluidStack output = entry.getFluidOutput();
						if (!ItemStack.areItemStacksEqual(stack, input) && !OreDictionary.itemMatches(input, stack, true)) {
							continue;
						}
						int amount = output.amount;
						if (Objects.equals(output.getFluid().getName(), "seedoil")) {
							amount *= 2;
						}
						fruitPressManager.addRecipe(stack, juice.stack(amount));
					}
				}
			}
		}
		if (breweryManager != null) {
			for (Alcohol alcohol : Alcohol.values()) {
				FluidType type = alcohol.fluid();
				for (String fermentLiquid : alcohol.getFermentationLiquid()) {
					FluidStack fluid = Binnie.LIQUID.getFluidStack(fermentLiquid);
					if (fluid != null) {
						breweryManager.addRecipe(fluid, type.stack());
					}
				}
			}

			breweryManager.addGrainRecipe(OreDictUtils.GRAIN_BARLEY, Alcohol.Ale.stack(), OreDictUtils.HOPS);

			breweryManager.addGrainRecipe(OreDictUtils.GRAIN_BARLEY, Alcohol.Lager.stack(), OreDictUtils.HOPS, ExtraTreeMiscItems.LAGER_YEAST.stack(1));
			breweryManager.addGrainRecipe(OreDictUtils.GRAIN_ROASTED, Alcohol.Stout.stack(), OreDictUtils.HOPS);
			breweryManager.addGrainRecipe(OreDictUtils.GRAIN_CORN, Alcohol.CornBeer.stack(), OreDictUtils.HOPS);
			breweryManager.addGrainRecipe(OreDictUtils.GRAIN_RYE, Alcohol.RyeBeer.stack(), OreDictUtils.HOPS);
			breweryManager.addGrainRecipe(OreDictUtils.GRAIN_WHEAT, Alcohol.WheatBeer.stack(), OreDictUtils.HOPS);

			breweryManager.addGrainRecipe(OreDictUtils.GRAIN_BARLEY, Alcohol.Barley.stack());
			breweryManager.addGrainRecipe(OreDictUtils.GRAIN_CORN, Alcohol.Corn.stack());
			breweryManager.addGrainRecipe(OreDictUtils.GRAIN_RYE, Alcohol.Rye.stack());
			breweryManager.addGrainRecipe(OreDictUtils.GRAIN_WHEAT, Alcohol.Wheat.stack());
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

	private void addDistillery(IFluidDefinition source, IFluidDefinition singleDistilled, IFluidDefinition doubleDistilled, IFluidDefinition tripleDistilled) {
		int inAmount = DistilleryLogic.INPUT_FLUID_AMOUNT;
		int outAmount1 = inAmount * 4 / 5;
		int outAmount2 = inAmount * 2 / 5;
		int outAmount3 = inAmount / 5;

		IDistilleryManager distilleryManager = ExtraTreesRecipeManager.distilleryManager;

		distilleryManager.addRecipe(source.stack(inAmount), singleDistilled.stack(outAmount1), 0);
		distilleryManager.addRecipe(source.stack(inAmount), doubleDistilled.stack(outAmount2), 1);
		distilleryManager.addRecipe(source.stack(inAmount), tripleDistilled.stack(outAmount3), 2);

		distilleryManager.addRecipe(singleDistilled.stack(inAmount), doubleDistilled.stack(outAmount2), 0);
		distilleryManager.addRecipe(singleDistilled.stack(inAmount), tripleDistilled.stack(outAmount3), 1);
		distilleryManager.addRecipe(doubleDistilled.stack(inAmount), tripleDistilled.stack(outAmount2), 0);
	}


	private <L extends IFluidDefinition & ICocktailIngredientProvider> void registerLiquids(L[] liquids) {
		//Binnie.LIQUID.createLiquids(liquids);
		Cocktail.registerIngredients(liquids);
	}

	@SubscribeEvent
	public void afterFluidCreation(FeatureCreationEvent event) {
		if (!event.containerID.equals(Constants.EXTRA_TREES_MOD_ID) || event.type != FeatureType.FLUID) {
			return;
		}
		registerLiquids(Juice.values());
		registerLiquids(Alcohol.values());
		registerLiquids(Spirit.values());
		registerLiquids(Liqueur.values());
	}
}
