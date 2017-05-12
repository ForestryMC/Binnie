package binnie.extrabees.items;

import binnie.extrabees.ExtraBees;
import net.minecraft.item.ItemStack;

public class ItemHoneyCrystalEmpty extends ItemHoneyCrystal {

	public ItemHoneyCrystalEmpty(String name) {
		super(name);
		this.setMaxDamage(0);
		this.setMaxStackSize(64);
		this.setUnlocalizedName("honey_crystal_empty");
	}

	@Override
	public String getItemStackDisplayName(final ItemStack i) {
		return ExtraBees.proxy.localise("item.honeycrystal.empty");
	}

}
