package binnie.extratrees.modules;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.oredict.OreDictionary;

import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import forestry.api.modules.ForestryModule;
import forestry.api.recipes.ICarpenterManager;
import forestry.api.recipes.RecipeManagers;

import binnie.core.Binnie;
import binnie.core.Constants;
import binnie.core.liquid.ManagerLiquid;
import binnie.core.modules.BinnieModule;
import binnie.core.modules.ExtraTreesModuleUIDs;
import binnie.extratrees.modules.features.ExtraTreesItems;

@ForestryModule(moduleID = ExtraTreesModuleUIDs.TREE_DATABASE, containerID = Constants.EXTRA_TREES_MOD_ID, name = "Database", unlocalizedDescription = "extratrees.module.database.tree")
public class ModuleTreeDatabase extends BinnieModule {

	public ModuleTreeDatabase() {
		super(Constants.EXTRA_TREES_MOD_ID, ExtraTreesModuleUIDs.CORE);
	}

	@SubscribeEvent(priority = EventPriority.LOW)
	public void onRegisterItem(RegistryEvent.Register<Item> event) {
		if (ExtraTreesItems.ARBORIST_DATABASE.hasItem()) {
			OreDictionary.registerOre("binnie_database", ExtraTreesItems.ARBORIST_DATABASE.getItem());
		}
	}

	@Override
	public void doInit() {
		ICarpenterManager carpenterManager = RecipeManagers.carpenterManager;
		carpenterManager.addRecipe(100, Binnie.LIQUID.getFluidStack(ManagerLiquid.WATER, 2000), ItemStack.EMPTY, ExtraTreesItems.ARBORIST_DATABASE.stack(),
			"X#X",
			"YEY",
			"RDR",
			'#', Blocks.GLASS_PANE,
			'X', Items.GOLD_INGOT,
			'Y', "ingotCopper",
			'R', Items.REDSTONE,
			'D', Items.DIAMOND,
			'E', Items.EMERALD);
	}
}
