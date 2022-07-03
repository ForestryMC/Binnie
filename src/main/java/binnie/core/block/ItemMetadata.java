package binnie.core.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class ItemMetadata extends ItemBlock {
    public ItemMetadata(Block block) {
        super(block);
    }

    @Override
    public int getMetadata(int damage) {
        return 0;
    }

    @Override
    public boolean placeBlockAt(
            ItemStack stack,
            EntityPlayer player,
            World world,
            int x,
            int y,
            int z,
            int side,
            float hitX,
            float hitY,
            float hitZ,
            int metadata) {
        Block block = field_150939_a;
        if (!(block instanceof IBlockMetadata)) {
            return false;
        }

        int placedMeta = ((IBlockMetadata) block).getPlacedMeta(stack, world, x, y, z, ForgeDirection.values()[side]);
        if (placedMeta < 0 || !world.setBlock(x, y, z, block, metadata, 3)) {
            return false;
        }

        if (world.getBlock(x, y, z) == block) {
            TileEntityMetadata tile = TileEntityMetadata.getTile(world, x, y, z);
            if (tile != null) {
                tile.setTileMetadata(placedMeta, false);
            }
            block.onBlockPlacedBy(world, x, y, z, player, stack);
            block.onPostBlockPlaced(world, x, y, z, metadata);
        }
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getItemStackDisplayName(ItemStack par1ItemStack) {
        return ((IBlockMetadata) field_150939_a).getBlockName(par1ItemStack);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemStack, EntityPlayer player, List tooltip, boolean advanced) {
        ((IBlockMetadata) field_150939_a).addBlockTooltip(itemStack, tooltip);
    }

    @Override
    public IIcon getIconFromDamage(int damage) {
        return field_150939_a.getIcon(1, damage);
    }
}
