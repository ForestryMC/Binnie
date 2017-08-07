package binnie.botany.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.core.IItemModelRegister;
import forestry.api.core.IModelManager;
import forestry.core.items.IColoredItem;

import binnie.botany.CreativeTabBotany;
import binnie.botany.genetics.EnumFlowerColor;
import binnie.core.util.I18N;

public class ItemPigment extends Item implements IItemModelRegister, IColoredItem {
	public ItemPigment() {
		setUnlocalizedName("botany.pigment");
		setHasSubtypes(true);
		setCreativeTab(CreativeTabBotany.instance);
		setRegistryName("pigment");
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModel(Item item, IModelManager manager) {
		for (EnumFlowerColor color : EnumFlowerColor.values()) {
			manager.registerItemModel(item, color.ordinal());
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getColorFromItemstack(ItemStack stack, int tintIndex) {
		EnumFlowerColor color = EnumFlowerColor.get(stack.getItemDamage());
		return color.getFlowerColorAllele().getColor(false);
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		EnumFlowerColor color = EnumFlowerColor.get(stack.getItemDamage());
		return I18N.localise("item.botany.pigment.name", color.getFlowerColorAllele().getColorName());
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (this.isInCreativeTab(tab)) {
			for (EnumFlowerColor color : EnumFlowerColor.values()) {
				items.add(new ItemStack(this, 1, color.ordinal()));
			}
		}
	}
}
