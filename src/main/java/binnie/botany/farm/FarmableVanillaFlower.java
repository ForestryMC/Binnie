package binnie.botany.farm;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import binnie.botany.api.IFlower;
import binnie.botany.api.IFlowerRoot;
import binnie.botany.core.BotanyCore;

public class FarmableVanillaFlower extends FarmableFlower {
	@Override
	public boolean isGermling(final ItemStack itemstack) {
		return BotanyCore.getFlowerRoot().getConversion(itemstack) != null;
	}

	@Override
	public boolean plantSaplingAt(final EntityPlayer player, final ItemStack germling, final World world, final BlockPos pos) {
		IFlowerRoot flowerRoot = BotanyCore.getFlowerRoot();
		IFlower flower = flowerRoot.getConversion(germling);
		return flowerRoot.plant(world, pos, flower, player.getGameProfile());
	}
}
