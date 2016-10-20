// 
// Decompiled by Procyon v0.5.30
// 

package binnie.botany.farm;

import binnie.botany.api.IFlower;
import binnie.botany.gardening.Gardening;
import net.minecraft.entity.player.EntityPlayer;
import binnie.botany.api.EnumFlowerStage;
import binnie.botany.core.BotanyCore;
import net.minecraft.item.ItemStack;
import forestry.api.farming.ICrop;
import binnie.botany.Botany;
import net.minecraft.world.World;
import forestry.api.farming.IFarmable;

public class FarmableFlower implements IFarmable
{
	@Override
	public boolean isSaplingAt(final World world, final int x, final int y, final int z) {
		return world.getBlock(x, y, z) == Botany.flower;
	}

	@Override
	public ICrop getCropAt(final World world, final int x, final int y, final int z) {
		return null;
	}

	@Override
	public boolean isGermling(final ItemStack itemstack) {
		final EnumFlowerStage stage = BotanyCore.speciesRoot.getStageType(itemstack);
		return stage == EnumFlowerStage.FLOWER || stage == EnumFlowerStage.SEED;
	}

	@Override
	public boolean isWindfall(final ItemStack itemstack) {
		return false;
	}

	@Override
	public boolean plantSaplingAt(final EntityPlayer player, final ItemStack germling, final World world, final int x, final int y, final int z) {
		final IFlower flower = BotanyCore.getFlowerRoot().getMember(germling);
		Gardening.plant(world, x, y, z, flower, player.getGameProfile());
		return true;
	}
}
