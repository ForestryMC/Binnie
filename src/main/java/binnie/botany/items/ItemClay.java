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

public class ItemClay extends Item implements IColoredItem, IItemModelRegister {
	public ItemClay() {
		this.setUnlocalizedName("clay");
		this.setHasSubtypes(true);
		this.setCreativeTab(CreativeTabBotany.instance);
		setRegistryName("clay");
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModel(Item item, IModelManager manager) {
		for (EnumFlowerColor c : EnumFlowerColor.values()) {
			manager.registerItemModel(item, c.ordinal());
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getColorFromItemstack(ItemStack stack, int tintIndex) {
		return EnumFlowerColor.get(stack.getMetadata()).getFlowerColorAllele().getColor(false);
	}

	@Override
	public String getItemStackDisplayName(final ItemStack stack) {
		return EnumFlowerColor.get(stack.getItemDamage()).getFlowerColorAllele().getColourName() + " " + super.getItemStackDisplayName(stack);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(final Item itemIn, final CreativeTabs tab, final NonNullList<ItemStack> list) {
		for (final EnumFlowerColor c : EnumFlowerColor.values()) {
			list.add(new ItemStack(this, 1, c.ordinal()));
		}
	}
}
