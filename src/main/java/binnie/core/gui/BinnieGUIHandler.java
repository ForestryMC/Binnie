package binnie.core.gui;

import binnie.core.AbstractMod;
import binnie.core.BinnieCore;
import binnie.core.craftgui.minecraft.Window;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class BinnieGUIHandler implements IGuiHandler {
	private AbstractMod mod;

	public BinnieGUIHandler(AbstractMod mod) {
		this.mod = mod;
	}

	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		Window window = getWindow(id, player, world, x, y, z, Side.SERVER);
		if (window == null) {
			return null;
		}
		window.initialiseServer();
		return window.getContainer();
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		if (BinnieCore.proxy.isSimulating(world)) {
			return getServerGuiElement(id, player, world, x, y, z);
		}

		Window window = getWindow(id, player, world, x, y, z, Side.CLIENT);
		if (window == null) {
			return null;
		}
		return window.getGui();
	}

	public Window getWindow(int id, EntityPlayer player, World world, int x, int y, int z, Side side) {
		for (IBinnieGUID guid : mod.getGUIDs()) {
			if (guid.ordinal() == id) {
				return guid.getWindow(player, world, x, y, z, side);
			}
		}
		return null;
	}
}
