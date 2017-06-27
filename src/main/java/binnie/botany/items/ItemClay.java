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

public class ItemClay extends Item implements IColoredItem, IItemModelRegister {
	public ItemClay() {
		this.setUnlocalizedName("botany.clay");
		this.setHasSubtypes(true);
		this.setCreativeTab(CreativeTabBotany.instance);
		this.setRegistryName("clay");
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
	public String getItemStackDisplayName(final ItemStack stack) {
		EnumFlowerColor color = EnumFlowerColor.get(stack.getItemDamage());
		return I18N.localise("item.botany.clay.name", color.getFlowerColorAllele().getColorName());
		//return EnumFlowerColor.get(stack.getItemDamage()).getFlowerColorAllele().getColorName() + " " + super.getItemStackDisplayName(stack);
}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(final Item itemIn, final CreativeTabs tab, final NonNullList<ItemStack> list) {
		for (EnumFlowerColor color : EnumFlowerColor.values()) {
			list.add(new ItemStack(this, 1, color.ordinal()));
		}
	}
}
