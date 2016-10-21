package binnie.botany.farm;

import binnie.botany.Botany;
import binnie.botany.api.EnumFlowerStage;
import binnie.botany.api.IFlower;
import binnie.botany.core.BotanyCore;
import binnie.botany.gardening.Gardening;
import forestry.api.farming.ICrop;
import forestry.api.farming.IFarmable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class FarmableFlower implements IFarmable {
    @Override
    public boolean isSaplingAt(final World world, final BlockPos pos) {
        return world.getBlockState(pos) == Botany.flower;
    }

    @Nullable
    @Override
    public ICrop getCropAt(World world, BlockPos pos, IBlockState blockState) {
        return null;
    }

    @Override
    public boolean isGermling(final ItemStack itemstack) {
        final EnumFlowerStage stage = BotanyCore.speciesRoot.getType(itemstack);
        return stage == EnumFlowerStage.FLOWER || stage == EnumFlowerStage.SEED;
    }

    @Override
    public boolean isWindfall(final ItemStack itemstack) {
        return false;
    }

    @Override
    public boolean plantSaplingAt(final EntityPlayer player, final ItemStack germling, final World world, final BlockPos pos) {
        final IFlower flower = BotanyCore.getFlowerRoot().getMember(germling);
        Gardening.plant(world, pos, flower, player.getGameProfile());
        return true;
    }


}
