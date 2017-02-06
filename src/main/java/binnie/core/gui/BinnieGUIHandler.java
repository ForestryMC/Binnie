package binnie.core.gui;

import binnie.core.AbstractMod;
import binnie.core.BinnieCore;
import binnie.craftgui.minecraft.Window;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.relauncher.Side;

public final class BinnieGUIHandler implements IGuiHandler {
	private AbstractMod mod;

	public BinnieGUIHandler(final AbstractMod mod) {
		this.mod = mod;
	}

	@Override
	public final Object getServerGuiElement(final int id, final EntityPlayer player, final World world, final int x, final int y, final int z) {
		final Window window = this.getWindow(id, player, world, x, y, z, Side.SERVER);
		if (window == null) {
			return null;
		}
		window.initialiseServer();
		return window.getContainer();
	}

	@Override
	public final Object getClientGuiElement(final int id, final EntityPlayer player, final World world, final int x, final int y, final int z) {
		if (BinnieCore.getBinnieProxy().isSimulating(world)) {
			return this.getServerGuiElement(id, player, world, x, y, z);
		}
		final Window window = this.getWindow(id, player, world, x, y, z, Side.CLIENT);
		if (window == null) {
			return null;
		}
		return window.getGui();
	}

	public Window getWindow(final int id, final EntityPlayer player, final World world, final int x, final int y, final int z, final Side side) {
		for (final IBinnieGUID guid : this.mod.getGUIDs()) {
			if (guid.ordinal() == id) {
				return guid.getWindow(player, world, x, y, z, side);
			}
		}
		return null;
	}
}
