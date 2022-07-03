package binnie.extrabees.worldgen;

import binnie.core.util.I18N;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBeehive extends ItemBlock {
    public ItemBeehive(Block block) {
        super(block);
        setMaxDamage(0);
        setHasSubtypes(true);
        setCreativeTab(CreativeTabs.tabBlock);
    }

    @Override
    public int getMetadata(int i) {
        return i;
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        for (int i = 0; i < 4; ++i) {
            list.add(new ItemStack(this, 1, i));
        }
    }

    @Override
    public String getItemStackDisplayName(ItemStack itemStack) {
        return I18N.localise("extrabees.block.hive." + itemStack.getItemDamage());
    }
}
