package binnie.botany.items;

import binnie.botany.Botany;
import binnie.botany.CreativeTabBotany;
import binnie.botany.genetics.EnumFlowerColor;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemClay extends Item {
	public ItemClay() {
		setUnlocalizedName("clay");
		setHasSubtypes(true);
		setCreativeTab(CreativeTabBotany.instance);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack stack, int index) {
		int damage = stack.getItemDamage();
		return EnumFlowerColor.get(damage).getColor(false);
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return EnumFlowerColor.get(stack.getItemDamage()).getName() + " Clay";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		for (EnumFlowerColor c : EnumFlowerColor.values()) {
			list.add(new ItemStack(this, 1, c.ordinal()));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister register) {
		itemIcon = Botany.proxy.getIcon(register, "clay");
	}
}
