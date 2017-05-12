package binnie.extrabees.init;

import binnie.extrabees.ExtraBees;
import binnie.extrabees.items.types.EnumHoneyComb;
import binnie.extrabees.items.types.EnumHoneyDrop;
import binnie.extrabees.items.types.EnumPropolis;
import binnie.extrabees.utils.Utils;
import forestry.api.recipes.RecipeManagers;
import forestry.apiculture.PluginApiculture;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by Elec332 on 12-5-2017.
 */
public final class RecipeRegister {

	public static void postInitRecipes(){
		GameRegistry.addRecipe(new ItemStack(ExtraBees.honeyCrystalEmpty), "#@#", "@#@", "#@#", '@', PluginApiculture.getItems().honeyDrop, '#', EnumHoneyDrop.ENERGY.get(1));
		for (final EnumHoneyComb info : EnumHoneyComb.values()) {
			info.addRecipe();
		}
		for (final EnumHoneyDrop info2 : EnumHoneyDrop.values()) {
			info2.addRecipe();
		}
		for (final EnumPropolis info3 : EnumPropolis.values()) {
			info3.addRecipe();
		}
		addForestryRecipes();
	}

	private static void addForestryRecipes(){
		RecipeManagers.carpenterManager.addRecipe(100, Utils.getFluidFromName("water", 2000), ItemStack.EMPTY, new ItemStack(ExtraBees.dictionary), "X#X", "YEY", "RDR", '#', Blocks.GLASS_PANE, 'X', Items.GOLD_INGOT, 'Y', "ingotTin", 'R', Items.REDSTONE, 'D', Items.DIAMOND, 'E', Items.EMERALD);
	}

}
