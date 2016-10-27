package binnie.botany;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CreativeTabBotany extends CreativeTabs {
	public static CreativeTabs instance = new CreativeTabBotany();

	@Override
	public ItemStack getIconItemStack() {
		return new ItemStack(Blocks.RED_FLOWER, 1, 5);
	}

	public CreativeTabBotany() {
		super("Botany");
	}

	@Override
	public String getTranslatedTabLabel() {
		return this.getTabLabel();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getTabLabel() {
		return Botany.proxy.localise("tab.botany");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getTabIconItem() {
		return Item.getItemFromBlock(Blocks.YELLOW_FLOWER);
	}

}
