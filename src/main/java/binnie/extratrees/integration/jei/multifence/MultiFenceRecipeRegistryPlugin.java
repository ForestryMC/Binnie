package binnie.extratrees.integration.jei.multifence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import binnie.extratrees.block.IPlankType;
import binnie.extratrees.block.WoodManager;
import binnie.extratrees.block.decor.FenceDescription;
import binnie.extratrees.block.decor.FenceType;
import binnie.extratrees.block.decor.MultiFenceRecipePattern;
import mezz.jei.api.recipe.IFocus;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeRegistryPlugin;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import mezz.jei.api.recipe.IFocus.Mode;
import mezz.jei.gui.Focus;
import net.minecraft.item.ItemStack;

public class MultiFenceRecipeRegistryPlugin implements IRecipeRegistryPlugin {

	public List<IRecipeWrapper> recipes = new ArrayList<>();
	
	@Override
	public <V> List<String> getRecipeCategoryUids(IFocus<V> focus) {
		focus = Focus.check(focus);
		V ingredient = focus.getValue();
		
		if(!(ingredient instanceof ItemStack)){
			return Collections.emptyList();
		}
		ItemStack itemStack = (ItemStack) ingredient;
		FenceDescription desc;
		switch (focus.getMode()) {
			case INPUT:
				desc = WoodManager.getFenceDescription(itemStack);
				IPlankType plankType = WoodManager.getPlankType(itemStack);
				if(plankType != null || desc != null && (!desc.getFenceType().embossed || !desc.getFenceType().embossed)){
					return Collections.singletonList(VanillaRecipeCategoryUid.CRAFTING);
				}
				return Collections.emptyList();
			case OUTPUT:
				desc = WoodManager.getFenceDescription(itemStack);
				if(desc != null){
					return Collections.singletonList(VanillaRecipeCategoryUid.CRAFTING);
				}
				return Collections.emptyList();
			default:
				return Collections.singletonList(VanillaRecipeCategoryUid.CRAFTING);
		}
	}

	@Override
	public <T extends IRecipeWrapper, V> List<T> getRecipeWrappers(IRecipeCategory<T> recipeCategory, IFocus<V> focus) {
		focus = Focus.check(focus);
		V ingredient = focus.getValue();
		if(!recipeCategory.getUid().equals(VanillaRecipeCategoryUid.CRAFTING) || !(ingredient instanceof ItemStack)){
			return Collections.emptyList();
		}
		return (List<T>) getRecipes((IFocus<ItemStack>) focus);
	}

	@Override
	public <T extends IRecipeWrapper> List<T> getRecipeWrappers(IRecipeCategory<T> recipeCategory) {
		if(!recipeCategory.getUid().equals(VanillaRecipeCategoryUid.CRAFTING)){
			return Collections.emptyList();
		}
		if(recipes.isEmpty()){
			for(MultiFenceRecipePattern pattern : MultiFenceRecipePattern.VALUES){
				recipes.add(new MultiFenceRecipeSizeWrapper(pattern));
			}
			for(FenceType fenceType : FenceType.VALUES){
				recipes.add(new MultiFenceRecipeEmbeddedWrapper(fenceType));
				recipes.add(new MultiFenceRecipeSolidWrapper(fenceType));
			}
		}
		return (List<T>) recipes;
	}
	
	private List<IRecipeWrapper> getRecipes(IFocus<ItemStack> focus){
		ItemStack ingredient = focus.getValue();
		List<IRecipeWrapper> recipes = new ArrayList<>();
		if(focus.getMode() == Mode.INPUT){
			IPlankType plankType = WoodManager.getPlankType(ingredient);
			if(plankType != null){
				for(MultiFenceRecipePattern pattern : MultiFenceRecipePattern.VALUES){
					recipes.add(new MultiFenceRecipeSizeWrapper(pattern, plankType));
				}
				for(int size = 0;size < 3;size++){
					for (final boolean solid : new boolean[]{false, true}) {
						recipes.add(new MultiFenceRecipeEmbeddedWrapper(plankType, new FenceType(size, solid, false)));
					}
				}
			}else{
				FenceDescription desc = WoodManager.getFenceDescription(ingredient);
				if(desc != null){
					if(!desc.getFenceType().embossed){
						recipes.add(new MultiFenceRecipeEmbeddedWrapper(desc));
					}
					if(!desc.getFenceType().solid){
						recipes.add(new MultiFenceRecipeSolidWrapper(desc));
					}
				}
			}
		}else{
			FenceDescription desc = WoodManager.getFenceDescription(ingredient);
			if(desc != null){
				int size = desc.getFenceType().size;
				recipes.add(new MultiFenceRecipeSizeWrapper(MultiFenceRecipePattern.VALUES[size * 2], desc.getPlankType(), desc.getSecondaryPlankType()));
				recipes.add(new MultiFenceRecipeSizeWrapper(MultiFenceRecipePattern.VALUES[size * 2 + 1], desc.getPlankType(), desc.getSecondaryPlankType()));
				if(desc.getFenceType().embossed){
					recipes.add(new MultiFenceRecipeEmbeddedWrapper(desc));
				}
				if(desc.getFenceType().solid){
					recipes.add(new MultiFenceRecipeSolidWrapper(desc));
				}
			}
		}
		return recipes;
	}

}
