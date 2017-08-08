package binnie.core.item;

import binnie.Constants;
import binnie.core.util.RecipeUtil;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import binnie.core.BinnieCore;
import binnie.core.IInitializable;

public class ModuleItems implements IInitializable {
	@Override
	public void preInit() {
		BinnieCore.setFieldKit(new ItemFieldKit());
		BinnieCore.getBinnieProxy().registerItem(BinnieCore.getFieldKit());
		BinnieCore.setGenesis(new ItemGenesis());
		BinnieCore.getBinnieProxy().registerItem(BinnieCore.getGenesis());
	}

	@Override
	public void init() {
	}

	@Override
	public void postInit() {
		RecipeUtil recipeUtil = new RecipeUtil(Constants.CORE_MOD_ID);
		recipeUtil.addRecipe("field_kit", new ItemStack(BinnieCore.getFieldKit(), 1, 63), "g  ", " is", " pi", 'g', Blocks.GLASS_PANE, 'i', Items.IRON_INGOT, 'p', Items.PAPER, 's', new ItemStack(Items.DYE, 1));
	}
}
