package binnie.extratrees.block;

import binnie.core.block.ItemMetadata;
import binnie.core.block.TileEntityMetadata;
import binnie.extratrees.ExtraTrees;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemETSlab extends ItemMetadata {
    private boolean isFullBlock;
    private BlockETSlab theHalfSlab;
    private BlockETSlab doubleSlab;

    public ItemETSlab(Block block) {
        super(block);
        theHalfSlab = (BlockETSlab) ExtraTrees.blockSlab;
        doubleSlab = (BlockETSlab) ExtraTrees.blockDoubleSlab;
        isFullBlock = (block != theHalfSlab);
        setMaxDamage(0);
        setHasSubtypes(true);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean onItemUse(
            ItemStack stack,
            EntityPlayer player,
            World world,
            int x,
            int y,
            int z,
            int meta,
            float hitX,
            float hitY,
            float hitZ) {
        if (isFullBlock) {
            return super.onItemUse(stack, player, world, x, y, z, meta, hitX, hitY, hitZ);
        }
        if (stack.stackSize == 0 || !player.canPlayerEdit(x, y, z, meta, stack)) {
            return false;
        }

        Block i1 = world.getBlock(x, y, z);
        int j1 = world.getBlockMetadata(x, y, z);
        int tileMeta = TileEntityMetadata.getTileMetadata(world, x, y, z);
        int k1 = j1 & 0x7;
        boolean flag = (j1 & 0x8) != 0x0;

        if (((meta == 1 && !flag) || (meta == 0 && flag)) && i1 == theHalfSlab && tileMeta == stack.getItemDamage()) {
            if (world.checkNoEntityCollision(doubleSlab.getCollisionBoundingBoxFromPool(world, x, y, z))
                    && world.setBlock(x, y, z, doubleSlab, k1, 3)) {
                TileEntityMetadata tile = TileEntityMetadata.getTile(world, x, y, z);
                if (tile != null) {
                    tile.setTileMetadata(tileMeta, true);
                }
                stack.stackSize--;
            }
            return true;
        }
        return func_77888_a(stack, player, world, x, y, z, meta)
                || super.onItemUse(stack, player, world, x, y, z, meta, hitX, hitY, hitZ);
    }

    private boolean func_77888_a(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side) {
        if (side == 0) {
            y--;
        } else if (side == 1) {
            y++;
        } else if (side == 2) {
            z--;
        } else if (side == 3) {
            z++;
        } else if (side == 4) {
            x--;
        } else if (side == 5) {
            x++;
        }

        Block i1 = world.getBlock(x, y, z);
        int j1 = world.getBlockMetadata(x, y, z);
        int k1 = j1 & 0x7;
        int tileMeta = TileEntityMetadata.getTileMetadata(world, x, y, z);

        if (i1 == theHalfSlab && tileMeta == stack.getItemDamage()) {
            if (world.checkNoEntityCollision(doubleSlab.getCollisionBoundingBoxFromPool(world, x, y, z))
                    && world.setBlock(x, y, z, doubleSlab, k1, 3)) {
                TileEntityMetadata tile = TileEntityMetadata.getTile(world, x, y, z);
                if (tile != null) {
                    tile.setTileMetadata(tileMeta, false);
                    world.markBlockForUpdate(x, y, z);
                }
                stack.stackSize--;
            }
            return true;
        }
        return false;
    }
}
