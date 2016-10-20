package binnie.botany.farm;

import forestry.api.farming.FarmDirection;
import forestry.api.farming.IFarmHousing;
import forestry.api.farming.IFarmLogic;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class FarmLogic implements IFarmLogic {
    World world;
    IFarmHousing housing;
    boolean isManual;

    public FarmLogic(final IFarmHousing housing) {
        this.housing = housing;
    }

    @Override
    public IFarmLogic setManual(final boolean flag) {
        this.isManual = flag;
        return this;
    }

    protected final boolean isAirBlock(final Vect position) {
        return this.world.isAirBlock(new BlockPos(position.x, position.y, position.z));
    }

    protected final boolean isWaterBlock(final Vect position) {
        return this.world.getBlockState(new BlockPos(position.x, position.y, position.z)).getBlock() == Blocks.WATER;
    }

    protected final IBlockState getBlock(final Vect position) {
        return this.world.getBlockState(new BlockPos(position.x, position.y, position.z));
    }

    protected final int getBlockMeta(final Vect position) {
        return this.getBlock(position).getBlock().getMetaFromState(getBlock(position));
    }

    protected final ItemStack getAsItemStack(final Vect position) {
        return new ItemStack(this.getBlock(position).getBlock(), 1, this.getBlockMeta(position));
    }

    protected final Vect translateWithOffset(final int x, final int y, final int z, final FarmDirection direction, final int step) {
        return new Vect(x + direction.getFacing().getFrontOffsetX() * step, y + direction.getFacing().getFrontOffsetY() * step, z + direction.getFacing().getFrontOffsetZ() * step);
    }

    protected final void setBlock(final Vect position, final Block block, final int meta) {
        this.world.setBlockState(new BlockPos(position.x, position.y, position.z), getBlock(position), 2);
    }
}
