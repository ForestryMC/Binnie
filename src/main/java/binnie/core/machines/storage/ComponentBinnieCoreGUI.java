package binnie.core.machines.storage;

import binnie.core.*;
import binnie.core.gui.*;
import binnie.core.machines.*;
import binnie.core.machines.component.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;

class ComponentBinnieCoreGUI extends MachineComponent implements IInteraction.RightClick {
	private BinnieCoreGUI id;

	public ComponentBinnieCoreGUI(Machine machine, BinnieCoreGUI id) {
		super(machine);
		this.id = id;
	}

	@Override
	public void onRightClick(World world, EntityPlayer player, int x, int y, int z) {
		BinnieCore.proxy.openGui(id, player, x, y, z);
	}
}
