package binnie.core.item;

import binnie.core.BinnieCore;
import binnie.core.gui.BinnieCoreGUI;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.core.Tabs;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemGenesis extends Item {
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(final IIconRegister register) {
		itemIcon = BinnieCore.proxy.getIcon(register, "genesis");
	}

	public ItemGenesis() {
		setCreativeTab(Tabs.tabApiculture);
		setUnlocalizedName("genesis");
		setMaxStackSize(1);
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
