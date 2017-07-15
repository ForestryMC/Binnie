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
import binnie.botany.api.IFlowerRoot;
import binnie.botany.core.BotanyCore;
import binnie.botany.flower.TileEntityFlower;

public class FarmableFlower implements IFarmable {
	@Override
	public boolean isSaplingAt(World world, BlockPos pos) {
		return world.getBlockState(pos) == Botany.flower;
	}

	@Nullable
	@Override
	public ICrop getCropAt(World world, BlockPos pos, IBlockState blockState) {
		IFlower flower = null;
		if (world.getTileEntity(pos) instanceof TileEntityFlower) {
			flower = ((TileEntityFlower) world.getTileEntity(pos)).getFlower();
		}

		// TODO Look at TileEntityFlower::onShear logic
		if (flower != null && flower.getAge() > 1) {
			IFlowerRoot flowerRoot = BotanyCore.getFlowerRoot();
			ItemStack mature = flowerRoot.getMemberStack(flower, EnumFlowerStage.FLOWER);
			ItemStack seed = flowerRoot.getMemberStack(flower, EnumFlowerStage.SEED);
			if (mature != null && seed != null) {
				world.setBlockState(pos, Blocks.AIR.getDefaultState(), 2);
				return new FlowerCrop(pos, mature, seed);
			}
		}
		return null;
	}

	@Override
	public boolean isGermling(ItemStack itemstack) {
		EnumFlowerStage stage = BotanyCore.getFlowerRoot().getType(itemstack);
		return stage == EnumFlowerStage.FLOWER || stage == EnumFlowerStage.SEED;
	}

	@Override
	public boolean isWindfall(ItemStack itemstack) {
		return false;
	}

	@Override
	public boolean plantSaplingAt(EntityPlayer player, ItemStack germling, World world, BlockPos pos) {
		IFlowerRoot flowerRoot = BotanyCore.getFlowerRoot();
		IFlower flower = flowerRoot.getMember(germling);
		flowerRoot.plant(world, pos, flower, player.getGameProfile());
		return true;
	}
}
