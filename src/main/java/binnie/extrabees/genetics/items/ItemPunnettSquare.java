// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extrabees.genetics.items;

import binnie.extrabees.core.ExtraBeeGUID;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;
import net.minecraft.creativetab.CreativeTabs;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import binnie.extrabees.ExtraBees;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;

public class ItemPunnettSquare extends Item
{
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(final IIconRegister register) {
		this.itemIcon = ExtraBees.proxy.getIcon(register, "");
	}

	public ItemPunnettSquare() {
		this.setCreativeTab(CreativeTabs.tabTools);
		this.setMaxStackSize(1);
	}

	@Override
	public String getItemStackDisplayName(final ItemStack itemstack) {
		return "Punnett Square";
	}

	@Override
	public ItemStack onItemRightClick(final ItemStack itemstack, final World world, final EntityPlayer player) {
		ExtraBees.proxy.openGui(ExtraBeeGUID.PunnettSquare, player, (int) player.posX, (int) player.posY, (int) player.posZ);
		return itemstack;
	}
}
