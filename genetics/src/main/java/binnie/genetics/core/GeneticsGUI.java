package binnie.genetics.core;

import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.minecraftforge.fml.relauncher.Side;

import binnie.core.gui.IBinnieGUID;
import binnie.core.gui.minecraft.Window;
import binnie.genetics.gui.analyst.WindowAnalyst;
import binnie.genetics.gui.punnett.WindowPunnettSquare;
import binnie.genetics.machine.acclimatiser.WindowAcclimatiser;
import binnie.genetics.machine.analyser.WindowAnalyser;
import binnie.genetics.machine.craftgui.WindowGeneBank;
import binnie.genetics.machine.craftgui.WindowGeneBankMaster;
import binnie.genetics.machine.genepool.WindowGenepool;
import binnie.genetics.machine.incubator.WindowIncubator;
import binnie.genetics.machine.inoculator.WindowInoculator;
import binnie.genetics.machine.isolator.WindowIsolator;
import binnie.genetics.machine.polymeriser.WindowPolymeriser;
import binnie.genetics.machine.sequencer.WindowSequencer;
import binnie.genetics.machine.splicer.WindowSplicer;

public enum GeneticsGUI implements IBinnieGUID {
	GENEPOOL(WindowGenepool::new),
	ISOLATOR(WindowIsolator::new),
	SEQUENCER(WindowSequencer::new),
	POLYMERISER(WindowPolymeriser::new),
	INOCULATOR(WindowInoculator::new),
	GENE_BANK(WindowGeneBank::new),
	ANALYSER(WindowAnalyser::new),
	INCUBATOR(WindowIncubator::new),
	DATABASE(WindowGeneBank::new),
	DATABASE_MASTER(WindowGeneBankMaster::new),
	ACCLIMATISER(WindowAcclimatiser::new),
	SPLICER(WindowSplicer::new),
	ANALYST(WindowAnalyst.create(false, false)),
	REGISTRY(WindowAnalyst.create(true, false)),
	MASTER_REGISTRY(WindowAnalyst.create(true, true)),
	PUNNETT_SQUARE(WindowPunnettSquare::create);

	private final WindowFactory windowFactory;

	GeneticsGUI(WindowFactory windowFactory) {
		this.windowFactory = windowFactory;
	}

	public Window getWindow(EntityPlayer player, @Nullable IInventory object, Side side) {
		return windowFactory.create(player, object, side);
	}

	@Override
	public Window getWindow(EntityPlayer player, World world, int x, int y, int z, Side side) {
		TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));
		IInventory object = null;
		if (tileEntity instanceof IInventory) {
			object = (IInventory) tileEntity;
		}
		return getWindow(player, object, side);
	}

	public interface WindowFactory {
		Window create(EntityPlayer player, @Nullable IInventory inventory, Side side);
	}
}
