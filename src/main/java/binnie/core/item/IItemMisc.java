package binnie.core.item;

import java.util.List;

public interface IItemMisc extends IItemEnum {
//	IIcon getIcon(final ItemStack p0);
//
//	@SideOnly(Side.CLIENT)
//	void registerIcons(final IIconRegister p0);

    void addInformation(final List p0);
}
