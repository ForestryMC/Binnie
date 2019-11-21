package binnie.extratrees.modules;

import binnie.core.Binnie;
import binnie.core.Constants;
import binnie.core.liquid.ManagerLiquid;
import binnie.core.modules.BlankModule;
import binnie.core.modules.ExtraTreesModuleUIDs;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.items.ItemMothDatabase;
import com.google.common.collect.ImmutableSet;
import forestry.api.modules.ForestryModule;
import forestry.api.recipes.ICarpenterManager;
import forestry.api.recipes.RecipeManagers;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Set;

@ForestryModule(moduleID = ExtraTreesModuleUIDs.MOTH_DATABASE, containerID = Constants.EXTRA_TREES_MOD_ID, name = "Database", unlocalizedDescription = "extratrees.module.database.moth")
public class ModuleMothDatabase extends BlankModule {

	public static Item itemDictionaryLepi;

	public ModuleMothDatabase() {
		super(Constants.EXTRA_TREES_MOD_ID, ExtraTreesModuleUIDs.CORE);
	}

	@Override
	public void registerItemsAndBlocks() {
		itemDictionaryLepi = new ItemMothDatabase();
		ExtraTrees.proxy.registerItem(itemDictionaryLepi);
		OreDictionary.registerOre("binnie_database", itemDictionaryLepi);
	}

	@Override
	public void doInit() {
		ICarpenterManager carpenterManager = RecipeManagers.carpenterManager;

		carpenterManager.addRecipe(100, Binnie.LIQUID.getFluidStack(ManagerLiquid.WATER, 2000), ItemStack.EMPTY, new ItemStack(itemDictionaryLepi), "X#X", "YEY", "RDR", '#', Blocks.GLASS_PANE, 'X', Items.GOLD_INGOT, 'Y', "ingotBronze", 'R', Items.REDSTONE, 'D', Items.DIAMOND, 'E', Items.EMERALD);
	}

	@Override
	public Set<ResourceLocation> getDependencyUids() {
		return ImmutableSet.of(new ResourceLocation(Constants.EXTRA_TREES_MOD_ID, ExtraTreesModuleUIDs.CORE), new ResourceLocation("forestry", "lepidopterology"));
	}
}
