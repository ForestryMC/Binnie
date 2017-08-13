package binnie.extratrees.integration.jei.multifence;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import binnie.extratrees.blocks.decor.MultiFenceRecipePattern;
import binnie.extratrees.wood.WoodManager;
import binnie.extratrees.wood.planks.IPlankType;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;

public class MultiFenceRecipeSizeWrapper implements IRecipeWrapper {

	@Nullable
	private IPlankType plankType;
	@Nullable
	private IPlankType plankTypeSecond;
	private MultiFenceRecipePattern pattern;
	private String[] recipePattern;

	public MultiFenceRecipeSizeWrapper(MultiFenceRecipePattern pattern) {
		this(pattern, null, null);
	}

	public MultiFenceRecipeSizeWrapper(MultiFenceRecipePattern pattern, @Nullable IPlankType plankType) {
		this(pattern, plankType, null);
	}

	public MultiFenceRecipeSizeWrapper(MultiFenceRecipePattern pattern, @Nullable IPlankType plankType, @Nullable IPlankType plankTypeSecond) {
		this.pattern = pattern;
		String recipePattern = pattern.getPattern();
		this.recipePattern = new String[3];
		this.recipePattern[0] = recipePattern.substring(0, 3);
		this.recipePattern[1] = recipePattern.substring(3, 6);
		this.recipePattern[2] = recipePattern.substring(6);
		this.plankType = plankType;
		this.plankTypeSecond = plankTypeSecond;
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		int typeCount = pattern.getTypeCount();
		List<List<ItemStack>> types = NonNullList.create();
		if (this.plankType != null) {
			types.add(Collections.singletonList(plankType.getStack(false)));
		} else {
			List<ItemStack> planks = new ArrayList<>(WoodManager.getAllPlankStacks().values());
			Collections.shuffle(planks);
			types.add(planks);
		}
		if (typeCount > 1) {
			if (plankTypeSecond != null) {
				types.add(Collections.singletonList(plankTypeSecond.getStack(false)));
			} else {
				List<ItemStack> planks;
				if (plankType != null) {
					planks = new ArrayList<>(WoodManager.getAllPlankStacks(plankType));
				} else {
					planks = new ArrayList<>(WoodManager.getAllPlankStacks().values());
				}
				Collections.shuffle(planks);
				types.add(planks);
			}
		} else {
			types.add(types.get(0));
		}
		List<List<ItemStack>> itemInputs = NonNullList.withSize(9, NonNullList.create());
		for (int p = 0; p < 3; p++) {
			String recipePattern = this.recipePattern[p];
			for (int index = 0; index < 3; index++) {
				char c = recipePattern.charAt(index);
				if (c == '0') {
					itemInputs.set(p * 3 + index, types.get(0));
				} else if (c == '1') {
					itemInputs.set(p * 3 + index, types.get(1));
				} else {
					itemInputs.set(p * 3 + index, NonNullList.withSize(1, ItemStack.EMPTY));
				}
			}
		}
		List<List<ItemStack>> itemOutputs = new ArrayList<>();
		int size = typeCount > 1 || plankType == null ? types.get(1).size() : 1;
		List<ItemStack> outputs = new ArrayList<>();
		itemOutputs.add(outputs);
		for (int i = 0; i < size; i++) {
			IPlankType plankType = this.plankType;
			if (plankType == null) {
				ItemStack item = types.get(0).get(i);
				plankType = WoodManager.getPlankType(item);
			}
			IPlankType plankTypeSecond = this.plankTypeSecond;
			if (plankTypeSecond == null) {
				ItemStack itemSecond = types.get(1).get(i);
				plankTypeSecond = WoodManager.getPlankType(itemSecond);
			}
			outputs.add(pattern.createFence(plankType, plankTypeSecond));
		}
		ingredients.setOutputLists(ItemStack.class, itemOutputs);
		ingredients.setInputLists(ItemStack.class, itemInputs);
	}
}
