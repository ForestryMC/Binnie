// 
// Decompiled by Procyon v0.5.30
// 

package binnie.botany.items;

import binnie.botany.Botany;
import net.minecraft.client.renderer.texture.IIconRegister;
import java.util.List;
import net.minecraft.creativetab.CreativeTabs;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import binnie.botany.genetics.EnumFlowerColor;
import net.minecraft.item.ItemStack;
import binnie.botany.CreativeTabBotany;
import net.minecraft.item.Item;

public class ItemClay extends Item
{
	public ItemClay() {
		this.setUnlocalizedName("clay");
		this.setHasSubtypes(true);
		this.setCreativeTab(CreativeTabBotany.instance);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(final ItemStack stack, final int p_82790_2_) {
		final int damage = stack.getItemDamage();
		return EnumFlowerColor.get(damage).getColor(false);
	}

	@Override
	public String getItemStackDisplayName(final ItemStack stack) {
		return EnumFlowerColor.get(stack.getItemDamage()).getName() + " Clay";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(final Item p_150895_1_, final CreativeTabs p_150895_2_, final List list) {
		for (final EnumFlowerColor c : EnumFlowerColor.values()) {
			list.add(new ItemStack(this, 1, c.ordinal()));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(final IIconRegister register) {
		this.itemIcon = Botany.proxy.getIcon(register, "clay");
	}
}
