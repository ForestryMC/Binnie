package binnie.genetics;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.util.I18N;
import binnie.genetics.item.GeneticsItems;

public class CreativeTabGenetics extends CreativeTabs {
	public static final CreativeTabs INSTANCE = new CreativeTabGenetics();

	public CreativeTabGenetics() {
		super("Genetics");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getTranslatedTabLabel() {
		return getTabLabel();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getTabLabel() {
		return I18N.localise("genetics.tab.genetics");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ItemStack getTabIconItem() {
		return GeneticsItems.EMPTY_SERUM.get(1);
	}
}
