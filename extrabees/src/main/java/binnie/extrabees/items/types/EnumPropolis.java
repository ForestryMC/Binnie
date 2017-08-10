package binnie.extrabees.items.types;

import net.minecraft.item.ItemStack;

import net.minecraftforge.fluids.FluidStack;

import forestry.api.recipes.RecipeManagers;

import binnie.core.util.I18N;
import binnie.extrabees.ExtraBees;
import binnie.extrabees.utils.Utils;

public enum EnumPropolis implements IEBEnumItem {
	WATER(2405321, 12762791, "Water"),
	OIL(1519411, 12762791, "oil"),
	FUEL(10718482, 12762791, "fuel"),
	MILK,
	FRUIT,
	SEED,
	ALCOHOL,
	CREOSOTE(8877313, 12428819, "creosote"),
	GLACIAL,
	PEAT;

	public int[] colour;
	public String liquidName;
	public boolean active;

	EnumPropolis() {
		this(16777215, 16777215, "");
		this.active = false;
	}

	EnumPropolis(final int colour, final int colour2, final String liquid) {
		this.colour = new int[0];
		this.active = true;
		this.colour = new int[]{colour, colour2};
		this.liquidName = liquid;
	}

	public static EnumPropolis get(final ItemStack itemStack) {
		final int i = itemStack.getItemDamage();
		if (i >= 0 && i < values().length) {
			return values()[i];
		}
		return values()[0];
	}

	public void addRecipe() {
		final FluidStack liquid = Utils.getFluidFromName(this.liquidName, 500);
		if (liquid != null) {
			RecipeManagers.squeezerManager.addRecipe(20, this.get(1), liquid, ItemStack.EMPTY, 0);
		}
	}

	@Override
	public boolean isActive() {
		return this.active && Utils.getFluidFromName(this.liquidName, 100) != null;
	}

	@Override
	public ItemStack get(final int size) {
		return new ItemStack(ExtraBees.propolis, size, this.ordinal());
	}

	@Override
	public String getName(final ItemStack stack) {
		return I18N.localise("extrabees.item.propolis." + this.name().toLowerCase());
	}

}
