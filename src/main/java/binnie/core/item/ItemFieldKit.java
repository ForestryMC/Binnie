package binnie.core.item;

import binnie.core.BinnieCore;
import binnie.core.gui.BinnieCoreGUI;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.List;

public class ItemFieldKit extends Item {
	private IIcon fieldKit0;
	private IIcon fieldKit1;
	private IIcon fieldKit2;
	private IIcon fieldKit3;

	public ItemFieldKit() {
		setUnlocalizedName("fieldKit");
		setCreativeTab(CreativeTabs.tabTools);
		setMaxStackSize(1);
		setMaxDamage(64);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister register) {
		fieldKit0 = BinnieCore.proxy.getIcon(register, "fieldKit");
		fieldKit1 = BinnieCore.proxy.getIcon(register, "fieldKit1");
		fieldKit2 = BinnieCore.proxy.getIcon(register, "fieldKit2");
		fieldKit3 = BinnieCore.proxy.getIcon(register, "fieldKit3");
		itemIcon = fieldKit0;
	}

	@Override
	public IIcon getIcon(ItemStack stack, int pass) {
		int damage = stack.getItemDamage();
		if (damage < 24) {
			return fieldKit3;
		}
		if (damage < 48) {
			return fieldKit2;
		}
		if (damage < 64) {
			return fieldKit1;
		}
		return fieldKit0;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
		if (!player.isSneaking()) {
			BinnieCore.proxy.openGui(BinnieCoreGUI.FieldKit, player, (int) player.posX, (int) player.posY, (int) player.posZ);
		}
		return itemStack;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemStack, EntityPlayer player, List tooltip, boolean advanced) {
		int i = getMaxDamage() - itemStack.getItemDamage();
		if (i == 0) {
			tooltip.add("No paper");
		} else {
			tooltip.add("" + i + " sheet" + ((i > 1) ? "s" : "") + " of paper");
		}
		super.addInformation(itemStack, player, tooltip, advanced);
	}

	@Override
	public String getItemStackDisplayName(ItemStack p_77653_1_) {
		return "Field Kit";
	}
}
