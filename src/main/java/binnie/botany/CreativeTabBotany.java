package binnie.botany;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.util.I18N;

public class CreativeTabBotany extends CreativeTabs {
	public static CreativeTabs instance = new CreativeTabBotany();

	public CreativeTabBotany() {
		super("Botany");
	}

	@Override
	public ItemStack getIconItemStack() {
		return new ItemStack(Blocks.RED_FLOWER, 1, 5);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getTranslatedTabLabel() {
		return getTabLabel();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getTabLabel() {
		return I18N.localise("botany.tab.botany");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ItemStack getTabIconItem() {
		return new ItemStack(Blocks.YELLOW_FLOWER);
	}
}
