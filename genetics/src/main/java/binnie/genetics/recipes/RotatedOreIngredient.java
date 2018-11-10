package binnie.genetics.recipes;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;

import net.minecraft.client.util.RecipeItemHelper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;

import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.OreIngredient;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntComparators;
import it.unimi.dsi.fastutil.ints.IntList;

/**
 * Copy of {@link OreIngredient} to make the ingredients rotate in JEI properly,
 * instead of showing 4 of the same dictionary together.
 */
class RotatedOreIngredient extends Ingredient {
	private final NonNullList<ItemStack> ores;
	private final int offset;
	@Nullable
	private IntList itemIds;
	@Nullable
	private ItemStack[] array;

	public RotatedOreIngredient(String ore, int offset) {
		super(0);
		this.ores = OreDictionary.getOres(ore);
		this.offset = offset;
	}

	@Override
	@Nonnull
	public ItemStack[] getMatchingStacks() {
		if (array == null || this.array.length != ores.size()) {
			NonNullList<ItemStack> lst = NonNullList.create();
			for (ItemStack itemstack : this.ores) {
				if (itemstack.getMetadata() == OreDictionary.WILDCARD_VALUE) {
					itemstack.getItem().getSubItems(CreativeTabs.SEARCH, lst);
				} else {
					lst.add(itemstack);
				}
			}
			Collections.rotate(lst, offset);
			this.array = lst.toArray(new ItemStack[0]);
		}
		return this.array;
	}


	@Override
	@Nonnull
	@SideOnly(Side.CLIENT)
	public IntList getValidItemStacksPacked() {
		if (this.itemIds == null || this.itemIds.size() != ores.size()) {
			this.itemIds = new IntArrayList(this.ores.size());

			for (ItemStack itemstack : this.ores) {
				if (itemstack.getMetadata() == OreDictionary.WILDCARD_VALUE) {
					NonNullList<ItemStack> lst = NonNullList.create();
					itemstack.getItem().getSubItems(CreativeTabs.SEARCH, lst);
					for (ItemStack item : lst) {
						this.itemIds.add(RecipeItemHelper.pack(item));
					}
				} else {
					this.itemIds.add(RecipeItemHelper.pack(itemstack));
				}
			}

			this.itemIds.sort(IntComparators.NATURAL_COMPARATOR);
		}

		return this.itemIds;
	}


	@Override
	public boolean apply(@Nullable ItemStack input) {
		if (input == null) {
			return false;
		}

		for (ItemStack target : this.ores) {
			if (OreDictionary.itemMatches(target, input, false)) {
				return true;
			}
		}

		return false;
	}

	@Override
	protected void invalidate() {
		this.itemIds = null;
	}
}
