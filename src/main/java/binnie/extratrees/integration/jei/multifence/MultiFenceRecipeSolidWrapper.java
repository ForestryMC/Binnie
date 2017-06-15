package binnie.extratrees.integration.jei.multifence;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

import binnie.extratrees.block.IPlankType;
import binnie.extratrees.block.WoodManager;
import binnie.extratrees.block.decor.FenceDescription;
import binnie.extratrees.block.decor.FenceType;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import mezz.jei.api.recipe.wrapper.IShapedCraftingRecipeWrapper;

public class MultiFenceRecipeSolidWrapper extends BlankRecipeWrapper implements IShapedCraftingRecipeWrapper {

	private FenceType fenceType;
	@Nullable
	private IPlankType plankType;
	@Nullable
	private IPlankType plankTypeSecondary;

	public MultiFenceRecipeSolidWrapper(FenceType fenceType) {
		this.fenceType = fenceType;
		this.plankType = null;
		this.plankTypeSecondary = null;
	}

	public MultiFenceRecipeSolidWrapper(FenceDescription fenceDesc) {
		this.fenceType = fenceDesc.getFenceType();
		this.plankType = fenceDesc.getPlankType();
		this.plankTypeSecondary = fenceDesc.getSecondaryPlankType();
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		List<ItemStack> fences = new ArrayList<>();
		List<ItemStack> outputFences = new ArrayList<>();
		if (plankType != null) {
			if (plankTypeSecondary == null) {
				for (IPlankType plankTypeSecondary : WoodManager.getAllPlankTypes()) {
					if (plankType != plankTypeSecondary) {
						fences.add(WoodManager.getFence(plankType, plankTypeSecondary, new FenceType(fenceType.size, false, fenceType.embossed), 1));
						outputFences.add(WoodManager.getFence(plankType, plankTypeSecondary, new FenceType(fenceType.size, true, fenceType.embossed), 2));
					}
				}
			} else {
				fences.add(WoodManager.getFence(plankType, plankTypeSecondary, new FenceType(fenceType.size, false, fenceType.embossed), 1));
				outputFences.add(WoodManager.getFence(plankType, plankTypeSecondary, new FenceType(fenceType.size, true, fenceType.embossed), 2));
			}
		} else {
			for (IPlankType plankTypeSecondary : WoodManager.getAllPlankTypes()) {
				for (IPlankType plankType : WoodManager.getAllPlankTypes()) {
					if (plankType != plankTypeSecondary) {
						;
						fences.add(WoodManager.getFence(plankType, plankTypeSecondary, new FenceType(fenceType.size, false, fenceType.embossed), 1));
						outputFences.add(WoodManager.getFence(plankType, plankTypeSecondary, new FenceType(fenceType.size, true, fenceType.embossed), 2));
					}
				}
			}
		}
		List<List<ItemStack>> itemInputs = new ArrayList<>();
		itemInputs.add(fences);
		itemInputs.add(fences);
		itemInputs.add(fences);
		List<List<ItemStack>> itemOutputs = new ArrayList<>();
		itemOutputs.add(outputFences);
		ingredients.setOutputLists(ItemStack.class, itemOutputs);
		ingredients.setInputLists(ItemStack.class, itemInputs);
	}

	@Override
	public int getWidth() {
		return 3;
	}

	@Override
	public int getHeight() {
		return 1;
	}
}
