package binnie.botany.farm;

import binnie.botany.api.IFlower;
import binnie.botany.core.BotanyCore;
import binnie.botany.gardening.Gardening;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class FarmableVanillaFlower extends FarmableFlower {
    @Override
    public boolean isGermling(ItemStack itemstack) {
        return BotanyCore.getFlowerRoot().getConversion(itemstack) != null;
    }

    @Override
    public boolean plantSaplingAt(EntityPlayer player, ItemStack germling, World world, int x, int y, int z) {
        IFlower flower = BotanyCore.getFlowerRoot().getConversion(germling);
        return Gardening.plant(world, x, y, z, flower, player.getGameProfile());
    }
}
