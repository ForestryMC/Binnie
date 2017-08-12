package binnie.core.machines.storage;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import binnie.core.BinnieCore;
import binnie.core.gui.BinnieCoreGUI;
import binnie.core.machines.Machine;
import binnie.core.machines.MachineComponent;
import binnie.core.machines.component.IInteraction;

class ComponentBinnieCoreGUI extends MachineComponent implements IInteraction.RightClick {
	private BinnieCoreGUI id;

	public ComponentBinnieCoreGUI(final Machine machine, final BinnieCoreGUI id) {
		super(machine);
		this.id = id;
	}

	@Override
	public void onRightClick(final World world, final EntityPlayer player, final BlockPos pos) {
		BinnieCore.getBinnieProxy().openGui(this.id, player, pos);
	}
}
