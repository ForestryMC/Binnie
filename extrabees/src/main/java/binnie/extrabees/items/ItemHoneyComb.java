package binnie.extrabees.items;

import net.minecraft.item.ItemStack;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.core.Tabs;
import forestry.apiculture.PluginApiculture;
import forestry.core.items.IColoredItem;

import binnie.extrabees.ExtraBees;
import binnie.extrabees.items.types.EnumHoneyComb;

public class ItemHoneyComb extends ItemProduct implements IColoredItem {

	public ItemHoneyComb() {
		super(EnumHoneyComb.values());
		this.setCreativeTab(Tabs.tabApiculture);
		this.setUnlocalizedName("honey_comb");
		setRegistryName("honey_comb");
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getColorFromItemstack(ItemStack stack, int tintIndex) {
		EnumHoneyComb honeyComb = EnumHoneyComb.get(stack);
		if (tintIndex == 1) {
			return honeyComb.colour[0];
		}
		return honeyComb.colour[1];
	}

	public static boolean isInvalidComb(ItemStack stack){
		if(stack.isEmpty()){
			return true;
		}
		if(stack.getItem() != ExtraBees.comb){
			return false;
		}
		EnumHoneyComb honeyComb = EnumHoneyComb.get(stack);
		return !honeyComb.isActive();
	}

	public enum VanillaComb {
		HONEY,
		COCOA,
		SIMMERING,
		STRINGY,
		FROZEN,
		DRIPPING,
		SILKY,
		PARCHED,
		MYSTERIOUS,
		IRRADIATED,
		POWDERY,
		REDDENED,
		DARKENED,
		OMEGA,
		WHEATEN,
		MOSSY,
		QUARTZ;

		public ItemStack get() {
			return new ItemStack(PluginApiculture.getItems().beeComb, 1, this.ordinal());
		}

	}
}
