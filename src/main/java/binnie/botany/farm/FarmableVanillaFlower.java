// 
// Decompiled by Procyon v0.5.30
// 

package binnie.botany.farm;

import binnie.botany.api.IFlower;
import binnie.botany.gardening.Gardening;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;
import binnie.botany.core.BotanyCore;
import net.minecraft.item.ItemStack;

public class FarmableVanillaFlower extends FarmableFlower
{
	@Override
	public boolean isGermling(final ItemStack itemstack) {
		return BotanyCore.getFlowerRoot().getConversion(itemstack) != null;
	}

	@Override
	public boolean plantSaplingAt(final EntityPlayer player, final ItemStack germling, final World world, final int x, final int y, final int z) {
		final IFlower flower = BotanyCore.getFlowerRoot().getConversion(germling);
		return Gardening.plant(world, x, y, z, flower, player.getGameProfile());
	}
}
