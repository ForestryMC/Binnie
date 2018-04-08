package binnie.core.gui;

import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.gui.minecraft.Window;

public final class BinnieGUIHandler implements IGuiHandler {
	private final IBinnieGUID[] guiIds;

	public BinnieGUIHandler(final IBinnieGUID[] guiIds) {
		this.guiIds = guiIds;
	}

	@Override
	@Nullable
	public final Object getServerGuiElement(final int id, final EntityPlayer player, final World world, final int x, final int y, final int z) {
		final Window window = this.getWindow(id, player, world, x, y, z, Side.SERVER);
		if (window == null) {
			return null;
		}
		window.initialiseServer();
		return window.getContainer();
	}

	@Override
	@Nullable
	@SideOnly(Side.CLIENT)
	public final Object getClientGuiElement(final int id, final EntityPlayer player, final World world, final int x, final int y, final int z) {
		if (!world.isRemote) {
			return this.getServerGuiElement(id, player, world, x, y, z);
		}
		final Window window = this.getWindow(id, player, world, x, y, z, Side.CLIENT);
		if (window == null) {
			return null;
		}
		return window.getGui();
	}

	@Nullable
	public Window getWindow(final int id, final EntityPlayer player, final World world, final int x, final int y, final int z, final Side side) {
		for (final IBinnieGUID guid : guiIds) {
			if (guid.ordinal() == id) {
				return guid.getWindow(player, world, x, y, z, side);
			}
		}
		return null;
	}
}
