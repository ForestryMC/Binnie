package binnie.botany.farm;

import forestry.api.farming.FarmDirection;
import forestry.api.farming.IFarmHousing;
import forestry.api.farming.IFarmLogic;
import forestry.core.utils.vect.Vect;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract class FarmLogic implements IFarmLogic {
    protected World world;
    protected IFarmHousing housing;
    protected boolean isManual;

    public FarmLogic(IFarmHousing housing) {
        this.housing = housing;
    }

    @Override
    public IFarmLogic setManual(boolean flag) {
        isManual = flag;
        return this;
    }

    protected boolean isAirBlock(Vect position) {
        return world.isAirBlock(position.x, position.y, position.z);
    }

    protected boolean isWaterBlock(Vect position) {
        return world.getBlock(position.x, position.y, position.z) == Blocks.water;
    }

    protected Block getBlock(Vect position) {
        return world.getBlock(position.x, position.y, position.z);
    }

    protected int getBlockMeta(Vect position) {
        return world.getBlockMetadata(position.x, position.y, position.z);
    }

    protected ItemStack getAsItemStack(Vect position) {
        return new ItemStack(getBlock(position), 1, getBlockMeta(position));
    }

    protected Vect translateWithOffset(int x, int y, int z, FarmDirection direction, int step) {
        return new Vect(
                x + direction.getForgeDirection().offsetX * step,
                y + direction.getForgeDirection().offsetY * step,
                z + direction.getForgeDirection().offsetZ * step);
    }

    protected void setBlock(Vect position, Block block, int meta) {
        world.setBlock(position.x, position.y, position.z, block, meta, 2);
    }
}
