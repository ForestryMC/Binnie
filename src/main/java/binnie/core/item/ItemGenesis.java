// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.item;

import binnie.core.gui.BinnieCoreGUI;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;
import forestry.api.core.Tabs;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import binnie.core.BinnieCore;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;

public class ItemGenesis extends Item
{
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(final IIconRegister register) {
		this.itemIcon = BinnieCore.proxy.getIcon(register, "genesis");
	}

	public ItemGenesis() {
		this.setCreativeTab(Tabs.tabApiculture);
		this.setUnlocalizedName("genesis");
		this.setMaxStackSize(1);
	}

	@Override
	public ItemStack onItemRightClick(final ItemStack itemstack, final World world, final EntityPlayer player) {
		BinnieCore.proxy.openGui(BinnieCoreGUI.Genesis, player, (int) player.posX, (int) player.posY, (int) player.posZ);
		return itemstack;
	}

	@Override
	public String getItemStackDisplayName(final ItemStack i) {
		return "Genesis";
	}
}
