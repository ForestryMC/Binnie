package binnie.core.gui;

import binnie.core.network.IOrdinaled;
import binnie.craftgui.minecraft.Window;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public interface IBinnieGUID extends IOrdinaled {
	Window getWindow(EntityPlayer player, World world, int x, int y, int z, Side side);
}
