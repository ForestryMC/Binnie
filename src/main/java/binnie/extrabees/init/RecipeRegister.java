package binnie.extrabees.init;

import binnie.core.Mods;
import binnie.extrabees.ExtraBees;
import binnie.extrabees.products.EnumHoneyComb;
import binnie.extrabees.products.EnumHoneyDrop;
import binnie.extrabees.products.EnumPropolis;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by Elec332 on 12-5-2017.
 */
public final class RecipeRegister {

	public static void postInitRecipes(){
		GameRegistry.addRecipe(new ItemStack(ExtraBees.honeyCrystalEmpty), "#@#", "@#@", "#@#", '@', Mods.Forestry.stack("honey_drop"), '#', EnumHoneyDrop.ENERGY.get(1));
		for (final EnumHoneyComb info : EnumHoneyComb.values()) {
			info.addRecipe();
		}
		for (final EnumHoneyDrop info2 : EnumHoneyDrop.values()) {
			info2.addRecipe();
		}
		for (final EnumPropolis info3 : EnumPropolis.values()) {
			info3.addRecipe();
		}
	}

}
