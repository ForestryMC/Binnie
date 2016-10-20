// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extrabees.products;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.item.ItemStack;
import forestry.api.core.Tabs;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import binnie.extrabees.ExtraBees;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;

public class ItemHoneyCrystal extends Item
{
	private int maxCharge;
	private int transferLimit;
	private int tier;

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(final IIconRegister register) {
		this.itemIcon = ExtraBees.proxy.getIcon(register, "honeyCrystal");
	}

	public ItemHoneyCrystal() {
		this.maxCharge = 8000;
		this.transferLimit = 500;
		this.tier = 1;
		this.setMaxDamage(27);
		this.setMaxStackSize(1);
		this.setCreativeTab(Tabs.tabApiculture);
		this.setUnlocalizedName("honeyCrystal");
	}

	@Override
	public String getItemStackDisplayName(final ItemStack i) {
		return ExtraBees.proxy.localise("item.honeycrystal");
	}

	public static NBTTagCompound getOrCreateNbtData(final ItemStack itemStack) {
		NBTTagCompound ret = itemStack.getTagCompound();
		if (ret == null) {
			ret = new NBTTagCompound();
			itemStack.setTagCompound(ret);
		}
		return ret;
	}
}
