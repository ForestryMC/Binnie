package binnie.core.gui;

import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import net.minecraftforge.fml.relauncher.Side;

import binnie.core.craftgui.minecraft.Window;
import binnie.core.network.IOrdinaled;

public interface IBinnieGUID extends IOrdinaled {
	@Nullable
	Window getWindow(final EntityPlayer player, final World world, final int x, final int y, final int z, final Side side);
}
