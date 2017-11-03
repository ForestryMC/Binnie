package binnie.extratrees.modules;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraftforge.oredict.OreDictionary;

import forestry.api.modules.ForestryModule;
import forestry.api.recipes.ICarpenterManager;
import forestry.api.recipes.RecipeManagers;

import binnie.core.Binnie;
import binnie.core.Constants;
import binnie.core.liquid.ManagerLiquid;
import binnie.core.modules.BlankModule;
import binnie.core.modules.ExtraTreesModuleUIDs;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.items.ItemArboristDatabase;

@ForestryModule(moduleID = ExtraTreesModuleUIDs.TREE_DATABASE, containerID = Constants.EXTRA_TREES_MOD_ID, name = "Database", unlocalizedDescription = "extratrees.module.database.tree")
public class ModuleTreeDatabase extends BlankModule {
	public static Item itemDictionary;

	public ModuleTreeDatabase() {
		super(Constants.EXTRA_TREES_MOD_ID, ExtraTreesModuleUIDs.CORE);
	}

	@Override
	public void registerItemsAndBlocks() {
		itemDictionary = new ItemArboristDatabase();
		ExtraTrees.proxy.registerItem(itemDictionary);
		OreDictionary.registerOre("binnie_database", itemDictionary);
	}

	@Override
	public void doInit() {
		ICarpenterManager carpenterManager = RecipeManagers.carpenterManager;
		carpenterManager.addRecipe(100, Binnie.LIQUID.getFluidStack(ManagerLiquid.WATER, 2000), ItemStack.EMPTY, new ItemStack(ModuleTreeDatabase.itemDictionary), "X#X", "YEY", "RDR", '#', Blocks.GLASS_PANE, 'X', Items.GOLD_INGOT, 'Y', "ingotCopper", 'R', Items.REDSTONE, 'D', Items.DIAMOND, 'E', Items.EMERALD);
	}
}
