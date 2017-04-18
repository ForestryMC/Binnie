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

	protected ItemMisc(final CreativeTabs tab, final IItemMisc[] items2) {
		setCreativeTab(tab);
		setHasSubtypes(true);
		setUnlocalizedName("misc");
		items = items2;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(final Item par1, final CreativeTabs tab, final List list) {
		for (final IItemMisc item : items) {
			if (item.isActive()) {
				list.add(getStack(item, 1));
			}
		}
	}

	private IItemMisc getItem(final int damage) {
		if (damage >= items.length) {
			return items[0];
		}
		return items[damage];
	}

	public ItemStack getStack(final IItemMisc type, final int size) {
		return new ItemStack(this, size, type.ordinal());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(final ItemStack par1ItemStack, final EntityPlayer par2EntityPlayer, final List par3List, final boolean par4) {
		super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
		final IItemMisc item = this.getItem(par1ItemStack.getItemDamage());
		if (item != null) {
			item.addInformation(par3List);
		}
	}

	@Override
	public String getItemStackDisplayName(final ItemStack stack) {
		final IItemMisc item = getItem(stack.getItemDamage());
		return (item != null) ? item.getName(stack) : "null";
	}

	@Override
	public IIcon getIcon(final ItemStack stack, final int pass) {
		final IItemMisc item = getItem(stack.getItemDamage());
		return (item != null) ? item.getIcon(stack) : null;
	}

	@Override
	public IIcon getIconFromDamage(final int damage) {
		final IItemMisc item = getItem(damage);
		return (item != null) ? item.getIcon(null) : null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(final IIconRegister register) {
		for (final IItemMisc item : items) {
			item.registerIcons(register);
		}
	}
}
