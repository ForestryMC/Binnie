package binnie.extrabees.items.types;

import java.util.List;

import net.minecraft.item.ItemStack;

import net.minecraftforge.fluids.FluidStack;

import forestry.api.recipes.RecipeManagers;

import binnie.core.item.IItemSubtypeMisc;
import binnie.core.util.I18N;
import binnie.extrabees.modules.ModuleCore;
import binnie.extrabees.utils.Utils;

public enum EnumPropolis implements IItemSubtypeMisc {
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

	private final int primaryColor;
	private final int secondaryColor;
	private final String liquidName;
	private boolean active;

	EnumPropolis() {
		this(16777215, 16777215, "");
		this.active = false;
	}

	EnumPropolis(int primaryColor, int secondaryColor, String liquidName) {
		this.active = true;
		this.primaryColor = primaryColor;
		this.secondaryColor = secondaryColor;
		this.liquidName = liquidName;
	}

	public static EnumPropolis get(ItemStack itemStack) {
		int i = itemStack.getItemDamage();
		if (i >= 0 && i < values().length) {
			return values()[i];
		}
		return values()[0];
	}

	public void addRecipe() {
		FluidStack liquid = Utils.getFluidFromName(this.liquidName, 500);
		if (liquid != null) {
			RecipeManagers.squeezerManager.addRecipe(20, this.get(1), liquid, ItemStack.EMPTY, 0);
		}
	}

	public int getColor(int renderPass) {
		if (renderPass == 0) {
			return primaryColor;
		}
		if (renderPass == 1) {
			return secondaryColor;
		}
		return 0xffffff;
	}

	@Override
	public boolean isActive() {
		return this.active && Utils.getFluidFromName(this.liquidName, 100) != null;
	}

	@Override
	public ItemStack get(int amount) {
		return new ItemStack(ModuleCore.propolis, amount, this.ordinal());
	}

	@Override
	public String getDisplayName(ItemStack itemStack) {
		return I18N.localise("extrabees.item.propolis." + this.name().toLowerCase());
	}


	@Override
	public void addInformation(List<String> tooltip) {
	}

	@Override
	public String getModelPath() {
		return "propolis";
	}
}
