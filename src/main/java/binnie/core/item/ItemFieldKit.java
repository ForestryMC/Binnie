// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.item;

import java.util.List;
import binnie.core.gui.BinnieCoreGUI;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import binnie.core.BinnieCore;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.IIcon;
import net.minecraft.item.Item;

public class ItemFieldKit extends Item
{
	private IIcon fieldKit0;
	private IIcon fieldKit1;
	private IIcon fieldKit2;
	private IIcon fieldKit3;

	public ItemFieldKit() {
		this.setUnlocalizedName("fieldKit");
		this.setCreativeTab(CreativeTabs.tabTools);
		this.setMaxStackSize(1);
		this.setMaxDamage(64);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(final IIconRegister register) {
		this.fieldKit0 = BinnieCore.proxy.getIcon(register, "fieldKit");
		this.fieldKit1 = BinnieCore.proxy.getIcon(register, "fieldKit1");
		this.fieldKit2 = BinnieCore.proxy.getIcon(register, "fieldKit2");
		this.fieldKit3 = BinnieCore.proxy.getIcon(register, "fieldKit3");
		this.itemIcon = this.fieldKit0;
	}

	@Override
	public IIcon getIcon(final ItemStack stack, final int pass) {
		final int damage = stack.getItemDamage();
		if (damage < 24) {
			return this.fieldKit3;
		}
		if (damage < 48) {
			return this.fieldKit2;
		}
		if (damage < 64) {
			return this.fieldKit1;
		}
		return this.fieldKit0;
	}

	@Override
	public ItemStack onItemRightClick(final ItemStack itemstack, final World world, final EntityPlayer player) {
		if (!player.isSneaking()) {
			BinnieCore.proxy.openGui(BinnieCoreGUI.FieldKit, player, (int) player.posX, (int) player.posY, (int) player.posZ);
		}
		return itemstack;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(final ItemStack p_77624_1_, final EntityPlayer p_77624_2_, final List p_77624_3_, final boolean p_77624_4_) {
		final int i = this.getMaxDamage() - p_77624_1_.getItemDamage();
		if (i == 0) {
			p_77624_3_.add("No paper");
		}
		else {
			p_77624_3_.add("" + i + " sheet" + ((i > 1) ? "s" : "") + " of paper");
		}
		super.addInformation(p_77624_1_, p_77624_2_, p_77624_3_, p_77624_4_);
	}

	@Override
	public String getItemStackDisplayName(final ItemStack p_77653_1_) {
		return "Field Kit";
	}
}
