// 
// Decompiled by Procyon v0.5.30
// 

package binnie.genetics;

import net.minecraft.item.Item;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import binnie.extrabees.ExtraBees;
import binnie.genetics.item.GeneticsItems;
import net.minecraft.item.ItemStack;
import net.minecraft.creativetab.CreativeTabs;

public class CreativeTabGenetics extends CreativeTabs
{
	public static CreativeTabs instance;

	@Override
	public ItemStack getIconItemStack() {
		return GeneticsItems.EmptySerum.get(1);
	}

	public CreativeTabGenetics() {
		super("Genetics");
	}

	@Override
	public String getTranslatedTabLabel() {
		return this.getTabLabel();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getTabLabel() {
		return ExtraBees.proxy.localise("tab.genetics");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getTabIconItem() {
		return null;
	}

	static {
		CreativeTabGenetics.instance = new CreativeTabGenetics();
	}
}
