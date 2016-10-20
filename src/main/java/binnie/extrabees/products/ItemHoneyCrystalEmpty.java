package binnie.extrabees.products;

import binnie.extrabees.ExtraBees;
import net.minecraft.item.ItemStack;

public class ItemHoneyCrystalEmpty extends ItemHoneyCrystal {
    public ItemHoneyCrystalEmpty(String name) {
        super(name);
        this.setMaxDamage(0);
        this.setMaxStackSize(64);
        this.setUnlocalizedName("honeyCrystalEmpty");
    }

//	@SideOnly(Side.CLIENT)
//	@Override
//	public void registerIcons(final IIconRegister register) {
//		this.itemIcon = ExtraBees.proxy.getIcon(register, "honeyCrystalEmpty");
//	}

    @Override
    public String getItemStackDisplayName(final ItemStack i) {
        return ExtraBees.proxy.localise("item.honeycrystal.empty");
    }
}
