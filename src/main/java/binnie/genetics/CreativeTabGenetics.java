package binnie.genetics;

import binnie.genetics.item.GeneticsItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CreativeTabGenetics extends CreativeTabs {
	public static CreativeTabs instance = new CreativeTabGenetics();

	public CreativeTabGenetics() {
		super("Genetics");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getTranslatedTabLabel() {
		return this.getTabLabel();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getTabLabel() {
		return Genetics.proxy.localise("tab.genetics");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ItemStack getTabIconItem() {
		return GeneticsItems.EMPTY_SERUM.get(1);
	}

}
