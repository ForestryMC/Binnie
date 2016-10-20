package binnie.botany.genetics;

import binnie.botany.CreativeTabBotany;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemEncyclopedia extends Item {
    boolean reinforced;

//	@Override
//	@SideOnly(Side.CLIENT)
//	public void registerIcons(final IIconRegister register) {
//		this.itemIcon = Botany.proxy.getIcon(register, "encylopedia" + (this.reinforced ? "Iron" : ""));
//	}
//
//	@Override
//	@SideOnly(Side.CLIENT)
//	public IIcon getIconFromDamage(final int par1) {
//		return this.itemIcon;
//	}

    public ItemEncyclopedia(final boolean reinforced) {
        this.reinforced = false;
        this.reinforced = reinforced;
        this.setCreativeTab(CreativeTabBotany.instance);
        this.setUnlocalizedName("encylopedia" + (reinforced ? "Iron" : ""));
        this.setMaxStackSize(1);
        this.setMaxDamage(reinforced ? 480 : 120);
        setRegistryName("encylopedia" + (reinforced ? "Iron" : ""));
    }

    @Override
    public String getItemStackDisplayName(final ItemStack i) {
        return (this.reinforced ? "Reinforced " : "") + "Florist's Encyclopaedia";
    }
}
