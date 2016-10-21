package binnie.botany.items;

import binnie.botany.CreativeTabBotany;
import binnie.botany.genetics.EnumFlowerColor;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemClay extends Item {
    public ItemClay() {
        this.setUnlocalizedName("clay");
        this.setHasSubtypes(true);
        this.setCreativeTab(CreativeTabBotany.instance);
        setRegistryName("clay");
    }
//
//	@Override
//	@SideOnly(Side.CLIENT)
//	public int getColorFromItemStack(final ItemStack stack, final int p_82790_2_) {
//		final int damage = stack.getItemDamage();
//		return EnumFlowerColor.get(damage).getColor(false);
//	}

    @Override
    public String getItemStackDisplayName(final ItemStack stack) {
        return EnumFlowerColor.get(stack.getItemDamage()).getName() + " Clay";
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(final Item itemIn, final CreativeTabs tab, final List<ItemStack> list) {
        for (final EnumFlowerColor c : EnumFlowerColor.values()) {
            list.add(new ItemStack(this, 1, c.ordinal()));
        }
    }
//
//	@Override
//	@SideOnly(Side.CLIENT)
//	public void registerIcons(final IIconRegister register) {
//		this.itemIcon = Botany.proxy.getIcon(register, "clay");
//	}
}
