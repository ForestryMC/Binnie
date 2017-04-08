package binnie.core.gui;

import binnie.core.craftgui.minecraft.Window;
import binnie.core.network.IOrdinaled;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;

import javax.annotation.Nullable;

public interface IBinnieGUID extends IOrdinaled {
	@Nullable
	Window getWindow(final EntityPlayer player, final World world, final int x, final int y, final int z, final Side side);
}
