package binnie.genetics.recipes;

import binnie.genetics.item.GeneticsItems;
import forestry.core.utils.ItemStackUtil;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.OreIngredient;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class RegistryRecipe extends ShapedOreRecipe {
	public static RegistryRecipe create(Item registry, Item itemGenetics) {
		return new RegistryRecipe(registry, " d ", "dcd", " d ",
				'c', GeneticsItems.IntegratedCircuit.get(itemGenetics, 1),
				'd', "binnie_database");
	}

	private RegistryRecipe(Item result, Object... recipe) {
		super(null, result, recipe);
		rotateIngredients();
	}


	private void rotateIngredients() {
		int offset = 0;
		for (int i = 0; i < input.size(); i++) {
			Ingredient ingredient = input.get(i);
			if (ingredient instanceof OreIngredient) {
				RotatedOreIngredient rotatedOreIngredient = new RotatedOreIngredient("binnie_database", offset);
				input.set(i, rotatedOreIngredient);
				offset++;
			}
		}
	}

	@Override
	public boolean matches(InventoryCrafting inv, World world) {
		if (super.matches(inv, world)) {
			int distinctDatabaseCount = OreDictionary.getOres("binnie_database").size();
			distinctDatabaseCount = Math.min(distinctDatabaseCount, 4);

			NonNullList<ItemStack> databases = NonNullList.create();
			databases.add(inv.getStackInRowAndColumn(0, 1));
			databases.add(inv.getStackInRowAndColumn(1, 0));
			databases.add(inv.getStackInRowAndColumn(1, 2));
			databases.add(inv.getStackInRowAndColumn(2, 1));

			databases = ItemStackUtil.condenseStacks(databases);
			return databases.size() >= distinctDatabaseCount;
		}
		return false;
	}
}
