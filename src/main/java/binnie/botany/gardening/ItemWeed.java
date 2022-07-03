package binnie.botany.gardening;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemWeed extends ItemBlock {
    public ItemWeed(Block block) {
        super(block);
        setHasSubtypes(true);
        hasSubtypes = true;
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return BlockPlant.Type.values()[stack.getItemDamage()].getName();
    }

    @Override
    public int getMetadata(int meta) {
        return meta;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int damage) {
        return field_150939_a.getIcon(2, damage);
    }
}
