// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extratrees.item;

import binnie.extratrees.core.ExtraTreesGUID;
import net.minecraft.world.World;
import forestry.api.core.Tabs;
import net.minecraft.creativetab.CreativeTabs;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import binnie.extratrees.ExtraTrees;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.item.Item;

public class ItemMothDatabase extends Item
{
	IIcon iconMaster;

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(final IIconRegister register) {
		this.itemIcon = ExtraTrees.proxy.getIcon(register, "lepiDatabase");
		this.iconMaster = ExtraTrees.proxy.getIcon(register, "masterLepiDatabase");
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
			par3List.add("Binnie's Emporium of Lepidopterans");
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(final Item par1, final CreativeTabs par2CreativeTabs, final List par3List) {
		super.getSubItems(par1, par2CreativeTabs, par3List);
		par3List.add(new ItemStack(par1, 1, 1));
	}

	public ItemMothDatabase() {
		this.setCreativeTab(Tabs.tabLepidopterology);
		this.setUnlocalizedName("databaseMoth");
		this.setMaxStackSize(1);
	}

	@Override
	public ItemStack onItemRightClick(final ItemStack itemstack, final World world, final EntityPlayer player) {
		if (itemstack.getItemDamage() == 0) {
			ExtraTrees.proxy.openGui(ExtraTreesGUID.MothDatabase, player, (int) player.posX, (int) player.posY, (int) player.posZ);
		}
		else {
			ExtraTrees.proxy.openGui(ExtraTreesGUID.MothDatabaseNEI, player, (int) player.posX, (int) player.posY, (int) player.posZ);
		}
		return itemstack;
	}

	@Override
	public String getItemStackDisplayName(final ItemStack i) {
		return (i.getItemDamage() == 0) ? "Lepidopterist Database" : "Master Lepidopterist Database";
	}
}
