package binnie.extrabees.products;

import binnie.Binnie;
import binnie.core.item.IItemEnum;
import binnie.extrabees.ExtraBees;
import forestry.api.recipes.RecipeManagers;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public enum EnumHoneyDrop implements IItemEnum {
	ENERGY(0x9c4972, 0xe37171, ""),
	ACID(0x4bb541, 0x49de3c, "acid"),
	POISON(0xd106b9, 0xff03e2, "poison"),
	APPLE(0xc75252, 0xc92a2a, "juice"),
	CITRUS,
	ICE(0xaee8e2, 0x96fff5, "liquidnitrogen"),
	MILK(0xe0e0e0, 0xffffff, "milk"),
	SEED(0x7cc272, 0xc2bea7, "seedoil"),
	ALCOHOL(0xdbe84d, 0xa5e84d, "short.mead"),
	FRUIT,
	VEGETABLE,
	PUMPKIN,
	MELON,
	RED(0xcc4c4c, 0xff0000, "for.honey"),
	YELLOW(0xe5e533, 0xffdd00, "for.honey"),
	BLUE(0x99b2f2, 0x0022ff, "for.honey"),
	GREEN(0x667f33, 0x009900, "for.honey"),
	BLACK(0x191919, 0x575757, "for.honey"),
	WHITE(0xd6d6d6, 0xffffff, "for.honey"),
	BROWN(0x7f664c, 0x5c350f, "for.honey"),
	ORANGE(0xf2b233, 0xff9d00, "for.honey"),
	CYAN(0x4c99b2, 0x00ffe5, "for.honey"),
	PURPLE(0xb266e5, 0xae00ff, "for.honey"),
	GRAY(0x4c4c4c, 0xbababa, "for.honey"),
	LIGHTBLUE(0x99b2f2, 0x009dff, "for.honey"),
	PINK(0xf2b2cc, 0xff80df, "for.honey"),
	LIMEGREEN(0x7fcc19, 0x00ff08, "for.honey"),
	MAGENTA(0xe57fd8, 0xff00cc, "for.honey"),
	LIGHTGRAY(0x999999, 0xc9c9c9, "for.honey");

	public boolean deprecated;

	protected int[] color;
	protected String liquidName;
	protected ItemStack remenant;

	EnumHoneyDrop() {
		this(0xffffff, 0xffffff, "");
	}

	EnumHoneyDrop(int color, int color2, String liquid) {
		liquidName = "";
		remenant = null;
		deprecated = false;
		this.color = new int[]{color, color2};
		liquidName = liquid;
	}

	public static EnumHoneyDrop get(ItemStack itemStack) {
		int i = itemStack.getItemDamage();
		if (i >= 0 && i < values().length) {
			return values()[i];
		}
		return values()[0];
	}

	public void addRemenant(ItemStack stack) {
		remenant = stack;
		deprecated = true;
	}

	public void addRecipe() {
		FluidStack liquid = Binnie.Liquid.getLiquidStack(liquidName, 200);
		if (liquid != null) {
			RecipeManagers.squeezerManager.addRecipe(10, new ItemStack[]{get(1)}, liquid, remenant, 100);
		}
	}

	@Override
	public boolean isActive() {
		return !deprecated && (liquidName == null || FluidRegistry.isFluidRegistered(liquidName));
	}

	@Override
	public ItemStack get(int count) {
		return new ItemStack(ExtraBees.honeyDrop, count, ordinal());
	}

	@Override
	public String getName(ItemStack itemStack) {
		return ExtraBees.proxy.localise("item.honeydrop." + name().toLowerCase());
	}
}
