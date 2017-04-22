package binnie.core.item;

import cpw.mods.fml.relauncher.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.util.*;

import java.util.*;

public class ItemMisc extends Item {
	private IItemMisc[] items;

	protected ItemMisc(CreativeTabs tab, IItemMisc[] items2) {
		setCreativeTab(tab);
		setHasSubtypes(true);
		setUnlocalizedName("misc");
		items = items2;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item par1, CreativeTabs tab, List list) {
		for (IItemMisc item : items) {
			if (item.isActive()) {
				list.add(getStack(item, 1));
			}
		}
	}

	private IItemMisc getItem(int damage) {
		if (damage >= items.length) {
			return items[0];
		}
		return items[damage];
	}

	public ItemStack getStack(IItemMisc type, int size) {
		return new ItemStack(this, size, type.ordinal());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemStack, EntityPlayer player, List tooltip, boolean advanced) {
		super.addInformation(itemStack, player, tooltip, advanced);
		IItemMisc item = getItem(itemStack.getItemDamage());
		if (item != null) {
			item.addInformation(tooltip);
		}
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		IItemMisc item = getItem(stack.getItemDamage());
		return (item != null) ? item.getName(stack) : "null";
	}

	@Override
	public IIcon getIcon(ItemStack stack, int pass) {
		IItemMisc item = getItem(stack.getItemDamage());
		return (item != null) ? item.getIcon(stack) : null;
	}

	@Override
	public IIcon getIconFromDamage(int damage) {
		IItemMisc item = getItem(damage);
		return (item != null) ? item.getIcon(null) : null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister register) {
		for (IItemMisc item : items) {
			item.registerIcons(register);
		}
	}
}
