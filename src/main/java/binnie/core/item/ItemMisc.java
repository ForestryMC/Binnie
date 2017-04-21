package binnie.core.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;

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
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
		IItemMisc item = this.getItem(par1ItemStack.getItemDamage());
		if (item != null) {
			item.addInformation(par3List);
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
