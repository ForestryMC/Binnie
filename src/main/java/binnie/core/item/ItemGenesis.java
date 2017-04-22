package binnie.core.item;

import binnie.core.*;
import binnie.core.gui.*;
import cpw.mods.fml.relauncher.*;
import forestry.api.core.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.world.*;

public class ItemGenesis extends Item {
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister register) {
		itemIcon = BinnieCore.proxy.getIcon(register, "genesis");
	}

	public ItemGenesis() {
		setCreativeTab(Tabs.tabApiculture);
		setUnlocalizedName("genesis");
		setMaxStackSize(1);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
		BinnieCore.proxy.openGui(BinnieCoreGUI.Genesis, player, (int) player.posX, (int) player.posY, (int) player.posZ);
		return itemStack;
	}

	@Override
	public String getItemStackDisplayName(ItemStack itemStack) {
		return "Genesis";
	}
}
