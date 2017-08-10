package binnie.core.machines.component;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IRender {
	interface DisplayTick {
		@SideOnly(Side.CLIENT)
		void onDisplayTick(World world, BlockPos pos, Random rand);
	}

	interface RandomDisplayTick {
		@SideOnly(Side.CLIENT)
		void onRandomDisplayTick(World world, BlockPos pos, Random rand);
	}

	interface Render {
		@SideOnly(Side.CLIENT)
		void renderInWorld(double xPos, double yPos, double zPos);
	}
}
