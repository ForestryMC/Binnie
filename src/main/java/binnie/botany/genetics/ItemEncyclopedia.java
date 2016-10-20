// 
// Decompiled by Procyon v0.5.30
// 

package binnie.botany.genetics;

import net.minecraft.item.ItemStack;
import binnie.botany.CreativeTabBotany;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import binnie.botany.Botany;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;

public class ItemEncyclopedia extends Item
{
	boolean reinforced;

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(final IIconRegister register) {
		this.itemIcon = Botany.proxy.getIcon(register, "encylopedia" + (this.reinforced ? "Iron" : ""));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(final int par1) {
		return this.itemIcon;
	}

	public ItemEncyclopedia(final boolean reinforced) {
		this.reinforced = false;
		this.reinforced = reinforced;
		this.setCreativeTab(CreativeTabBotany.instance);
		this.setUnlocalizedName("encylopedia" + (reinforced ? "Iron" : ""));
		this.setMaxStackSize(1);
		this.setMaxDamage(reinforced ? 480 : 120);
	}

	@Override
	public String getItemStackDisplayName(final ItemStack i) {
		return (this.reinforced ? "Reinforced " : "") + "Florist's Encyclopaedia";
	}
}
