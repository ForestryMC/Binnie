// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extrabees.products;

import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import binnie.extrabees.ExtraBees;
import net.minecraft.client.renderer.texture.IIconRegister;

public class ItemHoneyCrystalEmpty extends ItemHoneyCrystal
{
	public ItemHoneyCrystalEmpty() {
		this.setMaxDamage(0);
		this.setMaxStackSize(64);
		this.setUnlocalizedName("honeyCrystalEmpty");
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(final IIconRegister register) {
		this.itemIcon = ExtraBees.proxy.getIcon(register, "honeyCrystalEmpty");
	}

	@Override
	public String getItemStackDisplayName(final ItemStack i) {
		return ExtraBees.proxy.localise("item.honeycrystal.empty");
	}
}
