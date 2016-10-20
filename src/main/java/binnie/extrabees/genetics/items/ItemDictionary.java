// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extrabees.genetics.items;

import binnie.extrabees.core.ExtraBeeGUID;
import net.minecraft.world.World;
import forestry.api.core.Tabs;
import net.minecraft.creativetab.CreativeTabs;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import binnie.extrabees.ExtraBees;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.item.Item;

public class ItemDictionary extends Item
{
	IIcon iconMaster;

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(final IIconRegister register) {
		this.itemIcon = ExtraBees.proxy.getIcon(register, "apiaristDatabase");
		this.iconMaster = ExtraBees.proxy.getIcon(register, "masterApiaristDatabase");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(final int par1) {
		return (par1 == 0) ? this.itemIcon : this.iconMaster;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(final ItemStack par1ItemStack, final EntityPlayer par2EntityPlayer, final List par3List, final boolean par4) {
		super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
		if (par1ItemStack.getItemDamage() > 0) {
			par3List.add("Flora-in-a-box");
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(final Item par1, final CreativeTabs par2CreativeTabs, final List par3List) {
		super.getSubItems(par1, par2CreativeTabs, par3List);
		par3List.add(new ItemStack(par1, 1, 1));
	}

	public ItemDictionary() {
		this.setCreativeTab(Tabs.tabApiculture);
		this.setUnlocalizedName("dictionary");
		this.setMaxStackSize(1);
	}

	@Override
	public ItemStack onItemRightClick(final ItemStack itemstack, final World world, final EntityPlayer player) {
		if (itemstack.getItemDamage() == 0) {
			ExtraBees.proxy.openGui(ExtraBeeGUID.Database, player, (int) player.posX, (int) player.posY, (int) player.posZ);
		}
		else {
			ExtraBees.proxy.openGui(ExtraBeeGUID.DatabaseNEI, player, (int) player.posX, (int) player.posY, (int) player.posZ);
		}
		return itemstack;
	}

	@Override
	public String getItemStackDisplayName(final ItemStack i) {
		return (i.getItemDamage() == 0) ? "Apiarist Database" : "Master Apiarist Database";
	}
}
