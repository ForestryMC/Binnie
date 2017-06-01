package binnie.extrabees.items;

import binnie.extrabees.ExtraBees;
import forestry.api.core.Tabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemHoneyCrystal extends Item {

	//TODO: Usage??
	private int maxCharge;
	private int transferLimit;
	private int tier;

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
}
