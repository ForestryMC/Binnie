package binnie.extrabees.items.types;

import java.util.List;

import net.minecraft.item.ItemStack;

import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import forestry.api.recipes.RecipeManagers;

import binnie.core.item.IItemSubtypeMisc;
import binnie.core.util.I18N;
import binnie.extrabees.modules.ModuleCore;
import binnie.extrabees.utils.Utils;

public enum EnumHoneyDrop implements IItemSubtypeMisc {

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

	private final int primaryColor;
	private final int secondaryColor;
	private final String liquidName;
	private ItemStack remnant;
	private boolean isActive = true;

	EnumHoneyDrop() {
		this(16777215, 16777215, "");
		isActive = false;
	}

	EnumHoneyDrop(int primaryColor, int secondaryColor, String liquidName) {
		this.remnant = ItemStack.EMPTY;
		this.primaryColor = primaryColor;
		this.secondaryColor = secondaryColor;
		this.liquidName = liquidName;
	}

	public static EnumHoneyDrop get(ItemStack itemStack) {
		int i = itemStack.getItemDamage();
		if (i >= 0 && i < values().length) {
			return values()[i];
		}
		return values()[0];
	}

	public void addRemnant(ItemStack stack) {
		this.remnant = stack;
	}

	public void addRecipe() {
		FluidStack liquid = Utils.getFluidFromName(this.getLiquidName(), 200);
		if (liquid != null) {
			RecipeManagers.squeezerManager.addRecipe(10, this.stack(1), liquid, this.getRemnant(), 100);
		}
	}

	@Override
	public boolean isActive() {
		return FluidRegistry.isFluidRegistered(this.getLiquidName()) || isActive;
	}

	@Override
	public ItemStack stack(int amount) {
		return new ItemStack(ModuleCore.honeyDrop, amount, this.ordinal());
	}

	@Override
	public String getDisplayName(ItemStack itemStack) {
		return I18N.localise("extrabees.item.honeydrop." + this.name().toLowerCase());
	}

	@Override
	public void addInformation(List<String> tooltip) {
	}

	@Override
	public String getModelPath() {
		return "honey_drop";
	}


	public int getSpriteColour(int renderPass) {
		if (renderPass == 0) {
			return primaryColor;
		}
		if (renderPass == 1) {
			return secondaryColor;
		}
		return 0xffffff;
	}

	public String getLiquidName() {
		return liquidName;
	}

	public ItemStack getRemnant() {
		return remnant;
	}

}
