package binnie.genetics.gui.analyst;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.NonNullList;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import forestry.api.recipes.ICentrifugeRecipe;
import forestry.api.recipes.ISqueezerRecipe;
import forestry.api.recipes.RecipeManagers;

import binnie.core.gui.IWidget;
import binnie.core.gui.controls.ControlTextCentered;
import binnie.core.gui.geometry.Area;
import binnie.core.gui.minecraft.control.ControlItemDisplay;
import binnie.core.util.UniqueFluidStackSet;
import binnie.core.util.UniqueItemStackSet;
import binnie.extratrees.machines.brewery.recipes.BreweryRecipeManager;
import binnie.extratrees.machines.distillery.recipes.DistilleryRecipeManager;
import binnie.extratrees.machines.fruitpress.recipes.FruitPressRecipeManager;
import binnie.genetics.gui.analyst.bee.AnalystPageProducts;

public abstract class AnalystPageProduce extends ControlAnalystPage {
	public AnalystPageProduce(IWidget parent, Area area) {
		super(parent, area);
	}

	protected Collection<? extends ItemStack> getAllProducts(ItemStack key) {
		Collection<ItemStack> products = new UniqueItemStackSet();
		products.addAll(getCentrifuge(key));
		products.addAll(getSqueezer(key));
		products.add(FurnaceRecipes.instance().getSmeltingResult(key));
		products.addAll(getCrafting(key));
		return products;
	}

	public Collection<ItemStack> getCentrifuge(ItemStack stack) {
		List<ItemStack> products = new ArrayList<>();
		for (ICentrifugeRecipe recipe : RecipeManagers.centrifugeManager.recipes()) {
			boolean isRecipe = false;
			if (stack.isItemEqual(recipe.getInput())) {
				isRecipe = true;
			}
			if (isRecipe) {
				for (Object obj : recipe.getAllProducts().keySet()) {
					if (obj instanceof ItemStack) {
						products.add((ItemStack) obj);
					}
				}
			}
		}
		return products;
	}

	public NonNullList<ItemStack> getSqueezer(ItemStack stack) {
		NonNullList<ItemStack> products = NonNullList.create();
		for (ISqueezerRecipe recipe : RecipeManagers.squeezerManager.recipes()) {
			boolean isRecipe = false;
			for (Object obj : recipe.getResources()) {
				if (obj instanceof ItemStack && stack.isItemEqual((ItemStack) obj)) {
					isRecipe = true;
				}
			}
			if (isRecipe) {
				if (!recipe.getRemnants().isEmpty()) {
					products.add(recipe.getRemnants());
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
		if (FruitPressRecipeManager.getOutput(stack) != null) {
			products.add(FruitPressRecipeManager.getOutput(stack));
		}
		return products;
	}

	public Collection<FluidStack> getSqueezerFluid(ItemStack stack) {
		List<FluidStack> products = new ArrayList<>();
		for (ISqueezerRecipe recipe : RecipeManagers.squeezerManager.recipes()) {
			boolean isRecipe = false;
			for (Object obj : recipe.getResources()) {
				if (obj instanceof ItemStack && stack.isItemEqual((ItemStack) obj)) {
					isRecipe = true;
				}
			}
			if (isRecipe) {
				products.add(recipe.getFluidOutput());
			}
		}
		return products;
	}

	protected Collection<? extends FluidStack> getAllProducts(FluidStack stack) {
		Collection<FluidStack> fluids = new UniqueFluidStackSet();
		fluids.add(BreweryRecipeManager.getOutput(stack));
		fluids.add(DistilleryRecipeManager.getOutput(stack, 0));
		fluids.add(DistilleryRecipeManager.getOutput(stack, 1));
		fluids.add(DistilleryRecipeManager.getOutput(stack, 2));
		return fluids;
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
			ItemStack container = AnalystPageProducts.getContainer(fluid);
			if (container != null) {
				products.add(container);
			}
		}
		return products;
	}

	protected int getRefined(String string, int y, Collection<ItemStack> products) {
		new ControlTextCentered(this, y, string).setColor(getColor());
		y += 10;
		int maxBiomePerLine = (getWidth() + 2 - 16) / 18;
		int biomeListX = (getWidth() - (Math.min(maxBiomePerLine, products.size()) * 18 - 2)) / 2;
		int dx = 0;
		int dy = 0;
		for (ItemStack soilStack : products) {
			if (dx >= 18 * maxBiomePerLine) {
				dx = 0;
				dy += 18;
			}
			FluidStack fluid = FluidUtil.getFluidContained(soilStack);
			soilStack.setCount(1);
			ControlItemDisplay display = new ControlItemDisplay(this, biomeListX + dx, y + dy, soilStack, fluid == null);
			if (fluid != null) {
				display.addTooltip(fluid.getLocalizedName());
			}
			dx += 18;
		}
		if (dx != 0) {
			dy += 18;
		}
		y += dy;
		return y;
	}
}
