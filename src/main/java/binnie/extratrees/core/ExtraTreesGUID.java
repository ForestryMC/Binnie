package binnie.extratrees.core;

import binnie.core.craftgui.minecraft.Window;
import binnie.core.gui.IBinnieGUID;
import binnie.extratrees.kitchen.craftgui.WindowBottleRack;
import binnie.extratrees.machines.craftgui.WindowArboristDatabase;
import binnie.extratrees.machines.craftgui.WindowBrewery;
import binnie.extratrees.machines.craftgui.WindowDistillery;
import binnie.extratrees.machines.craftgui.WindowLepidopteristDatabase;
import binnie.extratrees.machines.craftgui.WindowLumbermill;
import binnie.extratrees.machines.craftgui.WindowPress;
import binnie.extratrees.machines.craftgui.WindowSetSquare;
import binnie.extratrees.machines.craftgui.WindowWoodworker;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;

public enum ExtraTreesGUID implements IBinnieGUID {
	Database,
	Woodworker,
	Lumbermill,
	DatabaseNEI,
	Incubator,
	MothDatabase,
	MothDatabaseNEI,
	Press,
	Brewery,
	Distillery,
	KitchenBottleRack,
	Infuser,
	SetSquare;

	@Override
	public Window getWindow(final EntityPlayer player, final World world, final int x, final int y, final int z, final Side side) {
		Window window = null;
		final TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));
		IInventory object = null;
		if (tileEntity instanceof IInventory) {
			object = (IInventory) tileEntity;
		}
		switch (this) {
			case Database:
			case DatabaseNEI: {
				window = WindowArboristDatabase.create(player, side, this != ExtraTreesGUID.Database);
				break;
			}
			case Woodworker: {
				window = WindowWoodworker.create(player, object, side);
				break;
			}
			case Lumbermill: {
				window = WindowLumbermill.create(player, object, side);
				break;
			}
			case KitchenBottleRack: {
				window = WindowBottleRack.create(player, object, side);
				break;
			}
			case Press: {
				window = WindowPress.create(player, object, side);
				break;
			}
			case Brewery: {
				window = WindowBrewery.create(player, object, side);
				break;
			}
			case Distillery: {
				window = WindowDistillery.create(player, object, side);
				break;
			}
			case MothDatabase:
			case MothDatabaseNEI: {
				window = WindowLepidopteristDatabase.create(player, side, this != ExtraTreesGUID.MothDatabase);
				break;
			}
			case SetSquare: {
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
