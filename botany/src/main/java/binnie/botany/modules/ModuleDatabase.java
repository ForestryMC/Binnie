package binnie.botany.modules;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import net.minecraftforge.oredict.OreDictionary;

import forestry.api.modules.ForestryModule;
import forestry.api.recipes.RecipeManagers;

import binnie.botany.Botany;
import binnie.botany.items.ItemDatabaseBotany;
import binnie.core.Binnie;
import binnie.core.Constants;
import binnie.core.liquid.ManagerLiquid;
import binnie.core.modules.BinnieModule;
import binnie.core.modules.BotanyModuleUIDs;

@ForestryModule(moduleID = BotanyModuleUIDs.DATABASE, containerID = Constants.BOTANY_MOD_ID, name = "Database", unlocalizedDescription = "botany.module.database")
public class ModuleDatabase extends BinnieModule {
	public static ItemDatabaseBotany database;

	public ModuleDatabase() {
		super(Constants.BOTANY_MOD_ID, BotanyModuleUIDs.FLOWERS);
	}

	@Override
	public void registerItemsAndBlocks() {
		database = new ItemDatabaseBotany();
		Botany.proxy.registerItem(database);
		OreDictionary.registerOre("binnie_database", database);
	}

	@Override
	public void registerRecipes() {
		RecipeManagers.carpenterManager.addRecipe(
			100,
			Binnie.LIQUID.getFluidStack(ManagerLiquid.WATER, 2000),
			ItemStack.EMPTY,
			new ItemStack(database),
			"X#X",
			"YEY",
			"RDR",
			'#', Blocks.GLASS_PANE,
			'X', Items.GOLD_INGOT,
			'Y', Items.GOLD_NUGGET,
			'R', Items.REDSTONE,
			'D', Items.DIAMOND,
			'E', Items.EMERALD
		);
	}
}
