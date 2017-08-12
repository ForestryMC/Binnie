package binnie.extratrees.modules;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import forestry.api.recipes.ICarpenterManager;
import forestry.api.recipes.RecipeManagers;

import binnie.core.Binnie;
import binnie.core.Constants;
import binnie.core.BinnieCore;
import binnie.core.liquid.ManagerLiquid;
import binnie.core.modules.ExtraTreesModuleUIDs;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.items.ItemMothDatabase;
import binnie.core.modules.BinnieModule;
import binnie.core.modules.Module;

@BinnieModule(moduleID = ExtraTreesModuleUIDs.MOTH_DATABASE, moduleContainerID = Constants.EXTRA_TREES_MOD_ID, name = "Database", unlocalizedDescription = "extratrees.module.database.moth")
public class ModuleMothDatabase extends Module {

	public static Item itemDictionaryLepi;

	@Override
	public void registerItemsAndBlocks() {
		itemDictionaryLepi = new ItemMothDatabase();
		ExtraTrees.proxy.registerItem(itemDictionaryLepi);
	}

	@Override
	public void postInit() {
		ICarpenterManager carpenterManager = RecipeManagers.carpenterManager;

		carpenterManager.addRecipe(100, Binnie.LIQUID.getFluidStack(ManagerLiquid.WATER, 2000), ItemStack.EMPTY, new ItemStack(itemDictionaryLepi), "X#X", "YEY", "RDR", '#', Blocks.GLASS_PANE, 'X', Items.GOLD_INGOT, 'Y', "ingotBronze", 'R', Items.REDSTONE, 'D', Items.DIAMOND, 'E', Items.EMERALD);
	}

	@Override
	public boolean isAvailable() {
		return BinnieCore.isLepidopteryActive();
	}
}
