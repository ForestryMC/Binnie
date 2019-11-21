package binnie.botany.gui;

import binnie.botany.gui.database.WindowBotanistDatabase;
import binnie.core.gui.IBinnieGUID;
import binnie.core.gui.minecraft.Window;
import binnie.design.gui.WindowDesigner;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;

public enum BotanyGUI implements IBinnieGUID {
	DATABASE,
	DATABASE_MASTER,
	TILEWORKER;

	@Override
	public Window getWindow(EntityPlayer player, World world, int x, int y, int z, Side side) {
		Window window = null;
		final TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));
		IInventory inventory = null;
		if (tileEntity instanceof IInventory) {
			inventory = (IInventory) tileEntity;
		}
		switch (this) {
			case DATABASE:
			case DATABASE_MASTER: {
				window = WindowBotanistDatabase.create(player, side, this != BotanyGUI.DATABASE);
				break;
			}
			case TILEWORKER: {
				window = WindowDesigner.create(player, inventory, side);
				break;
			}
		}
		return window;
	}
}
