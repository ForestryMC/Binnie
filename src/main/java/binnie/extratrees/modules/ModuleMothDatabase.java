package binnie.extratrees.modules;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;

import forestry.api.recipes.ICarpenterManager;
import forestry.api.recipes.RecipeManagers;

import binnie.Binnie;
import binnie.Constants;
import binnie.core.BinnieCore;
import binnie.core.liquid.ManagerLiquid;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.item.ItemMothDatabase;
import binnie.modules.BinnieModule;
import binnie.modules.Module;

@BinnieModule(moduleID = ExtraTreesModuleUIDs.MOTH_DATABASE, moduleContainerID = Constants.EXTRA_TREES_MOD_ID, name = "Database", unlocalizedDescription = "extratrees.module.database.moth")
public class ModuleMothDatabase extends Module {

	public static Item itemDictionaryLepi;

	@Override
	public void registerItemsAndBlocks() {
		itemDictionaryLepi = new ItemMothDatabase();
		ExtraTrees.proxy.registerItem(itemDictionaryLepi);
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		ICarpenterManager carpenterManager = RecipeManagers.carpenterManager;

		carpenterManager.addRecipe(100, Binnie.LIQUID.getFluidStack(ManagerLiquid.WATER, 2000), ItemStack.EMPTY, new ItemStack(itemDictionaryLepi), "X#X", "YEY", "RDR", '#', Blocks.GLASS_PANE, 'X', Items.GOLD_INGOT, 'Y', "ingotBronze", 'R', Items.REDSTONE, 'D', Items.DIAMOND, 'E', Items.EMERALD);
	}

	@Override
	public boolean isAvailable() {
		return BinnieCore.isLepidopteryActive();
	}
}
