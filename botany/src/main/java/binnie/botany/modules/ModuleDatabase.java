package binnie.botany.modules;

import com.google.common.collect.ImmutableSet;

import java.util.Set;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import net.minecraftforge.oredict.OreDictionary;

import forestry.api.recipes.RecipeManagers;

import binnie.botany.Botany;
import binnie.botany.items.ItemDatabaseBotany;
import binnie.core.Binnie;
import binnie.core.Constants;
import binnie.core.liquid.ManagerLiquid;
import binnie.core.modules.BinnieModule;
import binnie.core.modules.BotanyModuleUIDs;
import binnie.core.modules.Module;

@BinnieModule(moduleID = BotanyModuleUIDs.DATABASE, moduleContainerID = Constants.BOTANY_MOD_ID, name = "Database", unlocalizedDescription = "botany.module.database")
public class ModuleDatabase extends Module {
	public static ItemDatabaseBotany database;

	@Override
	public void registerItemsAndBlocks() {
		database = new ItemDatabaseBotany();
		Botany.proxy.registerItem(database);
		OreDictionary.registerOre("binnie_database", database);
	}

	@Override
	public void init() {
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

	@Override
	public Set<String> getDependencyUids() {
		return ImmutableSet.of(BotanyModuleUIDs.FLOWERS);
	}
}
