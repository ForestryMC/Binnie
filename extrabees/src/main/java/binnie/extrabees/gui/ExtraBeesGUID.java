package binnie.extrabees.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.minecraftforge.fml.relauncher.Side;

import binnie.core.gui.IBinnieGUID;
import binnie.core.gui.minecraft.Window;
import binnie.extrabees.genetics.gui.database.WindowApiaristDatabase;
import binnie.extrabees.machines.frame.window.WindowAlvearyFrame;
import binnie.extrabees.machines.hatchery.window.WindowAlvearyHatchery;
import binnie.extrabees.machines.mutator.window.WindowAlvearyMutator;
import binnie.extrabees.machines.stimulator.window.WindowAlvearyStimulator;

public enum ExtraBeesGUID implements IBinnieGUID {
	ALVEARY_MUTATOR,
	ALVEARY_FRAME,
	ALVEARY_STIMULATOR,
	ALVEARY_HATCHERY,
	DATABASE,
	DATABASE_MASTER;

	@Override
	public Window getWindow(final EntityPlayer player, final World world, final int x, final int y, final int z, final Side side) {
		Window window = null;
		final TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));
		IInventory object = null;
		if (tileEntity instanceof IInventory) {
			object = (IInventory) tileEntity;
		}
		switch (this) {
			case ALVEARY_MUTATOR: {
				window = WindowAlvearyMutator.create(player, object, side);
				break;
			}
			case ALVEARY_FRAME: {
				window = WindowAlvearyFrame.create(player, object, side);
				break;
			}
			case ALVEARY_STIMULATOR: {
				window = WindowAlvearyStimulator.create(player, object, side);
				break;
			}
			case ALVEARY_HATCHERY: {
				window = WindowAlvearyHatchery.create(player, object, side);
				break;
			}
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
