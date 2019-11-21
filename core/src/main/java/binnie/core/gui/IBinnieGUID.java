package binnie.core.gui;

import binnie.core.gui.minecraft.Window;
import binnie.core.network.IOrdinaled;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;

import javax.annotation.Nullable;

public interface IBinnieGUID extends IOrdinaled {
	@Nullable
	Window getWindow(EntityPlayer player, World world, int x, int y, int z, Side side);
}
