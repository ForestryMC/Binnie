package binnie.core.machines.component;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.world.World;

public interface IRender {
    interface DisplayTick {
        @SideOnly(Side.CLIENT)
        void onDisplayTick(World world, int x, int y, int z, Random rand);
    }

    interface RandomDisplayTick {
        @SideOnly(Side.CLIENT)
        void onRandomDisplayTick(World world, int x, int y, int p3, Random rand);
    }

    interface Render {
        @SideOnly(Side.CLIENT)
        void renderInWorld(RenderItem renderItem, double x, double y, double z);
    }
}
