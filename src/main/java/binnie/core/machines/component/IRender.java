package binnie.core.machines.component;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.world.World;

import java.util.Random;

public interface IRender {
	interface DisplayTick {
		@SideOnly(Side.CLIENT)
		void onDisplayTick(final World world, final int x, final int y, final int z, final Random rand);
	}

	interface RandomDisplayTick {
		@SideOnly(Side.CLIENT)
		void onRandomDisplayTick(final World world, final int x, final int y, final int p3, final Random rand);
	}

	interface Render {
		@SideOnly(Side.CLIENT)
		void renderInWorld(final RenderItem renderItem, final double x, final double y, final double z);
	}
}
