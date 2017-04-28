package binnie.botany.genetics;

import binnie.botany.Botany;
import binnie.botany.CreativeTabBotany;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemEncyclopedia extends Item {
	boolean reinforced;

	public ItemEncyclopedia(boolean reinforced) {
		this.reinforced = false;
		this.reinforced = reinforced;
		setCreativeTab(CreativeTabBotany.instance);
		setUnlocalizedName("encylopedia" + (reinforced ? "Iron" : ""));
		setMaxStackSize(1);
		setMaxDamage(reinforced ? 480 : 120);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister register) {
		itemIcon = Botany.proxy.getIcon(register, "encylopedia" + (reinforced ? "Iron" : ""));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int par1) {
		return itemIcon;
	}

	@Override
	public String getItemStackDisplayName(ItemStack i) {
		return (reinforced ? "Reinforced " : "") + "Florist's Encyclopaedia";
	}
}
