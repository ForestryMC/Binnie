package binnie.genetics.core;

import binnie.core.craftgui.minecraft.Window;
import binnie.core.gui.IBinnieGUID;
import binnie.genetics.gui.WindowAnalyst;
import binnie.genetics.gui.bee.database.WindowApiaristDatabase;
import binnie.genetics.gui.punnett.WindowPunnettSquare;
import binnie.genetics.machine.acclimatiser.WindowAcclimatiser;
import binnie.genetics.machine.analyser.WindowAnalyser;
import binnie.genetics.machine.craftgui.WindowGeneBank;
import binnie.genetics.machine.craftgui.WindowGeneBankNEI;
import binnie.genetics.machine.genepool.WindowGenepool;
import binnie.genetics.machine.incubator.WindowIncubator;
import binnie.genetics.machine.inoculator.WindowInoculator;
import binnie.genetics.machine.isolator.WindowIsolator;
import binnie.genetics.machine.polymeriser.WindowPolymeriser;
import binnie.genetics.machine.sequencer.WindowSequencer;
import binnie.genetics.machine.splicer.WindowSplicer;
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
	MasterRegistry(WindowAnalyst.create(true, true)),
	BeeDatabase((player, inventory, side) -> WindowApiaristDatabase.create(player, side, false)),
	BeeDatabaseNEI((player, inventory, side) -> WindowApiaristDatabase.create(player, side, true)),
	PunnettSquare(WindowPunnettSquare::create);

	private final WindowFactory windowFactory;

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

	public interface WindowFactory {
		Window create(final EntityPlayer player, @Nullable final IInventory inventory, final Side side);
	}
}
