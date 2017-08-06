package binnie.botany.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import net.minecraftforge.fml.relauncher.Side;

import binnie.botany.gui.database.WindowBotanistDatabase;
import binnie.core.gui.IBinnieGUID;
import binnie.core.gui.minecraft.Window;

public enum BotanyGUI implements IBinnieGUID {
	DATABASE,
	DATABASE_MASTER;

	@Override
	public Window getWindow(EntityPlayer player, World world, int x, int y, int z, Side side) {
		Window window = null;
		switch (this) {
			case DATABASE:
			case DATABASE_MASTER: {
				window = WindowBotanistDatabase.create(player, side, this != BotanyGUI.DATABASE);
				break;
			}
		}
		return window;
	}
}
