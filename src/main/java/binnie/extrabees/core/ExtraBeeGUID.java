// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extrabees.core;

import net.minecraft.tileentity.TileEntity;
import binnie.extrabees.gui.WindowAlvearyHatchery;
import binnie.extrabees.gui.WindowAlvearyStimulator;
import binnie.extrabees.gui.WindowAlvearyFrame;
import binnie.extrabees.gui.WindowAlvearyMutator;
import binnie.extrabees.gui.database.WindowApiaristDatabase;
import net.minecraft.inventory.IInventory;
import binnie.craftgui.minecraft.Window;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;
import binnie.core.gui.IBinnieGUID;

public enum ExtraBeeGUID implements IBinnieGUID
{
	Database,
	DatabaseNEI,
	AlvearyMutator,
	AlvearyFrame,
	AlvearyStimulator,
	PunnettSquare,
	AlvearyHatchery;

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
			window = WindowApiaristDatabase.create(player, side, this != ExtraBeeGUID.Database);
			break;
		}
		case AlvearyMutator: {
			window = WindowAlvearyMutator.create(player, object, side);
			break;
		}
		case AlvearyFrame: {
			window = WindowAlvearyFrame.create(player, object, side);
			break;
		}
		case AlvearyStimulator: {
			window = WindowAlvearyStimulator.create(player, object, side);
			break;
		}
		case AlvearyHatchery: {
			window = WindowAlvearyHatchery.create(player, object, side);
			break;
		}
		}
		return window;
	}
}
