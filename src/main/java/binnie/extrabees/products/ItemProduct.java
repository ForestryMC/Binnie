package binnie.extrabees.products;

import binnie.core.item.IItemEnum;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemProduct extends Item {
	protected IItemEnum[] types;

	public ItemProduct(IItemEnum[] types) {
		setMaxStackSize(64);
		setMaxDamage(0);
		setHasSubtypes(true);
		this.types = types;
	}

	public IItemEnum get(ItemStack stack) {
		int i = stack.getItemDamage();
		if (i >= 0 && i < types.length) {
			return types[i];
		}
		return types[0];
	}

	@Override
	public String getItemStackDisplayName(ItemStack itemstack) {
		return get(itemstack).getName(itemstack);
	}

	@Override
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List itemList) {
		for (IItemEnum type : types) {
			if (type.isActive()) {
				itemList.add(new ItemStack(this, 1, type.ordinal()));
			}
		}
	}
}
