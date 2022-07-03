package binnie.genetics.gui;

import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.ControlTextCentered;
import binnie.core.craftgui.geometry.IArea;
import binnie.core.craftgui.minecraft.control.ControlItemDisplay;
import binnie.core.util.UniqueFluidStackSet;
import binnie.core.util.UniqueItemStackSet;
import forestry.api.recipes.RecipeManagers;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public abstract class AnalystPageProduce extends ControlAnalystPage {
    public AnalystPageProduce(IWidget parent, IArea area) {
        super(parent, area);
    }

    protected Collection<? extends ItemStack> getAllProducts(ItemStack key) {
        Collection<ItemStack> products = new UniqueItemStackSet();
        products.addAll(getCentrifuge(key));
        products.addAll(getSqueezer(key));
        products.add(FurnaceRecipes.smelting().getSmeltingResult(key));
        products.addAll(getCrafting(key));
        return products;
    }

    public Collection<ItemStack> getCentrifuge(ItemStack stack) {
        List<ItemStack> products = new ArrayList<>();
        for (Map.Entry<Object[], Object[]> recipe :
                RecipeManagers.centrifugeManager.getRecipes().entrySet()) {
            boolean isRecipe = false;
            for (Object obj : recipe.getKey()) {
                if (obj instanceof ItemStack && stack.isItemEqual((ItemStack) obj)) {
                    isRecipe = true;
                }
            }

            if (isRecipe) {
                for (Object obj : recipe.getValue()) {
                    if (obj instanceof ItemStack) {
                        products.add((ItemStack) obj);
                    }
                }
            }
        }
        return products;
    }

    public Collection<ItemStack> getSqueezer(ItemStack stack) {
        List<ItemStack> products = new ArrayList<>();
        for (Map.Entry<Object[], Object[]> recipe :
                RecipeManagers.squeezerManager.getRecipes().entrySet()) {
            boolean isRecipe = false;
            for (Object obj : recipe.getKey()) {
                if (obj instanceof ItemStack && stack.isItemEqual((ItemStack) obj)) {
                    isRecipe = true;
                }
            }

            if (isRecipe) {
                for (Object obj : recipe.getValue()) {
                    if (obj instanceof ItemStack) {
                        products.add((ItemStack) obj);
                    }
                }
            }
        }
        return products;
    }

    public Collection<ItemStack> getCrafting(ItemStack stack) {
        List<ItemStack> products = new ArrayList<>();
        for (Object recipeO : CraftingManager.getInstance().getRecipeList()) {
            if (recipeO instanceof ShapelessRecipes) {
                ShapelessRecipes recipe = (ShapelessRecipes) recipeO;
                boolean match = true;
                for (Object rec : recipe.recipeItems) {
                    if (rec != null && (!(rec instanceof ItemStack) || !stack.isItemEqual((ItemStack) rec))) {
                        match = false;
                    }
                }

                if (match) {
                    products.add(recipe.getRecipeOutput());
                }
            }

            if (recipeO instanceof ShapedRecipes) {
                ShapedRecipes recipe2 = (ShapedRecipes) recipeO;
                boolean match = true;
                for (Object rec2 : recipe2.recipeItems) {
                    if (rec2 != null && (!(rec2 instanceof ItemStack) || !stack.isItemEqual((ItemStack) rec2))) {
                        match = false;
                    }
                }

                if (match) {
                    products.add(recipe2.getRecipeOutput());
                }
            }

            if (recipeO instanceof ShapelessOreRecipe) {
                ShapelessOreRecipe recipe3 = (ShapelessOreRecipe) recipeO;
                boolean match = true;
                for (Object rec : recipe3.getInput()) {
                    if (rec != null && (!(rec instanceof ItemStack) || !stack.isItemEqual((ItemStack) rec))) {
                        match = false;
                    }
                }

                if (!match) {
                    continue;
                }
                products.add(recipe3.getRecipeOutput());
            }
        }
        return products;
    }

    public Collection<FluidStack> getAllFluids(ItemStack stack) {
        List<FluidStack> products = new ArrayList<>();
        products.addAll(getSqueezerFluid(stack));
        return products;
    }

    public Collection<FluidStack> getSqueezerFluid(ItemStack stack) {
        List<FluidStack> products = new ArrayList<>();
        for (Map.Entry<Object[], Object[]> recipe :
                RecipeManagers.squeezerManager.getRecipes().entrySet()) {
            boolean isRecipe = false;
            for (Object obj : recipe.getKey()) {
                if (obj instanceof ItemStack && stack.isItemEqual((ItemStack) obj)) {
                    isRecipe = true;
                }
            }

            if (isRecipe) {
                for (Object obj : recipe.getValue()) {
                    if (obj instanceof FluidStack) {
                        products.add((FluidStack) obj);
                    }
                }
            }
        }
        return products;
    }

    protected Collection<? extends FluidStack> getAllProducts(FluidStack stack) {
        return new UniqueFluidStackSet();
    }

    protected Collection<ItemStack> getAllProductsAndFluids(Collection<ItemStack> collection) {
        Collection<ItemStack> products = new UniqueItemStackSet();
        for (ItemStack stack : collection) {
            products.addAll(getAllProducts(stack));
        }

        Collection<ItemStack> products2 = new UniqueItemStackSet();
        for (ItemStack stack2 : products) {
            products2.addAll(getAllProducts(stack2));
        }

        Collection<ItemStack> products3 = new UniqueItemStackSet();
        for (ItemStack stack3 : products2) {
            products3.addAll(getAllProducts(stack3));
        }

        products.addAll(products2);
        products.addAll(products3);
        Collection<FluidStack> allFluids = new UniqueFluidStackSet();
        for (ItemStack stack4 : collection) {
            allFluids.addAll(getAllFluids(stack4));
        }

        Collection<FluidStack> fluids2 = new UniqueFluidStackSet();
        for (FluidStack stack5 : allFluids) {
            fluids2.addAll(getAllProducts(stack5));
        }

        Collection<FluidStack> fluids3 = new UniqueFluidStackSet();
        for (FluidStack stack6 : fluids2) {
            fluids3.addAll(getAllProducts(stack6));
        }

        allFluids.addAll(fluids2);
        allFluids.addAll(fluids3);
        for (FluidStack fluid : allFluids) {
            ItemStack container = null;
            for (FluidContainerRegistry.FluidContainerData data :
                    FluidContainerRegistry.getRegisteredFluidContainerData()) {
                if (data.emptyContainer.isItemEqual(new ItemStack(Items.glass_bottle))
                        && data.fluid.isFluidEqual(fluid)) {
                    container = data.filledContainer;
                    break;
                }

                if (data.emptyContainer.isItemEqual(new ItemStack(Items.bucket)) && data.fluid.isFluidEqual(fluid)) {
                    container = data.filledContainer;
                    break;
                }

                if (data.fluid.isFluidEqual(fluid)) {
                    container = data.filledContainer;
                    break;
                }
            }
            if (container != null) {
                products.add(container);
            }
        }
        return products;
    }

    protected int getRefined(String string, int y, Collection<ItemStack> products) {
        new ControlTextCentered(this, y, string).setColor(getColor());
        y += 10;
        int maxBiomePerLine = (int) ((w() + 2.0f - 16.0f) / 18.0f);
        float biomeListX = (w() - (Math.min(maxBiomePerLine, products.size()) * 18 - 2)) / 2.0f;
        int dx = 0;
        int dy = 0;
        for (ItemStack soilStack : products) {
            if (dx >= 18 * maxBiomePerLine) {
                dx = 0;
                dy += 18;
            }

            FluidStack fluid = FluidContainerRegistry.getFluidForFilledItem(soilStack);
            soilStack.stackSize = 1;
            ControlItemDisplay display =
                    new ControlItemDisplay(this, biomeListX + dx, y + dy, soilStack, fluid == null);
            if (fluid != null) {
                display.addTooltip(fluid.getLocalizedName());
            }
            dx += 18;
        }

        if (dx != 0) {
            dy += 18;
        }
        return y + dy;
    }
}
