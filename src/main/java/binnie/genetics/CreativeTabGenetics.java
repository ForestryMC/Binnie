package binnie.genetics;

import binnie.extrabees.ExtraBees;
import binnie.genetics.item.GeneticsItems;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CreativeTabGenetics extends CreativeTabs {
	public static CreativeTabs instance;

	static {
		CreativeTabGenetics.instance = new CreativeTabGenetics();
	}

	public CreativeTabGenetics() {
		super("Genetics");
	}

	@Override
	public ItemStack getIconItemStack() {
		return GeneticsItems.EmptySerum.get(1);
	}

	@Override
	public String getTranslatedTabLabel() {
		return getTabLabel();
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
}
