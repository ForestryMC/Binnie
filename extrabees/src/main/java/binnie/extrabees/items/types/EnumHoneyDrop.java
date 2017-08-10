package binnie.extrabees.items.types;

import net.minecraft.item.ItemStack;

import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import forestry.api.recipes.RecipeManagers;

import binnie.core.util.I18N;
import binnie.extrabees.ExtraBees;
import binnie.extrabees.utils.Utils;

public enum EnumHoneyDrop implements IEBEnumItem {

	ENERGY(10242418, 14905713, ""),
	ACID(4961601, 4841020, "acid"),
	POISON(13698745, 16712674, "poison"),
	APPLE(13062738, 13183530, "juice"),
	CITRUS,
	ICE(11462882, 9895925, "liquidnitrogen"),
	MILK(14737632, 16777215, "milk"),
	SEED(8176242, 12762791, "seedoil"),
	ALCOHOL(14411853, 10872909, "short.mead"),
	FRUIT,
	VEGETABLE,
	PUMPKIN,
	MELON,
	RED(13388876, 16711680, "for.honey"),
	YELLOW(15066419, 16768256, "for.honey"),
	BLUE(10072818, 8959, "for.honey"),
	GREEN(6717235, 39168, "for.honey"),
	BLACK(1644825, 5723991, "for.honey"),
	WHITE(14079702, 16777215, "for.honey"),
	BROWN(8349260, 6042895, "for.honey"),
	ORANGE(15905331, 16751872, "for.honey"),
	CYAN(5020082, 65509, "for.honey"),
	PURPLE(11691749, 11403519, "for.honey"),
	GRAY(5000268, 12237498, "for.honey"),
	LIGHTBLUE(10072818, 40447, "for.honey"),
	PINK(15905484, 16744671, "for.honey"),
	LIMEGREEN(8375321, 65288, "for.honey"),
	MAGENTA(15040472, 16711884, "for.honey"),
	LIGHTGRAY(10066329, 13224393, "for.honey");

	private int[] colour;
	private String liquidName;
	private ItemStack remnant;

	EnumHoneyDrop() {
		this(16777215, 16777215, "");
	}

	EnumHoneyDrop(final int colour, final int colour2, final String liquid) {
		this.remnant = ItemStack.EMPTY;
		this.colour = new int[]{colour, colour2};
		this.liquidName = liquid;
	}

	public static EnumHoneyDrop get(final ItemStack itemStack) {
		final int i = itemStack.getItemDamage();
		if (i >= 0 && i < values().length) {
			return values()[i];
		}
		return values()[0];
	}

	public void addRemnant(final ItemStack stack) {
		this.remnant = stack;
	}

	public void addRecipe() {
		final FluidStack liquid = Utils.getFluidFromName(this.getLiquidName(), 200);
		if (liquid != null) {
			RecipeManagers.squeezerManager.addRecipe(10, this.get(1), liquid, this.getRemnant(), 100);
		}
	}

	@Override
	public boolean isActive() {
		return !this.remnant.isEmpty() && FluidRegistry.isFluidRegistered(this.getLiquidName());
	}

	@Override
	public ItemStack get(final int size) {
		return new ItemStack(ExtraBees.honeyDrop, size, this.ordinal());
	}

	@Override
	public String getName(final ItemStack stack) {
		return I18N.localise("extrabees.item.honeydrop." + this.name().toLowerCase());
	}

	public int[] getColour() {
		return colour;
	}

	public String getLiquidName() {
		return liquidName;
	}

	public ItemStack getRemnant() {
		return remnant;
	}

}
