package binnie.extrabees.genetics.items;

import binnie.extrabees.ExtraBees;
import binnie.extrabees.core.ExtraBeeGUID;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

// TODO unused class?
public class ItemPunnettSquare extends Item {
	public ItemPunnettSquare() {
		setCreativeTab(CreativeTabs.tabTools);
		setMaxStackSize(1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister register) {
		itemIcon = ExtraBees.proxy.getIcon(register, "");
	}

	@Override
	public String getItemStackDisplayName(ItemStack itemstack) {
		return "Punnett Square";
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		ExtraBees.proxy.openGui(ExtraBeeGUID.PunnettSquare, player, (int) player.posX, (int) player.posY, (int) player.posZ);
		return stack;
	}
}
