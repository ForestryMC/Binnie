package binnie.extrabees.products;

import binnie.extrabees.ExtraBees;
import forestry.api.core.Tabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemHoneyCrystal extends Item {
	private int maxCharge;
	private int transferLimit;
	private int tier;

//	@Override
//	@SideOnly(Side.CLIENT)
//	public void registerIcons(final IIconRegister register) {
//		this.itemIcon = ExtraBees.proxy.getIcon(register, "honeyCrystal");
//	}

	public ItemHoneyCrystal(String name) {
		this.maxCharge = 8000;
		this.transferLimit = 500;
		this.tier = 1;
		this.setMaxDamage(27);
		this.setMaxStackSize(1);
		this.setCreativeTab(Tabs.tabApiculture);
		this.setUnlocalizedName("honey_crystal");
		setRegistryName(name);
		ExtraBees.proxy.registerModel(this, 0);
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
