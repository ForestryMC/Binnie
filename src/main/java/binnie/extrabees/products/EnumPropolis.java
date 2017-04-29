package binnie.extrabees.products;

import binnie.Binnie;
import binnie.core.item.IItemEnum;
import binnie.extrabees.ExtraBees;
import forestry.api.recipes.RecipeManagers;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public enum EnumPropolis implements IItemEnum {
	WATER(0x24b3c9, 0xc2bea7, "Water"),
	OIL(0x172f33, 0xc2bea7, "oil"),
	FUEL(0xa38d12, 0xc2bea7, "fuel"),
	MILK,
	FRUIT,
	SEED,
	ALCOHOL,
	CREOSOTE(0x877501, 0xbda613, "creosote"),
	GLACIAL,
	PEAT;

	int[] colour;
	String liquidName;
	boolean active;

	EnumPropolis() {
		this(0xffffff, 0xffffff, "");
		active = false;
	}

	EnumPropolis(int colour, int colour2, String liquid) {
		this.colour = new int[0];
		active = true;
		this.colour = new int[]{colour, colour2};
		liquidName = liquid;
	}

	public static EnumPropolis get(ItemStack itemStack) {
		int i = itemStack.getItemDamage();
		if (i >= 0 && i < values().length) {
			return values()[i];
		}
		return values()[0];
	}

	public void addRecipe() {
		FluidStack liquid = Binnie.Liquid.getLiquidStack(liquidName, 500);
		if (liquid != null) {
			RecipeManagers.squeezerManager.addRecipe(20, new ItemStack[]{get(1)}, liquid, null, 0);
		}
	}

	@Override
	public boolean isActive() {
		return active && Binnie.Liquid.getLiquidStack(liquidName, 100) != null;
	}

	@Override
	public ItemStack get(int count) {
		return new ItemStack(ExtraBees.propolis, count, ordinal());
	}

	@Override
	public String getName(ItemStack itemStack) {
		return ExtraBees.proxy.localise("item.propolis." + name().toLowerCase());
	}
}
