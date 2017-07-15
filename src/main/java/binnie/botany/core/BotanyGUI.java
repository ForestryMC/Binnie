package binnie.botany.core;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.minecraftforge.fml.relauncher.Side;

import binnie.botany.craftgui.WindowBotanistDatabase;
import binnie.core.craftgui.minecraft.Window;
import binnie.core.gui.IBinnieGUID;

public enum BotanyGUI implements IBinnieGUID {
	DATABASE,
	DATABASE_NEI;
	
	@Override
	public Window getWindow(EntityPlayer player, World world, int x, int y, int z, Side side) {
		Window window = null;
		TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));
		IInventory object = null;
		if (tileEntity instanceof IInventory) {
			object = (IInventory) tileEntity;
		}
		switch (this) {
			case DATABASE:
			case DATABASE_NEI: {
				window = WindowBotanistDatabase.create(player, side, this != BotanyGUI.DATABASE);
				break;
			}
		}
		return window;
	}
}
