package binnie.extrabees.core;

import binnie.core.gui.IBinnieGUID;
import binnie.craftgui.minecraft.Window;
import binnie.extrabees.gui.WindowAlvearyFrame;
import binnie.extrabees.gui.WindowAlvearyHatchery;
import binnie.extrabees.gui.WindowAlvearyMutator;
import binnie.extrabees.gui.WindowAlvearyStimulator;
import binnie.extrabees.gui.database.WindowApiaristDatabase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;

import javax.annotation.Nullable;

public enum ExtraBeeGUID implements IBinnieGUID {
	Database,
	DatabaseNEI,
	AlvearyMutator,
	AlvearyFrame,
	AlvearyStimulator,
	PunnettSquare,
	AlvearyHatchery;

	@Override
	@Nullable
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
			default: {
				break;
			}
		}
		return window;
	}
}
