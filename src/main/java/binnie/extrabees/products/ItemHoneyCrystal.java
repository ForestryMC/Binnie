package binnie.extrabees.products;

import binnie.core.util.I18N;
import binnie.extrabees.ExtraBees;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.core.Tabs;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemHoneyCrystal extends Item {
	private int maxCharge;
	private int transferLimit;
	private int tier;

	public ItemHoneyCrystal() {
		maxCharge = 8000;
		transferLimit = 500;
		tier = 1;
		setMaxDamage(27);
		setMaxStackSize(1);
		setCreativeTab(Tabs.tabApiculture);
		setUnlocalizedName("honeyCrystal");
	}

	public static NBTTagCompound getOrCreateNbtData(ItemStack itemStack) {
		NBTTagCompound ret = itemStack.getTagCompound();
		if (ret == null) {
			ret = new NBTTagCompound();
			itemStack.setTagCompound(ret);
		}
		return ret;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister register) {
		itemIcon = ExtraBees.proxy.getIcon(register, "honeyCrystal");
	}

	@Override
	public String getItemStackDisplayName(ItemStack i) {
		return I18N.localise("extrabees.item.honeycrystal");
	}
}
