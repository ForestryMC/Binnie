// 
// Decompiled by Procyon v0.5.30
// 

package binnie.genetics.gui;

import binnie.core.craftgui.minecraft.control.ControlItemDisplay;
import binnie.core.craftgui.controls.ControlTextCentered;
import net.minecraft.init.Items;
import net.minecraftforge.fluids.FluidContainerRegistry;
import binnie.extratrees.machines.Distillery;
import binnie.extratrees.machines.Brewery;
import binnie.core.util.UniqueFluidStackSet;
import binnie.extratrees.machines.Press;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.item.crafting.CraftingManager;
import java.util.List;
import java.util.Map;
import forestry.api.recipes.RecipeManagers;
import java.util.ArrayList;
import net.minecraft.item.crafting.FurnaceRecipes;
import binnie.core.util.UniqueItemStackSet;
import java.util.Collection;
import net.minecraft.item.ItemStack;
import binnie.core.craftgui.geometry.IArea;
import binnie.core.craftgui.IWidget;

public abstract class AnalystPageProduce extends ControlAnalystPage
{
	public AnalystPageProduce(final IWidget parent, final IArea area) {
		super(parent, area);
	}

	protected Collection<? extends ItemStack> getAllProducts(final ItemStack key) {
		final Collection<ItemStack> products = new UniqueItemStackSet();
		products.addAll(this.getCentrifuge(key));
		products.addAll(this.getSqueezer(key));
		products.add(FurnaceRecipes.smelting().getSmeltingResult(key));
		products.addAll(this.getCrafting(key));
		return products;
	}

	public Collection<ItemStack> getCentrifuge(final ItemStack stack) {
		final List<ItemStack> products = new ArrayList<ItemStack>();
		for (final Map.Entry<Object[], Object[]> recipe : RecipeManagers.centrifugeManager.getRecipes().entrySet()) {
			boolean isRecipe = false;
			for (final Object obj : recipe.getKey()) {
				if (obj instanceof ItemStack && stack.isItemEqual((ItemStack) obj)) {
					isRecipe = true;
				}
			}
			if (isRecipe) {
				for (final Object obj : recipe.getValue()) {
					if (obj instanceof ItemStack) {
						products.add((ItemStack) obj);
					}
				}
			}
		}
		return products;
	}

	public Collection<ItemStack> getSqueezer(final ItemStack stack) {
		final List<ItemStack> products = new ArrayList<ItemStack>();
		for (final Map.Entry<Object[], Object[]> recipe : RecipeManagers.squeezerManager.getRecipes().entrySet()) {
			boolean isRecipe = false;
			for (final Object obj : recipe.getKey()) {
				if (obj instanceof ItemStack && stack.isItemEqual((ItemStack) obj)) {
					isRecipe = true;
				}
			}
			if (isRecipe) {
				for (final Object obj : recipe.getValue()) {
					if (obj instanceof ItemStack) {
						products.add((ItemStack) obj);
					}
				}
			}
		}
		return products;
	}

	public Collection<ItemStack> getCrafting(final ItemStack stack) {
		final List<ItemStack> products = new ArrayList<ItemStack>();
		for (final Object recipeO : CraftingManager.getInstance().getRecipeList()) {
			if (recipeO instanceof ShapelessRecipes) {
				final ShapelessRecipes recipe = (ShapelessRecipes) recipeO;
				boolean match = true;
				for (final Object rec : recipe.recipeItems) {
					if (rec != null && (!(rec instanceof ItemStack) || !stack.isItemEqual((ItemStack) rec))) {
						match = false;
					}
				}
				if (match) {
					products.add(recipe.getRecipeOutput());
				}
			}
			if (recipeO instanceof ShapedRecipes) {
				final ShapedRecipes recipe2 = (ShapedRecipes) recipeO;
				boolean match = true;
				for (final Object rec2 : recipe2.recipeItems) {
					if (rec2 != null && (!(rec2 instanceof ItemStack) || !stack.isItemEqual((ItemStack) rec2))) {
						match = false;
					}
				}
				if (match) {
					products.add(recipe2.getRecipeOutput());
				}
			}
			if (recipeO instanceof ShapelessOreRecipe) {
				final ShapelessOreRecipe recipe3 = (ShapelessOreRecipe) recipeO;
				boolean match = true;
				for (final Object rec : recipe3.getInput()) {
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

	public Collection<FluidStack> getAllFluids(final ItemStack stack) {
		final List<FluidStack> products = new ArrayList<FluidStack>();
		products.addAll(this.getSqueezerFluid(stack));
		if (Press.getOutput(stack) != null) {
			products.add(Press.getOutput(stack));
		}
		return products;
	}

	public Collection<FluidStack> getSqueezerFluid(final ItemStack stack) {
		final List<FluidStack> products = new ArrayList<FluidStack>();
		for (final Map.Entry<Object[], Object[]> recipe : RecipeManagers.squeezerManager.getRecipes().entrySet()) {
			boolean isRecipe = false;
			for (final Object obj : recipe.getKey()) {
				if (obj instanceof ItemStack && stack.isItemEqual((ItemStack) obj)) {
					isRecipe = true;
				}
			}
			if (isRecipe) {
				for (final Object obj : recipe.getValue()) {
					if (obj instanceof FluidStack) {
						products.add((FluidStack) obj);
					}
				}
			}
		}
		return products;
	}

	protected Collection<? extends FluidStack> getAllProducts(final FluidStack stack) {
		final Collection<FluidStack> fluids = new UniqueFluidStackSet();
		fluids.add(Brewery.getOutput(stack));
		fluids.add(Distillery.getOutput(stack, 0));
		fluids.add(Distillery.getOutput(stack, 1));
		fluids.add(Distillery.getOutput(stack, 2));
		return fluids;
	}

	protected Collection<ItemStack> getAllProductsAndFluids(final Collection<ItemStack> collection) {
		final Collection<ItemStack> products = new UniqueItemStackSet();
		for (final ItemStack stack : collection) {
			products.addAll(this.getAllProducts(stack));
		}
		final Collection<ItemStack> products2 = new UniqueItemStackSet();
		for (final ItemStack stack2 : products) {
			products2.addAll(this.getAllProducts(stack2));
		}
		final Collection<ItemStack> products3 = new UniqueItemStackSet();
		for (final ItemStack stack3 : products2) {
			products3.addAll(this.getAllProducts(stack3));
		}
		products.addAll(products2);
		products.addAll(products3);
		final Collection<FluidStack> allFluids = new UniqueFluidStackSet();
		for (final ItemStack stack4 : collection) {
			allFluids.addAll(this.getAllFluids(stack4));
		}
		final Collection<FluidStack> fluids2 = new UniqueFluidStackSet();
		for (final FluidStack stack5 : allFluids) {
			fluids2.addAll(this.getAllProducts(stack5));
		}
		final Collection<FluidStack> fluids3 = new UniqueFluidStackSet();
		for (final FluidStack stack6 : fluids2) {
			fluids3.addAll(this.getAllProducts(stack6));
		}
		allFluids.addAll(fluids2);
		allFluids.addAll(fluids3);
		for (final FluidStack fluid : allFluids) {
			ItemStack container = null;
			for (final FluidContainerRegistry.FluidContainerData data : FluidContainerRegistry.getRegisteredFluidContainerData()) {
				if (data.emptyContainer.isItemEqual(new ItemStack(Items.glass_bottle)) && data.fluid.isFluidEqual(fluid)) {
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

	protected int getRefined(final String string, int y, final Collection<ItemStack> products) {
		new ControlTextCentered(this, y, string).setColor(this.getColor());
		y += 10;
		final int maxBiomePerLine = (int) ((this.w() + 2.0f - 16.0f) / 18.0f);
		final float biomeListX = (this.w() - (Math.min(maxBiomePerLine, products.size()) * 18 - 2)) / 2.0f;
		int dx = 0;
		int dy = 0;
		for (final ItemStack soilStack : products) {
			if (dx >= 18 * maxBiomePerLine) {
				dx = 0;
				dy += 18;
			}
			final FluidStack fluid = FluidContainerRegistry.getFluidForFilledItem(soilStack);
			soilStack.stackSize = 1;
			final ControlItemDisplay display = new ControlItemDisplay(this, biomeListX + dx, y + dy, soilStack, fluid == null);
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
