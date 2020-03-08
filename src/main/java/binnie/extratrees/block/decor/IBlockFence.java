package binnie.extratrees.block.decor;

import binnie.core.Mods;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.world.IBlockAccess;

public interface IBlockFence {

    /**
     * Test if the specified block is a fence
     *
     * @param block the block to test
     * @return {@code true} if the specified block is a fence
     */
    static boolean isFence(Block block) {
        return block == ((ItemBlock) Mods.forestry.item("fences")).field_150939_a
                || block instanceof BlockFence && block != Blocks.nether_brick_fence
                || block instanceof BlockFenceGate;
    }

    boolean canConnectFenceTo(IBlockAccess world, int x, int y, int z);
}
