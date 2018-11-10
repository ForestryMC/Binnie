package binnie.extrabees.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import net.minecraftforge.fml.relauncher.Side;

import binnie.core.gui.IBinnieGUID;
import binnie.core.gui.minecraft.Window;
import binnie.extrabees.genetics.gui.database.WindowApiaristDatabase;

public enum ExtraBeesGUID implements IBinnieGUID {
	DATABASE,
	DATABASE_MASTER;

	@Override
	public Window getWindow(final EntityPlayer player, final World world, final int x, final int y, final int z, final Side side) {
		Window window = null;
		switch (this) {
			case DATABASE: {
				window = WindowApiaristDatabase.create(player, side, false);
				break;
			}
			case DATABASE_MASTER: {
				window = WindowApiaristDatabase.create(player, side, true);
				break;
			}
			default: {
				break;
			}
		}
		return window;
	}
}
