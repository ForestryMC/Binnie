// 
// Decompiled by Procyon v0.5.30
// 

package binnie.genetics.item;

import binnie.genetics.core.GeneticsGUI;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;
import binnie.genetics.CreativeTabGenetics;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import binnie.genetics.Genetics;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;

public class ItemAnalyst extends Item
{
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(final IIconRegister register) {
		this.itemIcon = Genetics.proxy.getIcon(register, "analyst");
	}

	public ItemAnalyst() {
		this.setCreativeTab(CreativeTabGenetics.instance);
		this.setUnlocalizedName("analyst");
		this.setMaxStackSize(1);
	}

	@Override
	public ItemStack onItemRightClick(final ItemStack itemstack, final World world, final EntityPlayer player) {
		Genetics.proxy.openGui(GeneticsGUI.Analyst, player, (int) player.posX, (int) player.posY, (int) player.posZ);
		return itemstack;
	}

	@Override
	public String getItemStackDisplayName(final ItemStack i) {
		return "Analyst";
	}
}
