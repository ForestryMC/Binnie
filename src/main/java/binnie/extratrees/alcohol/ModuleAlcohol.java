package binnie.extratrees.alcohol;

import binnie.Binnie;
import binnie.core.IInitializable;
import binnie.core.liquid.IFluidType;
import binnie.core.liquid.ItemFluidContainer;
import binnie.core.resource.BinnieIcon;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.alcohol.drink.DrinkLiquid;
import binnie.extratrees.alcohol.drink.DrinkManager;
import binnie.extratrees.alcohol.drink.ItemDrink;
import binnie.extratrees.item.Food;
import binnie.extratrees.machines.Brewery;
import binnie.extratrees.machines.Distillery;
import binnie.extratrees.machines.Press;
import forestry.api.recipes.ISqueezerRecipe;
import forestry.api.recipes.RecipeManagers;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ModuleAlcohol implements IInitializable {
    public static int drinkRendererID;
    public static BinnieIcon liquid;

    @Override
    public void preInit() {
        ModuleAlcohol.liquid = Binnie.Resource.getBlockIcon(ExtraTrees.instance, "liquids/liquid");
        //ModuleAlcohol.drinkRendererID = BinnieCore.proxy.getUniqueRenderID();
        ExtraTrees.drink = new ItemDrink();
        //BinnieCore.proxy.registerCustomItemRenderer(ExtraTrees.drink, new CocktailRenderer());
        Binnie.Liquid.createLiquids(Juice.values(), ItemFluidContainer.LiquidJuice);
        Binnie.Liquid.createLiquids(Alcohol.values(), ItemFluidContainer.LiquidAlcohol);
        Binnie.Liquid.createLiquids(Spirit.values(), ItemFluidContainer.LiquidSpirit);
        Binnie.Liquid.createLiquids(Liqueur.values(), ItemFluidContainer.LiquidLiqueuer);
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
        DrinkManager.registerDrinkLiquid("water", new DrinkLiquid("Water", 13421823, 0.1f, 0.0f));
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
                    try {
                        final ItemStack input = entry.getResources()[0];
                        final FluidStack output = entry.getFluidOutput();
                        if (!ItemStack.areItemStacksEqual(stack, input) && !OreDictionary.itemMatches(input, stack, true)) {
                            continue;
                        }
                        int amount = output.amount;
                        if (Objects.equals(output.getFluid().getName(), "seedoil")) {
                            amount *= 2;
                        }
                        Press.addRecipe(stack, juice.get(amount));
                    } catch (Exception ex) {
                    }
                }
            }
        }
        for (final Alcohol alcohol : Alcohol.values()) {
            for (final String fermentLiquid : alcohol.fermentationLiquid) {
                final FluidStack fluid = Binnie.Liquid.getLiquidStack(fermentLiquid, 5);
                if (fluid != null) {
                    Brewery.addRecipe(fluid, alcohol.get(5));
                }
            }
        }
        Brewery.addBeerAndMashRecipes();
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

    private void addDistillery(final IFluidType source, final IFluidType a, final IFluidType b, final IFluidType c) {
        Distillery.addRecipe(source.get(5), a.get(4), 0);
        Distillery.addRecipe(source.get(5), b.get(2), 1);
        Distillery.addRecipe(source.get(5), c.get(1), 2);
        Distillery.addRecipe(a.get(5), b.get(2), 0);
        Distillery.addRecipe(a.get(5), b.get(1), 1);
        Distillery.addRecipe(b.get(5), c.get(2), 0);
    }
}
