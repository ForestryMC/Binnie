package binnie.core.gui;

import binnie.core.network.IOrdinaled;
import binnie.craftgui.minecraft.Window;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public interface IBinnieGUID extends IOrdinaled {
	Window getWindow(final EntityPlayer player, final World world, final int x, final int y, final int z, final Side side);
}
