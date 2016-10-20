// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemMisc extends Item
{
	private IItemMisc[] items;

	protected ItemMisc(final CreativeTabs tab, final IItemMisc[] items2) {
		this.setCreativeTab(tab);
		this.setHasSubtypes(true);
		this.setUnlocalizedName("misc");
		this.items = items2;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(final Item par1, final CreativeTabs par2CreativeTabs, final List par3List) {
		for (final IItemMisc item : this.items) {
			if (item.isActive()) {
				par3List.add(this.getStack(item, 1));
			}
		}
	}

	private IItemMisc getItem(final int damage) {
		return (damage >= this.items.length) ? this.items[0] : this.items[damage];
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
		final IItemMisc item = this.getItem(stack.getItemDamage());
		return (item != null) ? item.getName(stack) : "null";
	}

	@Override
	public IIcon getIcon(final ItemStack stack, final int pass) {
		final IItemMisc item = this.getItem(stack.getItemDamage());
		return (item != null) ? item.getIcon(stack) : null;
	}

	@Override
	public IIcon getIconFromDamage(final int damage) {
		final IItemMisc item = this.getItem(damage);
		return (item != null) ? item.getIcon(null) : null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(final IIconRegister register) {
		for (final IItemMisc item : this.items) {
			item.registerIcons(register);
		}
	}
}
