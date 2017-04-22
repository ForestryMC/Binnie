package binnie.core.gui;

import binnie.core.network.*;
import binnie.craftgui.minecraft.*;
import cpw.mods.fml.relauncher.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;

public interface IBinnieGUID extends IOrdinaled {
	Window getWindow(EntityPlayer player, World world, int x, int y, int z, Side side);
}
