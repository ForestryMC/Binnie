package binnie.botany.farm;

import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import forestry.api.farming.ICrop;
import forestry.api.farming.IFarmable;

import binnie.botany.Botany;
import binnie.botany.api.EnumFlowerStage;
import binnie.botany.api.IFlower;
import binnie.botany.core.BotanyCore;
import binnie.botany.flower.TileEntityFlower;
import binnie.botany.gardening.Gardening;

public class FarmableFlower implements IFarmable {
	@Override
	public boolean isSaplingAt(final World world, final BlockPos pos) {
		return world.getBlockState(pos) == Botany.flower;
	}

	@Nullable
	@Override
	public ICrop getCropAt(World world, BlockPos pos, IBlockState blockState) {
		IFlower flower = null;
		if (world.getTileEntity(pos) instanceof TileEntityFlower) {
			flower = ((TileEntityFlower) world.getTileEntity(pos)).getFlower();
		}
		// Look at TileEntityFlower::onShear logic
		if (flower != null && flower.getAge() > 1) {
			ItemStack mature = BotanyCore.getFlowerRoot().getMemberStack(flower, EnumFlowerStage.FLOWER);
			ItemStack seed = BotanyCore.getFlowerRoot().getMemberStack(flower, EnumFlowerStage.SEED);
			if (mature != null && seed != null) {
				world.setBlockState(pos, Blocks.AIR.getDefaultState(), 2);
				return new FlowerCrop(pos, mature, seed);
			}
		}

		return null;
	}

	@Override
	public boolean isGermling(final ItemStack itemstack) {
		final EnumFlowerStage stage = BotanyCore.getFlowerRoot().getType(itemstack);
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
