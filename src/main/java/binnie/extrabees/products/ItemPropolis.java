package binnie.extrabees.products;

import forestry.api.core.Tabs;

public class ItemPropolis extends ItemProduct {
    public ItemPropolis() {
        super(EnumPropolis.values());
        this.setCreativeTab(Tabs.tabApiculture);
        this.setUnlocalizedName("propolis");
        setRegistryName("propolis");
    }

//	@Override
//	public int getColorFromItemStack(final ItemStack itemStack, final int j) {
//		final int i = itemStack.getItemDamage();
//		if (j == 0) {
//			return EnumPropolis.get(itemStack).colour[0];
//		}
//		return EnumPropolis.get(itemStack).colour[1];
//	}

//	@Override
//	@SideOnly(Side.CLIENT)
//	public void registerIcons(final IIconRegister register) {
//		this.itemIcon = BinnieCore.proxy.getIcon(register, "forestry", "propolis.0");
//	}
}
