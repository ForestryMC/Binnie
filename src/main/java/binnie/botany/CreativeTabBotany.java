package binnie.botany;

import binnie.core.util.I18N;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CreativeTabBotany extends CreativeTabs {
    public static CreativeTabs instance = new CreativeTabBotany();

    public CreativeTabBotany() {
        super("Botany");
    }

    @Override
    public ItemStack getIconItemStack() {
        return new ItemStack(Blocks.red_flower, 1, 5);
    }

    @Override
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
    public Item getTabIconItem() {
        return Item.getItemFromBlock(Blocks.yellow_flower);
    }
}
