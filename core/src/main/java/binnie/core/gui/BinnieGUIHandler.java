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

	public BinnieGUIHandler(IBinnieGUID[] guiIds) {
		this.guiIds = guiIds;
	}

	@Override
	@Nullable
	public final Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		Window window = this.getWindow(id, player, world, x, y, z, Side.SERVER);
		if (window == null) {
			return null;
		}
		window.initialiseServer();
		return window.getContainer();
	}

	@Override
	@Nullable
	@SideOnly(Side.CLIENT)
	public final Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		if (!world.isRemote) {
			return this.getServerGuiElement(id, player, world, x, y, z);
		}
		Window window = this.getWindow(id, player, world, x, y, z, Side.CLIENT);
		if (window == null) {
			return null;
		}
		return window.getGui();
	}

	@Nullable
	public Window getWindow(int id, EntityPlayer player, World world, int x, int y, int z, Side side) {
		for (IBinnieGUID guid : guiIds) {
			if (guid.ordinal() == id) {
				return guid.getWindow(player, world, x, y, z, side);
			}
		}
		return null;
	}
}
