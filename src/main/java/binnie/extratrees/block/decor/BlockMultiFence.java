package binnie.extratrees.block.decor;

import binnie.core.block.IBlockMetadata;
import binnie.core.block.TileEntityMetadata;
import binnie.core.util.I18N;
import binnie.extratrees.block.IPlankType;
import binnie.extratrees.block.PlankType;
import binnie.extratrees.block.WoodManager;
import java.util.List;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class BlockMultiFence extends BlockFence implements IBlockMetadata {
    public BlockMultiFence() {
        setBlockName("multifence");
    }

    @Override
    public void getSubBlocks(Item item, CreativeTabs tab, List list) {
        for (FenceType type : FenceType.values()) {
            list.add(WoodManager.getFence(PlankType.VanillaPlanks.SPRUCE, PlankType.VanillaPlanks.BIRCH, type, 1));
        }
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        return (FenceRenderer.layer == 0)
                ? getDescription(meta).getPlankType().getIcon()
                : getDescription(meta).getSecondaryPlankType().getIcon();
    }

    @Override
    public String getBlockName(ItemStack itemStack) {
        int meta = TileEntityMetadata.getItemDamage(itemStack);
        IPlankType type1 = getDescription(meta).getPlankType();
        IPlankType type2 = getDescription(meta).getSecondaryPlankType();
        FenceType type3 = getDescription(meta).getFenceType();
        boolean twoTypes = type1 != type2;
        return I18N.localise(
                "extratrees.block.woodslab.name" + (twoTypes ? "2" : ""),
                type3.getPrefix(),
                type1.getName(),
                type2.getName());
    }
}
