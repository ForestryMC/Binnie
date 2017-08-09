package binnie.extratrees.modules;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import forestry.api.recipes.ICarpenterManager;
import forestry.api.recipes.RecipeManagers;

import binnie.Binnie;
import binnie.Constants;
import binnie.core.liquid.ManagerLiquid;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.item.ItemArboristDatabase;
import binnie.modules.BinnieModule;
import binnie.modules.Module;

@BinnieModule(moduleID = ExtraTreesModuleUIDs.TREE_DATABASE, moduleContainerID = Constants.EXTRA_TREES_MOD_ID, name = "Database", unlocalizedDescription = "extratrees.module.database.tree")
public class ModuleTreeDatabase extends Module {
	public static Item itemDictionary;

	@Override
	public void registerItemsAndBlocks() {
		itemDictionary = new ItemArboristDatabase();
		ExtraTrees.proxy.registerItem(itemDictionary);
	}

	@Override
	public void postInit() {
		ICarpenterManager carpenterManager = RecipeManagers.carpenterManager;
		carpenterManager.addRecipe(100, Binnie.LIQUID.getFluidStack(ManagerLiquid.WATER, 2000), ItemStack.EMPTY, new ItemStack(ModuleTreeDatabase.itemDictionary), "X#X", "YEY", "RDR", '#', Blocks.GLASS_PANE, 'X', Items.GOLD_INGOT, 'Y', "ingotCopper", 'R', Items.REDSTONE, 'D', Items.DIAMOND, 'E', Items.EMERALD);
	}
}
