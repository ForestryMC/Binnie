package binnie.botany;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.util.I18N;

public class CreativeTabBotany extends CreativeTabs {
	public static final CreativeTabs INSTANCE = new CreativeTabBotany();

	public CreativeTabBotany() {
		super("Botany");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getTabLabel() {
		return I18N.localise("botany.tab.botany");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ItemStack createIcon() {
		return new ItemStack(Blocks.RED_FLOWER, 1, 5);
	}
}
