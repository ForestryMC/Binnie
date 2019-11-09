package binnie.extratrees;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.Mods;
import binnie.core.util.I18N;

public class CreativeTabCarpentry extends CreativeTabs {
	public static final CreativeTabs INSTANCE = new CreativeTabCarpentry();

	public CreativeTabCarpentry() {
		super("Carpentry");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getTranslationKey() {
		return getTabLabel();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getTabLabel() {
		return I18N.localise("extratrees.tab.carpentry");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ItemStack createIcon() {
		return new ItemStack(Mods.Forestry.block("carpenter"));
	}
}
