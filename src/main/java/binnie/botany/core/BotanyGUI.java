// 
// Decompiled by Procyon v0.5.30
// 

package binnie.botany.core;

import net.minecraft.tileentity.TileEntity;
import binnie.botany.craftgui.WindowBotanistDatabase;
import net.minecraft.inventory.IInventory;
import binnie.core.craftgui.minecraft.Window;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;
import binnie.core.gui.IBinnieGUID;

public enum BotanyGUI implements IBinnieGUID
{
	Database,
	DatabaseNEI;

	@Override
	public Window getWindow(final EntityPlayer player, final World world, final int x, final int y, final int z, final Side side) {
		Window window = null;
		final TileEntity tileEntity = world.getTileEntity(x, y, z);
		IInventory object = null;
		if (tileEntity instanceof IInventory) {
			object = (IInventory) tileEntity;
		}
		switch (this) {
		case Database:
		case DatabaseNEI: {
			window = WindowBotanistDatabase.create(player, side, this != BotanyGUI.Database);
			break;
		}
		}
		return window;
	}
}
