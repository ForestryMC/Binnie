package binnie.genetics.core;

import binnie.core.gui.IBinnieGUID;
import binnie.craftgui.genetics.machine.WindowAcclimatiser;
import binnie.craftgui.genetics.machine.WindowAnalyser;
import binnie.craftgui.genetics.machine.WindowGeneBank;
import binnie.craftgui.genetics.machine.WindowGeneBankNEI;
import binnie.craftgui.genetics.machine.WindowGenepool;
import binnie.craftgui.genetics.machine.WindowIncubator;
import binnie.craftgui.genetics.machine.WindowInoculator;
import binnie.craftgui.genetics.machine.WindowIsolator;
import binnie.craftgui.genetics.machine.WindowPolymeriser;
import binnie.craftgui.genetics.machine.WindowSequencer;
import binnie.craftgui.genetics.machine.WindowSplicer;
import binnie.craftgui.minecraft.Window;
import binnie.genetics.gui.WindowAnalyst;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;

import javax.annotation.Nullable;

public enum GeneticsGUI implements IBinnieGUID {
	Genepool(WindowGenepool::new),
	Isolator(WindowIsolator::new),
	Sequencer(WindowSequencer::new),
	Replicator(WindowPolymeriser::new),
	Inoculator(WindowInoculator::new),
	GeneBank(WindowGeneBank::new),
	Analyser(WindowAnalyser::new),
	Incubator(WindowIncubator::new),
	Database(WindowGeneBank::new),
	DatabaseNEI(WindowGeneBankNEI::new),
	Acclimatiser(WindowAcclimatiser::new),
	Splicer(WindowSplicer::new),
	Analyst(WindowAnalyst.create(false, false)),
	Registry(WindowAnalyst.create(true, false)),
	MasterRegistry(WindowAnalyst.create(true, true));

	private final WindowFactory windowFactory;

	public interface WindowFactory {
		Window create(final EntityPlayer player, @Nullable final IInventory inventory, final Side side);
	}

	GeneticsGUI(final WindowFactory windowFactory) {
		this.windowFactory = windowFactory;
	}

	public Window getWindow(final EntityPlayer player, @Nullable final IInventory object, final Side side) {
		return this.windowFactory.create(player, object, side);
	}

	@Override
	public Window getWindow(final EntityPlayer player, final World world, final int x, final int y, final int z, final Side side) {
		final TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));
		IInventory object = null;
		if (tileEntity instanceof IInventory) {
			object = (IInventory) tileEntity;
		}

		return getWindow(player, object, side);
	}
}
