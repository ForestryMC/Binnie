package binnie.extratrees.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.minecraftforge.fml.relauncher.Side;

import binnie.core.gui.IBinnieGUID;
import binnie.core.gui.minecraft.Window;
import binnie.extratrees.gui.database.WindowArboristDatabase;
import binnie.extratrees.gui.database.WindowLepidopteristDatabase;
import binnie.extratrees.kitchen.craftgui.WindowBottleRack;
import binnie.extratrees.machines.brewery.window.WindowBrewery;
import binnie.extratrees.machines.designer.window.WindowDesigner;
import binnie.extratrees.machines.distillery.window.WindowDistillery;
import binnie.extratrees.machines.fruitpress.window.WindowPress;
import binnie.extratrees.machines.lumbermill.window.WindowLumbermill;

public enum ExtraTreesGUID implements IBinnieGUID {
	DATABASE,
	WOODWORKER,
	LUMBERMILL,
	DATABASE_MASTER,
	INCUBATOR,
	MOTH_DATABASE,
	MOTH_DATABASE_MASTER,
	PRESS,
	BREWERY,
	DISTILLERY,
	KITCHEN_BOTTLE_RACK,
	INFUSER,
	SET_SQUARE;

	@Override
	public Window getWindow(final EntityPlayer player, final World world, final int x, final int y, final int z, final Side side) {
		Window window = null;
		final TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));
		IInventory object = null;
		if (tileEntity instanceof IInventory) {
			object = (IInventory) tileEntity;
		}
		switch (this) {
			case DATABASE:
			case DATABASE_MASTER: {
				window = WindowArboristDatabase.create(player, side, this != ExtraTreesGUID.DATABASE);
				break;
			}
			case WOODWORKER: {
				window = WindowDesigner.create(player, object, side);
				break;
			}
			case LUMBERMILL: {
				window = WindowLumbermill.create(player, object, side);
				break;
			}
			case KITCHEN_BOTTLE_RACK: {
				window = WindowBottleRack.create(player, object, side);
				break;
			}
			case PRESS: {
				window = WindowPress.create(player, object, side);
				break;
			}
			case BREWERY: {
				window = WindowBrewery.create(player, object, side);
				break;
			}
			case DISTILLERY: {
				window = WindowDistillery.create(player, object, side);
				break;
			}
			case MOTH_DATABASE:
			case MOTH_DATABASE_MASTER: {
				window = WindowLepidopteristDatabase.create(player, side, this != ExtraTreesGUID.MOTH_DATABASE);
				break;
			}
			case SET_SQUARE: {
				window = WindowSetSquare.create(player, world, x, y, z, side);
				break;
			}
			default: {
				break;
			}
		}
		return window;
	}
}
